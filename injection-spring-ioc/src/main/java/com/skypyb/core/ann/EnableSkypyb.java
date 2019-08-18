package com.skypyb.core.ann;

import com.skypyb.core.strengthen.SkypybRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(SkypybRegistrar.class)
public @interface EnableSkypyb {

}
