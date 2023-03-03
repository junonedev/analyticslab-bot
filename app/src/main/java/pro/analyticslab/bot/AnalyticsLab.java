/**
 * @author jyunone
 */

package pro.analyticslab.bot;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import pro.analyticslab.bot.util.services.ServiceManager;
import pro.analyticslab.bot.util.services.ServiceManagerBuilder;
import pro.analyticslab.bot.util.slashcommands.SlashCommandsClientBuilder;

public class AnalyticsLab {
    private final ShardManager shardManager;
    private final ServiceManager serviceManager;


    public AnalyticsLab() {
        SlashCommandsClientBuilder slashCommandsClientBuilder = SlashCommandsClientBuilder.initialize()
                .setOwners()
                .setCommands();


        ServiceManagerBuilder serviceManagerBuilder = ServiceManagerBuilder.initialize(this)
                .addServices(
                );

        serviceManager = serviceManagerBuilder.build();


        DefaultShardManagerBuilder shardManagerBuilder = DefaultShardManagerBuilder.createDefault(
                Config.getProperty("analyticslab.discord.auth"),
                GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_VOICE_STATES
        )
                .setMemberCachePolicy(MemberCachePolicy.NONE)
                .enableCache(
                        CacheFlag.MEMBER_OVERRIDES,
                        CacheFlag.EMOJI,
                        CacheFlag.STICKER,
                        CacheFlag.VOICE_STATE
                )
                .disableCache(
                        CacheFlag.ACTIVITY,
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.FORUM_TAGS,
                        CacheFlag.ONLINE_STATUS,
                        CacheFlag.ROLE_TAGS,
                        CacheFlag.SCHEDULED_EVENTS
                )
                .setActivity(Activity.watching(
                        "the bot wakes up... " + Emoji.fromUnicode("\uD83D\uDCA4").getFormatted()
                ))
                .setShardsTotal(-1)
                .addEventListeners(
                        new Listener(),
                        slashCommandsClientBuilder.build(), // custom commands handler module
                        serviceManager // custom service manager
                );

        shardManager = shardManagerBuilder.build();
    }

    public static void main(String[] args) throws Exception {
        new Config().loadProperties("source/config/.properties");
        new Variables().loadTranslator("source/translator/messages.json");

        new AnalyticsLab();
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }
}
