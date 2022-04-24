package uz.pdp.homework1.aop;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermissionForPostAndCommet {

    String permission();
}
