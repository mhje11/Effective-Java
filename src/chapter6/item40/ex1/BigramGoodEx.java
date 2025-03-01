package chapter6.item40.ex1;

import java.util.HashSet;
import java.util.Set;

public class BigramGoodEx {
    private final char first;
    private final char second;

    public BigramGoodEx(char first, char second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BigramGoodEx)) {
            return false;
        }
        BigramGoodEx b = (BigramGoodEx) o;
        return b.first == first && b.second == second;
    }

    public int hashCode() {
        return 31 * first + second;
    }

    public static void main(String[] args) {
        Set<BigramGoodEx> s = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                s.add(new BigramGoodEx(ch, ch));
            }
            System.out.println(s.size());
        }
    }
}
