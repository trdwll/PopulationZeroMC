package com.trdwll.Engine;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class MapDetails {

    private String mapName;
    private int minPlayers, maxPlayers;

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

    public MapDetails(String mapName, int minPlayers, int maxPlayers, boolean isZombieLocationSpread, int startZombieSpawnCount, int zombieIncrementalCount, int maxZombieSpawnCount, int waveDuration, int wavesPerRound, int roundSpawnAddition, int maxRounds, LocationSerialized lobbySpawn, LocationSerialized playerSpawn, List<ZombieSpawnerData> zombieSpawnData) {
        this.mapName = mapName;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.isZombieLocationSpread = isZombieLocationSpread;
        this.startZombieSpawnCount = startZombieSpawnCount;
        this.zombieIncrementalCount = zombieIncrementalCount;
        this.maxZombieSpawnCount = maxZombieSpawnCount;
        this.waveDuration = waveDuration;
        this.wavesPerRound = wavesPerRound;
        this.roundSpawnAddition = roundSpawnAddition;
        this.maxRounds = maxRounds;
        this.lobbySpawn = lobbySpawn;
        this.playerSpawn = playerSpawn;
        this.zombieSpawnData = zombieSpawnData;
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
        List<ZombieSpawnerData> spawnData = new ArrayList<ZombieSpawnerData>();

        for (ZombieSpawnerData data : zombieSpawnData) {
            spawnData.add(data);
        }

        return spawnData;
    }
	
}
