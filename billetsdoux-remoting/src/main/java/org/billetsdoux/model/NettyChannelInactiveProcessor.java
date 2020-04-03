package org.billetsdoux.model;

import io.netty.channel.ChannelHandlerContext;
import org.billetsdoux.common.exception.remoting.RemotingSendRequestException;
import org.billetsdoux.common.exception.remoting.RemotingTimeoutException;

public interface NettyChannelInactiveProcessor {
    void processChannelInactive(ChannelHandlerContext ctx) throws RemotingSendRequestException, RemotingTimeoutException, InterruptedException;

}
