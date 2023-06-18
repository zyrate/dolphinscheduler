package org.apache.dolphinscheduler.remote.command.listener;

import org.apache.dolphinscheduler.remote.command.BaseMessage;
import org.apache.dolphinscheduler.remote.command.MessageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Task execute start message, from api to master.
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ListenerRegisterRequest extends BaseMessage{
    
    private int listenerId;
    
    @Override
    public MessageType getCommandType() {
       return MessageType.LISTENER_REGISTER_REQUEST;
    }
    
}
