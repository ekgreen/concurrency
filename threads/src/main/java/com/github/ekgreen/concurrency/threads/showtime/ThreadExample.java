package com.github.ekgreen.concurrency.threads.showtime;

import com.github.ekgreen.concurrency.threads.toolbox.ThreadAPI;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadExample {

    public static void main(String[] args) {
        log.info("Мы хотим запустить отдельный поток!");

        final Thread thread = new Thread(() -> {
            ThreadAPI.sleep(3, TimeUnit.SECONDS);
            log.info("Поток стартовал успешно :!");
        });

        thread.start();

        log.info("А точно в отдельном потоке?");
    }
}
