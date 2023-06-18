package org.apache.dolphinscheduler.server.master.processor;

import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.remote.command.Message;
import org.apache.dolphinscheduler.remote.command.MessageType;
import org.apache.dolphinscheduler.remote.command.listener.ListenerRegisterRequest;
import org.apache.dolphinscheduler.remote.command.listener.ListenerRegisterResponse;
import org.apache.dolphinscheduler.remote.processor.MasterRpcProcessor;
import org.apache.dolphinscheduler.server.master.listener.EventListener;
import org.apache.dolphinscheduler.server.master.listener.ListenerBus;
import org.apache.dolphinscheduler.server.master.listener.event.EventTaskFinished;
import org.apache.dolphinscheduler.server.master.listener.event.EventWorkflowSubmitted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ListenerRegisterProcessor implements MasterRpcProcessor {

    @Autowired
    private ListenerBus listenerBus;

    @Override
    public void process(Channel channel, Message message) {
        ListenerRegisterRequest listenerRegisterRequest = JSONUtils.parseObject(message.getBody(), ListenerRegisterRequest.class);
        log.info("Master receive listener register request: {}", listenerRegisterRequest);
        int listenerId = listenerRegisterRequest.getListenerId();
        log.info("Registering Listener: {}", listenerId);
        EventListener listener = new EventListener() {
            @Override
            public int getListenerId() {
                return listenerId;
            }
            @Override
            public void onWorkflowSubmitted(EventWorkflowSubmitted eventWorkflowSubmitted) {
                System.out.println("hello there, onWorkflowSubmitted");
            }
            @Override
            public void onTaskFinished(EventTaskFinished eventTaskFinished) {
                System.out.println("hello there, onTaskFinished");
            }
        };
        listenerBus.addListener(listener);
        ListenerRegisterResponse response = ListenerRegisterResponse.success(listenerId);        
        channel.writeAndFlush(response.convert2Command(message.getOpaque()));
    }

    @Override
    public MessageType getCommandType() {
        return MessageType.LISTENER_REGISTER_REQUEST;
    }
    
}
