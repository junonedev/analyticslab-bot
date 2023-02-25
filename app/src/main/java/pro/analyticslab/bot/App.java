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
import pro.analyticslab.bot.util.slashcommands.SlashCommandsClientBuilder;

public class App {
    public ShardManager shardManager;

    public App() {
        SlashCommandsClientBuilder slashCommandsClientBuilder = SlashCommandsClientBuilder.initialize()
                .setOwners()
                .setCommands();

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(
                Props.getProperty("analyticslab.discord.auth"),
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
                        slashCommandsClientBuilder.build() // custom commands handler module
                );

        shardManager = builder.build();
    }

    public static void main(String[] args) throws Exception {
        new Props().load("source/config/.properties");
        new Constant().loadTranslations("source/translator/messages.json");

        new App();
    }
}
