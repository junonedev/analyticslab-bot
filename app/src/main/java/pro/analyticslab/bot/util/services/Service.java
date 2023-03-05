package pro.analyticslab.bot.util.services;

import java.util.concurrent.TimeUnit;

public class Service implements ServiceImpl {
    public String threadName;
    public long delay;
    public long startDelay;
    public TimeUnit periodType;


    @Override
    public void exec() {}
}
