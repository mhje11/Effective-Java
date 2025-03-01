package chapter6.item37.EnumMapEx;

import java.util.EnumMap;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public class PlantV3 {
    enum LifeCycle {ANNUAL, PERENNIAL, BIENNIAL}

    final String name;
    final LifeCycle lifeCycle;

    public PlantV3(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        Set<PlantV3> garden = Set.of(
                new PlantV3("Rose", LifeCycle.PERENNIAL),
                new PlantV3("Sunflower", LifeCycle.ANNUAL),
                new PlantV3("Tulip", LifeCycle.BIENNIAL)
        );

        //EnumMap을 이용해 데이터와 열거 타입을 매핑
        System.out.println(garden
                .stream().collect(groupingBy(p -> p.lifeCycle,
                        () -> new EnumMap<>(LifeCycle.class), toSet())));
    }
}
