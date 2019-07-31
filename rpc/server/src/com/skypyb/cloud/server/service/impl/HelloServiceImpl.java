package com.skypyb.cloud.server.service.impl;

import com.skypyb.cloud.server.service.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String msg) {
        System.out.println(msg);
        return "hello" + msg;
    }
}
