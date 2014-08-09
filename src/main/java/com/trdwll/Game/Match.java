package com.trdwll.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.trdwll.Engine.MapDetails;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.trdwll.Engine.KitStorage;
import com.trdwll.Engine.Lobby;
import com.trdwll.Engine.MatchSpawnDetails;
import com.trdwll.Utilities.Utils;

@SuppressWarnings("unused")
public class Match {

	private List<Player> players;
    private MapDetails details;
	private Lobby lobby;
	
	private int scheduleId = -1;
	
	private List<Location> zombieSpawnLocations;
	private List<Zombie> spawnedZombies;

	private Random rand = new Random();

	public Match(List<Player> players, MapDetails spawnDetails, Lobby lobby) {
		this.players = players;
		this.details = details;
		this.lobby = lobby;
		this.zombieSpawnLocations = details.getZombieSpawnLocations();
		this.spawnedZombies = new ArrayList<Zombie>();
	}

	public void startGame() {
		for (Player p : players) 
			addPlayerToGame(p);
		
		scheduleId = lobby.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(lobby.getPlugin(), new Runnable() {
			
			
			int wave = 1;
			int zombieCount = details.getStartZombieSpawnCount();
			
			@Override
			public void run() {
				lobby.sendPlayersMessage(Utils.prefixDefault + " Wave " + wave + " Spawned!");
				
				spawnWave(zombieCount);
				
				zombieCount += details.getZombieIncrementalCount();

                if (details.getMaxZombieSpawnCount() != -1 && zombieCount > details.getMaxZombieSpawnCount())
                    zombieCount = details.getMaxZombieSpawnCount();

				wave ++;
			}
			
		}, 0, 20 * 30);
	}

	public boolean hasPlayer(Player player) {
		return players.contains(player);
	}

	public void addPlayerToGame(Player player) {
		if (hasPlayer(player)) {
			// player.teleport(new Location(Bukkit.getWorld("world"), 135.39872, 15.000, 241.46764));
			// player.teleport(new Location(Bukkit.getWorld("world"), 204, 70, 288));
			player.teleport(details.getGameSpawn());
			
			clearInventory(player);
			
			if (!player.hasPermission("pzm.kit.dev"))
				KitStorage.giveKit(player, rand.nextInt(3));
			else
				KitStorage.giveKit(player, 4);
			
			player.sendMessage(Utils.prefixDefault + " Have fun!");
			player.setGameMode(GameMode.ADVENTURE);
			player.setHealth(20.0); 
		}
	}

	public void removePlayerFromGame(Player player) {
		if (players.contains(player))
			players.remove(player);

		player.setGameMode(GameMode.ADVENTURE);
		clearInventory(player);
		
		if (players.size() <= 0)
			endGame();
	}

	public void onPlayerDeath(Player player, PlayerDeathEvent event) {
		lobby.removePlayerFromLobby(player);
	}
	
	public void clearInventory(Player player) {
		player.getInventory().clear();
		player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
	    player.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
	    player.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
	    player.getInventory().setBoots(new ItemStack(Material.AIR, 1));
	}
	
	public void spawnWave(int zombieAmount) {
		int zombiesPerSpawn = details.isZombieLocationSpread() ? zombieAmount / zombieSpawnLocations.size() : zombieAmount;

		for (Location l : zombieSpawnLocations) {
			for (int ignored = 0; ignored < zombiesPerSpawn; ignored ++) 
				spawnedZombies.add(l.getWorld().spawn(l, Zombie.class));
		}
	}
	
	public void endGame() {
		lobby.getPlugin().getServer().getScheduler().cancelTask(scheduleId);
		
		for (Zombie z : spawnedZombies) {
			if (!z.isDead())
				z.remove();
		}
		
		players.clear();
		lobby.reset();
	}

}
