# ordinal 인덱싱 대신 EnumMap을 사용하라

### ordinal()을 배열 인덱스로 사용 (좋지않은 예시)
- ordinal() 값이 변경되면 예측하기 어려운 버그 발생
- 순서가 바뀌거나 새로운 값을 추가할 경우 기존 코드를 수정해야함
- 배열은 제네릭과 호환되지 않으니 비검사 형변환을 수행해야함
- 제네릭 배열은 런타임에 정보가 소거되므로 타입 안정성도 없음 사실상 Set<Plant>[]는 Object[]로 변환되는거나 다름이 없음
- 정확한 정숫값을 사용한다는 것을 직접 보증해야한다는 치명적 단점 존재

```java
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
```

### EnumMap 을 사용해 데이터와 열거 타입을 매핑한 예시
- EnumMap 사용 -> ordinal() 없이도 안전하게 사용
- EnumMap<LifeCycle, Set<Plant>> 같이 타입이 명확하게 지정돼 타입 안정성을 보장
- EnumMap 은 내부적으로 배열을 사용하지만, 일반적인 HashMap 보다 메모리 사용량이 적음
- 열거 타입의 ordinal()을 내부적으로 사용하지만, 최적화된 방식으로 관리해 더 빠른 성능을 보장

```java
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
```

### Stream 을 사용한 코드 V1
- LifeCycle을 키로 PlantV2 객체들을 그룹화하여 Map으로 반환
- 더 최적화된 방법은 EnumMap
- 왜? HashMap 은 열거형을 위한 최적화가 없고, 메모리 사용량이 더 많기 때문
```java
public static void main(String[] args) {
    Set<PlantV2> garden = Set.of(
            new PlantV2("Rose", LifeCycle.PERENNIAL),
            new PlantV2("Sunflower", LifeCycle.ANNUAL),
            new PlantV2("Tulip", LifeCycle.BIENNIAL)
    );

    
    System.out.println(garden.stream()
            .collect(groupingBy(p -> p.lifeCycle)));
}
```

### Stream 을 사용한 코드 V2
- EnumMap 을 이용해 데이터와 열거 타입을 매핑
- EnumMap 을 이용하기 때문에 HashMap 보다 메모리 효율적, ordinal() 기반으로 동작해 빠르게 키를 찾음
```java
public static void main(String[] args) {
        Set<PlantV3> garden = Set.of(
                new PlantV3("Rose", LifeCycle.PERENNIAL),
                new PlantV3("Sunflower", LifeCycle.ANNUAL),
                new PlantV3("Tulip", LifeCycle.BIENNIAL)
        );
        
        System.out.println(garden
                .stream().collect(groupingBy(p -> p.lifeCycle,
                        () -> new EnumMap<>(LifeCycle.class), toSet())));
    }
```

### 배열들의 배열의 인덱스에 ordinal()을 사용한 예시 (안좋은 예시)
- 역시나 ordinal()을 써서 유지보수가 매우 불편
- 상전이 표는 상태의 가짓수가 늘어나면 매우 제곱해서 커지며 null 로 채워지는 칸이 너무 많아짐

```java
public enum Phase {
    SOLID, LIQUID, GAS;
    public enum Transition {
        MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;

        private static final Transition[][] TRANSITIONS = {
                {null, MELT, SUBLIME},
                {FREEZE, null, BOIL},
                {DEPOSIT, CONDENSE, null}
        };

        public static Transition from(Phase from, Phase to) {
            return TRANSITIONS[from.ordinal()][to.ordinal()];
        }
    }
}
```

### 중첩 EnumMap 으로 데이터와 열거 타입 쌍을 연결한 예시
- 상태 목록에 새로운 예시를 추가해도 문제되지 않음
- 단순히 PLASMA 를 추가하고 전이 목록에 IONIZE 를 추가하면 끝

```java
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

```


