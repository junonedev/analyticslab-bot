package pro.analyticslab.bot.util.services;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;

public class ServiceManagerBuilder {
    private final HashMap<String, Service> services = new HashMap<>();
    private final HashMap<String, ScheduledExecutorService> scheduledExecutorServiceHashMap = new HashMap<>();


    protected ServiceManagerBuilder() {}

    /**
     * Initialize service management system
     * @return ServiceManagerBuilder
     */
    public static ServiceManagerBuilder initialize() {
        return new ServiceManagerBuilder();
    }


    /**
     * Adds some services to list
     * @param sv Service objects
     * @return ServiceManagerBuilder
     */
    public ServiceManagerBuilder addServices(@Nonnull Service... sv) {
        for (Service function : sv) {
            if (services.get(function.threadName) != null) {
                throw new RuntimeException(function.threadName + " another thread with this name is found");
            }

            services.put(function.threadName, function);
        }
        return this;
    }

    /**
     * Starts all threads
     * @return ServiceManagerBuilder
     */
    public ServiceManager build() {
        return new ServiceManager(services, scheduledExecutorServiceHashMap);
    }
}
