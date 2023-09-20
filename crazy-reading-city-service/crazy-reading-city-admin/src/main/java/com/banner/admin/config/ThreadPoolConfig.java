package com.banner.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author rjj
 * @date 2023/8/24 - 8:36
 */
@Configuration
public class ThreadPoolConfig {

    //配置线程池
    @Bean
    public ExecutorService executorService(){
        AtomicInteger c = new AtomicInteger(1);
        return new ThreadPoolExecutor(
                17,//核心线程数目(2*CPU核数+1)
                50,//最大线程数目 = (核心线程+救急线程的最大数目)
                500,//救急线程的生存时间，生存时间内没有新任务，此线程资源会释放
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1000),//阻塞队列,当没有空闲核心线程时，新来任务会加入到此队列排队，队列满会创建救急线程执行任务
                r->new Thread(r,"banner-pool-"+c.get()),//可以定制线程对象的创建，例如设置线程名字、是否是守护线程等
                new ThreadPoolExecutor.DiscardPolicy()//当所有线程都在繁忙，workQueue 也放满时，会触发的拒绝策略
        );
    }

}
