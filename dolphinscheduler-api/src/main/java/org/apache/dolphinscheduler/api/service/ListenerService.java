package org.apache.dolphinscheduler.api.service;

import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.dao.entity.User;

public interface ListenerService {
    Result<Object> register(User loginUser, int listenerId);
}
