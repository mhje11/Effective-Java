# ordinal 메서드 대신 인스턴스 필드를 사용하라

### ordinal 을 잘못 사용한 예
- ordinal() 은 enum 상수의 선언 순서를 반환하는 메서드
- 중간에 TRIPLE_QUARTET 등을 추가하면 순서가 깨짐 -> 오류 발생
```java
public enum Ensemble {
    //ordinal() 은 enum 상수의 선언 순서를 반환하는 메서드
    //중간에 TRIPLE_QUARTET 등 추가하면 순서가 깨짐
    SOLO, DUET, TRIO, QUARTET, QUINTET,
    SEXTET, SEPTET, OCTET, NONET, DECTET;

    public int numberOfMusicians() {
        return ordinal() + 1;
    }
}
```

### ordinal 을 좋게 사용한 예시
- 열거 타입 상수에 연결된 값은 ordinal 메서드로 얻지 말고, 인스턴스 필드에 저장하자
- ordinal()을 사용하지 않기 때문에 순서 변경과 무관하게 값을 유지 가능
- ordinal()을 안쓰는게 더 나은 이유 --> 유지보수도 힘들고 아래 예시처럼 활용할시 더 직관적 코드 작성이 가능
- 또한 자동으로 생성되는 값이기 때문에 예상치 못한 버그가 발생 가능하기 때문
```java
public enum Ensemble {
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
```