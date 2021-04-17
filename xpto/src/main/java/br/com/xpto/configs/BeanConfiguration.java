package br.com.xpto.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

	@Value("${receitaWS.base-url}")
	public String receitaWSBaseUrl;

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder.baseUrl(receitaWSBaseUrl).build();
	}

}
