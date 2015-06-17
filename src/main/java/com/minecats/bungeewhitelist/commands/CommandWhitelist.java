package com.minecats.bungeewhitelist.commands;

import com.minecats.bungeewhitelist.BungeeWhitelist;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cindy on 11/1/2014.
 */
public class CommandWhitelist extends Command {

    TextComponent basemessage;

    public CommandWhitelist(String name)
    {
        super(name);
        basemessage = new TextComponent( "[Whitelist] " );
        basemessage.setColor( ChatColor.GOLD );
        basemessage.setBold(true);
    }

    @Override
    public void execute(CommandSender mcSender, String[] args) {

        if(!mcSender.hasPermission(BungeeWhitelist.adminPerm)) {
            TextComponent msg4 = new TextComponent("You do not have permissions for this command.");
            msg4.setColor(ChatColor.RED);
            mcSender.sendMessage(msg4);
            return;
        }

        if(args.length<2){
            TextComponent msg4 = new TextComponent("You do not have the proper number of arguments. whitelist [add/remove/list] [player]");
            msg4.setColor(ChatColor.RED);
            mcSender.sendMessage(msg4);
            return;
        }

        switch(args[0].toLowerCase())
        {
            case "add":
                //Add Player to whitelist
                WhiteListPlayer(mcSender, args[1].toLowerCase());
                break;

            case "list":
                //List Whitelist
                TextComponent msg4 = new TextComponent("Not Implemented...");
                msg4.setColor(ChatColor.RED);
                mcSender.sendMessage(msg4);
                break;

            case "remove":
                //Remove Player from whitelist
                RemoveWhitelistPlayer(mcSender, args[1].toLowerCase());
                break;

        }


    }

    void WhiteListPlayer(CommandSender cs, String pp)
    {
        if(BungeeWhitelist.whiteListData.Whitelist.contains(pp))
        {
            List<BaseComponent> bclist = new ArrayList<>();

            TextComponent msg2 = new TextComponent(pp + "is already whitelisted. " );
            msg2.setColor(ChatColor.GOLD);
            bclist.add(msg2);

            basemessage.setExtra(bclist);
            cs.sendMessage(basemessage);
            return;

        }

        BungeeWhitelist.whiteListData.Whitelist.add(pp);
        BungeeWhitelist.saveData();

        List<BaseComponent> bclist = new ArrayList<>();

        TextComponent msg2 = new TextComponent("The Player " );
        msg2.setColor(ChatColor.GRAY);
        bclist.add(msg2);

        TextComponent msg3 = new TextComponent(pp);
        msg3.setColor(ChatColor.YELLOW);
        bclist.add(msg3);

        TextComponent msg4 = new TextComponent(" has been whitelisted.");
        msg4.setColor(ChatColor.GRAY);
        bclist.add(msg4);

        basemessage.setExtra( bclist );

        cs.sendMessage( basemessage );
    }

    void RemoveWhitelistPlayer(CommandSender cs, String pp)
    {
        BungeeWhitelist.whiteListData.Whitelist.remove(pp);
        BungeeWhitelist.saveData();

        List<BaseComponent> bclist = new ArrayList<>();

        TextComponent msg2 = new TextComponent("The Player " );
        msg2.setColor(ChatColor.GRAY);
        bclist.add(msg2);

        TextComponent msg3 = new TextComponent(pp);
        msg3.setColor(ChatColor.YELLOW);
        bclist.add(msg3);

        TextComponent msg4 = new TextComponent(" has been removed from the Whitelist.");
        msg4.setColor(ChatColor.GRAY);
        bclist.add(msg4);

        basemessage.setExtra( bclist );


        cs.sendMessage( basemessage );
    }

}
