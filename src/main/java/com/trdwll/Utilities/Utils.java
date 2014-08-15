package com.trdwll.Utilities;


import com.trdwll.Engine.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {
	public static String Version = "0.1";
	
	public static final String prefixDefault = ChatColor.WHITE + "[" + ChatColor.BLUE + "PZM" + ChatColor.WHITE + "]" + ChatColor.WHITE;
	public static final String prefixOk = ChatColor.WHITE + "[" + ChatColor.GREEN + "PZM" + ChatColor.WHITE + "]"+ ChatColor.WHITE;
	public static final String prefixWarn = ChatColor.WHITE + "[" + ChatColor.YELLOW + "PZM" + ChatColor.WHITE + "]"+ ChatColor.WHITE;
	public static final String prefixError = ChatColor.WHITE + "[" + ChatColor.RED + "PZM" + ChatColor.WHITE + "]" + ChatColor.WHITE;
	public static final String prefixDebug = translate("&f[&4DEBUG&f]");
	public static final String modName = ChatColor.AQUA + "Population Zero Mod" + ChatColor.RESET;

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

    public static void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[] { new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR) });
    }

    public static void removeEntitiesBetween(Location locOne, Location locTwo) {
        Chunk c1 = locOne.getChunk();
        Chunk c2 = locTwo.getChunk();

        int xMin = Math.min(c1.getX(), c2.getX());
        int xMax = Math.max(c1.getX(), c2.getX());
        int zMin = Math.min(c1.getZ(), c2.getZ());
        int zMax = Math.max(c1.getZ(), c2.getZ());

        for (int x = xMin; x <= xMax; x++)
            for (int z = zMin; z <= zMax; z ++)
                for (Entity entity : locOne.getWorld().getChunkAt(x, z).getEntities())
                    if (!entity.isDead() && !(entity instanceof Player))
                        entity.remove();
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
