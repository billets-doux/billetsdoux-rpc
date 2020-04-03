package org.billetsdoux.model;

import org.billetsdoux.InvokeCallback;

import java.util.concurrent.CountDownLatch;

/***
* @Description: RPC响应对象的包装类
* @Param:
* @return:
* @Author: billets-doux
* @Date: 9:06 下午 2020/4/3
*/
public class RemotingResponse {

    private volatile RemotingTransporter remotingTransporter;

    private volatile Throwable cause;

    private volatile boolean requestSuccess = true;

    private final long opaque;

    private final InvokeCallback invokeCallback;

    private final long timeoutMillis;

    private final long beginTimestamp = System.currentTimeMillis();

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    public RemotingResponse(long opaque, long timeoutMillis, InvokeCallback invokeCallback) {
        this.invokeCallback = invokeCallback;
        this.opaque = opaque;
        this.timeoutMillis = timeoutMillis;
    }

    public void executeInvokeCallback() {
        if (invokeCallback != null) {
            invokeCallback.operationComplete(this);
        }
    }

    public RemotingTransporter getRemotingTransporter() {
        return remotingTransporter;
    }

    public void setRemotingTransporter(RemotingTransporter remotingTransporter) {
        this.remotingTransporter = remotingTransporter;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public boolean isRequestSuccess() {
        return requestSuccess;
    }

    public void setRequestSuccess(boolean requestSuccess) {
        this.requestSuccess = requestSuccess;
    }

    public long getOpaque() {
        return opaque;
    }

    public InvokeCallback getInvokeCallback() {
        return invokeCallback;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public long getBeginTimestamp() {
        return beginTimestamp;
    }

    /**
     * 当远程端返回结果的时候，TCP的长连接的上层载体channel 的handler会将其放入与requestId
     * 对应的Response中去
     * @param remotingTransporter
     */
    public void putResponse(final RemotingTransporter remotingTransporter){
        this.remotingTransporter = remotingTransporter;
        //接收到对应的消息之后需要countDown
        this.countDownLatch.countDown();
    }

   
}
