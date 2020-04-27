package Listeners;

import Utils.Config;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class ApprovalListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        try{

        if(event.getReaction().getTextChannel().getId().contains(Config.getValue("submissionchannel")) && event.getReaction().getReactionEmote().toString().contains("✅") && event.getGuild().getMemberById(event.getUser().getId()).getRoles().contains(event.getGuild().getRoleById(Config.getValue("allowed")))) {
            event.getJDA().getUserById(Config.getValue("auth" + Config.getValue(event.getMessageId()))).openPrivateChannel().queue(channel -> {
                channel.sendMessage(

                        new EmbedBuilder()
                                .setTitle("Congratulations, your submission has been approved!")
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

            ).complete();

            event.getGuild().getTextChannelById(Config.getValue("submissionchannel")).getMessageById(event.getMessageId()).queue(message -> message.delete().queue());

            Config.deleteKey("auth" + Config.getValue(event.getMessageId()));
            Config.deleteKey(Config.getValue(event.getMessageId()));
            Config.deleteKey(event.getMessageId());

        }else if (event.getReaction().getTextChannel().getId().contains(Config.getValue("submissionchannel")) && event.getReaction().getReactionEmote().toString().contains("❌") && event.getGuild().getMemberById(event.getUser().getId()).getRoles().contains(event.getGuild().getRoleById(Config.getValue("allowed")))) {
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

            ).complete();

            event.getGuild().getTextChannelById(Config.getValue("submissionchannel")).getMessageById(event.getMessageId()).queue(message -> message.delete().queue());

            Config.deleteKey("auth" + Config.getValue(event.getMessageId()));
            Config.deleteKey(Config.getValue(event.getMessageId()));
            Config.deleteKey(event.getMessageId());
            Config.init();

        }else {
            //EMPTY | SHOULD PREVENT ERRORS THO
        }




    }catch (Exception e) {
        Config.addKey("crashlogAL", e.getMessage());
    }

    }
}
