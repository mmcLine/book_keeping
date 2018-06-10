package com.mmc.book_keeping.tradeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeTypeService {

    @Autowired
    private TradeRespository tradeRespository;

    /**
     * 获取所有的消费类型集合
     * @return
     */
    public List<TradeType> getAllTradeType(){
        List<TradeType> tradeTypeList = tradeRespository.findAll();
        return  tradeTypeList;
    }

    public TradeType getTradeTypeById(Integer id){
        return tradeRespository.findById(id).orElse(new TradeType());
    }
}
