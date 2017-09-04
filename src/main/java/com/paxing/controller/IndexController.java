package com.paxing.controller;

import com.google.common.collect.Lists;
import com.paxing.entity.User;
import com.paxing.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author wayne-zhang
 * @date 2017/7/26 20:51.
 */
@Controller
@RequestMapping("index")
public class IndexController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        //根据id获取
        System.out.println(userService.getUserById(1));
        List<User> aList = Lists.newArrayList();
        aList.add(new User("2", "1", 1));
        aList.add(new User("2", "3", 4));
        //批量插入
        userService.batchSaveUser(aList);

        List<User> list = userService.listAllUser();
        for (User u : list) {
            System.out.println(u);
        }
        //更新
        User user = new User(1, "小名", "", 23);

        LOG.info(userService.updateUser(user) + "");

        return "index";
    }
}
