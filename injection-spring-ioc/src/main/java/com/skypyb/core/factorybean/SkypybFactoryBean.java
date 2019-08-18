package com.skypyb.core.factorybean;

import com.skypyb.core.proxy.SkypybInvocationHandler;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class SkypybFactoryBean<T> implements FactoryBean<T> {

    private Class<T> clazz;

    public SkypybFactoryBean(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, new SkypybInvocationHandler());
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }
}
