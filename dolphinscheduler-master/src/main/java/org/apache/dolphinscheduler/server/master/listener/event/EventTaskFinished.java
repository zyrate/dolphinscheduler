package org.apache.dolphinscheduler.server.master.listener.event;

public class EventTaskFinished extends ListenerEvent {

    @Override
    public String getEventType() {
        return "TASK_FINISHED";
    }
}
