package pro.analyticslab.bot.util.slashcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;

public class SlashCommandsClient extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(SlashCommandsClient.class);
    private final List<String> OWNER_IDS;
    private final List<SlashCommand> COMMANDS;


    /**
     * Initializing slash commands client
     * @param ownerIds owner ids
     * @param commands slash commands objects
     */
    public SlashCommandsClient(@Nonnull List<String> ownerIds, @Nonnull List<SlashCommand> commands) {
        this.OWNER_IDS = ownerIds;
        this.COMMANDS = commands;

        logger.info(SlashCommandsClient.class.getSimpleName() + " module initialized");
    }


    /**
     * Get all cached slash commands
     * @return List<SlashCommand>
     */
    public List<SlashCommand> getCommands() {
        return COMMANDS;
    }

    /**
     * Get all cached owners ids
     * @return List<String>
     */
    public List<String> getOwners() {
        return OWNER_IDS;
    }


    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        // todo: handler
    }
}
