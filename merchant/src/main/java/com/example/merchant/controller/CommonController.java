package com.example.merchant.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "通用", tags = "通用")
@RestController
@RequestMapping("/common")
public class CommonController {

//    @PostMapping("/unionpay/notify")
//    @ApiOperation(value = "银联入金回调接收", notes = "银联入金回调接收")
//    public String depositNotice(@RequestParam(value = "inacctBillId", required = false) String inacctBillId,
//                                @RequestParam(value = "acctId", required = false) String acctId,
//                                @RequestParam(value = "pfmAcctId", required = false) String pfmAcctId,
//                                @RequestParam(value = "amount", required = false) String amount,
//                                @RequestParam(value = "rcvAcctNo", required = false) String rcvAcctNo,
//                                @RequestParam(value = "rcvAcctName", required = false) String rcvAcctName,
//                                @RequestParam(value = "rcvBankCode", required = false) String rcvBankCode,
//                                @RequestParam(value = "bizType", required = false) String bizType,
//                                @RequestParam(value = "subBillType", required = false) String subBillType,
//                                @RequestParam(value = "remarks", required = false) String remarks,
//                                HttpServletRequest request) {
//
//        //验签处理
//        Map<String, String> params = Maps.newHashMap();
//        params.put("merch_no", merchNo);//商户号
//        params.put("method", unionPayMethod.getValue());
//        params.put("version", "1.0");
//        params.put("acct_no", acctNo);//平台账户账号
//        //加密业务参数
//        String encrypt = AlipaySignature.rsaEncrypt(JSON.toJSONString(content), pfmpubkey, "utf-8");
//        params.put("content", encrypt);
//        params.put("sign", createSign(prikey, params));
//        log.info("请求参数：{}", JSON.toJSONString(params));
//
//        UnionpayUtil.checkSign(map, publicKey);
//
//        //获取请求头信息
//        Enumeration headerNames = request.getHeaderNames();
//        //使用循环遍历请求头，并通过getHeader()方法获取一个指定名称的头字段
//        while (headerNames.hasMoreElements()) {
//            String headerName = (String) headerNames.nextElement();
//            log.info("沙盒模拟入金回调请求头:" + headerName + " : " + request.getHeader(headerName));
//        }
//        Map<String, Object> parameters = WebUtils.getParametersStartingWith(request, "");
//        log.info("沙盒模拟入金回调接收:" + JSON.toJSONString(parameters));
//        log.info("沙盒模拟入金回调接收:inacctBillId: " + inacctBillId + "  acctId :" + acctId);
//        return "success";
//    }

}
