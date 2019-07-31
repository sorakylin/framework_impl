package com.skypyb.cloud.server;

import com.skypyb.cloud.server.core.Server;
import com.skypyb.cloud.server.core.ServerCenter;
import com.skypyb.cloud.server.service.HelloService;
import com.skypyb.cloud.server.service.impl.HelloServiceImpl;

public class Application {
    public static void main(String[] args) {

        new Thread(() -> {
            Server server = new ServerCenter(9999);
            //注册
            server.register(HelloService.class, HelloServiceImpl.class);
            server.start();
        }).start();


    }
}
