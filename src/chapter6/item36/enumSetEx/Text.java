package chapter6.item36.enumSetEx;

import java.util.EnumSet;
import java.util.Set;

public class Text {
    public enum Style {
        BOLD, ITALIC, UNDERLINE, STRIKETHROUGH
    }

    public void applyStyles(Set<Style> styles) {
        System.out.println("적용된 스타일 : " + styles);
    }

    //Set<Style>을 적용해 타입안정성 확보
    //확장성이 좋음
    //직관적인 코드
    //EnumSet 의 유일한 단점 불변EnumSet 을 만들 수 없다
    public static void main(String[] args) {
        Text text = new Text();
        text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
    }
}
