package com.skypyb.core.proxy;

import com.skypyb.core.ann.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SkypybInvocationHandler implements InvocationHandler {


    /**
     * 假装执行了对应的 JDBC 逻辑
     * 我这只是简单地打印了一下注解里的sql语句
     *
     * @return proxy obj
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Select annotation = method.getAnnotation(Select.class);
        if (annotation == null) return null;


        Class<?> returnType = method.getReturnType();
        System.out.println("-------> " + annotation.value());


        return null;
    }

}
