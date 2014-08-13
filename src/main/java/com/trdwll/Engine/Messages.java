package com.trdwll.Engine;

import com.trdwll.Game.initGame;
import com.trdwll.Utilities.Utils;

import java.util.HashMap;
import java.util.Map;

public class Messages {

    private static initGame plugin;

    public static void setPlugin(initGame plugin) {
        Messages.plugin = plugin;
    }

    public static String addPrefix(String message) {
        return addPrefix(Utils.PrefixType.DEFAULT, message);
    }

    public static String addPrefix(Utils.PrefixType prefixType, String message) {
        return message.replace("%prefix%", prefixType.getPrefix().substring(0, Math.max(0, prefixType.getPrefix().length() - 1)));
    }

    public static String getCommandHelp(int page, int pages) {
        return getCommandHelp(Utils.PrefixType.DEFAULT, page, pages);
    }

    public static String getCommandHelp(Utils.PrefixType prefixType, int page, int pages) {
        return Utils.translate(addPrefix(prefixType, plugin.getConfig().getString("Messages.CommandHelp", "%prefix% &3Command Help &8(&3Page &b%page% &8/ &b%pages%&8):").replace("%page%", String.valueOf(page)).replace("%pages%", String.valueOf(pages))));
    }

    public static String getHelpFormat(String help, String command) {
        return Utils.translate(plugin.getConfig().getString("Messages.HelpFormat", "&8-&3] &3%help%&8: &b%command%").replace("%help%", help).replace("%command%", command));
    }

    public static String getReloaded() {
        return Utils.translate(addPrefix(plugin.getConfig().getString("Messages.Reloaded", "%prefix% &3Configurations Reloaded!")));
    }

    public static String getPlayersOnly() {
        return Utils.translate(addPrefix(plugin.getConfig().getString("Messages.PlayersOnly", "%prefix% &cPlayers Only!")));
    }

    public static String getNoPermission() {
        return Utils.translate(addPrefix(plugin.getConfig().getString("Messages.NoPermission", "%prefix% &cYou Do Not Have Permission for That!")));
    }

    public static String getNightVisionAdded() {
        return Utils.translate(addPrefix(plugin.getConfig().getString("Messages.NightVision", "%prefix% &3Night Vision Added")));
    }

    public static String getInvalidCommand() {
        return Utils.translate(addPrefix(plugin.getConfig().getString("Messages.InvalidCommand", "%prefix% &cInvalid Command!")));
    }

    public static String getTeleportedToSpawn() {
        return Utils.translate(addPrefix(plugin.getConfig().getString("Messages.TeleportedToSpawn", "%prefix% &3Teleported to Spawn")));
    }

    public static String getLobbyFull(Lobby lobby) {
        return Utils.translate(addPrefix(plugin.getConfig().getString("Messages.LobbyFull", "%prefix% &cThe Lobby for Map&8: &3%map% &8- &3is Full!").replace("%map%", lobby.getMapDetails().getMapName())));
    }

    public static String getLobbyInvalid() {
        return Utils.translate(addPrefix(plugin.getConfig().getString("Messages.LobbyInvalid", "%prefix% &cInvalid Lobby!")));
    }

    public static String getLobbyLeave() {
        return Utils.translate(addPrefix(plugin.getConfig().getString("Messages.LobbyLeave", "%prefix% &Left Lobby")));
    }

}
