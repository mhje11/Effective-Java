package chapter6.item37.ordinalBadEx;

import java.util.HashSet;
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

    //ordinal() 값이 변경되면 예측하기 어려운 버그 발생
    //순서가 바뀌거나 새로운 값을 추가할 경우 기존 코드를 수정해야함
    public static void main(String[] args) {
        Set<Plant> garden = Set.of(
                new Plant("Rose", LifeCycle.PERENNIAL),
                new Plant("Sunflower", LifeCycle.ANNUAL),
                new Plant("Tulip", LifeCycle.BIENNIAL)
        );

        //제네릭 배열은 런타임에 정보가 소거되므로 타입 안정성도 없음
        //사실상 Set<Plant>[]는 Object[]로 변환되는거나 다름이 없음
        Set<Plant>[] plantsByLifeCycle =
                (Set<Plant>[]) new Set[LifeCycle.values().length];

        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            plantsByLifeCycle[i] = new HashSet<>();
        }
        for (Plant p : garden) {
            plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);
        }
        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            System.out.printf("%s: %s%n",
                    Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
        }
    }


}
