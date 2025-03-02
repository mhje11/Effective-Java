# 확장할 수 있는 열거 타입이 필요하면 인터페이스를 사용하라

### 인터페이스를 이용해 확장 가능 열거 타입을 흉내낸 예시
- 열거 타입인 BasicOperation 을 확장할 수 없지만 인터페이스 Operation 은 가능
- 또한 이 인터페이스를 연산의 타입으로 사용하면 된다
- 각 열거 상수에서 메서드를 직접 구현
```java
public interface Operation {
    double apply(double x, double y);
}

public enum BasicOperation implements Operation{
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

    BasicOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
```

### 확장 가능 열거 타입
- BasicOperation 과 ExtendedOperation 을 같이 사용할 수 있음
- Operation 인터페이스를 통해 두개의 열거 타입을 같은 방식으로 다룰 수 있음
```java
public enum ExtendedOperation implements Operation {
    // enum 에서 각각의 상수가 개별적으로 apply 구현해서 오버라이딩 따로 구현 안해도됨
    // enum 내부에서 오버라이딩중
    // enum 끼리는 구현을 상속 불가
    EXP("^") {
        public double apply(double x, double y) {
            return Math.pow(x, y);
        }
    },
    REMAINDER("%") {
        public double apply(double x, double y) {
            return x % y;
        }
    };

    private final String symbol;

    ExtendedOperation(String symbol) {
        this.symbol = symbol;
    }


    @Override
    public String toString() {
        return symbol;
    }

    public static void main(String[] args) {
        double x = 3.0;
        double y = 4.0;
        test(ExtendedOperation.class, x, y);
        test(BasicOperation.class, x, y);
    }

    //T 는 enum 타입이면서 Operation 을 구현해야함
    //Operation 인터페이스를 사용해 enum 을 확장
    private static <T extends  Enum<T> & Operation> void test(
            //제네릭 클래스를 사용하여 enum 을 가져옴
            Class<T> opEnumType, double x, double y) {
        for (Operation op : opEnumType.getEnumConstants()) {
            System.out.printf("%f %s %f = %f%n",
                    x, op, y, op.apply(x, y));
        }
    }

}
```