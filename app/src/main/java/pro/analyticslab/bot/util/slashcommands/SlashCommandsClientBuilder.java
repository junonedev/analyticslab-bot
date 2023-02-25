package pro.analyticslab.bot.util.slashcommands;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SlashCommandsClientBuilder {
    private List<String> OWNER_IDS;
    private List<SlashCommand> COMMANDS;


    protected SlashCommandsClientBuilder() {
        this.OWNER_IDS = new ArrayList<>();
        this.COMMANDS = new ArrayList<>();
    }

    /**
     * Starting to config slash commands module
     * @return SlashCommandsClientBuilder
     */
    public static SlashCommandsClientBuilder initialize() {
        return new SlashCommandsClientBuilder();
    }


    /**
     * Set bot owners (ids)
     * @param ids String
     * @return SlashCommandsClientBuilder
     */
    public SlashCommandsClientBuilder setOwners(@Nonnull String... ids) {
        OWNER_IDS = List.of(ids);
        return this;
    }

    /**
     * Add some commands to slash commands client builder
     * @param commands Command list (SlashCommand)
     * @return SlashCommandsClientBuilder
     */
    public SlashCommandsClientBuilder setCommands(@Nonnull SlashCommand... commands) {
        COMMANDS = List.of(commands);
        return this;
    }

    /**
     * Build slash commands client
     * @return SlashCommandsClient
     */
    public SlashCommandsClient build() {
        return new SlashCommandsClient(OWNER_IDS, COMMANDS);
    }
}
