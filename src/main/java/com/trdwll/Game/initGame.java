package com.trdwll.Game;

import java.io.File;
import java.util.*;

import com.trdwll.Engine.Messages;
import com.trdwll.Engine.commands.CommandRegistry;
import com.trdwll.Utilities.GsonFileUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.trdwll.Engine.Lobby;
import com.trdwll.Engine.initEngine;
import com.trdwll.Utilities.Utils;

public class initGame extends JavaPlugin {

    private static initGame INSTANCE;

    public Location spawn;
    private Map<String, Lobby> lobbies;
    private CommandRegistry commandRegistry;

    public void onEnable() {
        INSTANCE = this;

        saveResource("config.yml", false);

        GsonFileUtils.setPlugin(this);
        Messages.setPlugin(this);

        lobbies = new HashMap<String, Lobby>();
        spawn = new Location(getServer().getWorld("world"), 2322, 4, -261);
        commandRegistry = new CommandRegistry();

        try {
            this.getServer().getPluginManager().registerEvents(new initEngine(this), this);
            System.out.print(Utils.prefixOk + "Successful startup!");
        }
        catch (Exception e) {
            System.out.print(Utils.prefixError + "Error starting up!");
        }

        // lobbies.add(new Lobby(this, GsonFileUtils.loadMapDetailsFromFile("DevArena.json", true)));
        reload();

        // lobbyOne = new Lobby(this, new Location(getServer().getWorld("world"), 138, 15, 247), getServer().getWorld("world"), 135, 10, 236, 104, 25, 252);
        // lobbyOne = new Lobby(this, new Location(getServer().getWorld("world"), 205, 70, 298), getServer().getWorld("world"), 135, 10, 236, 104, 25, 252);
		
		/* List<Location> locations = new ArrayList<Location>();
		List<Location> location = new ArrayList<Location>();
		locations.add(new Location(Bukkit.getWorld("world"), 192, 70, 298));
		locations.add(new Location(Bukkit.getWorld("world"), 191, 69, 291));

		location.add(new Location(Bukkit.getWorld("world"), -508, 62, 2700));
		location.add(new Location(Bukkit.getWorld("world"), -511, 62, 2682));
		location.add(new Location(Bukkit.getWorld("world"), -501, 62, 2658));
		location.add(new Location(Bukkit.getWorld("world"), -463, 62, 2651));
		location.add(new Location(Bukkit.getWorld("world"), -463, 62, 2651));
		location.add(new Location(Bukkit.getWorld("world"), -457, 62, 2677));
		location.add(new Location(Bukkit.getWorld("world"), -449, 63, 2696));

		lobbyOne = new Lobby(this, new Location(getServer().getWorld("world"), 205, 70, 298), new Location(getServer().getWorld("world"), 204, 70, 288), locations);

		// lobby = new Lobby(this, <lobby spawn>, (<game spawn>), (<list of zombie spawns>));
		area51 = new Lobby(this, new Location(getServer().getWorld("world"), -486, 109, 2741), new Location(getServer().getWorld("world"), -497, 62, 2726), location); */

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                for (World world : getServer().getWorlds()) {
                    if (world.getTime() < 14000 || world.getTime() >= 22000)
                        world.setTime(14000);
                }
            }

        }, 20, 20);
    }

    public static initGame getInstance() {
        return INSTANCE;
    }

    public void reload() {
        lobbies.clear();

        for (Lobby lobby : GsonFileUtils.loadAllLobbiesFromDirectory(new File(getDataFolder(), "MapDetails")))
            lobbies.put(lobby.getMapDetails().getMapName().toLowerCase(), lobby);

        reloadConfig();
    }

    public Collection<Lobby> getLobbies() {
        return lobbies.values();
    }

    public Map<String, Lobby> getNamedLobbies() {
        return lobbies;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/* Player player = (Player) sender;
		// PZM Commands
		if (cmd.getName().equalsIgnoreCase("pzm")) {
			if (args.length == 0)
				sender.sendMessage(Utils.AboutMenu);
			else if (args.length >= 1 && args[0].equalsIgnoreCase("help") && sender.hasPermission("pzm.help"))
				sender.sendMessage(Utils.HelpMenu);
			else if (args.length == 2 && args[0].equalsIgnoreCase("join") && sender.hasPermission("pzm.join")) {
				sender.sendMessage("join message & code to join match (call from engine class)");
				
				if (lobbies.size() > 0) {
                    int i;

                    try {
                        i = Integer.valueOf(args[1]);
                    } catch (NumberFormatException e) {
                        Lobby l = null;

                        for (Lobby lobby : getLobbies())
                            if (lobby.getMapDetails().getMapName().equalsIgnoreCase(args[1]))
                                l = lobby;

                        if (l != null) {
                            Utils.message(Utils.PrefixType.DEFAULT, "Joining Lobby for Map: " + l.getMapDetails().getMapName(), player);

                            if (l.canPlayerJoin())
                                l.addPlayerToLobby(player);
                            else
                                player.sendMessage(Utils.prefixWarn + "Lobby is full for Map: " + l.getMapDetails().getMapName());
                        } else {
                            Utils.message(Utils.PrefixType.ERROR, "Invalid Lobby ID!", player);
                        }

                        return true;
                    }

                    if (lobbies.get(i - 1) != null) {
                        Lobby lobby = lobbies.get(i - 1);

                        Utils.message(Utils.PrefixType.DEFAULT, "Joining Lobby for Map: " + lobby.getMapDetails().getMapName(), player);

                        if (lobby.canPlayerJoin())
                            lobby.addPlayerToLobby(player);
                        else
                            player.sendMessage(Utils.prefixWarn + "Lobby is full for Map: " + lobby.getMapDetails().getMapName());
                    } else {
                        Utils.message(Utils.PrefixType.ERROR, "Invalid Lobby ID!", player);
                    }
                } else {
                    Utils.message(Utils.PrefixType.ERROR, "There are no lobbies available!", player);
                }
				*//* else if (args[1].equalsIgnoreCase("area51")) {
					if (area51.canPlayerJoin())
						area51.addPlayerToLobby(player);
					else
						player.sendMessage(Utils.prefixWarn + "Area51 Lobby is full!");
				} *//*
				//initEngine.joinMatch(player, new Location(player.getLocation().getWorld(), 138.84793, 15.000, 247.12367));
			}
			else if (args.length >= 1 && args[0].equalsIgnoreCase("leave") && sender.hasPermission("pzm.leave")) {
				sender.sendMessage("leave message + code to leave match (call from engine class)");

                for (Lobby lobby : lobbies)
                    lobby.removePlayerFromLobby(player);
				// lobbyOne.removePlayerFromLobby(player);
				// area51.removePlayerFromLobby(player);
				// lobby<whatever>.removePlayerFromLobby(player);

			}
		
			// KITS
			*//* else if (args.length >= 1 && args[0].equalsIgnoreCase("kit") && sender.hasPermission("pzm.kit")) {
				if (args.length == 1 && args[0].equalsIgnoreCase("kit"))
					sender.sendMessage(Utils.KitMenu);
				if (args.length == 2 && args[1].equalsIgnoreCase("noob") && sender.hasPermission("pzm.kit.noob")) 
					KitStorage.giveKit(player, 0);
				else if (args.length == 2 && args[1].equalsIgnoreCase("elite") && sender.hasPermission("pzm.kit.elite")) 
					KitStorage.giveKit(player, 1);
				else if (args.length == 2 && args[1].equalsIgnoreCase("veteran") && sender.hasPermission("pzm.kit.veteran")) 
					KitStorage.giveKit(player, 2);
				else if (args.length == 2 && args[1].equalsIgnoreCase("god") && sender.hasPermission("pzm.kit.god")) 
					KitStorage.giveKit(player, 3);
				else if (args.length == 2 && args[1].equalsIgnoreCase("dev") && sender.hasPermission("pzm.administration.kit.dev")) 
					KitStorage.giveKit(player, 4);
			} *//*
			return true;
		} 
		else if (cmd.getName().equalsIgnoreCase("gm")) {
			if (player.getGameMode() == GameMode.SURVIVAL  && sender.hasPermission("pzm.gamemode")) {
				Utils.defaultMessage(player, "Changed gamemode to CREATIVE");
				player.setGameMode(GameMode.CREATIVE);
			}
			else if (player.getGameMode() == GameMode.SURVIVAL && sender.hasPermission("pzm.gamemode")) {
				Utils.defaultMessage(player, "Changed gamemode to SURVIVAL");
				player.setGameMode(GameMode.SURVIVAL);
			}
			return true;
		} */
        // TODO: add more commands
        return commandRegistry.onCommand(sender, cmd, label, args);
    }

}