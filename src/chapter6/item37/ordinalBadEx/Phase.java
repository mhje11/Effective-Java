package chapter6.item37.ordinalBadEx;

public enum Phase {
    //역시나 ordinal을 써서 유지보수가 매우 불편
    //상전이 표는 상태의 가짓수가 늘어나면 매우 제곱해서 커지며 null 로 채워지는 칸이 너무 많아짐
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
