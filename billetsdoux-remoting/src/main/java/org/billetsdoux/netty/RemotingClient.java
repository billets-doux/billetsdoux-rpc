package org.billetsdoux.netty;

import org.billetsdoux.common.exception.remoting.RemotingException;
import org.billetsdoux.common.exception.remoting.RemotingSendRequestException;
import org.billetsdoux.common.exception.remoting.RemotingTimeoutException;
import org.billetsdoux.model.NettyChannelInactiveProcessor;
import org.billetsdoux.model.NettyRequestProcessor;
import org.billetsdoux.model.RemotingTransporter;

import java.util.concurrent.ExecutorService;

/***
* @Description: Netty客户端
* @Param:
* @return:
* @Author: billets-doux
* @Date: 8:41 下午 2020/4/3
*/
public interface RemotingClient extends BaseRemotingService {

    /***
    * @Description: 向某个地址发送request请求，并且返回{@link RemotingTransporter}
    * @Param: [addr, request, timeoutMillis]
    * @return: org.billetsdoux.model.RemotingTransporter
    * @Author: billets-doux
    * @Date: 8:42 下午 2020/4/3
    */
    RemotingTransporter invokeSync(final String addr, final RemotingTransporter request, final long timeoutMillis)throws RemotingTimeoutException, RemotingSendRequestException, InterruptedException, RemotingException;



    /**
     * 注入处理器，例如某个Netty的Client实例，这个实例是Consumer端的，它需要处理订阅返回的信息
     * 假如订阅的requestCode 是100，那么给定requestCode特定的处理器processorA,且指定该处理器的线程执行器是executorA
     * 这样做的好处就是业务逻辑很清晰，什么样的业务请求对应特定的处理器
     * 一般场景下，不是高并发的场景下，executor是可以复用的，这样减少线程上下文的切换
     * @param requestCode
     * @param processor
     * @param executor
     */
    void registerProcessor(final byte requestCode, final NettyRequestProcessor processor , final ExecutorService executor);

    /**
     * 注册channel inactive的处理器
     * @param processor
     * @param executor
     */
    void registerChannelInactiveProcessor(NettyChannelInactiveProcessor processor, ExecutorService executor);

    /**
     * 某个地址的长连接的channel是否可写
     * @param addr
     * @return
     */
    boolean isChannelWriteable(final String addr);


    /**
     * 当与server的channel inactive的时候，是否主动重连netty的server端
     * @param isReconnect
     */
    void setReconnect(boolean isReconnect);

}
