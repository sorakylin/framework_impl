package com.skypyb.cloud.server.core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

//服务中心
public interface Server {

    void start();

    void stop();

    void register(Class service, Class serviceImpl);

    Class getService(String serviceName);
}
