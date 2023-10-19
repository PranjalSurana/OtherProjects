package com.fidelity.testsupport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fidelity.services.ApplicationCommonConfig;
import com.fidelity.services.ImportantService;
import com.fidelity.services.StringProvider;
import com.fidelity.services.StringProviderMockImpl;

/*
 * Cannot be in the same package as the other config classes, or it will be discovered automatically
 */
@Configuration
@Import(ApplicationCommonConfig.class)
public class ApplicationTestConfig {
	@Bean
	public StringProvider mockStringProvider() {
		return new StringProviderMockImpl();
	}

	@Bean
	public ImportantService importantService(StringProvider sp) {
		// have opportunity to interact with StringProvider here
		return new ImportantService(sp);
	}
}
