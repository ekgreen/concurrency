package com.github.ekgreen.concurrency.threads.towns.little;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.github.ekgreen.concurrency.threads.towns.AnyGopherTown;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

@Slf4j
public class ExperimentalLittleGopherTown extends AnyGopherTown {

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
    private final Executor executor
            = new PokerFacePool(10);

    public synchronized void start() throws Exception {
        // thread-safe
        final ServerSocket socket = new ServerSocket(TOWN_ACCEPT_PORT);

        while (true) {
            try {
                // открываем канал связи
                final Socket connection = socket.accept();

                // создаем задачу для исполнения запроса и выполняем ее
                EquipExpedition expedition = new EquipExpedition(connection);
                executor.execute(expedition);
            } catch (Throwable equipExpeditionException) {
                log.error("Не удалось создать и исполнить проект!", equipExpeditionException);
            }
        }
    }

    @RequiredArgsConstructor
    public class EquipExpedition implements Runnable {
        // сокет для чтения данных
        private final Socket connection;

        @Override
        public void run() {
            try {
                // 1. Обработаем запрос от экспедиции
                final ExpeditionRequest expeditionRequest = handleConnection(connection);

                // 2. Отправимся в совет директоров экспедиции и согласуем разработку и поставку нужд экспедиции
                GoodsProduction<Goods> goodsProduction = boardOfDirectors.acceptNeeds(expeditionRequest);

                // 3. Производство
                Package<Goods> packageOfGoods = production(goodsProduction);

                // 4. Снарядим экспедицию
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
