package com.github.ekgreen.concurrency.threads.towns.medium;

import com.github.ekgreen.concurrency.threads.api.ExpeditionRequest;
import com.github.ekgreen.concurrency.threads.api.Package;
import com.github.ekgreen.concurrency.threads.api.expedition.BoardOfDirectors;
import com.github.ekgreen.concurrency.threads.api.expedition.Expedition;
import com.github.ekgreen.concurrency.threads.api.expedition.mars.GopherMarsExpedition;
import com.github.ekgreen.concurrency.threads.api.expedition.mars.MarsExpeditionBoardOfGophers;
import com.github.ekgreen.concurrency.threads.api.projects.Goods;
import com.github.ekgreen.concurrency.threads.api.projects.GoodsProduction;
import com.github.ekgreen.concurrency.threads.api.projects.drug.SuperProject;
import com.github.ekgreen.concurrency.threads.api.projects.unicorn.UnicornProject;
import com.github.ekgreen.concurrency.threads.toolbox.ThreadAPI;
import com.github.ekgreen.concurrency.threads.towns.AnyGopherTown;
import com.github.ekgreen.concurrency.threads.towns.little.PokerFacePool;
import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.*;

@Slf4j
public class MediumGopherTown extends AnyGopherTown {

    // порт по которому город Гоферов готов принимать запросы
    private final static int TOWN_ACCEPT_PORT = 8080;

    // thread-safe
    // Снаряжаемая экспедиция
    private final Expedition expedition = new GopherMarsExpedition();

    // thread-safe
    // Совет директоров, который принимает решения и выдает проектные решения для новой экспедиции
    private final BoardOfDirectors boardOfDirectors = new MarsExpeditionBoardOfGophers(
            new SuperProject(), new UnicornProject());

    // пул потоков, в котором мы будем исполнять наши запросы
    private final ExecutorService executor
            = Executors.newFixedThreadPool(10);

    private volatile boolean isCanceled = false;

    public synchronized void start() throws Exception {
        Runnable termination = setWorkingHours();

        // thread-safe
        final ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(TOWN_ACCEPT_PORT));
        final ServerSocket socket = channel.socket();
        // Поток исполняющий принятие запроса и отправляющий отбивку
        final ExecutorService service = Executors.newSingleThreadExecutor();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                // открываем канал связи
                final Socket connection = socket.accept();

                service.submit(() -> {
                    final ExpeditionRequest expeditionRequest = handleConnection(connection);

                    // создаем задачу для исполнения запроса и выполняем ее
                    EquipExpedition expedition = new EquipExpedition(expeditionRequest);
                    executor.execute(expedition);
                });
            } catch (Throwable equipExpeditionException) {
                log.error("Не удалось создать и исполнить проект!", equipExpeditionException);
            }
        }

        service.shutdown();
        termination.run();
        channel.close();
        executor.shutdown();
    }

    private Runnable setWorkingHours() {
        final ExecutorService workingHoursExecutor
                = Executors.newSingleThreadExecutor();

        // время начала работы
        final long start = System.nanoTime();

        // рабочее время
        final long workingHours = TimeUnit.MINUTES.toNanos(10);

        // родительский поток
        final Thread acceptanceThread = Thread.currentThread();

        workingHoursExecutor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                final long current = System.nanoTime();

                if (current >= start + workingHours) {
                    MediumGopherTown.this.isCanceled = true;
                    acceptanceThread.interrupt();

                    log.info("Приемная комиссия завершает свою работу!");
                    break;
                }

                log.info("Приемная комиссия продолжает свою работу, до закрытия: " + (start + workingHours - current) / Math.pow(10, 9));

                final int delay = ThreadLocalRandom.current().nextInt(500, 1000);
                ThreadAPI.sleep(delay, TimeUnit.MILLISECONDS);
            }
        });

        return workingHoursExecutor::shutdown;
    }

    @RequiredArgsConstructor
    public class EquipExpedition implements Runnable {
        // сокет для чтения данных
        private final ExpeditionRequest expeditionRequest;

        @Override
        public void run() {
            try {
                // 1. Отправимся в совет директоров экспедиции и согласуем разработку и поставку нужд экспедиции
                GoodsProduction<Goods> goodsProduction = boardOfDirectors.acceptNeeds(expeditionRequest);

                // 2. Производство
                Package<Goods> packageOfGoods = production(goodsProduction);

                // 3. Снарядим экспедицию
                expedition.equip(packageOfGoods);
            } catch (Throwable expeditionRequestException) {
                log.error("Не удалось создать и исполнить проект!", expeditionRequestException);
            }

        }

        private Package<Goods> production(GoodsProduction<Goods> goodsProduction) {
            // 1. Произведем сбор средств на разработку и производство
            goodsProduction.collectingInvestments();

            // 2. Произведем научную разработку
            goodsProduction.scientificDevelopment();

            // 3. Изготовим товары
            return goodsProduction.production();
        }
    }
}
