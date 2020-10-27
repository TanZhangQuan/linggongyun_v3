package com.example.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//测试接口
public class Demo {
	public static void main(String[] args) {
		KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
		try {
			//第一个参数是快递公司简称（YD -- 韵达速递）
			//第二个参数是需要查询的快递单号
			String result = api.getOrderTracesByJson("STO", "773062976582817");
			JSONObject jsonObject = JSONObject.fromObject(result);
			String ShipperCode = jsonObject.getString("ShipperCode");
			String LogisticCode = jsonObject.getString("LogisticCode");
			String State = jsonObject.getString("State");
			JSONArray Traces = jsonObject.getJSONArray("Traces");
			System.out.print(result+"\n");
			System.out.println("快递名称"+ShipperCode);
			System.out.println("快递单号"+LogisticCode);
			for(int i = 0; i < Traces.size(); i++) {
			    JSONObject object = (JSONObject) Traces.get(i);
				String AcceptTime = object.getString("AcceptTime");
				String AcceptStation = object.getString("AcceptStation");
				System.out.println("时间："+AcceptTime+"\t"+AcceptStation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
