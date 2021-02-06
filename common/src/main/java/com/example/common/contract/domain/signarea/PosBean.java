package com.example.common.contract.domain.signarea;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 签署区位置信息
 * @author 宫清
 * @date 2019年7月18日 下午3:41:47
 * @since JDK1.7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosBean {

	//页码信息
	private String posPage;
	
	//x坐标，默认空
	private Float posX;
	
	//y坐标
	private Float posY;
	
	//签署区宽，默认印章宽度
	private Float width;
	
	//是否添加签署时间戳，默认不添加，格式 yyyy-MM-dd
	private Boolean addSignTime;

}
