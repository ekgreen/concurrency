package com.github.ekgreen.concurrency.threads.towns;

import com.github.ekgreen.concurrency.threads.towns.little.ExperimentalLittleGopherTown;
import com.github.ekgreen.concurrency.threads.towns.small.SmallGopherTown;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class AnyGopherTownTest {

    @Test
    @DisplayName("Small town")
    public void smallGopherTown_1() throws Exception {
        final GopherTown gopherTown = new SmallGopherTown();

        gopherTown.start();
    }


    @Test
    @DisplayName("Little town. Poor pool")
    public void experimentalLittleGopherTown_1() throws Exception {
        try {
            final GopherTown gopherTown = new ExperimentalLittleGopherTown();

            gopherTown.start();
        } catch (Throwable throwable) {
            log.error("Ошибка исполнения теста!", throwable);
        }
    }
}