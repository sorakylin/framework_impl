package com.skypyb.cloud.server.connection;

import com.skypyb.cloud.server.core.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 客户端连接处理
 * 进行具体方法调用
 */
public class ClientConnectionProcessor implements Runnable {

    private Socket client;
    private Server server;

    public ClientConnectionProcessor(Socket client, Server server) {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream os = null;
        try {
            //向外暴露端口
            ois = new ObjectInputStream(client.getInputStream());

            //根据和客户端的约定 逐个接收参数
            String serviceName = ois.readUTF();
            String methodName = ois.readUTF();
            Class[] paramTypes = (Class[]) ois.readObject();
            Object[] args = (Object[]) ois.readObject();

            //根据客户端请求找到具体接口
            Class serviceClass = server.getService(serviceName);
            Method method = serviceClass.getMethod(methodName, paramTypes);
            Object result = method.invoke(serviceClass.newInstance(), args);

            //将方法执行完毕后的返回值返回给客户端
            os = new ObjectOutputStream(client.getOutputStream());
            os.writeObject(result);
        } catch (IOException | InstantiationException | InvocationTargetException |
                NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) ois.close();
                if (os != null) ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
