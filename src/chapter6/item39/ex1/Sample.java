package chapter6.item39.ex1;

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
    //정적 메서드를 사용해야 하는 이유
    //1. 객체를 생성하지 않고 바로 실행할 수 있기 때문
    //2. 테스트 환경에서 일관된 실행을 보장
    //3. 테스트 코드의 간결함을 위해
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
