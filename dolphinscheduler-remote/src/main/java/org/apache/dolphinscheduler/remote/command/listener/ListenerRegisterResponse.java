package org.apache.dolphinscheduler.remote.command.listener;

import org.apache.dolphinscheduler.remote.command.MessageType;
import org.apache.dolphinscheduler.remote.command.ResponseMessageBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListenerRegisterResponse  implements ResponseMessageBuilder {

    private int listenerId;

    private boolean registerSuccess;

    private String message;
    
    public static ListenerRegisterResponse success(int listenerId){
        return new ListenerRegisterResponse(listenerId, true, "listener rigister success");
    }

    public static ListenerRegisterResponse failed(int listenerId, String message){
        return new ListenerRegisterResponse(listenerId, false, message);
    }

    @Override
    public MessageType getCommandType() {
        return MessageType.LISTENER_REGISTER_RESPONSE;
    }
    
}
