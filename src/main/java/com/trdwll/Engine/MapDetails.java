package com.trdwll.Engine;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class MapDetails {

	private LocationSerialized lobbySpawn;
	private LocationSerialized gameSpawn; // TODO: Change to random locations in game
	private List<LocationSerialized> zombieSpawnLocations;
	
	private class LocationSerialized {
		
		private double x, y, z;
		private UUID world;
		
		public LocationSerialized(Location location) {
			this.x = location.getX();
			this.y = location.getY();
			this.z = location.getZ();
			this.world = location.getWorld().getUID();
		}
		
		public Location getLocation() {
			return Bukkit.getWorld(world) != null ? new Location(Bukkit.getWorld(world), x, y, z) : null;
		}
	}
	
}
