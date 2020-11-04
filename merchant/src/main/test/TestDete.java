import com.example.common.enums.UserType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
public class TestDete {

    @Test
    void Test() {
    }

    public static void main(String[] args) throws Exception{
        InputStream inputStream = new FileInputStream("D:/TemplateFile/inventory.xlsx");
        System.out.println(inputStream.available());
    }

    void sy(String id) {
        //log.info(id+"进入方法。。。。。。。。。");
        synchronized (id) {
            log.info(id + "进入同步代码块。。。。。。。。。");
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            log.info(id + "执行");
        }
    }
}
