package com.revature.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//This is a custom annotation
//Annotate methods that can only be managed by managers
@Target(ElementType.METHOD) //only to be used on methods
@Retention(RetentionPolicy.RUNTIME) //available at runtime
public @interface ManagerOnly {

}
