package com.mmc.book_keeping.work.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mmc.book_keeping.work.tradeType.TradeTypeService;
import com.mmc.book_keeping.utils.ExcelUtil;
import com.mmc.book_keeping.utils.SpringContextUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class OrderController {

    @Autowired
    private TradeTypeService tradeTypeService;

    @Autowired
    private OrderService orderService;


    @Autowired
    private ExcelUtil excelUtil;

    Logger logger= LoggerFactory.getLogger(OrderController.class);

    @GetMapping("book/order/getTop6")
    public String getTop6(HttpServletRequest request) {
        logger.info("查询订单前六笔数据");
        List<Order> top6 = orderService.getTop6();
        BigDecimal top6Sum=BigDecimal.ZERO;
        for (Order order:top6
             ) {
            top6Sum=top6Sum.add(new BigDecimal(String.valueOf(order.getAmt())));
        }
        request.setAttribute("orderTop",top6);
        request.setAttribute("orderTopSum",top6Sum);
        logger.info("查询消费金额");
        request.setAttribute("totalAmt",orderService.getTotalAmt());
        logger.info("查询消费笔数");
        request.setAttribute("totalCount",orderService.getTotalCount());
        logger.info("查询消费同比增长");
        request.setAttribute("yearByYear",orderService.getYearByYear()+"%");
        logger.info("查询消费环比增长");
        request.setAttribute("monthRaingRatio",orderService.getRingRatio()+"%");
        return "index";
    }


    /**
     * 首页折线图
     * @return
     */
    @PostMapping("book/order/brokenOrder")
    @ResponseBody
    public JSONObject getBrokenData(){
        return orderService.getBrokenOrders();
    }


    @PostMapping("book/order/orderDetail")
    @ResponseBody
    public JSONObject orderDetail(int pageNo,String startDate,String endDate){
        if(StringUtils.isEmpty(startDate)||StringUtils.isEmpty(endDate)){
            return orderService.getAllOrder(pageNo,LocalDate.parse("2016-01-01"),LocalDate.now());
        }else{
            return orderService.getAllOrder(pageNo,LocalDate.parse(startDate),LocalDate.parse(endDate));
        }
    }

    /**
     * 定向到新增页面
     * @param model
     * @return
     */
    @GetMapping("book/order/ins")
    public String  getTradeType(Model model){
        model.addAttribute("tradeTypes",tradeTypeService.getAllTradeType());
        return "order_ins";
    }


    /**
     * 页面的新增和保存
     * @param name
     * @param tradeType
     * @param amt
     * @param content
     * @param trade_date
     * @param pkey
     * @return
     */
    @RequestMapping("book/order/save")
    public String orderSave(String name, Integer tradeType, double amt,
                            String content, String trade_date,Integer pkey){
        Order order=new Order();
            order.setPkey(pkey);
            order.setName(name);
            order.setAmt(amt);
            order.setTradeType(tradeTypeService.getTradeTypeById(tradeType));
            order.setContent(content);
            order.setUser(SpringContextUtil.getLoginUser().getUser());
            order.setTrade_date(LocalDate.parse(trade_date));
            order.setFamilyer(SpringContextUtil.getLoginFamiler());
            orderService.orderIns(order);
        return "redirect:/order_detail.html";
    }

    /**
     * 修改页面的回显
     * @param pkey
     * @param model
     * @return
     */
    @GetMapping("book/order/upd/{pkey}")
    public String orderUpd1(@PathVariable  Integer pkey,Model model){
        model.addAttribute("order",orderService.getOrderById(pkey));
        model.addAttribute("tradeTypes",tradeTypeService.getAllTradeType());
        return "order_ins";
    }
    @GetMapping("book/order/del/{pkey}")
    public String orderDel(@PathVariable  Integer pkey){
        orderService.orderDel(pkey);
        return "redirect:/order_detail.html";
    }

    @GetMapping("book/order/exportOrder")
    public void exportOrder(String start, String end, HttpServletResponse response) throws IOException {
        List<Order> orderList=null;
        if(StringUtils.isEmpty(start)||StringUtils.isEmpty(end)){
            orderList= orderService.getAllOrderByDate(LocalDate.parse("2016-01-01"), LocalDate.now());
        }else {
            orderList= orderService.getAllOrderByDate(LocalDate.parse(start), LocalDate.parse(end));
        }
        List<BeanWrapper> collect = orderList.stream().map(order -> PropertyAccessorFactory.forBeanPropertyAccess(order)).collect(toList());
        Workbook workbook = ExcelUtil.exportCustom(collect);
        if (workbook != null) {
            // 将新建的XLS对象写入输出流中，下载的文件名使用默认的ISO编码
            response.reset();
            response.setContentType("applicationnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String("订单明细.xlsx".getBytes("gb2312"), "ISO-8859-1"));
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            workbook.close();
        }
    }

    /**
     * 月消费柱状图
     */
    @GetMapping("book/order/order_month")
    @ResponseBody
    public void orderMonth(HttpServletResponse response) throws IOException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("result", orderService.orderMonth());
        JSON.writeJSONString(response.getWriter(),jsonObject);
    }
}
