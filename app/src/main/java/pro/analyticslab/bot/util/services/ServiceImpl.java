package pro.analyticslab.bot.util.services;

import pro.analyticslab.bot.AnalyticsLab;

import javax.annotation.Nonnull;

public interface ServiceImpl {
    void exec(@Nonnull AnalyticsLab root, @Nonnull Service service);
}
