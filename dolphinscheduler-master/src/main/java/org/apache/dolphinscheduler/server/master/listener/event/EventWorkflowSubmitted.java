package org.apache.dolphinscheduler.server.master.listener.event;

public class EventWorkflowSubmitted extends ListenerEvent {

    @Override
    public String getEventType() {
        return "WORKFLOW_SUBMITTED";
    }
}
