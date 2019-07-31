package com.skypyb.cloud.client.core;

import jdk.jfr.events.SocketReadEvent;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    /**
     * 获取代表服务端接口的动态代理对象
     *
     * @param service 请求的接口
     * @param address 要请求的IP:PORT
     * @param <T>
     * @return 返回动态代理对象
     */
    public static <T> T getRemoteProxyObj(Class<T> service, InetSocketAddress address) {
        //类加载器直接用传入Class的加载器
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                (proxy, method, args) -> {

                    ObjectInputStream ois = null;
                    ObjectOutputStream os = null;

                    try {
                        Socket socket = new Socket();
                        socket.connect(address);

                        //发送给服务端, 需要方法名、方法参数、参数类型、参数值
                        os = new ObjectOutputStream(socket.getOutputStream());
                        os.writeUTF(service.getSimpleName());
                        os.writeUTF(method.getName());
                        os.writeObject(method.getParameterTypes());
                        os.writeObject(args);


                        //等待服务端处理完毕，接收服务端返回的值
                        ois = new ObjectInputStream(socket.getInputStream());
                        return ois.readObject();
                    } catch (Exception e) {
                        return null;
                    } finally {
                        try {
                            if (ois != null) ois.close();
                            if (os != null) ois.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
