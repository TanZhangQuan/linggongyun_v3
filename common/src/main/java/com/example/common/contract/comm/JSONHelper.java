package com.example.common.contract.comm;


import com.alibaba.fastjson.JSONObject;
import com.example.common.contract.exception.DefineException;

/**
 * @author 宫清
 * @description JSON 处理辅助类
 * @date 2019年7月20日 下午5:35:31
 * @since JDK1.7
 */
public class JSONHelper {

    private JSONHelper() {
    }

    /**
     * @param json
     * @return
     * @throws DefineException
     * @description 格式化json data数据
     * @author 宫清
     * @date 2019年7月20日 下午5:34:38
     */
    @SuppressWarnings("unchecked")
    public static <T> T castDataJson(JSONObject json, Class<T> clz) throws DefineException {
        Object obj = json.get("data");
        int code = json.getIntValue("code");
        if (code != 0 && obj == null) {
            throw new DefineException(json.getString("message"));
        }
        return (T) obj;
    }
}
