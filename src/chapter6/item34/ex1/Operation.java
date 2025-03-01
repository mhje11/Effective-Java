package chapter6.item34.ex1;

public enum Operation {
    PLUS, MINUS, TIMES, DIVIDE;

    //값에 따라 분기하는 연산
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
