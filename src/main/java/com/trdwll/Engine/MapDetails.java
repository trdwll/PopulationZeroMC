package com.trdwll.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class MapDetails {

    private String mapName;
    private int minPlayers, maxPlayers;

    private boolean isZombieLocationSpread;
    private int startZombieSpawnCount;
    private int zombieIncrementalCount;
    private int maxZombieSpawnCount;

	private LocationSerialized lobbySpawn;
	private LocationSerialized gameSpawn; // TODO: Change to random locations in game
	private List<LocationSerialized> zombieSpawnLocations;

    public MapDetails() { }

    public MapDetails(int minPlayers, String mapName, int maxPlayers, boolean isZombieLocationSpread, int startZombieSpawnCount, int zombieIncrementalCount, int maxZombieSpawnCount, LocationSerialized lobbySpawn, LocationSerialized gameSpawn, List<LocationSerialized> zombieSpawnLocations) {
        this.minPlayers = minPlayers;
        this.mapName = mapName;
        this.maxPlayers = maxPlayers;
        this.isZombieLocationSpread = isZombieLocationSpread;
        this.startZombieSpawnCount = startZombieSpawnCount;
        this.zombieIncrementalCount = zombieIncrementalCount;
        this.maxZombieSpawnCount = maxZombieSpawnCount;
        this.lobbySpawn = lobbySpawn;
        this.gameSpawn = gameSpawn;
        this.zombieSpawnLocations = zombieSpawnLocations;
    }

    public String getMapName() {
        return mapName;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isZombieLocationSpread() {
        return isZombieLocationSpread;
    }

    public int getStartZombieSpawnCount() {
        return startZombieSpawnCount;
    }

    public int getZombieIncrementalCount() {
        return zombieIncrementalCount;
    }

    public int getMaxZombieSpawnCount() {
        return maxZombieSpawnCount;
    }

    public Location getLobbySpawn() {
        return lobbySpawn.getLocation();
    }

    public Location getGameSpawn() {
        return gameSpawn.getLocation();
    }

    public List<Location> getZombieSpawnLocations() {
        List<Location> locations = new ArrayList<Location>();

        for (LocationSerialized location : zombieSpawnLocations) {
            locations.add(location.getLocation());
        }

        return locations;
    }

	private class LocationSerialized {
		
		private double x, y, z;
		private String world;
		
		public LocationSerialized(Location location) {
			this.x = location.getX();
			this.y = location.getY();
			this.z = location.getZ();
			this.world = location.getWorld().getName();
		}
		
		public Location getLocation() {
			return Bukkit.getWorld(world) != null ? new Location(Bukkit.getWorld(world), x, y, z) : null;
		}
	}
	
}
