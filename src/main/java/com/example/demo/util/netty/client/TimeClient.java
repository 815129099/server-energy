package com.example.demo.util.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

public class TimeClient {

    public void connect(int port,String host)throws Exception{
        //配置客户端
        System.out.println(port+"--"+host);
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try {
            Bootstrap b=new Bootstrap();
            b.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            //绑定端口，同步等待成功
            ChannelFuture f = b.connect(host,port).sync();
            //等待服务监听端口关闭
            f.channel().closeFuture().sync();
        }finally {
            //优雅退出，释放线程资源
            eventLoopGroup.shutdownGracefully();
        }
    }


    @Test
    public static void main(String[] args) throws Exception {
        //learnResouceService.test();
        new TimeClient().connect(6666,"127.0.0.1");

    }
}