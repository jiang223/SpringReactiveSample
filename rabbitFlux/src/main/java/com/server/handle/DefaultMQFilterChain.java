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

package com.server.handle;

import com.server.MQFilter;
import com.server.MQFilterChain;
import com.server.MQHandler;
import com.server.ServerMQExchange;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Default implementation of {@link MQFilterChain}.
 *
 * <p>Each instance of this class represents one link in the chain. The public
 * constructor {@link #DefaultMQFilterChain(MQHandler, List)}
 * initializes the full chain and represents its first link.
 *
 * <p>This class is immutable and thread-safe. It can be created once and
 * re-used to handle request concurrently.
 *
 * @author LZJ
 * @since 5.0
 */
public class DefaultMQFilterChain implements MQFilterChain {

	private final List<MQFilter> allFilters;

	private final MQHandler handler;

	@Nullable
	private final MQFilter currentFilter;

	@Nullable
	private final DefaultMQFilterChain chain;


	/**
	 * Public constructor with the list of filters and the target handler to use.
	 * @param handler the target handler
	 * @param filters the filters ahead of the handler
	 * @since 5.1
	 */
	public DefaultMQFilterChain(MQHandler handler, List<MQFilter> filters) {
		Assert.notNull(handler, "MQHandler is required");
		this.allFilters = Collections.unmodifiableList(filters);
		this.handler = handler;
		DefaultMQFilterChain chain = initChain(filters, handler);
		this.currentFilter = chain.currentFilter;
		this.chain = chain.chain;
	}

	private static DefaultMQFilterChain initChain(List<MQFilter> filters, MQHandler handler) {
		DefaultMQFilterChain chain = new DefaultMQFilterChain(filters, handler, null, null);
		ListIterator<? extends MQFilter> iterator = filters.listIterator(filters.size());
		while (iterator.hasPrevious()) {
			chain = new DefaultMQFilterChain(filters, handler, iterator.previous(), chain);
		}
		return chain;
	}

	/**
	 * Private constructor to represent one link in the chain.
	 */
	private DefaultMQFilterChain(List<MQFilter> allFilters, MQHandler handler,
                                 @Nullable MQFilter currentFilter, @Nullable DefaultMQFilterChain chain) {

		this.allFilters = allFilters;
		this.currentFilter = currentFilter;
		this.handler = handler;
		this.chain = chain;
	}

	/**
	 * Public constructor with the list of filters and the target handler to use.
	 * @param handler the target handler
	 * @param filters the filters ahead of the handler
	 * @deprecated as of 5.1 this constructor is deprecated in favor of
	 * {@link #DefaultMQFilterChain(MQHandler, List)}.
	 */
	@Deprecated
	public DefaultMQFilterChain(MQHandler handler, MQFilter... filters) {
		this(handler, Arrays.asList(filters));
	}


	public List<MQFilter> getFilters() {
		return this.allFilters;
	}

	public MQHandler getHandler() {
		return this.handler;
	}


	@Override
	public Mono<Void> filter(ServerMQExchange exchange) {
		return Mono.defer(() ->
				this.currentFilter != null && this.chain != null ?
						invokeFilter(this.currentFilter, this.chain, exchange) :
						this.handler.handle(exchange));
	}

	private Mono<Void> invokeFilter(MQFilter current, DefaultMQFilterChain chain, ServerMQExchange exchange) {
		String currentName = current.getClass().getName();
		return current.filter(exchange, chain).checkpoint(currentName + " [DefaultMQFilterChain]");
	}

}
