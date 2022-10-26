package com.github.ekgreen.concurrency.threads.showtime;

import com.github.ekgreen.concurrency.threads.toolbox.ThreadAPI;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
public class CompletableHell {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final Supplier<String> task = () -> {
            ThreadAPI.sleep(3, TimeUnit.SECONDS);
            log.info("[1] Поток стартовал успешно :!");

            return "Completable-Future ("+Thread.currentThread().threadId()+")";
        };


        CompletableFuture<String> future = CompletableFuture.supplyAsync(task);
        future.join();

        future = future.thenApply(value -> {
            ThreadAPI.sleep(2, TimeUnit.SECONDS);
            log.info("[2] Это внутри completable future");

            return value.toLowerCase();
        });

        future.whenComplete((value, throwable) -> {
            ThreadAPI.sleep(2, TimeUnit.SECONDS);
            log.info("[3] Future os completed = {}", value);
        });

        future.complete("value");

        log.info("execution result = {}", future.get());
    }
}
