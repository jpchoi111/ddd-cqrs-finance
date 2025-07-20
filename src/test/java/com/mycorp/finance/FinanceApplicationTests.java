package com.mycorp.finance;

import com.mycorp.finance.customer.application.service.CustomerCommandServiceImpl;
import com.mycorp.finance.global.config.CommandDataSourceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {CommandDataSourceConfig.class, CustomerCommandServiceImpl.class})
class FinanceApplicationTests {

	@Test
	void contextLoads() {
	}

}
