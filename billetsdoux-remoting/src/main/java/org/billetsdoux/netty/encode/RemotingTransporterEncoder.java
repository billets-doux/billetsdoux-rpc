package org.billetsdoux.netty.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.billetsdoux.common.serialization.SerializerHolder;
import org.billetsdoux.model.RemotingTransporter;
import org.xerial.snappy.Snappy;

import java.io.IOException;

import static org.billetsdoux.common.protocal.BilletsdouxProtocol.MAGIC;
import static org.billetsdoux.common.protocal.BilletsdouxProtocol.UNCOMPRESS;

/***
* @Description: netty 对 {@link RemotingTransporter}的编码器
* @Param
* @return:
* @Author: billets-doux
* @Date: 12:31 上午 2020/4/3
*/
@ChannelHandler.Sharable
public class RemotingTransporterEncoder extends MessageToByteEncoder<RemotingTransporter> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RemotingTransporter msg, ByteBuf out) throws Exception {

       doEncodeRemotingTransporter(msg,out);

    }

    private void doEncodeRemotingTransporter(RemotingTransporter msg,ByteBuf out) throws IOException{
        byte[] body = SerializerHolder.serializer().writeObject(msg.getCustomBody());
        byte isCompress = UNCOMPRESS;
        // 压缩内容
        if (body.length > 1024 * 2){
            body = Snappy.compress(body);
        }
        // 协议头
        out.writeShort(MAGIC)
                // 传说类型，请求/响应
                .writeByte(msg.getCode())
                // requestId
                .writeLong(msg.getOpaque())
                // 是否压缩
                .writeByte(isCompress)
                // 请求ti
                .writeBytes(body);

    }
}
