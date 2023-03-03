package pro.analyticslab.bot.util.services;

import pro.analyticslab.bot.AnalyticsLab;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class Service implements ServiceImpl {
    public String threadName;
    public long delay;
    public long startDelay;
    public TimeUnit periodType;
    public boolean isEnabled = true;
    public long lastRuntime = 0L;


    @Override
    public void exec(@Nonnull AnalyticsLab root, @Nonnull Service service) {}
}
