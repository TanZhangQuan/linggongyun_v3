package com.example.paas;

import com.example.mybatis.mapper.PaymentOrderDao;
import com.example.paas.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class PaasApplicationTests {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private PaymentOrderDao paymentOrderDao;

	@Value("${PWD_KEY")
	private String PWD_KEY;

	@Test
	void contextLoads() {
		String[] files = {"pdf","jpg","png","rar" ,"zip","7z","arj"};
		System.out.println(Arrays.toString(files));
	}

}
