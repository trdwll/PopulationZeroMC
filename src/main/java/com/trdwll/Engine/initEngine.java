package com.trdwll.Engine;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.trdwll.Game.initGame;
import com.trdwll.Utilities.Utils;

public class initEngine implements Listener {
	
	public static boolean bIsPVPOn = false;
	public static boolean bIsPlaying = false;
	
	private initGame plugin;
	
	public initEngine(initGame plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e) throws InterruptedException {
		Player p = e.getPlayer();
		String pname = p.getName();
		
		p.teleport(plugin.spawn);
		
		Bukkit.broadcastMessage(Utils.prefixDefault + "Welcome " + ChatColor.RED + pname + ChatColor.RESET + " to " + Utils.modName + "!");
		p.sendMessage(Utils.prefixDefault + "Use /pzm help to get help!");
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		event.getPlayer().teleport(plugin.spawn);
		event.getPlayer().sendMessage(Utils.prefixDefault + " You have been teleported back to spawn!");
	}
	 
	@EventHandler (priority = EventPriority.NORMAL)
	public void onWeatherChange(WeatherChangeEvent event) {
		if (event.toWeatherState())
			event.setCancelled(true);
	}
	
	public void spawnAI(Location location, Player player) {

		//int maxAI = 150;
		
		//for (int i = 0; i < maxAI / 2; i++) {
			//location = player.getLocation();
			//player.getWorld().spawnCreature(location, EntityType.ZOMBIE);
		//}
	}
	
	public static boolean togglePVPMode() {
		if (!bIsPVPOn) {
			Bukkit.getWorld("world").setPVP(true);
			bIsPVPOn = true;
		}
		else if (bIsPVPOn) {
			Bukkit.getWorld("world").setPVP(false);
			bIsPVPOn = false;
		}
		return bIsPVPOn;
	}
	
	public static List<Entity> getNearbyEntities(Location location, int size) {
		List<Entity> entities = new ArrayList<Entity>();
		
		for (Entity e : location.getWorld().getEntities())
			if (location.distance(e.getLocation()) <= size)
				entities.add(e);
		return entities;
	}
	
	
	public static void setRandomSpawn(Player player) {
		
	}
	
	public static void joinMatch(Player player, Location location) {
		player.teleport(location);
	}
	
	public static void leaveMatch(Player player, Location location) {
		Bukkit.getWorld("world").setPVP(true);
		player.sendMessage("PVP ON");
		player.teleport(location);
	}
	
	public static void startMatch(Player player, Location location) {
		
		for (Entity e : getNearbyEntities(player.getLocation(), 12)) {
			if (e == player) continue;
			//player.sendMessage((e instanceof Player ? ((Player) e).getName() : e.getType().name()));
			if (e instanceof Player) {
				e.teleport(location);
				Bukkit.broadcastMessage("TP'd everyone to game");
			}
		}
		
		//Bukkit.getWorld("world").setPVP(false);
		//player.sendMessage("PVP OFF");
		//player.teleport(location);
	}
}
