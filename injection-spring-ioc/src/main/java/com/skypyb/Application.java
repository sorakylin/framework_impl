package com.skypyb;

import com.skypyb.config.MainConfig;
import com.skypyb.dao.OneTestDao;
import com.skypyb.dao.TwoTestDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        //注解方式加载配置
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);

        OneTestDao dao1 = (OneTestDao) applicationContext.getBean("oneTestDao");
        dao1.query();

        TwoTestDao dao2 = (TwoTestDao) applicationContext.getBean("twoTestDao");
        dao2.query();

    }
}
