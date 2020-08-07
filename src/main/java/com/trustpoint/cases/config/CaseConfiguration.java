package com.trustpoint.cases.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CaseConfiguration {
	@Value("${CASE_OWNER_HAS_FULL_ACCESS:TRUE}")
	private String caseOwnerHasFullAccess;

	@Value("${DISABLE_CASE_ACCESS_IF_UNASSIGNED:TRUE}")
	private String disableCaseAccessIfUnassigned;

	@Autowired
	private Environment env;

	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}

	public boolean allowAccessIfUnassigned() {
		return !disableCaseAccessIfUnassigned.equals("TRUE");
	}

	public boolean caseOwnerHasAccess() {
		return caseOwnerHasFullAccess.equals("TRUE");
	}

	public String getAlertsURI() {
		String host = env.getProperty("alerts.service.host");

		String port = env.getProperty("alerts.service.port");

		return "http://" + host + ":" + port;
	}
}
