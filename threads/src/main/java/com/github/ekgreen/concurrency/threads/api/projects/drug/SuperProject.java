
package com.github.ekgreen.concurrency.threads.api.projects.drug;

import com.github.ekgreen.concurrency.threads.api.projects.GoodsProduction;
import com.github.ekgreen.concurrency.threads.api.projects.GoodsProjectFactory;
import com.github.ekgreen.concurrency.threads.api.projects.unicorn.Unicorn;
import com.github.ekgreen.concurrency.threads.api.projects.unicorn.UnicornProduction;

public class SuperProject implements GoodsProjectFactory<SuperDrug> {

    @Override
    public GoodsProduction<SuperDrug> createProject(Long quantity) {
        return new SuperDrugProduction(quantity);
    }

    @Override
    public ProjectKey getKey() {
        return new ProjectKey("super-pill");
    }
}
