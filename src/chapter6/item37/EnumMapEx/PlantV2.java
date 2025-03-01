package chapter6.item37.EnumMapEx;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class PlantV2 {
    enum LifeCycle {ANNUAL, PERENNIAL, BIENNIAL}

    final String name;
    final LifeCycle lifeCycle;

    public PlantV2(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        Set<PlantV2> garden = Set.of(
                new PlantV2("Rose", LifeCycle.PERENNIAL),
                new PlantV2("Sunflower", LifeCycle.ANNUAL),
                new PlantV2("Tulip", LifeCycle.BIENNIAL)
        );

        //스트림을 사용한 코드 V1
        //LifeCycle을 키로 PlantV2 객체들을 그룹화하여 Map으로 반환
        //더 최적화된 방법은 ENumMap
        System.out.println(garden.stream()
                .collect(groupingBy(p -> p.lifeCycle)));
    }
}
