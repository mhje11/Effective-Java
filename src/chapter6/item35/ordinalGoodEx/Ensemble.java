package chapter6.item35.ordinalGoodEx;

public enum Ensemble {
    //ordinal()을 사용하지 않으므로 순서 변경과 무관하게 값 유지 가능
    //그냥 ordinal() 안쓰는게 나음
    //왜? 유지보수도 힘들고 아래처럼 더 직관적으로 코드 작성이 가능
    //또한 자동 생성되는 값이라 예상치 못한 버그 발생 가능
    SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
    SEXTET(6), SEPTET(7), OCTET(8), NONET(9), DECTET(10),
    TRIPLE_QUARTET(12);
    private final int numberOfMusicians;

    Ensemble(int size) {
        this.numberOfMusicians = size;
    }

    public int numberOfMusicians() {
        return ordinal() + 1;
    }
}
