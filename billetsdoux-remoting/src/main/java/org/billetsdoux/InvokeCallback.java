package org.billetsdoux;

import org.billetsdoux.model.RemotingResponse;
/***
* @Description: RPC调用后的回调函数
* @Param: 
* @return: 
* @Author: billets-doux
* @Date: 9:00 下午 2020/4/3
*/
public interface InvokeCallback {
    void operationComplete(final RemotingResponse remotingResponse);

}
