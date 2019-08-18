package com.skypyb.core.strengthen;

import com.skypyb.core.factorybean.SkypybFactoryBean;
import com.skypyb.dao.OneTestDao;
import com.skypyb.dao.TwoTestDao;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class SkypybRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        Class[] classes = scan();

        for (Class clazz : classes) {

            //使用Spring提供的建造器建造出一个BeanDefinition
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) builder.getBeanDefinition();

            //先保存下这个刚创建的BeanDefinition的类名
            String beanClassName = beanDefinition.getBeanClassName();

            //设置为我自定义的FactoryBean
            beanDefinition.setBeanClass(SkypybFactoryBean.class);

            //设置构造参数
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);

            //注册
            beanDefinitionRegistry.registerBeanDefinition(beanNameProcess(clazz.getSimpleName()), beanDefinition);
        }


    }


    /**
     * 假装扫描
     *
     * @return 扫描出的需要代理的接口
     */
    private Class[] scan() {
        return new Class[]{OneTestDao.class, TwoTestDao.class};
    }


    private String beanNameProcess(String simpleName) {
        char c = simpleName.charAt(0);
        return simpleName.replaceFirst("" + c, "" + (char) (c + 32));
    }


}
