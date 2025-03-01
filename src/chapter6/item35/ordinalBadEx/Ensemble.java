package chapter6.item35.ordinalBadEx;

public enum Ensemble {
    //ordinal() 은 enum 상수의 선언 순서를 반환하는 메서드
    //중간에 TRIPLE_QUARTET 등 추가하면 순서가 깨짐
    SOLO, DUET, TRIO, QUARTET, QUINTET,
    SEXTET, SEPTET, OCTET, NONET, DECTET;

    public int numberOfMusicians() {
        return ordinal() + 1;
    }
}
