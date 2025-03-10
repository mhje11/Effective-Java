package chapter2.item2.ex2;

public class Calzone extends Pizza{
    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Builder> {
        private boolean sauceInside = false;

        public Builder sauceInside() {
            this.sauceInside = true;
            return this;
        }

        @Override
        public Calzone build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }

    }

    private Calzone(Builder builder) {
        super(builder);
        this.sauceInside = builder.sauceInside;;
    }

    public boolean isSauceInside() {
        return sauceInside;
    }

}
