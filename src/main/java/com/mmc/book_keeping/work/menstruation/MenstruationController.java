package com.mmc.book_keeping.work.menstruation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@Controller
public class MenstruationController {

    @Autowired
    private MenstruationService menstruationService;

    @PostMapping("/book/menstruation/list")
    public void listMensruation(HttpServletResponse response) throws IOException {
       response.getWriter().print(menstruationService.listForTimeRecord());
    }

    @PostMapping(value = "book/menstruation/ins")
    public String ins(String mensDate,int cycle){
        menstruationService.insMens(Menstruation.builder().mensDate(LocalDate.parse(mensDate)).cycle(cycle).build());
        return "menstruation_detail";
    }
}
