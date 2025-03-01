package chapter6.item34.ex1;

public enum Planet {
    // 열거타입은 근본적으로 불변 따라서 모든 필드는 final 이어야 함
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
