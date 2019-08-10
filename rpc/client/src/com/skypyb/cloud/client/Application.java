package com.skypyb.cloud.client;

import com.skypyb.cloud.client.core.Client;
import com.skypyb.cloud.client.service.HelloService;

import java.net.InetSocketAddress;

public class Application {

    public static void main(String[] args) throws ClassNotFoundException {
        HelloService helloService = Client.getRemoteProxyObj(HelloService.class,
                new InetSocketAddress(9999));

        String msg = helloService.hello("你好好");

        System.out.println(msg);

    }
}
