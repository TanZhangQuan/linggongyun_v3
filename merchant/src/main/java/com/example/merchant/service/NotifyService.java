package com.example.merchant.service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 异步回调相关接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface NotifyService {

    /**
     * 银联入金回调接收
     *
     * @param request
     */
    String depositNotice(HttpServletRequest request) throws Exception;

}
