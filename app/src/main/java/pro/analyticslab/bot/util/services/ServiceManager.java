package pro.analyticslab.bot.util.services;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ServiceManager extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(ServiceManager.class);

    private final HashMap<String, Service> services;
    private final HashMap<String, ScheduledExecutorService> scheduledExecutorServiceHashMap;


    public ServiceManager(
            HashMap<String, Service> services,
            HashMap<String, ScheduledExecutorService> scheduled
    ) {
        this.services = services;
        this.scheduledExecutorServiceHashMap = scheduled;

        logger.info(ServiceManager.class.getSimpleName() + " built (" + services.size() +
                " services initialized)");
    }


    private void run(@Nonnull Service s) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor((r) -> {
            Thread thread = new Thread(r, s.threadName);
            thread.setDaemon(true);
            return thread;
        });
        // todo: enabled? filter
        service.scheduleAtFixedRate(s::exec, s.delay, s.startDelay, s.periodType);

        scheduledExecutorServiceHashMap.put(s.threadName, service);
    }


    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        logger.info(
                "Starting " + services.size() + " threads (tasks)... (" +
                        services.values().stream().filter(s -> true).count() + "/" + // todo: enabled? filter
                        services.size() + " enabled)"
        );
        services.forEach((k, v) -> run(v));
    }
}
