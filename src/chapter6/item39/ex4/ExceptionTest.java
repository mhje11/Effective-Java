package chapter6.item39.ex4;

import java.lang.annotation.*;

//반복 가능한 어노테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ExceptionTestContainer.class)
public @interface ExceptionTest {
    Class<? extends Throwable> value();
}
