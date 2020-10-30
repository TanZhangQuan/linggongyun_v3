import com.example.common.util.Tools;
import com.example.merchant.service.LianLianPayService;
import com.example.merchant.service.PaymentOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

@Slf4j
public class TestDete {

    @Test
    void Test(){

    }

    public static void main(String[] args) {
        TestDete testDete = new TestDete();

        Thread thread = new Thread(() -> {
            int i = 0;
            while (true) {
                testDete.sy("123");
            }
        });
        Thread thread1 = new Thread(() -> {
            while (true) {
                testDete.sy("321");
            }
        });
        thread.start();
//        thread1.start();
    }
    void sy(String id){
        //log.info(id+"进入方法。。。。。。。。。");
        synchronized (id) {
            log.info(id+"进入同步代码块。。。。。。。。。");
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            log.info(id+"执行");
        }
    }
}
