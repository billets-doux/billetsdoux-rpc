package org.billetsdoux.netty.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.billetsdoux.common.exception.remoting.RemotingContextException;
import org.billetsdoux.common.protocal.BilletsdouxProtocol;
import org.billetsdoux.model.RemotingTransporter;
import org.xerial.snappy.Snappy;

import java.util.List;

import static org.billetsdoux.common.protocal.BilletsdouxProtocol.MAGIC;

public class RemotingTransporterDecoder extends ReplayingDecoder<RemotingTransporterDecoder.State> {
    // 请求体/响应体最大5M
    private static final int MAX_BODY_SIZE = 1024*1024*5;

    private final BilletsdouxProtocol header = new BilletsdouxProtocol();

    public RemotingTransporterDecoder(){
        //设置(下文#state()的默认返回对象)
        super(State.HEADER_MAGIC);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        switch (state()){
            case HEADER_MAGIC:
                // 校验MAGIC是否匹配
                checkMagic(in.readShort());
                // 将state()的值改为HEADER_TYPE
                checkpoint(State.HEADER_TYPE);
            case HEADER_TYPE:
                header.sign(in.readByte());
                checkpoint(State.HEADER_SIGN);
            case HEADER_SIGN:
                // 消息标志位
                header.sign(in.readByte());
                checkpoint(State.HEADER_ID);
            case HEADER_ID:
                //requestId
                header.id(in.readLong());
                checkpoint(State.HEADER_BODY_LENGTH);
            case HEADER_BODY_LENGTH:
                // 校验消息的长度
                header.bodyLength(in.readInt());
                checkpoint(State.HEADER_COMPRESS);
            case HEADER_COMPRESS:
                // 消息是否压缩
                header.setCompress(in.readByte());
                checkpoint(State.BODY);
            case BODY:
                int bodyLength = checkBodyLength(header.bodyLength());
                byte[] bytes = new byte[bodyLength];
                in.readBytes(bytes);
                if(header.compress() == BilletsdouxProtocol.COMPRESS){
                    bytes = Snappy.uncompress(bytes);
                }
                out.add(RemotingTransporter.newInstance(header.id(), header.sign(),header.type(), bytes));
                break;
            default:
                break;
        }
        checkpoint(State.HEADER_MAGIC);

    }

    private int checkBodyLength(int bodyLength) throws RemotingContextException {
        if (bodyLength > MAX_BODY_SIZE) {
            throw new RemotingContextException("body of request is bigger than limit value "+ MAX_BODY_SIZE);
        }
        return bodyLength;
    }

    private void checkMagic(short magic) throws RemotingContextException {
        if (MAGIC != magic) {
            throw new RemotingContextException("magic value is not equal "+MAGIC);
        }
    }

    enum State {
        HEADER_MAGIC, HEADER_TYPE, HEADER_SIGN, HEADER_ID, HEADER_BODY_LENGTH,HEADER_COMPRESS, BODY
    }
}
