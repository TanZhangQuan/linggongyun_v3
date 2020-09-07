package com.example.common.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@ApiModel(description = "统一返回对象")
public class ReturnJson<T> {
	@ApiModelProperty(notes = "状态码，成功：200 , 失败: 300, 登录超时或者无权限: 401，  格式错误： 201", value = "状态码")
	private int code;
	@ApiModelProperty(notes = "响应消息内容", value = "响应消息")
	private String message;
	@ApiModelProperty(notes = "返回数据集合", value = "返回数据集合")
	private Collection<T> data;
	@ApiModelProperty(notes = "分页数据,若无分页则可省略", value = "分页数据")
	private boolean finished;
	@ApiModelProperty(notes = "响应状态 success error", value = "响应状态 success error")
	private String state;
	@ApiModelProperty(notes = "返回总的记录数", value = "总的记录数")
	private Integer itemsCount = 0;
	@ApiModelProperty(notes = "返回每页显示条数", value = "每页显示条数")
	private Integer pageSize = 10;
	private T obj;

	public Integer getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(Integer itemsCount) {
		this.itemsCount = itemsCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
		if (code == 200) {
			this.state = "success";
		} else {
			setState("error");
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Collection<T> getData() {
		return data;
	}

	public void setData(Collection<T> data) {
		this.data = data;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public ReturnJson(String msg, int code) {

		this.message = msg;
		this.code = code;

		RichInfo sm = new RichInfo();
		List<RichInfo> lsm = new ArrayList<RichInfo>();
		sm.setContent(msg);
		lsm.add(sm);
		this.data = (Collection<T>) lsm;
		this.finished = false;
		if (code == 200) {
			this.state = "success";
		} else {
			this.state = "error";
		}
	}

	public ReturnJson() {
		this.finished = false;
	}

	public ReturnJson(String msg, String Content, int code) {

		this.message = msg;
		this.code = code;
		RichInfo sm = new RichInfo();
		List<RichInfo> lsm = new ArrayList<RichInfo>();
		sm.setContent(Content);
		lsm.add(sm);
		this.data = (Collection<T>) lsm;
		if (code == 200) {
			this.state = "success";
		} else {
			this.state = "error";
		}
		this.finished = false;
	}

	public ReturnJson(String msg, List<T> obj, int code) {

		this.message = msg;
		this.code = code;
		this.data = (Collection<T>) obj;
		this.finished = false;
		if (code == 200) {
			this.state = "success";
		} else {
			this.state = "error";
		}
	}
	public ReturnJson(String msg, List<T> obj, int RecCount, int code) {

		this.message = msg;
		this.code = code;
		this.data = (Collection<T>) obj;
		this.finished = false;
		if (code == 200) {
			this.state = "success";
		} else {
			this.state = "error";
		}
		this.itemsCount=RecCount;
	}
	public ReturnJson(String msg, List<T> obj, int code, boolean isf) {

		this.message = msg;
		this.code = code;
		this.data = (Collection<T>) obj;
		this.finished = isf;
		if (code == 200) {
			this.state = "success";
		} else {
			this.state = "error";
		}
	}
	public ReturnJson(String msg, List<T> obj, int RecCount, int code, boolean isf) {

		this.message = msg;
		this.code = code;
		this.data = (Collection<T>) obj;
		this.finished = isf;
		if (code == 200) {
			this.state = "success";
		} else {
			this.state = "error";
		}
		this.itemsCount=RecCount;
	}
}
