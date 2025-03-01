package chapter6.item39.ex2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//매개변수 하나를 받는 어노테이션 타입
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {
    //기대하는 예외 타입을 설정
    Class<? extends Throwable> value();
    //명시한 예외를 던져야만 성공하는 테스트 메서드용 어노테이션


}
