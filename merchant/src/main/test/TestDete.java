import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDete {

    public static void main(String[] args) {
        TestDete testDete = new TestDete();
        System.out.println(testDete.f(100));
    }

    long f(int n) {
        if (n == 1) {
            return 1;
        }
        // 相同重复逻辑，缩小问题的规模
        return n * f(n - 1);
    }
}
