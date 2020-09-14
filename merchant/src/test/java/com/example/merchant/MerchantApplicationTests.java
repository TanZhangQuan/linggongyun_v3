package com.example.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.sms.SenSMS;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.PaymentOrderDto;
import com.example.merchant.service.InvoiceService;
import com.example.merchant.service.PaymentOrderService;
import com.example.merchant.service.WorkerService;
import com.example.merchant.service.impl.MerchantServiceImpl;
import com.example.mybatis.entity.Invoice;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.PaymentOrder;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.mapper.InvoiceDao;
import com.example.mybatis.mapper.PaymentOrderDao;
import com.example.mybatis.mapper.PaymentOrderManyDao;
import com.example.mybatis.mapper.WorkerDao;
import com.example.mybatis.po.InvoicePO;
import com.example.redis.dao.RedisDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
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

	@Test
	void TestPage(){
		Page<Invoice> invoicePage = new Page<>(2,2);
		QueryWrapper<Invoice> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("merchant_id","3666abe4ec7691d8c83d5b7b4d257bc9");
		Page<Invoice> invoicePages = invoiceDao.selectPage(invoicePage, queryWrapper);
		long total = invoicePages.getTotal();
		long pages = invoicePages.getPages();
		List<Invoice> records = invoicePages.getRecords();
		System.out.println("总页数"+pages);
		System.out.println("总记录数"+total);
		for (Invoice invoice : records){
			System.out.println(invoice.getId());
		}
	}

	@Autowired
	private WorkerService workerService;

	@Autowired
	private WorkerDao workerDao;

	@Test
	void workerServiceTest(){
//		Worker worker = new Worker();
//		worker.setMerchantId("3666abe4ec7691d8c83d5b7b4d257bc9");
//		worker.setAccountName("王五");
//		worker.setWorkerName("大冬瓜");
//		worker.setUserName("wangwu");
//		worker.setMobileCode("15243556316");
//		worker.setUserPwd(PWD_KEY+ MD5.md5("123"));
//		workerService.save(worker);
		ReturnJson workerAll = workerService.getWorkerAll("3666abe4ec7691d8c83d5b7b4d257bc9", 1, 2);
		Collection<Worker> data = workerAll.getData();
		for (Worker worker : data){
			System.out.println(worker.getAccountName());
		}
	}

	@Test
	void workerDaoTest(){
//		List<Worker> workers = workerDao.selectByIdAndAccountNameAndMobile("3666abe4ec7691d8c83d5b7b4d257bc9", "", "李四", "15243556316");
//		for (Worker worker : workers){
//			System.out.println(worker.getAccountName());
//		}
		List list = new ArrayList();
		list.add("15243556316");
		list.add("15243556317");
		list.add("15243556318");
		List<Worker> workers = workerDao.selectList(new QueryWrapper<Worker>().in("mobile_code", list));
		for (Worker worker : workers) {
			System.out.println(worker.getAccountName());
		}

	}

	@Test
	void PayTest(){
//		QueryWrapper<PaymentOrder> paymentOrderQueryWrapper = new QueryWrapper<>();
////		paymentOrderQueryWrapper.eq("merchant_id","3666abe4ec7691d8c83d5b7b4d257bc9");
////		paymentOrderQueryWrapper.eq("real_money","28888.22");
//		List<PaymentOrder> list = paymentOrderDao.selectList(paymentOrderQueryWrapper);
//		System.out.println(list.size());
//		Date date = DateUtil.fomatDate("2020-09-08");
//		System.out.println(date.toString());

	}


	@Test
	void payService(){
		PaymentOrderDto paymentOrderDto = new PaymentOrderDto();
		paymentOrderDto.setMerchantId("3666abe4ec7691d8c83d5b7b4d257bc9");
		paymentOrderDto.setBeginDate("2020-08-09");
		paymentOrderDto.setEndDate("2020-09-09");
		ReturnJson paymentOrder = paymentOrderService.getPaymentOrder(paymentOrderDto);
		System.out.println(paymentOrder.getData().size());
	}

	@Autowired
	private PaymentOrderManyDao paymentOrderManyDao;

	@Test
	void getWorker(){
//		PaymentOrderMany paymentOrderMany = new PaymentOrderMany();
//		paymentOrderMany.setMerchantId("3666abe4ec7691d8c83d5b7b4d257bc9");
//		paymentOrderMany.setTaxId("081843094c614c8d8b1107ffc078feb1");
//		paymentOrderMany.setCompanySName("广州医药有限公司");
//		paymentOrderMany.setTaxId("081843094c614c8d8b1107ffc078feb1");
//		paymentOrderMany.setRealMoney(BigDecimal.valueOf(6666.66));
//		int insert = paymentOrderManyDao.insert(paymentOrderMany);


		PaymentOrder paymentOrder = this.paymentOrderDao.selectById("1303227817155014657");
		paymentOrder.setId(null);
		paymentOrder.setCompositeTax(BigDecimal.valueOf(7.5));
		paymentOrder.setMerchantTax(BigDecimal.valueOf(4.5));
		paymentOrder.setReceviceTax(BigDecimal.valueOf(3));
		paymentOrder.setTaxStatus(2);
		List<PaymentInventory> list = new LinkedList<>();
		PaymentInventory paymentInventory = new PaymentInventory();
		paymentInventory.setWorkerId("172b0c1d479874f938a5cf344308f82e");
		paymentInventory.setWorkerName("李四");
		paymentInventory.setMobileCode("15243556316");
		paymentInventory.setIdCardCode("431021199910108765");
		paymentInventory.setBankName("建设银行");
		paymentInventory.setBankCode("40131232104102072141");
		paymentInventory.setAttestation(1);
		paymentInventory.setRealMoney(BigDecimal.valueOf(8888.22));
		list.add(paymentInventory);
		ReturnJson returnJson = paymentOrderService.saveOrUpdataPaymentOrder(paymentOrder, list);
		System.out.println(returnJson);

//		PaymentOrder paymentOrderDto = new PaymentOrder();
//		paymentOrderDto.setId("1304319493147660290");
//		paymentOrderDto.setRealMoney(BigDecimal.valueOf(22222.222));
//		paymentOrderDao.updateById(paymentOrderDto);

//		PaymentOrderDto paymentOrderDto = new PaymentOrderDto();
//		String s = JsonUtils.objectToJson(paymentOrderDto);
//		System.out.println(s);
	}

}
