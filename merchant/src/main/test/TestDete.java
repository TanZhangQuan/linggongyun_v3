import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TestDete {



    @Test
    void testDete() {
        //生成0-9的随机数
        System.out.println();

        Date date = new Date();
        int hours = date.getHours();
        if (hours <= 8 && hours > 4) {
            System.out.println("早上");
        } else if (hours <= 11) {
            System.out.println("上午");
        } else if (hours <= 13) {
            System.out.println("中午");
        } else if (hours <= 18) {
            System.out.println("下午");
        } else {
            System.out.println("晚上");
        }
    }
}
