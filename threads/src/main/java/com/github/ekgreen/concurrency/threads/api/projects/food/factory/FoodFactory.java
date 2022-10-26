package com.github.ekgreen.concurrency.threads.api.projects.food.factory;

import com.github.ekgreen.concurrency.threads.api.Package;
import com.github.ekgreen.concurrency.threads.api.projects.food.Food;

public interface FoodFactory {

    Package<Food> cookAndPackage(long quantity);
}
