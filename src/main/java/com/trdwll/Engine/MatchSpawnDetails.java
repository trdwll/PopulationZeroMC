package com.trdwll.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@SuppressWarnings("unused")
public class MatchSpawnDetails {

	private World world;
	private int minX, minY, minZ;
	private int maxX, maxY, maxZ;
	private Random rand;
	
	private Location location;
	private List<Location> spawnLocations;
	
	public MatchSpawnDetails(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		this.world = world;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		this.rand = new Random();
	}
	
	public MatchSpawnDetails(Location location, List<Location> spawnLocations) {
		this.location = location;
		this.spawnLocations = spawnLocations;
	}
	
	// game - 204, 70, 288
	// spawn 1 - 192, 70, 298
	// spawn 2 - 191, 69, 291
	public List<Location> getSpawnLocations() {
		List<Location> locations = new ArrayList<Location>();
		
		locations.add(new Location(Bukkit.getWorld("world"), 192, 70, 298));
		locations.add(new Location(Bukkit.getWorld("world"), 191, 69, 291));
		
		return spawnLocations != null ? spawnLocations : locations;
	}
	
	public Location getRandomLocation() {
		return location;
	}
	
}
