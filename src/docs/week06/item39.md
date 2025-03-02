# 명명 패턴보다 애너테이션을 사용하라

### 마커 애너테이션 타입 선언

- 마커 애너테이션 : 값을 가지지 않는 애너테이션
- 단순히 특정 의미를 부여하기 위해 사용
- 주로 컴파일러나 런타임 환경에서 해당 요소를 식별하는 용도로 사용
- ex) @Override, @Deprecated, @FunctionalInterface
- 아래 코드는 정적 메서드 + 매개변수 없을 경우만 사용가능 하다는 전제로 적용
- 매겨변수 X + 정적 메서드 일때만 강제 하기 위해서는 적절한 애너테이션 처리기를 직접 구현해야함
```java
//@Retention 메타 애너테이션은 @Test 가 런타임에도 유지되어야 한다는 표시
//RUNTIME : 런타임에서 유지
//SOURCE : 컴파일 시 제거됨
//CLASS : 클래스 파일에 남아있지만 런타임에는 사라짐 JVM 에서 읽을 수 없음
@Retention(RetentionPolicy.RUNTIME)
//@Target 메타애너테이션은 @Test 가 반드시 메서드 선언에서만 사용돼야 한다고 알려줌
@Target(ElementType.METHOD)
public @interface Test {
}
```

### 마커 애너테이션을 사용한 프로그램 예시
- 정적 메서드를 사용해야 하는 이유
- 1. 객체를 생성하지 않고 바로 실행할 수 있기 때문
- 2. 테스트 환경에서 일관된 실행을 보장
- 3. 테스트 코드의 간결함을 위해

```java
public class Sample {
    @Test
    public static void m1() {
    }

    public static void m2() {}

    @Test
    public static void m3() {
        throw new RuntimeException("실패");
    }

    public static void m4() {}

    //잘못 사용한 예 정적 메서드가 아님
    @Test
    public void m5() {}

    public static void m6() {}

    //실패 해야함
    @Test
    public static void m7() {
        throw new RuntimeException("실패");
    }

    public static void m8() {}
}
```

### 마커 애너테이션을 처리하는 프로그램 예시

```java
public class RunTests {
    public static void main(String[] args) throws Exception{
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Class.forName("chapter6.item39.ex1.Sample");
        for (Method m : testClass.getDeclaredMethods()) {
            //isAnnotationPresent 로 실행할 메서드를 찾아줌
            if (m.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    m.invoke(null);
                    passed++;
                    //테스트 메서드가 예외를 던지면 InvocationTargetException 으로 감싸 다시 던짐
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(m + " 실패 : " + exc);
                } catch (Exception exc) {
                    System.out.println("잘못 사용한 @Test: " + m);
                }
            }
        }
        System.out.printf("성공 : %d, 실패 : %d%n", passed, tests - passed);
    }
}
```

### 매개변수 하나를 받는 애너테이션 타입, 특정 예외를 던져야만 성공하는 테스트
```java
//매개변수 하나를 받는 어노테이션 타입
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {
    //기대하는 예외 타입을 설정
    //애너테이션의 매개변수 타입 --> Class<? extends Throwable>
    Class<? extends Throwable> value();
    //명시한 예외를 던져야만 성공하는 테스트 메서드용 어노테이션
}
```

```java
public class Sample2 {
    //성공 해야 함
    @ExceptionTest(ArithmeticException.class)
    public static void m1() {
        int[] a = new int[0];
        int i = 0;
        i = i / i;
    }

    //실패 해야 함(다른 예외 발생)
    @ExceptionTest(ArithmeticException.class)
    public static void m2() {
        int[] a = new int[0];
        int i = a[1];
    }

    //실패 해야함 (예외 발생 x)
    @ExceptionTest(ArithmeticException.class)
    public static void m3() {}
}
```

```java
public class RunTests {
    public static void main(String[] args) throws Exception{
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Class.forName("chapter6.item39.ex2.Sample2");
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", m);
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    Class<? extends  Throwable> excType =
                            m.getAnnotation(ExceptionTest.class).value();
                    if (excType.isInstance(exc)) {
                        passed++;
                    } else {
                        System.out.printf("테스트 %s 실패 : 기대한 예외 %s, 발생한 예외 %s%n", m, excType.getName(), exc);
                    }
                } catch (Exception exc) {
                    System.out.println("잘못 사용한 @ExceptionTest: " + m);
                }
            }
        }
        System.out.printf("성공 : %d, 실패 : %d%n", passed, tests - passed);
    }
}
```

### 배열 매개변수를 받는 애터네이션을 사용한 코드

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {
    Class<? extends Throwable>[] value();
}
```
```java
public class Sample3 {
    @ExceptionTest({ IndexOutOfBoundsException.class, NullPointerException.class})
    public static void doublyBad() {
        List<String> list = new ArrayList<>();

        list.addAll(5, null);
    }
}
```

```java
public class RunTests {
    public static void main(String[] args) throws Exception{
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Class.forName("chapter6.item39.ex3.Sample3");
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(chapter6.item39.ex3.ExceptionTest.class)) {
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", m);
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    int oldPassed = passed;
                    Class<? extends Throwable>[] excTypes =
                            m.getAnnotation(ExceptionTest.class).value();
                    for (Class<? extends Throwable> excType : excTypes) {
                        if (excType.isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }
                    if (passed == oldPassed) {
                        System.out.printf("테스트 %s 실패 :  %s %n", m, exc);
                    }
                } catch (Exception exc) {
                    System.out.println("잘못 사용한 @ExceptionTest: " + m);
                }
            }
        }
        System.out.printf("성공 : %d, 실패 : %d%n", passed, tests - passed);
    }
}
```

### 반복 가능한 애너테이션 타입
- 여러개의 값을 받는 애너테이션의 다른 방식
- 배열 매개변수 대신 @Repeatable 메타애너테이션을 사용하는 방식
- 반복 가능한 애너테이션을 사용할때 주의 할 점
- 1. @Repeatable 을 단 애너테이션을 반환하는 컨테이너 애너테이션을 하나 더 정의하고 @Repeatable 에 이 컨테이너 애너테이션의 class 객체를 매개변수로 전달해야 함
- 2. 컨테이너 애너테이션은 내부 애너테이션의 타입의 배열을 반환하는 value 메서드를 정의해야 한다.
- 3. 컨테이너 애너테이션 타입에 적절한 @Retention 과 @Target 을 명시해야 함
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ExceptionTestContainer.class)
public @interface ExceptionTest {
    Class<? extends Throwable> value();
}
```

- 컨테이너 애너테이션
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTestContainer {
    ExceptionTest[] value();
}
```

```java
public class Sample4 {
    @ExceptionTest(IndexOutOfBoundsException.class)
    @ExceptionTest(NullPointerException.class)
    public static void doublyBad() {
    }
}
```

```java
public class RunTests {
    public static void main(String[] args) throws Exception{
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Class.forName("chapter6.item39.ex4.Sample4");
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(chapter6.item39.ex4.ExceptionTest.class) || m.isAnnotationPresent(ExceptionTestContainer.class)) {
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", m);
                } catch (Throwable wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    int oldPassed = passed;
                    ExceptionTest[] excTests =
                            m.getAnnotationsByType(ExceptionTest.class);
                    for (ExceptionTest excTest : excTests) {
                        if (excTest.value().isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }
                    if (passed == oldPassed) {
                        System.out.printf("테스트 %s 실패 : %s %n", m, exc);
                    }
                }
            }
        }
        System.out.printf("성공 : %d, 실패 : %d%n", passed, tests - passed);
    }
}
```

### 정리
- 다른 프로그래머가 소스코드에 추가 정보를 제공할 수 있는 도구를 만드는 일을 한다면 적당한 애너테이션 타입도 함께 제공하자
- 애너테이션으로 할수 있는 일을 명명 패턴으로 처리할 이유가 딱히 없음

- 명명 패턴의 문제점
- 1. 컴파일러가 오타가 있어도 체크해주지 않음
- 2. 명명 규칙이 일관되지 않으면 어떤 용도인지 파악하기 어려움
- 3. 리플렉션을 사용할때 모든 메서드를 탐색해야 하므로 비효율적

- 애너테이션의 장점
- 1. 컴파일러가 체크해줘서 안전
- 2. 코드 가독성면에서 유리, @Test 같이 어노테이션만 붙이면 테스트 용도 메서드인지 파악 가능
- 3. 리플렉션 활용시 애너테이션 있는 메서드만 골라 실행해서 더 효율적
- 4. 애너테이션에 메타데이터 추가 가능 마커 역할뿐 아니라 추가적인 정보를 전달하기도 가능, ex) 기대하는 예외 타입 명시
- 5. 유지보수와 확장성이 뛰어남, 새로운 애너테이션 추가시 기존 코드 변경 없이 기능 확장 가능


