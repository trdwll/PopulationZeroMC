package com.trdwll.Utilities;


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
	
	
	// DEBUG
	
	public static final String prefixDebug = translate("&f[&4DEBUG&f]");
	
	// END DEBUG
	
	public static final String modName = ChatColor.AQUA + "Population Zero Mod" + ChatColor.RESET;

    // Hai

	public static final String HelpMenu = ChatColor.WHITE + "[" + ChatColor.BLUE + "PZM" + ChatColor.WHITE + "]" + ChatColor.AQUA + " === Help Menu ===\n" + ChatColor.WHITE +  "/pzm help - help\n/pzm join - join game\n/pzm leave - leave game\n/pzm start - start game";
	public static final String KitMenu = ChatColor.WHITE + "[" + ChatColor.BLUE + "PZM" + ChatColor.WHITE + "]" + ChatColor.AQUA + " === Kit Menu ===\n" + ChatColor.WHITE +  "/pzm kit noob\n/pzm kit elite\n/pzm kit veteran\n/pzm kit god\n/pzm kit dev";
	public static final String AboutMenu = ChatColor.WHITE + "[" + ChatColor.BLUE + "PZM" + ChatColor.WHITE + "]" + ChatColor.AQUA + " === Population Zero Mod ===\nVersion: " + Version + "\nLead Programmer - Twitter/@trdwll\nLead Programmer - Twitter/@OhYea777\nProgrammer - Twitter/@parkbully3\nQA Tester - Twitter/@dalton_test";

	// 135.39872, 15.000, 241.46764 default
	// 107.56087, 15.000, 252.69701
	// 123.48725, 15.000, 252.59861
	// 120.33968, 5.000, 237.05808

    // test message
	public static double[] spawnPoint1 = {135.39872, 15.000, 241.46764};
	public static double[] spawnPoint2 = {107.56087, 15.000, 252.69701};
	public static double[] spawnPoint3 = {123.48725, 15.000, 252.59861};
	public static double[] spawnPoint4 = {120.33968, 5.000, 237.05808};

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

}
