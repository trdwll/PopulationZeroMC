package com.trdwll.Engine;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class MapDetails {

    private String mapName;
    private int minPlayers, maxPlayers;

    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;

    private boolean isZombieLocationSpread;
    private int startZombieSpawnCount;
    private int zombieIncrementalCount;
    private int maxZombieSpawnCount;

    private int waveDuration;
    private int wavesPerRound;
    private int roundSpawnAddition;
    private int maxRounds;

	private LocationSerialized lobbySpawn;
	private LocationSerialized playerSpawn; // TODO: Change to random locations in game

	private List<ZombieSpawnerData> zombieSpawnData;

    public MapDetails() { }

    public String getMapName() {
        return mapName;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxZ() {
        return maxZ;
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

    public int getWaveDuration() {
        return waveDuration;
    }

    public int getWavesPerRound() {
        return wavesPerRound;
    }

    public int getRoundSpawnAddition() {
        return roundSpawnAddition;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public Location getLobbySpawn() {
        return lobbySpawn.getLocation();
    }

    public Location getGameSpawn() {
        return playerSpawn.getLocation();
    }

    public List<ZombieSpawnerData> getZombieSpawnData() {
        return zombieSpawnData;
    }
	
}
