package chapter6.item39.ex1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//마커 애너테이션 타입 선언
//@Retention 메타 애너테이션은 @Test 가 런타임에도 유지되어야 한다는 표시
@Retention(RetentionPolicy.RUNTIME)
//@Target 메타애너테이션은 @test 가 반드시 메서드 선언에서만 사용돼야 한다고 알려줌
//RUNTIME : 런타임에서 유지
//SOURCE : 컴파일 시 제거됨
//CLASS : 클래스 파일에 남아있지만 런타임에는 사라짐 JVM 에서 읽을 수 없음
@Target(ElementType.METHOD)
public @interface Test {
}
