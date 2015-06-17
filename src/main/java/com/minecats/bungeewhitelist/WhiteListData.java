package com.minecats.bungeewhitelist;

import net.cubespace.Yamler.Config.Comment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cindy on 11/1/2014.
 */
public class WhiteListData  extends  net.cubespace.Yamler.Config.Config {

    public WhiteListData(BungeeWhitelist plugin) {

        plugin.getLogger().info("[WhiteListData] msgData Constructor");

        CONFIG_HEADER = new String[]{"Whitelist Data"};
        CONFIG_FILE = new File(plugin.getDataFolder(), "data.yml");
    }

    @Comment("The list of Whitelisted players")
    public ArrayList<String> Whitelist;

//    @Comment("The list of players ignores")
//    public HashMap<String,PlayerIgnores> playerIgnores = new HashMap<>();;

}
