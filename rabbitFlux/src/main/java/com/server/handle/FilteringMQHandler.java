/*
 * Copyright 2002-2018 the original author or authors.
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

package com.server.handle;

import com.server.MQFilter;
import com.server.MQHandler;
import com.server.ServerMQExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * {@link MQHandlerDecorator} that invokes a chain of {@link MQFilter MQFilters}
 * before invoking the delegate {@link MQHandler}.
 *
 * @author LZJ
 * @since 5.0
 */
public class FilteringMQHandler extends MQHandlerDecorator {

	private final DefaultMQFilterChain chain;


	/**
	 * Constructor.
	 * @param filters the chain of filters
	 */
	public FilteringMQHandler(MQHandler handler, List<MQFilter> filters) {
		super(handler);
		this.chain = new DefaultMQFilterChain(handler, filters);
	}


	/**
	 * Return a read-only list of the configured filters.
	 */
	public List<MQFilter> getFilters() {
		return this.chain.getFilters();
	}


	@Override
	public Mono<Void> handle(ServerMQExchange exchange) {
		return this.chain.filter(exchange);
	}

}
