package com.github.ekgreen.concurrency.threads.api.projects;

public interface GoodsProjectFactory<T extends Goods> {

    /**
     *
     * @return
     * @param quantity
     */
    GoodsProduction<T> createProject(Long quantity);

    /**
     *
     *
     * @return
     */
    ProjectKey getKey();

    /**
     *
     *
     */
    record ProjectKey(String projectName) {}
}
