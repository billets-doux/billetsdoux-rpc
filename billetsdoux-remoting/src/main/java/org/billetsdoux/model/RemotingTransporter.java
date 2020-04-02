package org.billetsdoux.model;

import org.billetsdoux.common.protocal.BilletsdouxProtocol;
import org.billetsdoux.common.transport.body.CommonCustomBody;

import java.util.concurrent.atomic.AtomicLong;

/***
* @Description: 网络传输的唯一对象
* @Param:
* @return:
* @Author: billets-doux
* @Date: 11:46 下午 2020/4/2
*/
public class RemotingTransporter extends ByteHolder {

    private volatile static RemotingTransporter transporter;

    private static final AtomicLong requestId = new AtomicLong(0L);

    /**
     * 请求的类型
     * 例如该请求是用来订阅服务的，该请求是用来发布服务的等等的
     * 假设 code == 1 代表是消费者订阅服务，则接收方注册中心接到该对象的时候，就会先获取该code，判断如果该code==1 则走订阅服务的处理分支代码
     * 假设 code == 2 代表是提供者发布服务，则接收发注册中心接收该对象的时候，也会先获取该code，判断如果该code==2则走发布服务的处理分支代码
     */
    private byte code;

    /**
     * 请求的主体信息 {@link CommonCustomBody}是一个接口
     * 假如code==1 则CommonCustomBody中则是一些订阅服务的具体信息
     * 假如code==2 则CommonCustomBody中则是一些发布服务的具体信息
     */
    private transient CommonCustomBody customBody;

    /**
     * 请求的时间戳
     */
    private transient long timestamp;

    /**
     * 请求id，从1开始自增
     */
    private long opaque = requestId.getAndIncrement();

    /**
     * 定义该传输对象是请求还是响应信息
     */
    private byte transporterType;

    private RemotingTransporter(){}

    /***
    * @Description: 创建一个请求传输对象
    * @param code 请求的类型
    * @param commonCustomBody 请求体
    * @return: org.billetsdoux.model.RemotingTransporter
    * @Author: billets-doux
    * @Date: 12:19 上午 2020/4/3
    */
    public static RemotingTransporter createRequestTransporter(byte code,CommonCustomBody commonCustomBody){
        if (transporter == null){
            synchronized (RemotingTransporter.class){
                if (transporter == null){
                    transporter = new RemotingTransporter();

                }
            }
        }
        transporter.setCode(code);
        transporter.setCustomBody(commonCustomBody);
        transporter.setTransporterType(BilletsdouxProtocol.REQUEST_REMOTING);
        return transporter;

    }
    /***
    * @Description: 创建一个响应对象
    *
    * @param code 响应对象的类型
    * @param commonCustomBody 响应体
    * @param opaque 此响应对应的请求对象的id
    * @return: org.billetsdoux.model.RemotingTransporter
    * @Author: billets-doux
    * @Date: 12:24 上午 2020/4/3
    */
    public static RemotingTransporter createResponseTransporter(byte code,CommonCustomBody commonCustomBody,long opaque){
        if (transporter == null){
            synchronized (RemotingTransporter.class){
                if (transporter == null){
                    transporter = new RemotingTransporter();
                }
            }
        }
        transporter.setCode(code);
        transporter.setCustomBody(commonCustomBody);
        transporter.setTransporterType(BilletsdouxProtocol.RESPONSE_REMOTING);
        transporter.setOpaque(opaque);
        return transporter;
    }

    public static RemotingTransporter newInstance(long id, byte sign,byte type, byte[] bytes) {
       if (transporter == null){
           synchronized (RemotingTransporter.class){
               if (transporter == null){
                   transporter = new RemotingTransporter();
               }
           }
       }
        transporter.setCode(sign);
        transporter.setTransporterType(type);
        transporter.setOpaque(id);
        transporter.bytes(bytes);
        return transporter;
    }



    public static AtomicLong getRequestId() {
        return requestId;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public CommonCustomBody getCustomBody() {
        return customBody;
    }

    public void setCustomBody(CommonCustomBody customBody) {
        this.customBody = customBody;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getOpaque() {
        return opaque;
    }

    public void setOpaque(long opaque) {
        this.opaque = opaque;
    }

    public byte getTransporterType() {
        return transporterType;
    }

    public void setTransporterType(byte transporterType) {
        this.transporterType = transporterType;
    }
}
