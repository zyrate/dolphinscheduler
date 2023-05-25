package org.apache.dolphinscheduler.server.master.listener;

import org.apache.dolphinscheduler.server.master.listener.event.EventTaskFinished;
import org.apache.dolphinscheduler.server.master.listener.event.EventWorkflowSubmitted;
import org.apache.dolphinscheduler.server.master.listener.event.ListenerEvent;
import org.apache.dolphinscheduler.server.master.listener.event.StopListenerBus;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ListenerBus {

    private ConcurrentHashMap<Integer, EventListener> listeners = new ConcurrentHashMap<>();
    private LinkedBlockingDeque<ListenerEvent> queue = new LinkedBlockingDeque();
    private boolean isStarted = false;
    private Thread dispatchThread;

    @SneakyThrows
    private void dispatch() {
        log.info("ListenerBus started.");
        ListenerEvent nextEvent = queue.take();
        while (!nextEvent.getEventType().equals("STOP_LISTENERBUS")) {
            for (EventListener listener : listeners.values()) {
                handleEvent(listener, nextEvent);
            }
            nextEvent = queue.take();
        }
    }

    public void addListener(EventListener listener) {
        listeners.put(listener.getListenerId(), listener);
        log.info("Add listener {} to ListenerBus.", listener.getListenerId());
    }

    public void removeListenerById(Integer listenerId) {
        listeners.remove(listenerId);
    }

    public void handleEvent(EventListener listener, ListenerEvent event) {
        switch (event.getEventType()) {
            case "WORKFLOW_SUBMITTED":
                listener.onWorkflowSubmitted((EventWorkflowSubmitted) event);
                break;
            case "TASK_FINISHED":
                listener.onTaskFinished((EventTaskFinished) event);
                break;
            // and other event types...
        }
    }

    @SneakyThrows
    public void postEvent(ListenerEvent event) {
        if (isStarted) {
            queue.put(event);
        }
    }

    public void start() {
        if (!isStarted) {
            isStarted = true;
            dispatchThread = new Thread() {

                @Override
                public void run() {
                    dispatch();
                }
            };
            dispatchThread.start();
        }
    }

    @SneakyThrows
    public void stop() {
        if (isStarted) {
            postEvent(new StopListenerBus());
            dispatchThread.join();
            isStarted = false;
            log.info("ListenerBus stopped.");
        }
    }
}
