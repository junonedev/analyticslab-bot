package pro.analyticslab.bot.util.services;

import pro.analyticslab.bot.AnalyticsLab;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;

public class ServiceManagerBuilder {
    private final HashMap<String, Service> services = new HashMap<>();
    private final HashMap<String, ScheduledExecutorService> scheduledExecutorServiceHashMap = new HashMap<>();
    private final AnalyticsLab root;


    protected ServiceManagerBuilder(AnalyticsLab root) {
        this.root = root;
    }

    /**
     * Initialize service management system
     * @return ServiceManagerBuilder
     */
    public static ServiceManagerBuilder initialize(@Nonnull AnalyticsLab root) {
        return new ServiceManagerBuilder(root);
    }


    /**
     * Adds some services to list
     * @param sv Service objects
     * @return ServiceManagerBuilder
     */
    public ServiceManagerBuilder addServices(@Nonnull Service... sv) {
        for (Service function : sv) {
            if (services.get(function.getThreadName()) != null) {
                throw new RuntimeException(function.getThreadName() + " another thread with this name is found");
            }

            services.put(function.getThreadName(), function);
        }
        return this;
    }

    /**
     * Starts all threads
     * @return ServiceManagerBuilder
     */
    public ServiceManager build() {
        return new ServiceManager(services, scheduledExecutorServiceHashMap, root);
    }
}
