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

package com.server.adapter;

import com.server.MQFilter;
import com.server.MQHandler;
import com.server.handle.DispatcherHandler;
import com.server.handle.FilteringMQHandler;
import com.server.handle.MQHandlerDecorator;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.HttpHandlerDecoratorFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.i18n.LocaleContextResolver;
import org.springframework.web.server.session.WebSessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This builder has two purposes:
 *
 * <p>One is to assemble a processing chain that consists of a target {@link WebHandler},
 * then decorated with a set of {@link WebFilter WebFilters}, then further decorated with
 * a set of {@link WebExceptionHandler WebExceptionHandlers}.
 *
 * <p>The second purpose is to adapt the resulting processing chain to an {@link HttpHandler}:
 * the lowest-level reactive HTTP handling abstraction which can then be used with any of the
 * supported runtimes. The adaptation is done with the help of {@link WebHttpMQHandlerBuilder}.
 *
 * <p>The processing chain can be assembled manually via builder methods, or detected from
 * a Spring {@link ApplicationContext} via {@link #applicationContext}, or a mix of both.
 *
 * @author LZJ
 * @since 5.0
 * @see WebMQHandlerBuilder
 */
public final class WebMQHandlerBuilder {

	private final MQHandler mqHandler;

	private final List<MQFilter> filters = new ArrayList<>();

	private final List<WebExceptionHandler> exceptionHandlers = new ArrayList<>();

	@Nullable
	private Function<MQHandler, MQHandler> httpHandlerDecorator;

	@Nullable
	private final ApplicationContext applicationContext;


	private WebMQHandlerBuilder(MQHandler mqHandler, @Nullable ApplicationContext applicationContext) {
		//Assert.notNull(mqHandler, "WebHandler must not be null");
		this.mqHandler = mqHandler;
		this.applicationContext = applicationContext;
	}
	/**
	 * Static factory method to create a new builder instance by detecting beans
	 * in an {@link ApplicationContext}. The following are detected:
	 * <ul>
	 * <li>{@link WebHandler} [1] -- looked up by the name
	 * {@link #WEB_HANDLER_BEAN_NAME}.
	 * <li>{@link WebFilter} [0..N] -- detected by type and ordered,
	 * see {@link AnnotationAwareOrderComparator}.
	 * <li>{@link WebExceptionHandler} [0..N] -- detected by type and
	 * ordered.
	 * <li>{@link HttpHandlerDecoratorFactory} [0..N] -- detected by type and
	 * ordered.
	 * <li>{@link WebSessionManager} [0..1] -- looked up by the name
	 * {@link #WEB_SESSION_MANAGER_BEAN_NAME}.
	 * <li>{@link ServerCodecConfigurer} [0..1] -- looked up by the name
	 * {@link #SERVER_CODEC_CONFIGURER_BEAN_NAME}.
	 * <li>{@link LocaleContextResolver} [0..1] -- looked up by the name
	 * {@link #LOCALE_CONTEXT_RESOLVER_BEAN_NAME}.
	 * </ul>
	 * @param context the application context to use for the lookup
	 * @return the prepared builder
	 */
	public static WebMQHandlerBuilder applicationContext(ApplicationContext context) {

		WebMQHandlerBuilder builder = new WebMQHandlerBuilder(
				new DispatcherHandler(), context);

		List<MQFilter> webFilters = context
				.getBeanProvider(MQFilter.class)
				.orderedStream()
				.collect(Collectors.toList());
		builder.filters(filters -> filters.addAll(webFilters));

//		List<WebExceptionHandler> exceptionHandlers = context
//				.getBeanProvider(WebExceptionHandler.class)
//				.orderedStream()
//				.collect(Collectors.toList());
//		builder.exceptionHandlers(handlers -> handlers.addAll(exceptionHandlers));

		return builder;
	}

	/**
	 * Manipulate the "live" list of currently configured filters.
	 * @param consumer the consumer to use
	 */
	public WebMQHandlerBuilder filters(Consumer<List<MQFilter>> consumer) {
		consumer.accept(this.filters);
		//updateFilters();
		return this;
	}

	public MQHandler build() {
		MQHandler filteringMQHandler = new FilteringMQHandler(this.mqHandler, this.filters);
		MQHandler mqHandlerDecorator = new MQHandlerDecorator(filteringMQHandler);
		//HttpWebHandlerAdapter adapted = new HttpWebHandlerAdapter(decorated);
		return  mqHandlerDecorator;
	}
//	private void updateFilters() {
//		if (this.filters.isEmpty()) {
//			return;
//		}
//
//		List<WebFilter> filtersToUse = this.filters.stream()
//				.collect(Collectors.toList());
//
//		this.filters.clear();
//		this.filters.addAll(filtersToUse);
//	}
}
