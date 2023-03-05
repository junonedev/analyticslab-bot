package pro.analyticslab.bot.services;


import gnu.trove.map.hash.TIntLongHashMap;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.internal.JDAImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.analyticslab.bot.AnalyticsLab;
import pro.analyticslab.bot.util.Variables;
import pro.analyticslab.bot.util.services.Service;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

import static gnu.trove.impl.Constants.DEFAULT_CAPACITY;
import static gnu.trove.impl.Constants.DEFAULT_LOAD_FACTOR;

public class ShardWatcher extends Service {
    private final Logger logger = LoggerFactory.getLogger(ShardWatcher.class);

    private final AnalyticsLab analyticsLab;
    private static final TIntLongHashMap shardMap = new TIntLongHashMap(
            DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR,
            -1, -18
    );


    public ShardWatcher(@Nonnull AnalyticsLab root) {
        this.analyticsLab = root;

        this.threadName = "ShardWatcher";
        this.delay = 10;
        this.startDelay = 10;
        this.periodType = TimeUnit.MINUTES;
    }


    public static void insertShard(int shardId, long ping) {
        shardMap.put(shardId, ping);
    }


    public static void setJDAContext(JDA jda) {
        ((JDAImpl) jda).setContext();
    }

    @Override
    public void exec() {
        ShardManager shardManager = analyticsLab.getShardManager();

        for (JDA shard : shardManager.getShardCache()) {
            setJDAContext(shard);

            JDA.ShardInfo info = shard.getShardInfo();
            int shardId = info.getShardId();


            if (shardMap.get(shardId) < 1) {
                if (Variables.REBOOT_DIED_SHARD) {
                    logger.warn("shard {} is down, rebooting it...", info.getShardId());
                    shardManager.restart(shardId);
                } else {
                    logger.warn("shard {} is possibly down", info.getShardId());
                }

                shardMap.remove(shardId);
            }
        }
    }
}
