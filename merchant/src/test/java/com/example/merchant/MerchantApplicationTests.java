package com.example.merchant;

import com.example.common.sms.SenSMS;
import com.example.merchant.service.InvoiceService;
import com.example.merchant.service.PaymentOrderService;
import com.example.merchant.service.impl.MerchantServiceImpl;
import com.example.mybatis.entity.Invoice;
import com.example.mybatis.mapper.InvoiceDao;
import com.example.mybatis.mapper.PaymentOrderDao;
import com.example.mybatis.po.InvoicePO;
import com.example.redis.dao.RedisDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MerchantApplicationTests {

	@Autowired
	private MerchantServiceImpl merchantService;
	@Value("${PWD_KEY}")
	private String PWD_KEY;

	@Autowired
	private SenSMS senSMS;

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private PaymentOrderService paymentOrderService;

	@Autowired
	private PaymentOrderDao paymentOrderDao;

	@Test
	void contextLoads() {
//		Merchant merchant = new Merchant();
//		merchant.setSalesManId("1111");
//		merchant.setUserName("yixi");
//		merchant.setPassWord(PWD_KEY+ MD5.md5("123"));
//		merchant.setCompanyId("111");
//		merchantService.save(merchant);
//		System.out.println(StringUtils.isBlank(""));
//		Map<String, Object> map = senSMS.senSMS("12312312312");
//		System.out.println(map.get("statusCode"));

//		redisDao.set("key","value");
//		System.out.println(redisDao.get("key"));
//		boolean b = Tools.checkMobileNumber("17373671818");
//		System.out.println(b);

//		PaymentOrder paymentOrder = new PaymentOrder();
//		paymentOrder.setMerchantId("3666abe4ec7691d8c83d5b7b4d257bc9");
//		paymentOrder.setPackageStatus(0);
//		paymentOrder.setPaymentOrderStatus(1);
//		paymentOrder.setRealMoney(BigDecimal.valueOf(28888.2222));
//
//		PaymentOrder paymentOrder2 = new PaymentOrder();
//		paymentOrder2.setMerchantId("3666abe4ec7691d8c83d5b7b4d257bc9");
//		paymentOrder2.setPackageStatus(0);
//		paymentOrder2.setPaymentOrderStatus(0);
//		paymentOrder2.setRealMoney(BigDecimal.valueOf(28888.2222));
//
//		PaymentOrder paymentOrder3 = new PaymentOrder();
//		paymentOrder3.setMerchantId("3666abe4ec7691d8c83d5b7b4d257bc9");
//		paymentOrder3.setPackageStatus(1);
//		paymentOrder3.setPaymentOrderStatus(1);
//		paymentOrder3.setRealMoney(BigDecimal.valueOf(28888.2222));
//		List<PaymentOrder> list = new ArrayList<>();
//		list.add(paymentOrder);
//		list.add(paymentOrder2);
//		list.add(paymentOrder3);
//		boolean b = paymentOrderService.saveBatch(list);
//		System.out.println(b);

//		List<PaymentOrderPO> paymentOrderPOS = paymentOrderDao.selectBy30Day("3666abe4ec7691d8c83d5b7b4d257bc9");
//		for (PaymentOrderPO payPo : paymentOrderPOS){
//			System.out.println(payPo.getPackageStatus()+"=================="+payPo.getTotalMoney());
//		}

	}

	@Test
	void getTotal(){
//		List<PaymentOrderPO> paymentOrderPOS = paymentOrderDao.selectTotal("3666abe4ec7691d8c83d5b7b4d257bc9");
//		for (PaymentOrderPO payPo : paymentOrderPOS){
//			System.out.println(payPo.getPackageStatus()+"=================="+payPo.getTotalMoney());
//		}
	}


	@Autowired
	private InvoiceService invoiceService;

	@Test
	void TestInvoice(){
		Invoice invoice = new Invoice();
		invoice.setMerchantId("3666abe4ec7691d8c83d5b7b4d257bc9");
		invoice.setPackageStatus(0);
		invoice.setInvoiceMoney(BigDecimal.valueOf(2888.22));

		Invoice invoice1 = new Invoice();
		invoice1.setMerchantId("3666abe4ec7691d8c83d5b7b4d257bc9");
		invoice1.setPackageStatus(0);
		invoice1.setInvoiceMoney(BigDecimal.valueOf(2888.22));

		Invoice invoice2 = new Invoice();
		invoice2.setMerchantId("3666abe4ec7691d8c83d5b7b4d257bc9");
		invoice2.setPackageStatus(1);
		invoice2.setInvoiceMoney(BigDecimal.valueOf(2888.22));

		List<Invoice> invoices = new ArrayList<>();
		invoices.add(invoice);
		invoices.add(invoice1);
		invoices.add(invoice2);
		invoiceService.saveBatch(invoices);

	}

	@Autowired
	private InvoiceDao invoiceDao;

	@Test
	void TestInvoiceDao(){
		List<InvoicePO> invoicePOS = invoiceDao.selectTotal("3666abe4ec7691d8c83d5b7b4d257bc9");
		for (InvoicePO in : invoicePOS ){
			System.out.println(in.getPackageStatus()+"------"+in.getTotalMoney()+"-------"+in.getCount());
		}
	}

}
