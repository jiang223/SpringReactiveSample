/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.main;

import com.filter.CustomMQFilterChain;
import com.server.MQFilterChainProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author LIU ZJ
 * @since 5.0
 */
//@Configuration(proxyBeanMethods = false)
class MQFluxCustomConfiguration {

	public static final int MQ_FILTER_CHAIN_FILTER_ORDER = 0 - 100;

	private static final String BEAN_NAME_PREFIX = "com.webflux.config.MQFluxCustomConfiguration.";

	private static final String SPRING_SECURITY_WEBFILTERCHAINFILTER_BEAN_NAME = BEAN_NAME_PREFIX
			+ "MQFilterChainFilter";
	

	private List<CustomMQFilterChain> CustomMQFilterChains;

	@Autowired
	ApplicationContext context;

	@Autowired(required = false)
	void setCustomMQFilterChains(List<CustomMQFilterChain> CustomMQFilterChains) {
		this.CustomMQFilterChains = CustomMQFilterChains;
	}

	@Bean(SPRING_SECURITY_WEBFILTERCHAINFILTER_BEAN_NAME)
	@Order(MQ_FILTER_CHAIN_FILTER_ORDER)
	MQFilterChainProxy springCustomMQFilterChainFilter() {
		return new MQFilterChainProxy(getCustomMQFilterChains());
	}

//	@Bean(name = AbstractView.REQUEST_DATA_VALUE_PROCESSOR_BEAN_NAME)
//	CsrfRequestDataValueProcessor requestDataValueProcessor() {
//		return new CsrfRequestDataValueProcessor();
//	}
//
//	@Bean
//	static BeanFactoryPostProcessor conversionServicePostProcessor() {
//		return new RsaKeyConversionServicePostProcessor();
//	}

	private List<CustomMQFilterChain> getCustomMQFilterChains() {
		List<CustomMQFilterChain> result = this.CustomMQFilterChains;
		if (ObjectUtils.isEmpty(result)) {
			return Arrays.asList(springSecurityFilterChain());
		}
		return result;
	}

	private CustomMQFilterChain springSecurityFilterChain() {
		ServerMQConfig http = this.context.getBean(ServerMQConfig.class);
		return springSecurityFilterChain(http);
	}

	/**
	 * The default {@link ServerMQConfig} configuration.
	 * @param mqConfig
	 * @return
	 */
	private CustomMQFilterChain springSecurityFilterChain(ServerMQConfig mqConfig) {
		//http.authorizeExchange().anyExchange().authenticated();
//		if (isOAuth2Present && OAuth2ClasspathGuard.shouldConfigure(this.context)) {
//			OAuth2ClasspathGuard.configure(this.context, http);
//		}
//
//		http.httpBasic();
//		http.formLogin();

		CustomMQFilterChain result = mqConfig.build();
		return result;
	}

}
