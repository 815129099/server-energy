package com.example.demo.util.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class TimeClientHandler extends ChannelHandlerAdapter {


    private byte[] req;
    public TimeClientHandler(){
        req= "aaaa".getBytes();
    }


    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for(int i = 0; i<10; i++){
            req = ("qweqw"+i).getBytes();
            ByteBuf message=null;
            message= Unpooled.buffer(req.length);
            message.writeBytes(req);
           // ctx.writeAndFlush(message);

//			ctx.write(subReq(i));
            ctx.write(req);
        }
        ctx.flush();

    }

    //消息在管道中都是以ChannelHandlerContext的形势传递的

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            ByteBuf in = (ByteBuf) msg;
            System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
        }  finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常就关闭
        cause.printStackTrace();
        ctx.close();
    }


    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception{
        System.out.println("----------------handler channelReadComplete");
        ctx.flush();
    }


}