package chapter6.item37.EnumMapEx;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public enum Phase {
    SOLID, LIQUID, GAS;

    public enum Transition {
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);

        private final Phase from;
        private final Phase to;

        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        private static final Map<Phase, Map<Phase, Transition>>
        m = Stream.of(values()).collect(groupingBy
                (t -> t.from, //첫번째 분류 기준 : 출발 상태
                () -> new EnumMap<>(Phase.class), // 첫번째 맵은 EnumMap<Phase, Map<Phase, Transition>> 사용
                toMap(t -> t.to, // 두 번째 분류 기준 : 도착 상태
                        t -> t, // 값 : Transition 객체
                        (x, y) -> y, // 병합 로직
                        () -> new EnumMap<>(Phase.class)))); // 두번째 맵 EnumMap<Phase, Transition> 사용

        public static Transition from(Phase from, Phase to) {
            return m.get(from).get(to);
        }
    }
}
