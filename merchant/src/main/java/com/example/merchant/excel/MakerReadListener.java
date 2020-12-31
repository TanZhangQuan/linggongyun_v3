package com.example.merchant.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * MakerReadListener
 *
 * @author xjw
 * @since 2020/12/30 11:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MakerReadListener extends AnalysisEventListener<MakerExcel> {

    /**
     * 自定义用于暂时存储
     * 可以通过实例获取该值
     */
    private List<MakerExcel> list = new ArrayList<>();

    @Override
    public void invoke(MakerExcel makerExcel, AnalysisContext context) {
        list.add(makerExcel);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //解析结束销毁不用的资源
        //注意不要调用datas.clear(),否则getDatas为null
    }

}
