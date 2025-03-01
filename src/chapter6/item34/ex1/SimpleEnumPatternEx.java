package chapter6.item34.ex1;

public class SimpleEnumPatternEx {
    //가장 단순한 열거 타입
    //열거 타입 자체는 클래스이며 상수 하나당 자신의 인스턴스를 하나씩 만들어 public static final 필드로 공개
    //즉 인스턴스는 단 하나만 존재
    //싱글턴 --> 원소가 하나뿐인 열거 타입
    //열거타입 --> 싱글턴을 일반화한 형태
    public enum Apple {FUJI, PIPPIN, GRANNY_SMITH}
    public enum Orange {NAVEL, TEMPLE, BLOOD}
}
