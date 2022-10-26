package com.github.ekgreen.concurrency.threads.towns.large;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.checkerframework.checker.units.qual.C;

import javax.annotation.concurrent.ThreadSafe;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;

public class CronSchedulerThreadPool {

    private final static Pattern CRON_EXPRESSION
            = Pattern.compile("((\\*/([1-9]{1,2})?|\\*|[1-9]{1,2})[\s]?){5}");

    public static void main(String[] args) {
        System.out.println(CRON_EXPRESSION.matcher("*/1 * * * *").matches());
    }

    private final static CronDefinition CRON_DEFINITION
            = CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX);

    private final ExecutorService scheduler
            = Executors.newSingleThreadExecutor();

    public void schedule(String cronExpression, Runnable task) {
        // not thread safe
        final CronParser parser = new CronParser(CRON_DEFINITION);
        final Cron cron = parser.parse(cronExpression);

    }

    public static class Schedule {

        // some fields of cron schedule ....


        Duration nextExecution(LocalDateTime dateTime) {
            return null;
        }
    }
}
