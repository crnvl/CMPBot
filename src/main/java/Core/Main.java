package Core;

import Commands.Handle.CommandHandler;
import Commands.Handle.CommandListener;
import Commands.rSubmit;
import Listeners.ApprovalListener;
import Utils.Config;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

public class Main {

    public static JDABuilder builder;

    public static void main(String[] Args) throws LoginException {

        Config.init();
        if(!Config.propExist("submissionchannel")) {
            Config.addKey("submissionchannel", "704161800744206398");
        }
        if(!Config.propExist("sublog")) {
            Config.addKey("sublog", "704176921344475137");
        }

        builder = new JDABuilder(AccountType.BOT);

        //Important
        builder.setToken(Config.RunConfig("token"));

        //Status
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setGame(Game.watching("Submissions | " + Config.RunConfig("prefix") + "submit"));

        builder.addEventListener(new CommandListener());
        builder.addEventListener(new ApprovalListener());

        Commands();

        JDA jda = builder.build();
    }

    public static void Commands() {

        CommandHandler.commands.put("submit", new rSubmit());

    }

}
