package pro.analyticslab.bot.util.slashcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public interface SlashCommandImpl {

    void exec(@NotNull SlashCommandInteractionEvent event, String locale);
}
