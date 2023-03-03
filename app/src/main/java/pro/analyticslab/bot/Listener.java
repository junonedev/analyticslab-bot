package pro.analyticslab.bot;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Listener extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger("Listener");

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        logger.info("Connected to " + event.getJDA().getSelfUser().getAsTag());

        // activity
        Objects.requireNonNull(event.getJDA().getShardManager()).setActivity(Activity.playing("Terraria"));
        logger.info("Activity initialized");
    }
}
