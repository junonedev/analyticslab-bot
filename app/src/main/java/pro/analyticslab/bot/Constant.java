package pro.analyticslab.bot;

import java.time.OffsetDateTime;

public class Constant {
    public static final OffsetDateTime STARTUP = OffsetDateTime.now();
    public static final String SUPPORT_GUILD_ID = Props.getProperty("analyticslab.guild.id");
}
