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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
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
		
		p.teleport(plugin.getSettings().getSpawn());
		
		Bukkit.broadcastMessage(Utils.prefixDefault + "Welcome " + ChatColor.RED + pname + ChatColor.RESET + " to " + Utils.modName + "!");
		p.sendMessage(Utils.prefixDefault + "Use /pzm help to get help!");
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		event.getPlayer().teleport(plugin.getSettings().getSpawn());
		event.getPlayer().sendMessage(Messages.getTeleportedToSpawn());
	}

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDeath(PlayerDeathEvent event) {
        for (Lobby lobby : plugin.getLobbies())
            if (lobby.removePlayerFromLobby(event.getEntity()))
                event.getDrops().clear();
    }
	 
	@EventHandler (priority = EventPriority.NORMAL)
	public void onWeatherChange(WeatherChangeEvent event) {
		if (event.toWeatherState())
			event.setCancelled(true);
	}

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equalsIgnoreCase("/?") || event.getMessage().equalsIgnoreCase("?")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Utils.translate("&cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is an error."));
        }
    }

}
