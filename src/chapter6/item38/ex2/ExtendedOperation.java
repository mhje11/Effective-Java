package chapter6.item38.ex2;

import chapter6.item38.ex1.BasicOperation;
import chapter6.item38.ex1.Operation;

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
