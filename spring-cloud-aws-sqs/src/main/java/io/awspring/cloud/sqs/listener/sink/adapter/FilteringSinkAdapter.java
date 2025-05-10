package io.awspring.cloud.sqs.listener.sink.adapter;

import org.springframework.messaging.Message;

public interface FilteringSinkAdapter {
    boolean shouldProcess(Message<?> message);
}
