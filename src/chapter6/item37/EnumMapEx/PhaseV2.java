package chapter6.item37.EnumMapEx;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public enum PhaseV2 {
    SOLID, LIQUID, GAS, PLASMA;

    //새로운 전이상태 추가해도 문제 없음
    public enum Transition {
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID),
        IONIZE(GAS, PLASMA), DEIONIZE(PLASMA, GAS);

        private final PhaseV2 from;
        private final PhaseV2 to;

        Transition(PhaseV2 from, PhaseV2 to) {
            this.from = from;
            this.to = to;
        }

        private static final Map<PhaseV2, Map<PhaseV2, Transition>>
        m = Stream.of(values()).collect(groupingBy
                (t -> t.from, //첫번째 분류 기준 : 출발 상태
                () -> new EnumMap<>(PhaseV2.class), // 첫번째 맵은 EnumMap<Phase, Map<Phase, Transition>> 사용
                toMap(t -> t.to, // 두 번째 분류 기준 : 도착 상태
                        t -> t, // 값 : Transition 객체
                        (x, y) -> y, // 병합 로직
                        () -> new EnumMap<>(PhaseV2.class)))); // 두번째 맵 EnumMap<Phase, Transition> 사용

        public static Transition from(PhaseV2 from, PhaseV2 to) {
            return m.get(from).get(to);
        }
    }
}
