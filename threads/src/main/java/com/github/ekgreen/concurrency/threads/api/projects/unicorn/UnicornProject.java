package com.github.ekgreen.concurrency.threads.api.projects.unicorn;

import com.github.ekgreen.concurrency.threads.api.projects.GoodsProduction;
import com.github.ekgreen.concurrency.threads.api.projects.GoodsProjectFactory;

public class UnicornProject implements GoodsProjectFactory<Unicorn> {

    @Override
    public GoodsProduction<Unicorn> createProject(Long quantity) {
        return new UnicornProduction(quantity);
    }

    @Override
    public ProjectKey getKey() {
        return new ProjectKey("unicorns");
    }
}
