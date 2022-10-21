package com.github.ekgreen.concurrency.threads.api.expedition;

import com.github.ekgreen.concurrency.threads.api.Package;
import com.github.ekgreen.concurrency.threads.api.projects.Goods;

public interface Expedition {

    /**
     *
     *
     * @param goods
     */
    void equip(Package<? extends Goods> goods);
}
