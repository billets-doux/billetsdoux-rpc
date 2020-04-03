package org.billetsdoux;

import org.billetsdoux.model.RemotingTransporter;
/***
* @Description: RPC的回调钩子，在发送请求和接收到请求的时候触发
* @Param:
* @return:
* @Author: billets-doux
* @Date: 8:34 下午 2020/4/3
*/
public interface RPCHook {

    void doBeforeRequest(final String remoteAddr, final RemotingTransporter request);

    void doAfterResponse(final String remoteAddr, final RemotingTransporter request, final RemotingTransporter response);
}
