package chapter6.item36.bitFieldEx;

public class Text {
    public static final int STYLE_BOLD = 1 << 0; //1
    public static final int STYLE_ITALIC = 1 << 1; //2
    public static final int STYLE_UNDERLINE = 1 << 2; // 4
    public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8

    public void applyStyles(int styles) {

    }
    //비트 필드의 단점
    //1. 아무 숫자나 집어넣을 수 있음 (타입 안정성 x)
    //ex) applyStyles(99)
    //2. 확장성 부적
    //int 는 최대 32개 비트만 사용가능 API 를 수정하지 않고서는 32비트, 64비트 이상으로 늘릴 수 없음
    //3.가독성 떨어짐 여러개의 스타일 적용할때 OR(|) 연산 사용해야함 또한 특정 스타일 포함됐는지 확인할시 AND(&) 연산 사용해야함
    //직관적이지 않음
    //4.디버깅, 유지보수가 어려움
    //ex) text.applyStyles(5); 5가 어떤 스타일인지 코드만 보고 이해하기 어려움
}
