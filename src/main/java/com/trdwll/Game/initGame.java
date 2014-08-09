package com.trdwll.Game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.trdwll.Engine.Lobby;
import com.trdwll.Engine.initEngine;
import com.trdwll.Utilities.Utils;

public class initGame extends JavaPlugin {
	
	public Location spawn;
	private Lobby lobbyOne;
	private Lobby area51;
	
	public void onEnable() {
		spawn = new Location(getServer().getWorld("world"), 122, 69, 251);
		
		try {
			this.getServer().getPluginManager().registerEvents(new initEngine(this), this);	
			System.out.print(Utils.prefixOk + "Successful startup!");
		}
		catch (Exception e) {
			System.out.print(Utils.prefixError + "Error starting up!");
		}
		
		// lobbyOne = new Lobby(this, new Location(getServer().getWorld("world"), 138, 15, 247), getServer().getWorld("world"), 135, 10, 236, 104, 25, 252);
		// lobbyOne = new Lobby(this, new Location(getServer().getWorld("world"), 205, 70, 298), getServer().getWorld("world"), 135, 10, 236, 104, 25, 252);
		
		List<Location> locations = new ArrayList<Location>();
		List<Location> location = new ArrayList<Location>();
		locations.add(new Location(Bukkit.getWorld("world"), 192, 70, 298));
		locations.add(new Location(Bukkit.getWorld("world"), 191, 69, 291));
		
		location.add(new Location(Bukkit.getWorld("world"), -508, 62, 2700));
		location.add(new Location(Bukkit.getWorld("world"), -511, 62, 2682));
		location.add(new Location(Bukkit.getWorld("world"), -501, 62, 2658));
		location.add(new Location(Bukkit.getWorld("world"), -463, 62, 2651));
		location.add(new Location(Bukkit.getWorld("world"), -457, 62, 2677));
		location.add(new Location(Bukkit.getWorld("world"), -449, 63, 2696));
		
		lobbyOne = new Lobby(this, new Location(getServer().getWorld("world"), 205, 70, 298), new Location(getServer().getWorld("world"), 204, 70, 288), locations);
		
		// lobby = new Lobby(this, <lobby spawn>, (<game spawn>), (<list of zombie spawns>));
		area51 = new Lobby(this, new Location(getServer().getWorld("world"), -486, 109, 2741), new Location(getServer().getWorld("world"), -497, 62, 2726), location);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				for (World world : getServer().getWorlds()) {
					if (world.getTime() > 14000 || world.getTime() < 14000)
						world.setTime(14000);
				}
			}
			
		}, 20, 20);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		// PZM Commands
		if (cmd.getName().equalsIgnoreCase("pzm")) {
			if (args.length == 0)
				sender.sendMessage(Utils.AboutMenu);
			else if (args.length >= 1 && args[0].equalsIgnoreCase("help") && sender.hasPermission("pzm.help"))
				sender.sendMessage(Utils.HelpMenu);
			else if (args.length == 2 && args[0].equalsIgnoreCase("join") && sender.hasPermission("pzm.join")) {
				sender.sendMessage("join message & code to join match (call from engine class)");
				
				if (args[1].equalsIgnoreCase("one")) {
					if (lobbyOne.canPlayerJoin())
						lobbyOne.addPlayerToLobby(player);
					else
						player.sendMessage(Utils.prefixWarn + "Test Lobby is full!");
				}
				else if (args[1].equalsIgnoreCase("area51")) {
					if (area51.canPlayerJoin())
						area51.addPlayerToLobby(player);
					else
						player.sendMessage(Utils.prefixWarn + "Area51 Lobby is full!");
				}
				//initEngine.joinMatch(player, new Location(player.getLocation().getWorld(), 138.84793, 15.000, 247.12367));
			}
			else if (args.length >= 1 && args[0].equalsIgnoreCase("leave") && sender.hasPermission("pzm.leave")) {
				sender.sendMessage("leave message + code to leave match (call from engine class)");
		
				lobbyOne.removePlayerFromLobby(player);
				area51.removePlayerFromLobby(player);
				// lobby<whatever>.removePlayerFromLobby(player);
				
			}
		
			// KITS
			/* else if (args.length >= 1 && args[0].equalsIgnoreCase("kit") && sender.hasPermission("pzm.kit")) {
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
			} */
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
		}
		// TODO: add more commands
		return false;
	}
}
