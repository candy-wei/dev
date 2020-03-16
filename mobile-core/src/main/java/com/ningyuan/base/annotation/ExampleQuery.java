package com.ningyuan.base.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExampleQuery {

    String method() default "andEqualTo";

    Class valueClass() default Object.class;

    boolean hasValue() default true;

}
