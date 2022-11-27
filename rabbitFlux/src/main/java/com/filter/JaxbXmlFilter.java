/*
 * Copyright 2002-2020 the original author or authors.
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

package com.filter;

import com.server.MQFilter;
import com.server.MQFilterChain;
import com.server.ServerMQExchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * A {@link MQFilter} that performs covert mq message. An outline of
 * the logic:
 *
 * <ul>
 * <li>A request comes in and if it does not match
 * {@link #setRequiresAuthenticationMatcher(ServerWebExchangeMatcher)}, then this filter
 * does nothing and the {@link WebFilterChain} is continued. If it does match then...</li>
 * <li>An attempt to convert the {@link ServerWebExchange} into an {@link Authentication}
 * is made. If the result is empty, then the filter does nothing more and the
 * {@link WebFilterChain} is continued. If it does create an {@link Authentication}...
 * </li>
 * <li>The {@link ReactiveAuthenticationManager} specified in
 * {@link #JaxbXmlFilter(ReactiveAuthenticationManager)} is used to perform
 * authentication.</li>
 * <li>The {@link ReactiveAuthenticationManagerResolver} specified in
 * {@link #JaxbXmlFilter(ReactiveAuthenticationManagerResolver)} is used to
 * resolve the appropriate authentication manager from context to perform authentication.
 * </li>
 * <li>If authentication is successful, {@link ServerAuthenticationSuccessHandler} is
 * invoked and the authentication is set on {@link ReactiveSecurityContextHolder}, else
 * {@link ServerAuthenticationFailureHandler} is invoked</li>
 * </ul>
 *
 * @author LIU ZJ
 * @since 5.0
 */
public class JaxbXmlFilter implements MQFilter {

	private static final Log logger = LogFactory.getLog(JaxbXmlFilter.class);

	//private final ReactiveAuthenticationManagerResolver<ServerWebExchange> authenticationManagerResolver;


	private ServerSecurityContextRepository securityContextRepository = NoOpServerSecurityContextRepository
			.getInstance();

	private ServerWebExchangeMatcher requiresAuthenticationMatcher = ServerWebExchangeMatchers.anyExchange();

	/**
	 * Creates an instance
	 * @param authenticationManager the authentication manager to use
	 */
	public JaxbXmlFilter(ReactiveAuthenticationManager authenticationManager) {
		//Assert.notNull(authenticationManager, "authenticationManager cannot be null");
		//this.authenticationManagerResolver = (request) -> Mono.just(authenticationManager);
	}

	/**
	 * Creates an instance
	 * @param authenticationManagerResolver the authentication manager resolver to use
	 * @since 5.3
	 */
	public JaxbXmlFilter(
			ReactiveAuthenticationManagerResolver<ServerWebExchange> authenticationManagerResolver) {
		//Assert.notNull(authenticationManagerResolver, "authenticationResolverManager cannot be null");
		//this.authenticationManagerResolver = authenticationManagerResolver;
	}

	@Override
	public Mono<Void> filter(ServerMQExchange exchange, MQFilterChain chain) {
		logger.info("is called");
		 return  null;
//		return this.requiresAuthenticationMatcher.matches(exchange).filter((matchResult) -> matchResult.isMatch());
//				.flatMap((matchResult) -> this.authenticationConverter.convert(exchange))
//				.onErrorResume(AuthenticationException.class, (ex) -> this.authenticationFailureHandler
//						.onAuthenticationFailure(new WebFilterExchange(exchange, chain), ex));
	}


}
