package chapter6.item39.ex2;

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
