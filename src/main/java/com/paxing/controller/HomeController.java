package com.paxing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wayne-zhang
 * @date 2017/7/21 16:22.
 */
@Controller
@RequestMapping("home")
public class HomeController {
    // 添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    //映射一个action
    @RequestMapping("/index")
    public String index() {
        logger.info("返回index.jsp");
        return "home1";
    }
}
