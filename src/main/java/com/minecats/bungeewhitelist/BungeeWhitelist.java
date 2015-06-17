package com.minecats.bungeewhitelist;

import com.minecats.bungeewhitelist.commands.CommandWhitelist;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;

/**
 * Created by cindy on 11/1/2014.
 */
public class BungeeWhitelist extends Plugin implements Listener {

    private static BungeeWhitelist instance;
    public static WhiteListData whiteListData;

    public static String adminPerm = "whitelist.admin";
    public static String kickMessage = "You are not whitelisted on this server. Please see the management!";

    @Override
    public void onEnable() {
        super.onEnable();

        getProxy().getPluginManager().registerCommand(this, new CommandWhitelist("whitelist"));
        getProxy().getPluginManager().registerListener(this,this);

        setupWhitelist();

    }

    void setupWhitelist()
    {
        try {
            whiteListData = new WhiteListData(this);
            whiteListData.init();
            whiteListData.load();

            if(whiteListData.Whitelist==null)
            {
                whiteListData.Whitelist = new ArrayList<String>();
            }



        } catch(InvalidConfigurationException ex) {

            getLogger().info("[catMsg] Your Data YML was wrong");
            ex.printStackTrace();
        }


    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static void saveData(){

        try{
            whiteListData.save();
        }
        catch(InvalidConfigurationException ex)
        {
            instance.getLogger().info("Data save failed! " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    @EventHandler
    public void onLogin(PostLoginEvent event)
    {
        ProxiedPlayer p = event.getPlayer();

        //Admins are automatically whitelisted.
        if(p.hasPermission(BungeeWhitelist.adminPerm))
            return;


        if(!whiteListData.Whitelist.contains(p.getName().toLowerCase()))
        {
            getLogger().info("[BungeeWhitelist] Player "+ p.getName() +" is not whitelisted." );
            TextComponent kickmessage = new TextComponent(kickMessage);
            kickmessage.setColor(ChatColor.RED);
            p.disconnect(kickmessage);
        }

    }

    @EventHandler
    public void onServerConnect(ServerConnectEvent event)
    {
        ProxiedPlayer p = event.getPlayer();

        //Admins are automatically whitelisted.
        if(p.hasPermission(BungeeWhitelist.adminPerm))
            return;


        if(!whiteListData.Whitelist.contains(p.getName().toLowerCase()))
        {
            getLogger().info("[BungeeWhitelist] Serverconnect Event Player "+ p.getName() +" is not whitelisted." );
            TextComponent kickmessage = new TextComponent(kickMessage);
            kickmessage.setColor(ChatColor.RED);
            event.setCancelled(true);
            p.disconnect(kickmessage);
        }
    }

}
