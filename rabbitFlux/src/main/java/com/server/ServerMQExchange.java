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

package com.server;

import com.mq.server.ServerMqRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Contract for an MQ-HTTP request-response interaction. Provides access to the HTTP
 * request and response and also exposes additional server-side processing
 * related properties and features such as request attributes.
 *
 * @author LIU ZJ
 * @since 5.0
 */
public interface ServerMQExchange {



	/**
	 * Return the current HTTP request.
	 */
	ServerMqRequest getRequest();
	ServerMqRequest setRequest(ServerMqRequest serverMqRequest);

	ServerMqRequest setRequest();

	/**
	 * Return the current HTTP response.
	 */
	ServerHttpResponse getResponse();

	/**
	 * Return a mutable map of request attributes for the current exchange.
	 */
	Map<String, Object> getAttributes();

	/**
	 * Return the request attribute value if present.
	 * @param name the attribute name
	 * @param <T> the attribute type
	 * @return the attribute value
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	default <T> T getAttribute(String name) {
		return (T) getAttributes().get(name);
	}




	/**
	 * Builder for mutating an existing {@link ServerMQExchange}.
	 * Removes the need
	 */
	interface Builder {

		/**
		 * Configure a consumer to modify the current request using a builder.
		 * <p>Effectively this:
		 * <pre>
		 * exchange.mutate().request(builder -&gt; builder.method(HttpMethod.PUT));
		 *
		 * // vs...
		 *
		 * ServerHttpRequest request = exchange.getRequest().mutate()
		 *     .method(HttpMethod.PUT)
		 *     .build();
		 *
		 * exchange.mutate().request(request);
		 * </pre>
		 * @see ServerHttpRequest#mutate()
		 */
		Builder request(Consumer<ServerHttpRequest.Builder> requestBuilderConsumer);

		/**
		 * Set the request to use especially when there is a need to override
		 * {@link ServerHttpRequest} methods. To simply mutate request properties
		 * see {@link #request(Consumer)} instead.
		 * @see org.springframework.http.server.reactive.ServerHttpRequestDecorator
		 */
		Builder request(ServerHttpRequest request);

		/**
		 * Set the response to use.
		 * @see org.springframework.http.server.reactive.ServerHttpResponseDecorator
		 */
		Builder response(ServerHttpResponse response);

		/**
		 * Set the {@code Mono<Principal>} to return for this exchange.
		 */
		Builder principal(Mono<Principal> principalMono);

		/**
		 * Build a {@link ServerMQExchange} decorator with the mutated properties.
		 */
		ServerMQExchange build();
	}

}
