package Commands;

import Commands.Handle.Command;
import Utils.Config;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Random;

public class rSubmit implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        Random rateId = new Random();
        int modify = rateId.nextInt(126258872) + 1;

        if(args.length >= 1) {

            event.getGuild().getTextChannelById(Config.getValue("submissionchannel")).sendMessage(

                    new EmbedBuilder()
                        .setColor(Color.RED)
                            .setAuthor(event.getAuthor().getAsTag())
                            .addField("Submission", args[0], true)
                            .setThumbnail(event.getAuthor().getAvatarUrl())
                            .setFooter("USER ID: " + event.getAuthor().getId() + " | " + event.getMessage().getCreationTime().getMonthValue() + "/" + event.getMessage().getCreationTime().getDayOfMonth() + "/" + event.getMessage().getCreationTime().getYear(), null)
                            .addField("ID", String.valueOf(modify), true)
                    .build()

            ).queue(message -> {
                Config.addKey(String.valueOf(message.getId()), String.valueOf(modify));
                Config.addKey(String.valueOf(modify), args[0]);
                Config.addKey("auth" + modify, event.getMember().getUser().getId());
                message.addReaction("✅").queue();
                message.addReaction("❌").queue();
            });

            event.getMessage().delete().queue();

        }else {
            event.getTextChannel().sendMessage("Please provide a link for your Submission!").queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
