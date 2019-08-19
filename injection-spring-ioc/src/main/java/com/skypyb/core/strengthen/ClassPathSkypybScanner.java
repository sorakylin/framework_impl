package com.skypyb.core.strengthen;

import com.skypyb.core.factorybean.SkypybFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

/**
 * 扫描器
 * 继承Spring提供的类路径扫描器
 */
public class ClassPathSkypybScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathSkypybScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    /**
     * 注册过滤器
     * 设置要注册哪些类，我这是无脑全扫描出来
     */
    public void registerFilters() {
        boolean flag = true;

        //to do

        if (flag) this.addIncludeFilter((reader, readerFactory) -> true);

    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {

        //这一步调用父类的doScan() 会给注册进BeanDefinitionMap里边去
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            this.logger.warn("No Skypyb mapper was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            this.processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }


    /**
     * 专门用来处理 BeanDefinition
     *
     * @param beanDefinitions
     */
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {

        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitions) {

            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();

            //设置构造参数
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());

            //设置为我自定义的FactoryBean
            beanDefinition.setBeanClass(SkypybFactoryBean.class);
        }


    }

}
