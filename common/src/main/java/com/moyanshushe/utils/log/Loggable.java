package com.moyanshushe.utils.log;

/*
 * Author: Napbad
 * Version: 1.0
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
    String value() default "defaultLogger";
}
