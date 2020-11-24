import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.example.common.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class TestDete {

    public static void main(String[] args) throws Exception {
        InputStream inputStream = new FileInputStream("D:/TemplateFile/acceptanceCertificate.xlsx");
        System.out.println(inputStream.available());
        File file=new File("D:\\TemplateFile\\acceptanceCertificate.xlsx");
        ExcelReader excelReader = ExcelUtil.getReader(file, "acceptanceCertificate");
        List<List<Object>> read = excelReader.read(2, excelReader.getRowCount());
        System.out.println(read.toString());
        List<String> list = ExcelUtils.importExcel(inputStream, 1, 0,false, String.class);
        System.out.println(list.toString());
    }

    @Test
    void Test() {
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
