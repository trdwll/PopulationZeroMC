package com.trdwll.Engine;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.trdwll.Game.Match;
import com.trdwll.Game.initGame;
import com.trdwll.Utilities.Utils;

public class Lobby implements Listener {

	private static final int MIN_LOBBY_COUNT = 1;
	private static final int MAX_LOBBY_COUNT = 5;

	private initGame plugin;
	private Location spawnLocation;
	private MatchSpawnDetails spawnDetails;
	private List<Player> players;
	private LobbyState lobbyState;

	private int scheduleId = -1;
	private int countdownId = -1;
	private Match match;

	public Lobby(initGame plugin, Location spawnLocation, World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		this.plugin = plugin;
		this.spawnLocation = spawnLocation;
		this.spawnDetails = new MatchSpawnDetails(world, minX, minY, minZ, maxX, maxY, maxZ);
		this.players = new ArrayList<Player>();
		this.lobbyState = LobbyState.PRE_GAME;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public Lobby(initGame plugin, Location spawnLocation, Location location, List<Location> zombieSpawnLocations) {
		this.plugin = plugin;
		this.spawnLocation = spawnLocation;
		this.spawnDetails = new MatchSpawnDetails(location, zombieSpawnLocations);
		this.players = new ArrayList<Player>();
		this.lobbyState = LobbyState.PRE_GAME;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public Location getLocation() {
		return spawnLocation;
	}

	public void addPlayerToLobby(Player player) {
		if (!players.contains(player)) {
			players.add(player);
			player.teleport(spawnLocation);
			checkLobbyStatus();
		}
	}

	public void removePlayerFromLobby(Player player) {
		if (match != null)
			match.removePlayerFromGame(player);

		if (players.contains(player)) {
			players.remove(player);

			player.teleport(plugin.spawn);
			player.sendMessage(Utils.prefixDebug + " Tp'd to spawn");
		}
	}

	public void setLobbyState(LobbyState lobbyState) {
		this.lobbyState = lobbyState;
	}

	public LobbyState getLobbyState() {
		return lobbyState;
	}

	public boolean canPlayerJoin() {
		return getLobbyState() != LobbyState.IN_GAME && players.size() < MAX_LOBBY_COUNT;
	}

	public void checkLobbyStatus() {
		if (players.size() >= MIN_LOBBY_COUNT && getLobbyState() == LobbyState.PRE_GAME) {
			match = new Match(players, spawnDetails, this);

			setLobbyState(LobbyState.COUNTDOWN);

			scheduleId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					match.startGame();
					plugin.getServer().getScheduler().cancelTask(countdownId);

					setLobbyState(LobbyState.IN_GAME);
				}

			}, 200);

			countdownId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

				int count = 10;

				@Override
				public void run() {
					for (Player p : players)
						p.sendMessage(Utils.prefixDefault + " " + count + " SECONDS TILL START!");

					count --;
				}

			}, 0, 20);
		}
	}

	public void cancelCountdown() {
		plugin.getServer().getScheduler().cancelTask(scheduleId);
		plugin.getServer().getScheduler().cancelTask(countdownId);

		sendPlayersMessage(Utils.prefixWarn + " Cancelled game!");
		setLobbyState(LobbyState.PRE_GAME);
	}

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerLeave(PlayerQuitEvent event) {
		if (players.contains(event.getPlayer())) {
			players.remove(event.getPlayer());

			if (getLobbyState() == LobbyState.COUNTDOWN) 
				cancelCountdown();
		}

		if (match != null)
			match.removePlayerFromGame(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (match != null && match.hasPlayer(event.getEntity()))
			match.onPlayerDeath(event.getEntity(), event);
	}

	public void sendPlayersMessage(String message) {
		for (Player p : players)
			p.sendMessage(message);
	}

	public initGame getPlugin() {
		return plugin;
	}

	public void reset() {
		match = null;

		for (Player p : players) {
			if (p.isOnline())
				removePlayerFromLobby(p);
		}

		players.clear();

		setLobbyState(LobbyState.PRE_GAME);
	}

	public enum LobbyState {

		PRE_GAME, COUNTDOWN, IN_GAME;

	}

	/* public enum Kit {

		NOOB, ELITE, SHIT;

		private List<ItemStack> items;

		private Kit(ItemStack... items) {
			// items = Arrays.asList(items);
		}

		public void addItemsToPlayer(Player player) {
			for (ItemStack item : items) {
				player.getInventory().addItem(item);
			}
		}

	} */

}
