package pro.analyticslab.bot.util.slashcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SlashCommand implements SlashCommandImpl {
    public String commandName;
    public String commandDescription;
    public List<OptionData> options = new ArrayList<>();
    public CommandPermissions permission = CommandPermissions.ALL;


    @Override
    public void exec(@NotNull SlashCommandInteractionEvent event, String locale) {}


    public enum CommandPermissions {
        ALL ("0"),
        ADMINISTRATOR ("1"),
        OWNER ("-1");

        CommandPermissions(String permission) {}
    }
}
