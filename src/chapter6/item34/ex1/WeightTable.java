package chapter6.item34.ex1;

public class WeightTable {
    public static void main(String[] args) {
        double earthWeight = 185.0;
        double mass = earthWeight / Planet.EARTH.getSurfaceGravity();
        for (Planet p : Planet.values()) {
            System.out.printf("%s에서의 무게는 %f이다.%n",
                    p, p.surfaceWeight(mass));
        }
    }
}
