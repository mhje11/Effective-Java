package chapter6.item40.ex1;

import java.util.HashSet;
import java.util.Set;

public class BigramBadEx {
    private final char first;
    private final char second;

    public BigramBadEx(char first, char second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(BigramBadEx b) {
        return b.first == first && b.second == second;
    }

    public int hashCode() {
        return 31 * first + second;
    }

    public static void main(String[] args) {
        Set<BigramBadEx> s = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            //equals, hashcode 를 오버라이딩 하지 않아서 각 Bigram 이 다른 객체로 판단됨
            for (char ch = 'a'; ch <= 'z'; ch++) {
                s.add(new BigramBadEx(ch, ch));
            }
            System.out.println(s.size());
        }
    }
}
