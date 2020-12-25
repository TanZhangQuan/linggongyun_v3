import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestDete {

    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.parallelStream().forEach(integer -> System.out.println(integer.toString()));
    }

    long f(int n) {
        if (n == 1) {
            return 1;
        }
        // 相同重复逻辑，缩小问题的规模
        return n * f(n - 1);
    }
}
