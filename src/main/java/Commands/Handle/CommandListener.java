package Commands.Handle;

import Utils.Config;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Objects;

public class CommandListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().startsWith(Config.RunConfig("prefix"))
                && !Objects.equals(event.getMessage().getAuthor().getId(), event.getJDA().getSelfUser().getId())
                && !event.getMessage().getAuthor().isBot())
        {

            try {
                CommandHandler.handleCommand(CommandHandler.parse.parser(event.getMessage().getContentRaw(), event, Config.RunConfig("prefix")));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            return;
        }

    }

}
