package chapter6.item39.ex4;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
