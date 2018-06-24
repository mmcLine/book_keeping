package com.mmc.book_keeping.work.menstruation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mmc.book_keeping.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MenstruationService {

    @Autowired
    private MenstruationRespository menstruationRespository;

    //新增记录,新增时要更改上次记录的nextDate为当前日期
    public void insMens(Menstruation menstruation){
        menstruation.setNextMensDate(getNextDate(menstruation));

        //查询最后一条记录
        Menstruation lastRecord = menstruationRespository.findFourRecord(SpringContextUtil.getLoginFamilerPkey(), 0, 1).get(0);
        lastRecord.setNextMensDate(menstruation.getMensDate());
        menstruationRespository.save(lastRecord);
        menstruationRespository.save(menstruation);
    }


    //最近四次的大姨妈日期加预计下次的日期
    public JSONObject listForTimeRecord(){
        List<Menstruation> dateList=menstruationRespository.findFourRecord(SpringContextUtil.getLoginFamilerPkey(),0,4);
        JSONArray jsonArray=new JSONArray();
        for(int i=dateList.size()-1;i>=0;i--){
            Menstruation menstruation = dateList.get(i);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("mensDate",menstruation.getMensDate());
            jsonObject.put("cycle", menstruation.getMensDate().until(menstruation.getNextMensDate(),ChronoUnit.DAYS));
            jsonArray.add(jsonObject);
            //放入预计的一次
            if(i==0){
                JSONObject temp=new JSONObject();
                temp.put("mensDate", menstruation.getNextMensDate());
                temp.put("cycle",menstruation.getCycle());
                jsonArray.add(temp);
            }
        }
        JSONObject result=new JSONObject();
        result.put("result", jsonArray);
        return result;
        }



    //获取下一次的日期
    public LocalDate getNextDate(Menstruation menstruation){
        if(menstruation.getCycle()>0){
            return menstruation.getMensDate().plusDays(menstruation.getCycle());
        }else {
           return menstruation.getMensDate().plusDays(25);
        }
    }

    public static void main(String[] args) {
    }
}
