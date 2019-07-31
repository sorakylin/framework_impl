package com.skypyb.cloud.server.core;

import com.skypyb.cloud.server.connection.ClientConnectionProcessor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 服务中心的具体实现
 */
public class ServerCenter implements Server {

    // 服务端所有可供客户端访问的接口，都注册进该map中
    //key: 接口名  value: 真正的实现
    private static HashMap<String, Class> serviceRegister = new HashMap();

    private static ThreadPoolExecutor clientConnectionPool =
            new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private volatile boolean isRunning = false;

    private int port;


    public ServerCenter(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        isRunning = true;

        ServerSocket server = null;

        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress(this.port));
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        //标准的BIO多线程socket处理流程
        while (isRunning) {
            try {
                Socket socket = server.accept();
                clientConnectionPool.execute(new ClientConnectionProcessor(socket, this));
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }

    }

    @Override
    public void stop() {
        isRunning = false;
        clientConnectionPool.shutdown();
        serviceRegister.clear();
    }

    @Override
    public void register(Class service, Class serviceImpl) {
        serviceRegister.put(service.getSimpleName(), serviceImpl);
    }

    @Override
    public Class getService(String serviceName) {
        return serviceRegister.get(serviceName);
    }
}
