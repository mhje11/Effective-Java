package chapter6.item34.ex1;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public enum OperationV3 {
    //상수별 클래스 몸체와 데이터를 사용한 열거 타입
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
}
