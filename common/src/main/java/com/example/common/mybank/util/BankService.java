/**
 *
 */
package com.example.common.mybank.util;

import org.apache.commons.lang3.StringUtils;


/**
 * <p>注释</p>
 * @author fjl
 * @version $Id: BaseField.java, v 0.1 2013-11-12 下午3:38:12 fjl Exp $
 */
public enum BankService {
    create_instant_trade("mybank.tc.trade.pay.instant", "tpu"),
    create_ensure_trade("mybank.tc.trade.pay.ensure", "tpu"),
    create_deposit("mybank.tc.trade.deposit", "tpu"),
    personal_info_modify("mybank.tc.user.personal.info.modify", "tpu"),
    enterprise_info_modify("mybank.tc.user.enterprise.info.modify", "tpu"),
    personal_info_query("mybank.tc.user.personal.info.query", "tpu"),
    enterprise_info_query("mybank.tc.user.enterprise.info.query", "tpu"),
    account_balance("mybank.tc.user.account.balance", "tpu"),
    payment_to_card("mybank.tc.trade.paytocard", "tpu"),
    with_draw_to_card("mybank.tc.trade.withdrawtocard", "tpu"),
    trade_info_query("mybank.tc.trade.info.query", "tpu"),
    trade_query("mybank.tc.trade.query", "tpu"),
    trade_refund_ticket("mybank.tc.trade.refundticket", "tpu"),
    balance_transfer("mybank.tc.trade.transfer", "tpu"),
    trade_remit_subaccount("mybank.tc.trade.remit.subaccount", "tpu"),
    batch_file_payment_to_card("mybank.tc.trade.batchfilepaytocard", "tpu"),
    create_personal_member("mybank.tc.user.personal.register", "mag"),
    create_enterprise_member("mybank.tc.user.enterprise.register", "mag"),
    create_bank_card("mybank.tc.user.bankcard.bind", "mag"),
    unbind_bank_card("mybank.tc.user.bankcard.unbind", "mag"),
    bank_card_query("mybank.tc.user.bankcard.query", "mag"),
    alipay_bind("mybank.tc.user.alipay.bind", "mag"),
    query_personal_info("mybank.tc.user.personal.info.query", "mag"),
    ;


    private String serviceName;

    private String serviceUrl;

    private BankService(String serviceName, String serviceUrl) {
        this.serviceName = serviceName;
        this.setServiceUrl(serviceUrl);
    }


    public String getServiceName() {
        return serviceName;
    }


    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public static BankService getByServiceName(String serviceName) {
        if (StringUtils.isBlank(serviceName)) {
            return null;
        }
        for (BankService item : values()) {
            if (item.getServiceName().equals(serviceName)) {
                return item;
            }
        }
        return null;
    }


    public String getServiceUrl() {
        return serviceUrl;
    }


    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
