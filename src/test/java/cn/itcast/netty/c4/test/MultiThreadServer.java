package cn.itcast.netty.c4.test;

import cn.itcast.netty.c1.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: MultiThreadServer
 * Package: cn.itcast.netty.c4.test
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/15 - 17:48
 * Version: v1.0
 */
@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey sscKeey = ssc.register(boss, 0, null);
        sscKeey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        //创建固定数量的worker
        //System.out.println(Runtime.getRuntime().availableProcessors());
        Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors()];
        for(int i = 0; i < workers.length; i++){
            workers[i] = new Worker("worker-" + i);
        }
        //Worker worker = new Worker("worker-0 ");
        AtomicInteger index = new AtomicInteger();
        while(true){
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connected...{}", sc.getRemoteAddress());
                    //关联 selector
                    log.debug("before register...{}", sc.getRemoteAddress());
                    // round robin
                    workers[index.getAndIncrement() % workers.length].register(sc);
                    log.debug("after register...{}", sc.getRemoteAddress());

                }
            }
        }
    }
    static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile boolean start = false;
        private ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();
        public Worker(String name) {
            this.name = name;
        }
        //初始化线程和selector
        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                thread = new Thread(this, name);
                selector = Selector.open();
                start = true;
                thread.start();
            }
            //向队列添加了任务，但是任务没有执行
            queue.add(()->{

                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            selector.wakeup();

        }

        @Override
        public void run() {
            while(true){
                try {
                    selector.select();
                    Runnable task = queue.poll();
                    if(task != null){
                        task.run();
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if(key.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            log.debug("readed...{}",channel.getRemoteAddress());
                            channel.read(buffer);
                            buffer.flip();
                            ByteBufferUtil.debugAll(buffer);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
