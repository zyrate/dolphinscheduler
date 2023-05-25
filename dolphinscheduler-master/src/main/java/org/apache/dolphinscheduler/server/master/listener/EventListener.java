package org.apache.dolphinscheduler.server.master.listener;

import org.apache.dolphinscheduler.server.master.listener.event.EventTaskFinished;
import org.apache.dolphinscheduler.server.master.listener.event.EventWorkflowSubmitted;

public interface EventListener {

    int getListenerId();

    void onWorkflowSubmitted(EventWorkflowSubmitted eventWorkflowSubmitted);

    void onTaskFinished(EventTaskFinished eventTaskFinished);
}
