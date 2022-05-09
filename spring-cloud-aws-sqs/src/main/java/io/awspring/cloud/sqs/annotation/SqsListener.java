/*
 * Copyright 2013-2022 the original author or authors.
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
package io.awspring.cloud.sqs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * Annotation for mapping a {@link org.springframework.messaging.Message} onto listener methods by matching to the
 * message destination. The destination can be a logical queue name (CloudFormation), a physical queue name or a queue
 * URL.
 * <p>
 * Listener methods which are annotated with this annotation are allowed to have flexible signatures. They may have
 * arguments of the following types, in arbitrary order:
 * <ul>
 * <li>{@link org.springframework.messaging.Message} to get access to the complete message being processed.</li>
 * <li>{@link org.springframework.messaging.handler.annotation.Payload}-annotated method arguments to extract the
 * payload of a message and optionally convert it using a
 * {@link org.springframework.messaging.converter.MessageConverter}. The presence of the annotation is not required
 * since it is assumed by default for method arguments that are not annotated.
 * <li>{@link org.springframework.messaging.handler.annotation.Header}-annotated method arguments to extract a specific
 * header value along with type conversion with a {@link org.springframework.core.convert.converter.Converter} if
 * necessary.</li>
 * <li>{@link org.springframework.messaging.handler.annotation.Headers}-annotated argument that must also be assignable
 * to {@link java.util.Map} for getting access to all headers.</li>
 * <li>{@link org.springframework.messaging.MessageHeaders} arguments for getting access to all headers.</li>
 * <li>{@link org.springframework.messaging.support.MessageHeaderAccessor}</li>
 * <li>{@link io.awspring.cloud.messaging.listener.Acknowledgment} to be able to acknowledge the reception of a message
 * an trigger the deletion of it. This argument is only available when using the deletion policy
 * {@link SqsMessageDeletionPolicy#NEVER}.</li>
 * </ul>
 * <p>
 * Additionally a deletion policy can be chosen to define when a message must be deleted once the listener method has
 * been called. To get an overview of the available deletion policies read the {@link SqsMessageDeletionPolicy}
 * documentation.
 * </p>
 * <p>
 * By default the return value is wrapped as a message and sent to the destination specified with an
 * {@link org.springframework.messaging.handler.annotation.SendTo @SendTo} method-level annotation.
 *
 * @author Alain Sahli
 * @author Matej Nedic
 * @author Tomaz Fernandes
 * @since 1.1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqsListener {

	/**
	 * List of queue names. The returned queues will be handled by the same
	 * {@link io.awspring.cloud.sqs.listener.MessageListenerContainer};
	 * @return list of queues
	 */
	String[] value() default {};

	@AliasFor("value")
	String[] queueNames() default {};

	/**
	 * The {@link io.awspring.cloud.sqs.config.MessageListenerContainerFactory} bean name to be used to process this
	 * endpoint.
	 * @return the factory bean name.
	 */
	String factory() default "";

	/**
	 * An ID for the {@link io.awspring.cloud.sqs.listener.MessageListenerContainer} that will be created to handle this
	 * endpoint. If none provided a default ID will be used.
	 * @return the container id.
	 */
	String id() default "";

	/**
	 * The maximum number of inflight messages from each queue in that this endpoint should process simultaneously.
	 * @return the maximum number of inflight messages.
	 */
	String maxInflightMessagesPerQueue() default "";

	/**
	 * The maximum number of seconds to wait for messages in a given poll.
	 * @return the poll timeout.
	 */
	String pollTimeoutSeconds() default "";

	/**
	 * The minimum amount of seconds a message needs to be processed by this listener. If by the time the message is
	 * processed the remaining visibility is less than this value, it will be automatically extended to this value.
	 * @return the minimum visibility for this endpoint.
	 * @see io.awspring.cloud.sqs.listener.interceptor.MessageVisibilityExtenderInterceptor
	 */
	String minimumVisibility() default "";

}