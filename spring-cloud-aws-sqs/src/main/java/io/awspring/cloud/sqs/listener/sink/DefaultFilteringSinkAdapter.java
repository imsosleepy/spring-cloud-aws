package io.awspring.cloud.sqs.listener.sink;

import io.awspring.cloud.sqs.listener.sink.adapter.FilteringSinkAdapter;
import org.springframework.messaging.Message;

public class DefaultFilteringSinkAdapter implements FilteringSinkAdapter {

    @Override
    public boolean shouldProcess(Message<?> message) {
        return true;
    }
}
