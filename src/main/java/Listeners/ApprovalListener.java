package Listeners;

import Utils.Config;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class ApprovalListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        if(event.getReaction().getTextChannel().getId().contains(Config.getValue("submissionchannel")) && event.getReaction().getReactionEmote().toString().contains("✅") && event.getMember().getUser().getId().contains("265849018662387712")) {
            event.getJDA().getUserById(Config.getValue("auth" + Config.getValue(event.getMessageId()))).openPrivateChannel().queue(channel -> {
                channel.sendMessage(

                        new EmbedBuilder()
                                .setTitle("Congratulations, you submission has been approved!")
                                .setColor(Color.GREEN)
                                .addField("Submission", Config.getValue(Config.getValue(event.getMessageId())), true)
                                .addField("ID", Config.getValue(event.getMessageId()), true)
                                .build()

                ).queue();
            });

            event.getGuild().getTextChannelById(Config.getValue("sublog")).sendMessage(

                    new EmbedBuilder()
                        .setTitle("Log for " + Config.getValue(event.getMessageId()))
                            .addField("Submission", Config.getValue(Config.getValue(event.getMessageId())), true)
                            .addField("Author", event.getJDA().getUserById(Config.getValue("auth" + Config.getValue(event.getMessageId()))).getAsMention(), false)
                            .addField("Status", "Approved", true)
                    .build()

            ).queue();

            event.getGuild().getTextChannelById(Config.getValue("submissionchannel")).getMessageById(event.getMessageId()).queue(message -> message.delete().queue());

        }else if (event.getReaction().getTextChannel().getId().contains(Config.getValue("submissionchannel")) && event.getReaction().getReactionEmote().toString().contains("❌") && event.getMember().getUser().getId().contains("265849018662387712")) {
            event.getJDA().getUserById(Config.getValue("auth" + Config.getValue(event.getMessageId()))).openPrivateChannel().queue(channel -> {
                channel.sendMessage(

                        new EmbedBuilder()
                                .setTitle("Your Submission has been rejected!")
                                .setColor(Color.RED)
                                .addField("Submission", Config.getValue(Config.getValue(event.getMessageId())), true)
                                .addField("ID", Config.getValue(event.getMessageId()), true)
                                .build()

                ).queue();
            });

            event.getGuild().getTextChannelById(Config.getValue("sublog")).sendMessage(

                    new EmbedBuilder()
                            .setTitle("Log for " + Config.getValue(event.getMessageId()))
                            .addField("Submission", Config.getValue(Config.getValue(event.getMessageId())), true)
                            .addField("Author", event.getJDA().getUserById(Config.getValue("auth" + Config.getValue(event.getMessageId()))).getAsMention(), false)
                            .addField("Status", "Rejected", true)
                            .build()

            ).queue();

            event.getGuild().getTextChannelById(Config.getValue("submissionchannel")).getMessageById(event.getMessageId()).queue(message -> message.delete().queue());

        }

        Config.deleteKey(Config.getValue("auth" + Config.getValue(event.getMessageId())));
        Config.deleteKey(Config.getValue(Config.getValue(event.getMessageId())));
        Config.init();

    }
}
