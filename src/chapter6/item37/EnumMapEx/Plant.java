package chapter6.item37.EnumMapEx;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Plant {
    enum LifeCycle {ANNUAL, PERENNIAL, BIENNIAL}

    final String name;
    final LifeCycle lifeCycle;

    public Plant(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        Set<Plant> garden = Set.of(
                new Plant("Rose", LifeCycle.PERENNIAL),
                new Plant("Sunflower", LifeCycle.ANNUAL),
                new Plant("Tulip", LifeCycle.BIENNIAL)
        );

        //EnumMap 사용 -> ordinal() 없이도 안전하게 사용
        //열거형 값을 안전하게 매핑
        //메모리 효율적 + 코드 가독성도 좋음
        Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle =
                new EnumMap<>(Plant.LifeCycle.class);

        for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
            plantsByLifeCycle.put(lc, new HashSet<>());
        }

        for (Plant p : garden) {
            plantsByLifeCycle.get(p.lifeCycle).add(p);
        }
        System.out.println(plantsByLifeCycle);
    }
}
