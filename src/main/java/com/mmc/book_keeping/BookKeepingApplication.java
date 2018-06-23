package com.mmc.book_keeping;

import com.mmc.book_keeping.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BookKeepingApplication {

    public static void main(String[] args) {
        ApplicationContext context=SpringApplication.run(BookKeepingApplication.class, args);
        SpringContextUtil.setApplicationContext(context);
    }
}
