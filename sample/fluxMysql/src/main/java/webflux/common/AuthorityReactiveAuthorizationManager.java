/*
 * Copyright 2002-2021 the original author or authors.
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

package webflux.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;
import webflux.service.IUserService;

/**
 * A {@link ReactiveAuthorizationManager} that determines if the current user is
 * authorized by evaluating if the {@link Authentication} contains a specified authority.
 *
 * @param <T> the type of object being authorized
 * @author LZJ
 * @since 5.0
 */
public class AuthorityReactiveAuthorizationManager<T> implements ReactiveAuthorizationManager<T> {
	private static final Log logger = LogFactory.getLog(AuthorityReactiveAuthorizationManager.class);

	private IUserService userService;
	public AuthorityReactiveAuthorizationManager(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, T object) {
		// @formatter:off
		return authentication.filter(Authentication::isAuthenticated)
				.flatMapIterable(Authentication::getAuthorities)
				.map(GrantedAuthority::getAuthority)
				.flatMap((grantedAuthority) -> userService.findPatternMatcherByRole(grantedAuthority).flatMapIterable(aa->aa))
				.flatMap((matcher) ->matcher.matches(((AuthorizationContext)object).getExchange()))
				.any(matchResult -> matchResult.isMatch())
				.map((granted) -> ((AuthorizationDecision) new AuthorityAuthorizationDecision(granted, null)))
				.defaultIfEmpty(new AuthorityAuthorizationDecision(false, null));
//				.flatMap((matcher) ->matcher.matches(((AuthorizationContext)object).getExchange()).filter(ServerWebExchangeMatcher.MatchResult::isMatch))
//				.next()
//				.map((granted) -> ((AuthorizationDecision) new AuthorityAuthorizationDecision(true, null)))
//				.defaultIfEmpty(new AuthorityAuthorizationDecision(false, null));
		// @formatter:on
	}

}
