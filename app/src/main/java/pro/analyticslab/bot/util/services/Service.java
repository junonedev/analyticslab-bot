package pro.analyticslab.bot.util.services;

import kotlin.jvm.functions.Function2;
import pro.analyticslab.bot.AnalyticsLab;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class Service {
    private final String threadName;
    private final Function2<AnalyticsLab, Service, Boolean> function;
    private final long delay;
    private final long startDelay;
    private final TimeUnit periodType;
    private boolean isEnabled = true;
    private long lastRuntime = 0L;


    /**
     * Get service object
     * @param threadName thread name
     * @param function service function
     */
    public Service(
            @Nonnull String threadName,
            @Nonnull Function2<AnalyticsLab, Service, Boolean> function,
            long delay,
            long startDelay,
            @Nonnull TimeUnit periodType
    ) {
        this.threadName = threadName;
        this.function = function;
        this.delay = delay;
        this.startDelay = startDelay;
        this.periodType = periodType;
    }

    /**
     * Service feature. PLEASE DON'T USE IT!
     * @param currentUTC Current timestamp in UTC format
     * @return Service
     */
    public Service setLastRuntime(@Nonnull Long currentUTC) {
        this.lastRuntime = currentUTC;
        return this;
    }

    public Service isEnabled(boolean el) {
        this.isEnabled = el;
        return this;
    }


    /**
     * Returns service thread name
     * @return String
     */
    public String getThreadName() {
        return threadName;
    }

    /**
     * Returns service's runnable function
     * @return Function2<AnalyticsLab, Service, Boolean>
     */
    public Function2<AnalyticsLab, Service, Boolean> getFunction() {
        return function;
    }

    /**
     * Returns number of period for specified time type (minutes, hours)
     * @return Long
     */
    public long getDelay() {
        return delay;
    }


    /**
     * Returns number of delay
     * @return Long
     */
    public long getStartDelay() {
        return startDelay;
    }

    /**
     * Returns time type (minutes, hours)
     * @return TimeUnit
     */
    public TimeUnit getPeriodType() {
        return periodType;
    }

    /**
     * Is this service enabled currently???
     * @return boolean
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Get last run time of the service in UTC timestamp format
     * @return Long
     */
    public Long getLastRuntime() {
        return lastRuntime;
    }
}
