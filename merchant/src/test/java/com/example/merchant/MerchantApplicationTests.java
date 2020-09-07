package com.example.merchant;

import com.example.merchant.service.impl.MerchantServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MerchantApplicationTests {

	@Autowired
	private MerchantServiceImpl merchantService;

	@Test
	void contextLoads() {
		merchantService.getById("");
	}

}
