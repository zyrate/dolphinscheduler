package org.apache.dolphinscheduler.api.service.impl;

import java.util.Collection;

import org.apache.dolphinscheduler.api.rpc.ApiRpcClient;
import org.apache.dolphinscheduler.api.service.ListenerService;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.registry.api.RegistryClient;
import org.apache.dolphinscheduler.remote.command.Message;
import org.apache.dolphinscheduler.remote.command.listener.ListenerRegisterRequest;
import org.apache.dolphinscheduler.remote.command.listener.ListenerRegisterResponse;
import org.apache.dolphinscheduler.remote.exceptions.RemotingException;
import org.apache.dolphinscheduler.remote.utils.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ListenerServiceImpl extends BaseServiceImpl implements ListenerService{

    @Autowired
    private ApiRpcClient apiRpcClient;

    @Autowired
    private RegistryClient registryClient;

    @Override
    public Result<Object> register(User loginUser, int listenerId) {
        Collection<String> coll = registryClient.getMasterNodesDirectly();
        ListenerRegisterRequest listenerRegisterRequest = new ListenerRegisterRequest(listenerId);
        ListenerRegisterResponse listenerRegisterResponse = null;
        for(String node: coll){
            System.out.println(node);
            try {
                Message result = apiRpcClient.sendSyncCommand(Host.of(node), listenerRegisterRequest.convert2Command());
                listenerRegisterResponse = JSONUtils.parseObject(result.getBody(), ListenerRegisterResponse.class);
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new Result<Object>(0, "recived id:"+listenerId, listenerRegisterResponse);
    }
    
}
