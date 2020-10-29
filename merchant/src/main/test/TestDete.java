import com.example.common.util.Tools;
import com.example.merchant.service.LianLianPayService;
import com.example.merchant.service.PaymentOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class TestDete {

    @Test
    void Test(){
        int i = Tools.getRandomNum();
        String s = "1308661072700252162";
        String str = i+s;
        System.out.println(str.substring(6));
    }
}
