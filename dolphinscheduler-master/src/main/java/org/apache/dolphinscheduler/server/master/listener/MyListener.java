package org.apache.dolphinscheduler.server.master.listener;

import org.apache.dolphinscheduler.server.master.listener.event.EventTaskFinished;
import org.apache.dolphinscheduler.server.master.listener.event.EventWorkflowSubmitted;

public class MyListener implements EventListener {

    @Override
    public int getListenerId() {
        return 1001;
    }

    @Override
    public void onWorkflowSubmitted(EventWorkflowSubmitted eventWorkflowSubmitted) {
        System.out.println("Yes~ Workflow submitted!");
    }

    @Override
    public void onTaskFinished(EventTaskFinished eventTaskFinished) {
        System.out.println("Yes~ Task finished!");
    }
}
