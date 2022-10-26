package com.github.ekgreen.concurrency.threads.api.projects.food;

import com.github.ekgreen.concurrency.threads.api.projects.GoodsProduction;
import com.github.ekgreen.concurrency.threads.api.projects.GoodsProjectFactory;
import com.github.ekgreen.concurrency.threads.api.projects.food.factory.BigGopherFoodFactor;
import com.github.ekgreen.concurrency.threads.api.projects.food.factory.FoodFactory;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class FoodProject implements GoodsProjectFactory<Food> {

    @Override
    public GoodsProduction<Food> createProject(Long quantity) {
        return new FoodProduction(quantity);
    }

    @Override
    public ProjectKey getKey() {
        return new ProjectKey("food");
    }
}
