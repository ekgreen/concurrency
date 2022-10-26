package com.github.ekgreen.concurrency.threads;

import com.github.ekgreen.concurrency.threads.towns.GopherTown;
import com.github.ekgreen.concurrency.threads.towns.little.ExperimentalLittleGopherTown;
import com.github.ekgreen.concurrency.threads.towns.little.LittleGopherTown;
import com.github.ekgreen.concurrency.threads.towns.medium.MediumGopherTown;
import com.github.ekgreen.concurrency.threads.towns.small.SmallGopherTown;
import com.github.ekgreen.concurrency.threads.towns.tiny.TinyGopherTown;

public class Main {

    public static void main(String[] args) throws Exception {
        final GopherTown gopherTown = new SmallGopherTown();

        gopherTown.start();
    }
}
