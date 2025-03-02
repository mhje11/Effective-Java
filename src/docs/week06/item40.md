# @Override 애너테이션을 일관되게 사용하라

### @Override 애너테이션 사용 안했을 시
- 오버라이딩 대신 오버로딩 사용한 경우
```java
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
```
### @Overriding 애너테이션 사용한 예시
- 상위 클래스의 메서드를 재정의하려는 모든 메서드에 @Override 에너테이션을 달자

```java
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
```