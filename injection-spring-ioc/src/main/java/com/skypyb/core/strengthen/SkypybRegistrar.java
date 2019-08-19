package com.skypyb.core.strengthen;

import com.skypyb.core.ann.EnableSkypyb;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;


public class SkypybRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        //通过 AnnotationMetadata 得到要扫描哪些包下的类
        AnnotationAttributes annAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableSkypyb.class.getName()));
        String[] enableSkypybValues = annAttrs.getStringArray("value");

        //用自己自定义的扫描器扫描
        ClassPathSkypybScanner scanner = new ClassPathSkypybScanner(beanDefinitionRegistry);

        if (this.resourceLoader != null) scanner.setResourceLoader(this.resourceLoader);
        scanner.registerFilters();
        scanner.doScan(enableSkypybValues);
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


//    private String beanNameProcess(String simpleName) {
//        char c = simpleName.charAt(0);
//        return simpleName.replaceFirst("" + c, "" + (char) (c + 32));
//    }


}
