package com.mmc.book_keeping.work.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mmc.book_keeping.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepsitory orderRepsitory;

    /**
     * 查询前六笔数据
     * @return
     */
    public List<Order> getTop6(){
        PageRequest pageable=PageRequest.of(0,6,new Sort(Sort.Direction.DESC,"pkey"));
        Specification specification=new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("familyer"),SpringContextUtil.getLoginFamiler().getPkey());
            }
        };
        Page<Order> orderPage = orderRepsitory.findAll(specification,pageable);
        return orderPage.getContent();
    }

    /**
     * 查询总金额
     * @return
     */
    public String getTotalAmt(){
      return   orderRepsitory.getTotalAmt(SpringContextUtil.getLoginFamiler().getPkey());
    }


    /**
     * 查询总笔数
     * @return
     */
    public  int  getTotalCount(){
        return orderRepsitory.getTotalCount(SpringContextUtil.getLoginFamiler().getPkey());
    }


    /**
     * 获取月环比，该月和上月同一时间段
     * 当期环比增长（下降）率(%)=[(当期消费/上期消费)-1]*100%
     * @return
     */
    public String getRingRatio(){
        //获取当天和上月当天
        LocalDate now=LocalDate.now();
        LocalDate firstDate=now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate preMonthDay = now.minusMonths(1);
        LocalDate preMonthFirstDay=preMonthDay.with(TemporalAdjusters.firstDayOfMonth());
        String ratio0 = orderRepsitory.getRatio2(firstDate, now,SpringContextUtil.getLoginFamiler().getPkey());
        String ratio1=orderRepsitory.getRatio2(preMonthFirstDay, preMonthDay,SpringContextUtil.getLoginFamiler().getPkey());
        if(ratio0==null){
            return "无限大";
        }else  if(ratio1==null){
            return "无";
        }else{
           return forwardPercent((Double.parseDouble(ratio0)/Double.parseDouble(ratio1))+"");
       }
    }

    /**
     * 查询同比，今年与去年比
     * @return
     */
    public String getYearByYear(){
        LocalDate now=LocalDate.now();
        LocalDate firstDate=now.with(TemporalAdjusters.firstDayOfYear());
        LocalDate preYearDay = now.minusYears(1);
        LocalDate preYearFirstDay=preYearDay.with(TemporalAdjusters.firstDayOfYear());
        Double ratio0 =  Double.parseDouble(orderRepsitory.getRatio2(firstDate, now,SpringContextUtil.getLoginFamilerPkey()));
        Double ratio1 = Double.parseDouble(orderRepsitory.getRatio2(preYearFirstDay, preYearDay,SpringContextUtil.getLoginFamilerPkey()));
        if(ratio1==0){
            return "无";
        }else{
            return forwardPercent((ratio0/ratio1)+"");
        }
    }

    /**
     * 返回本周，上周，大上周的消费额
     * @return
     */
    public JSONObject getBrokenOrders(){
        //获取本周的七天的日期
        LocalDate now=LocalDate.now();
        List<LocalDate[]> weekDays = getWeekDay();
        JSONObject resultJson=new JSONObject();
        for (int i=0;i<weekDays.size();i++){
            LocalDate[] arrayDate=weekDays.get(i);
            JSONArray jsonArray=new JSONArray(7);
            for (int j=0;j<arrayDate.length;j++){
                double amt=orderRepsitory.getSumByTrade_date(arrayDate[j],SpringContextUtil.getLoginFamilerPkey());
                JSONObject tempJson=new JSONObject();
                tempJson.put("amt",amt);
                jsonArray.add(tempJson);
            }
            resultJson.put("series"+i,jsonArray);
        }
        return resultJson;
    }


    /**
     * 将小数位转为百分数
     * @param number
     * @return
     */
    private  String forwardPercent(String number){
        BigDecimal bigDecimal=new BigDecimal(number);
        BigDecimal result=  bigDecimal.subtract(BigDecimal.ONE).multiply(BigDecimal.valueOf(100));
        return result.setScale(2,BigDecimal.ROUND_HALF_EVEN).toString();
    }

    /**
     * 获取本周的日期，上周的日期，上上周的日期
     * @return
     */
    private List<LocalDate[]> getWeekDay(){
        //获取今天
        //判断是星期几n
        //减去n-1天  得到本周start
        //减去1天得到上周end
        //减去7天得到上周start
        LocalDate currentWeekEnd=LocalDate.now();
        int n = currentWeekEnd.getDayOfWeek().getValue()-1;
        LocalDate currentWeekStart=currentWeekEnd.minusDays(n);
        LocalDate preWeekStart=currentWeekStart.minusWeeks(1);
        LocalDate prepreWeekStart=preWeekStart.minusWeeks(1);
        List<LocalDate[]> list=new ArrayList<>();
        LocalDate[] array1=new LocalDate[n+1];
        LocalDate[] array2=new LocalDate[7];
        LocalDate[] array3=new LocalDate[7];
        for(int i=0;i<=n;i++){
            array1[i]=currentWeekStart.plusDays(i);
        }
        for (int i=0;i<7;i++){
            array2[i]=preWeekStart.plusDays(i);
        }
        for (int i=0;i<7;i++){
            array3[i]=prepreWeekStart.plusDays(i);
        }
        list.add(array1);
        list.add(array2);
        list.add(array3);
        return list;
    }

    private int size=10;


    public List<Order> getAllOrderByDate(LocalDate start,LocalDate end){
       return orderRepsitory.findByTrade_dateBetween(start,end,SpringContextUtil.getLoginFamilerPkey());
    }

    /**
     * 日期筛选条件加分页查询
     * @param pageNo
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject getAllOrder(int pageNo,LocalDate startDate,LocalDate endDate) {
        Pageable pageable = PageRequest.of(pageNo, size, new Sort(Sort.Direction.DESC, "pkey"));
        Specification specification=new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.between(root.get("trade_date"),startDate,endDate);
            }
        };
        Page<Order> ordersPage = orderRepsitory.findAll(specification,pageable);
        List<Order> content = ordersPage.getContent();
        JSONArray jsonArray=new JSONArray(content.size());
        for (Order order : content) {
            if(StringUtils.isEmpty(order.getContent())){
                order.setContent("");
            }
            String temp = JSON.toJSONString(order);
            jsonArray.add(temp);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",jsonArray);
        return jsonObject;
    }

    /**
     * 消费月账单柱状图
     *
     */
    public JSONArray orderMonth(){
        List<Object[]> orderMonth = orderRepsitory.getOrderMonth(SpringContextUtil.getLoginFamilerPkey());
        JSONArray itemJsonArray=new JSONArray();
        for(int i=(orderMonth.size()-12);i<orderMonth.size();i++){
            Object[] order=orderMonth.get(i);
            JSONObject tempjson=new JSONObject();
            tempjson.put("month",order[0].toString());
            tempjson.put("sumamt",order[1].toString());
            itemJsonArray.add(tempjson);
        }
        return itemJsonArray;
    }

    /**
     *订单新增
     */
    public void orderIns(Order order){
        orderRepsitory.save(order);
    }

    public Order getOrderById(Integer id){
        return orderRepsitory.findById(id).orElse(new Order());
    }

    public void orderDel(Integer id){
        orderRepsitory.deleteById(id);
    }

    public static void main(String[] args) {
        Double.parseDouble("0");
    }
}
