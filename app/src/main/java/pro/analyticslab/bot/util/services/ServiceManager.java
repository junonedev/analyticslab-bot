package pro.analyticslab.bot.util.services;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.analyticslab.bot.AnalyticsLab;

import javax.annotation.Nonnull;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ServiceManager extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(ServiceManager.class);

    private final HashMap<String, Service> services;
    private final HashMap<String, ScheduledExecutorService> scheduledExecutorServiceHashMap;
    private final AnalyticsLab root;


    public ServiceManager(
            HashMap<String, Service> services,
            HashMap<String, ScheduledExecutorService> scheduled,
            AnalyticsLab root
    ) {
        this.services = services;
        this.scheduledExecutorServiceHashMap = scheduled;
        this.root = root;

        logger.info(ServiceManager.class.getSimpleName() + " built (" + services.size() +
                " services initialized)");
    }


    private void run(@Nonnull Service s) {
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor((r) -> {
            final Thread thread = new Thread(r, s.threadName);
            thread.setDaemon(true);
            return thread;
        });
        service.scheduleAtFixedRate(() -> {
            if (s.isEnabled) {
                s.exec(root, s);
                s.lastRuntime = OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond();
            }
        }, s.delay, s.startDelay, s.periodType);

        scheduledExecutorServiceHashMap.put(s.threadName, service);
    }


    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        logger.info(
                "Starting " + services.size() + " threads (tasks)... (" +
                        services.values().stream().filter(p -> p.isEnabled).count() + "/" +
                        services.size() + " enabled)"
        );
        services.forEach((k, v) -> run(v));
    }
}
