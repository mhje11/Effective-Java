package chapter6.item34.ex1;

public class IntEnumPatternBadEx {
    public static final int APPLE_FUJI = 0;
    public static final int APPLE_PIPPIN = 1;
    public static final int APPLE_GRANNY_SMITH = 2;

    public static final int ORANGE_NAVEL = 0;
    public static final int ORANGE_TEMPLE = 1;
    public static final int ORANGE_BLOOD = 2;

    public static void main(String[] args) {
        //단순한 값을 출력하기에 어떤 과일을 의미하는지 알 수 없음
        System.out.println(APPLE_FUJI);
        if (APPLE_FUJI == ORANGE_NAVEL) {
            //정수는 서로 다른 열거형끼리 섞여도 컴파일러가 막지 못함
            System.out.println("같은 과일");
        }
    }


}
