package com.mmc.book_keeping.tradeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TradeTypeController {

    @Autowired
    private  TradeTypeService tradeTypeService;

    @PostMapping("book/tradeType/getAllType")
    @ResponseBody
    public List<TradeType> getAllTradeType(){
      return  tradeTypeService.getAllTradeType();
    }
}
