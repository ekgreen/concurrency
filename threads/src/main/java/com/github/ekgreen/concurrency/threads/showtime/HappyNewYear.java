package com.github.ekgreen.concurrency.threads.showtime;

import com.github.ekgreen.concurrency.threads.api.Package;
import com.github.ekgreen.concurrency.threads.api.projects.food.Food;
import com.github.ekgreen.concurrency.threads.api.projects.food.FoodProduction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HappyNewYear {


    public static void main(String[] args) {
        final FoodProduction production = new FoodProduction(5);

        Package<Food> goods = production.production();
        log.info("Экспедиция снаряжена дополнительным грузом { название = {}, кол-во = {}, вес = {} }", goods.tag(), goods.quantity(), goods.weight());
    }

}
