package com.github.ekgreen.concurrency.threads.api.expedition;

import com.github.ekgreen.concurrency.threads.api.ExpeditionRequest;
import com.github.ekgreen.concurrency.threads.api.projects.Goods;
import com.github.ekgreen.concurrency.threads.api.projects.GoodsProduction;

public interface BoardOfDirectors {

    /**
     *
     *
     * @param needs
     * @param <T>
     * @return
     */
    <T extends Goods> GoodsProduction<T> acceptNeeds(ExpeditionRequest needs);
}
