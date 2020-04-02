package org.billetsdoux.common.transport.body;

import org.billetsdoux.common.exception.remoting.RemotingCommonCustomException;

/***
* @Description: 网络传输的主体对象
* @Param:
* @return:
* @Author: billets-doux
* @Date: 11:50 下午 2020/4/2
*/
public interface CommonCustomBody {

    void checkFields() throws RemotingCommonCustomException;
}
