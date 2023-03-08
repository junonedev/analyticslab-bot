package pro.analyticslab.bot.util;

import java.time.OffsetDateTime;

import java.util.List;
import java.util.Objects;

public class Variables {
    public static final OffsetDateTime STARTUP = OffsetDateTime.now();
    public static final String SUPPORT_GUILD_ID = Config.getProperty("analyticslab.guild.id");
    public static final List<String> SUPPORT_LOCALES = List.of(Config.getProperty(
            "analyticslab.options.locales.support").split(","));
    public static final String PRIMARY_LOCALE = Config.getProperty("analyticslab.options.locales.primary");
    public static final List<String> OWNER_IDS = List.of(Config.getProperty("analyticslab.owners").split(","));
    public static final boolean REBOOT_DIED_SHARD = Objects.equals(
            Config.getProperty("analyticslab.options.rebootDiedShard"), "true");
}
