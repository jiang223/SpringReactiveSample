package cn.ww.jmstemplate;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * a异步消息监听
 * @author yaoayao
 *
 */
@Component
public class Listener1 {

    // 用String 接收
    @JmsListener(destination = "TEST",concurrency = "20-100")
    public void receive(String msg) throws InterruptedException {
        Flux.generate((synchronousSink) -> {
                    synchronousSink.next("Thread["+Thread.currentThread().getName()+"]监听到的消息内容为: " + msg);
                    //不支持多个next操作
                    //synchronousSink.next(2);
                })
                .subscribe(System.out::println);



        System.out.println("Thread["+Thread.currentThread().getName()+"]监听到的消息内容为: " + msg);
        Thread.sleep(200);
    }
    //可以用javaBean接收
    //@JmsListener(destination = "TEST")
    public void receive(User msg) {
        System.out.println("监听到的消息内容为: " + msg.getName());
    }


}