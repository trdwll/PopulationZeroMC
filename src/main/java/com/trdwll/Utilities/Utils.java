package com.trdwll.Utilities;


import com.trdwll.Engine.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;

public class Utils {
	public static String Version = "0.1";
	
	public static final String prefixDefault = ChatColor.WHITE + "[" + ChatColor.BLUE + "PZM" + ChatColor.WHITE + "]" + ChatColor.WHITE;
	public static final String prefixOk = ChatColor.WHITE + "[" + ChatColor.GREEN + "PZM" + ChatColor.WHITE + "]"+ ChatColor.WHITE;
	public static final String prefixWarn = ChatColor.WHITE + "[" + ChatColor.YELLOW + "PZM" + ChatColor.WHITE + "]"+ ChatColor.WHITE;
	public static final String prefixError = ChatColor.WHITE + "[" + ChatColor.RED + "PZM" + ChatColor.WHITE + "]" + ChatColor.WHITE;
	public static final String prefixDebug = translate("&f[&4DEBUG&f]");
	public static final String modName = ChatColor.AQUA + "Population Zero Mod" + ChatColor.RESET;
	public static final String HelpMenu = ChatColor.WHITE + "[" + ChatColor.BLUE + "PZM" + ChatColor.WHITE + "]" + ChatColor.AQUA + " === Help Menu ===\n" + ChatColor.WHITE +  "/pzm help - help\n/pzm join - join game\n/pzm leave - leave game\n/pzm start - start game";
	public static final String KitMenu = ChatColor.WHITE + "[" + ChatColor.BLUE + "PZM" + ChatColor.WHITE + "]" + ChatColor.AQUA + " === Kit Menu ===\n" + ChatColor.WHITE +  "/pzm kit noob\n/pzm kit elite\n/pzm kit veteran\n/pzm kit god\n/pzm kit dev";
	public static final String AboutMenu = ChatColor.WHITE + "[" + ChatColor.BLUE + "PZM" + ChatColor.WHITE + "]" + ChatColor.AQUA + " === Population Zero Mod ===\nVersion: " + Version + "\nLead Programmer - Twitter/@trdwll\nLead Programmer - Twitter/@OhYea777\nProgrammer - Twitter/@parkbully3\nQA Tester - Twitter/@dalton_test";

    private static Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();

        builder.setPrettyPrinting();

        gson = builder.create();
    }

    public static Gson getGson() {
        return gson;
    }

	public static String translate(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

    public static void message(String message, Player player) {
        message(PrefixType.DEFAULT, message, player);
    }

    public static void message(PrefixType prefixType, String message, Player player) {
        player.sendMessage(translate(prefixType.getPrefix() + message));
    }

    public static void messageAll(String message, Lobby lobby) {
        messageAll(PrefixType.DEFAULT, message, lobby);
    }

    public static void messageAll(PrefixType prefixType, String message, Lobby lobby) {
        lobby.sendPlayersMessage(translate(prefixType.getPrefix() + message));
    }

	public static void defaultMessage(Player player, String string) {
		player.sendMessage(prefixDefault + " " + string);
	}
	
	public static void okMessage(Player player, String string) {
		player.sendMessage(prefixOk + " " + string);
	}
	
	public static void warnMessage(Player player, String string) {
		player.sendMessage(prefixWarn + " " + string);
	}
	
	public static void errorMessage(Player player, String string) {
		player.sendMessage(prefixError + " " + string);
	}
	
	public static void debugMessage(Player player, String string) {
		player.sendMessage(prefixDebug + " " + string);
	}

    public enum PrefixType {

        DEFAULT("&f[&1PZM&f] "),
        OK("&f[&2PZM&f] "),
        WARN("&f[&ePZM&f] "),
        ERROR("&f[&4PZM&f] "),
        DEBUG("&f[&4DEBUG&f] ");

        private String prefix;

        private PrefixType(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }

}
