package com.github.ekgreen.concurrency.threads.showtime;

import com.github.ekgreen.concurrency.threads.toolbox.ThreadAPI;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorExample {

    public static void main(String[] args) throws InterruptedException {
        final ThreadPoolExecutor fixedThreadPool
                = new ThreadPoolExecutor(3, 3, 10, TimeUnit.MINUTES, new SynchronousQueue<>());

        final Runnable task = () -> {
            ThreadAPI.sleep(3, TimeUnit.SECONDS);
            System.out.println("Task [" + UUID.randomUUID() + "] in thread [" + Thread.currentThread().threadId() + "]");
        };

        fixedThreadPool.execute(task);
        fixedThreadPool.execute(task);
        fixedThreadPool.execute(task);
        fixedThreadPool.execute(task);

        fixedThreadPool.shutdown();
    }
}
