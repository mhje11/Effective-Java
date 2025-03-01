# int 상수 대신 열거 타입을 사용하라

### 정수 열거 패턴의 취약성
1. 타입 안전을 보장할 방법이 없음
2. 표현력 또한 좋지 않음

```java
public class IntEnumPatternBadEx {
    public static final int APPLE_FUJI = 0;
    public static final int APPLE_PIPPIN = 1;
    public static final int APPLE_GRANNY_SMITH = 2;

    public static final int ORANGE_NAVEL = 0;
    public static final int ORANGE_TEMPLE = 1;
    public static final int ORANGE_BLOOD = 2;

    public static void main(String[] args) {
        //단순한 값을 출력하기에 어떤 과일을 의미하는지 알 수 없음
        System.out.println(APPLE_FUJI);
        if (APPLE_FUJI == ORANGE_NAVEL) {
            //정수는 서로 다른 열거형끼리 섞여도 컴파일러가 막지 못함
            System.out.println("같은 과일");
        }
    }
}
```

### 가장 단순한 열거 타입
1. 열거 타입 자체는 클래스, 상수 하나당 자신의 인스턴스를 하나씩 만들어 public static final 필드로 공개
2. 인스턴스를 하나만 보유 --> 싱글턴과 유사
3. 싱글턴 --> 원소가 하나뿐인 열거 타입
4. 열거타입 --> 싱글턴을 일반화한 상태

```java
public class SimpleEnumPatternEx {
    public enum Apple {FUJI, PIPPIN, GRANNY_SMITH}
    public enum Orange {NAVEL, TEMPLE, BLOOD}
}
```

### 데이터와 메서드를 갖는 열거타입
1. 열거타입은 근본적으로 불변 따라서 모든 필드는 final 이어야 함
2. 열거 타입 상수 각각을 특정 데이터와 연결지으려면 생성자에서 데이터를 받아 인스턴스 필드에 저장하면 됨
```java
public enum Planet {
    // 생성자를 통해 데이터 가져오기
    MERCURY(3.302e+23, 2.439e6),
    VENUS (4.869e+24, 6.052e6),
    EARTH(5.975e+24, 3.393e6);

    private final double mass;
    private final double radius;
    private final double surfaceGravity;

    private static final double G = 6.67300E-11;

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        surfaceGravity = G * mass / (radius * radius);
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public double getSurfaceGravity() {
        return surfaceGravity;
    }
    public double surfaceWeight(double mass) {
        return mass * surfaceGravity;
    }

}
```

### 값에 따라 분기하는 열거 타입
1. 마지막 throw 부분은 도달하지도 않음
2. 새로운 상수가 추가되면 case 문도 추가해야함 --> 추가하지 않으면 알수없는 연산에 도달함
3. 유지보수에 용이하지 않음

```java
public enum Operation {
    PLUS, MINUS, TIMES, DIVIDE;
    
    public double apply(double x, double y) {
        switch (this) {
            case PLUS:
                return x + y;
            case MINUS:
                return x - y;
            case TIMES:
                return x * y;
            case DIVIDE:
                return x / y;
        }
        throw new AssertionError("알 수 없는 연산 : " + this);
    }
}
```

### 상수별 메서드 구현을 활용한 열거 타입
1. 열거 타입에 Apply 라는 추상 메서드를 선언하고 각 상수별 클래스 몸체를 자신에 맞게 재정의 하는 방법 --> 이러한 방법을 상수별 메서드 구현이라함
2. 새로운 상수를 추가하면 apply를 반드시 구현해야함
```java
public enum OperationV2 {
    PLUS {
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS {
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES {
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE {
        public double apply(double x, double y) {
            return x / y;
        }
    };

    public abstract double apply(double x, double y);
}
```

### 상수별 클래스 몸체와 데이터를 사용한 열거 타입
1. OperationV2 에 비해 더 직관적 유지보수에도 용이
2. OperationV2 : 3.0 PLUS 5.0 = 8.0
3. OperationV3 : 3.0 + 5.0 = 8.0 더 직관적 
```java
public enum OperationV3 {
    PLUS("+") {
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        public double apply(double x, double y) {
            return x / y;
        }
    };
    private final String symbol;

    OperationV3(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public abstract double apply(double x, double y);
}
```

```java
public static void main(String[] args) {
        double x = 3.0;
        double y = 5.0;
        for (OperationV3 op : OperationV3.values()) {
            System.out.printf("%f %s %f = %f%n",
                    x, op, y, op.apply(x, y));
        }
    }

    //Optional 로 null 값을 방지하고
    //values 를 매번 순회 하는 대신 Map 을 사용하여 O(1)시간 복잡도로 빠르게 검색 가능
    private static final Map<String, OperationV3> stringToEnum =
            Stream.of(values()).collect(
                    toMap(Objects::toString, e -> e)
            );

    public static Optional<OperationV3> fromString(String symbol) {
        return Optional.ofNullable(stringToEnum.get(symbol));
    }
```

### 값에 따라 분기하는 코드를 공유하는 열거 타입
1. 관리 관점에선 위험한 코드 휴가같은 경우를 추가했을 경우 휴가기간에 일해도 평일과 같이 임금을 받음
2. 상수별 메서드 구현으로 급여를 정확히 계산하는 방법 두가지
- 1. 잔업수당을 계산하는 코드를 모든 상수에 중복해서 넣기
- 2. 계산 코드를 평일용과 주말용으로 나눠 각각을 도우미 메서드로 작성한 다음 각 상수가 자신에게 필요한 메서드를 적절히 호출
- 하지만 두 방식 모두 코드가 장황해져 가독성이 크게 떨어지고 오류발 생가능성이 높아짐
- 해결방법 : 새로운 상수를 추가할 때 잔업수당 전략을 선택하도록 하는것
```java
public enum PayrollDay {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
    SATURDAY, SUNDAY;

    private static final int MINS_PER_SHIFT = 8 * 60;

    int pay(int minutesWorked, int payRate) {
        int basePay = minutesWorked * payRate;

        int overtimePay;
        switch (this) {
            case SATURDAY:
            case SUNDAY:
                overtimePay = basePay / 2;
                break;
            default:
                overtimePay = minutesWorked <= MINS_PER_SHIFT ?
                        0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
        }
        return basePay + overtimePay;
    }
}
```

### 전략 열거 타입 패턴
1. 잔업수당 계산을 private 중첩 열거 타입으로 옮기고 PayrollDay 열거 타입의 생성자에서 이중 적당한 것을 선택하는것
2. 즉 잔업수당 계산을 전략 열거타입에 위임하는것
3. 새로운 근무 유형이 추가되더라고 코드 변경이 최소화 됨
4. ex)MONDAY(WEEKDAY) 처럼 PayType 을 선택하게해서 계산을 위임 즉 PAYTYPE 만 유지보수 하면 됨 
```java
public enum PayrollDayV2 {
    MONDAY(WEEKDAY), TUESDAY(WEEKDAY), WEDNESDAY(WEEKDAY), THURSDAY(WEEKDAY), FRIDAY(WEEKDAY),
    SATURDAY(WEEKEND), SUNDAY(WEEKEND);

    private final PayType payType;

    PayrollDayV2(PayType payType) {
        this.payType = payType;
    }

    int pay(int minutesWorked, int payRate) {
        return payType.pay(minutesWorked, payRate);
    }


    //전략 열거 타입
    enum PayType {
        WEEKDAY {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked <= MINS_PER_SHIFT ? 0 :
                        (minsWorked - MINS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked * payRate / 2;
            }
        };

        abstract int overtimePay(int mins, int payRate);
        private static final int MINS_PER_SHIFT = 8 * 60;

        int pay(int minsWorked, int payRate) {
            int basePay = minsWorked * payRate;
            return basePay + overtimePay(minsWorked, payRate);
        }
    }
}
```

### switch 문을 이용한 열거 타입

- 기존 열거 타입에 상수별 동작을 혼합해 넣을 때는 switch 문이 좋은 선택이 될 수 있다.
- 추가하려는 메서드가 의미상 열거 타입에 속하지 않는다면 직접 만든 열거 타입이라도 이 방식을 적용하는게 좋음
- 하나의 메서드가 상수별로 다르게 동작해야 할경우 사용하기 용이

```java
public static Operation inverse(Operation op) {
    switch (op) {
        case PLUS:
            return Operation.PLUS;
        case MINUS:
            return Operation.MINUS;
        case TIMES:
            return Operation.TIMES;
        case DIVIDE:
            return Operation.DIVIDE;
        default: throw new AssertionError("알 수 없는 연산 : " + op);
    }
}
```

### 열거 타입을 사용하는 경우
- 필요한 원소를 컴파일타임에 다 알 수 있는 상수집합인 경우
- 즉 메뉴 아이템, 연산 코드, 명령줄 플래그 등 허용하는 값 모두를 컴파일타임에 알고 있을 경우