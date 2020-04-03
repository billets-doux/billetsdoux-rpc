package org.billetsdoux.netty;

import org.billetsdoux.RPCHook;
/***
* @Description: netty网络通讯Client和Service都需要实现的方法
* @Param:
* @return:
* @Author: billets-doux
* @Date: 8:35 下午 2020/4/3
*/
public interface BaseRemotingService {
    /**
     * Netty的一些参数的初始化
     */
    void init();

    /**
     * 启动Netty方法
     */
    void start();

    /**
     * 关闭Netty C/S 实例
     */
    void shutdown();

    /**
     * 注入钩子，Netty在处理的过程中可以嵌入一些方法，增加代码的灵活性
     * @param rpcHook
     */
    void registerRPCHook(RPCHook rpcHook);


}
