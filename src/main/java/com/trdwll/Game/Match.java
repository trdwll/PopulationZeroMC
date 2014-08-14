package com.trdwll.Game;

import com.trdwll.Engine.*;
import com.trdwll.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.*;

public class Match {

    private Lobby lobby;
    private MatchState matchState;

    private MatchScoreboard scoreboard;

    private Map<UUID, Entity> spawnedEntities;
    private Map<String, Integer> zombieSpawnCount;


    private Random random = new Random();
    private int scheduleId = -1;

    public Match(Lobby lobby) {
        this.lobby = lobby;
        this.matchState = MatchState.PRE_MATCH;
        this.scoreboard = new MatchScoreboard(this);
        this.spawnedEntities = new HashMap<UUID, Entity>();
        this.zombieSpawnCount = new HashMap<String, Integer>();
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setMatchState(MatchState matchState) {
        this.matchState = matchState;
    }

    public MatchState getMatchState() {
        return matchState;
    }

    public MatchScoreboard getScoreboard() {
        return scoreboard;
    }

    public boolean hasStarted() {
        return matchState == MatchState.IN_MATCH;
    }

    public boolean canPlayerJoin() {
        return getMatchState() != MatchState.POST_MATCH;
    }

    public void onPlayerJoin(Player player) {
        if (getMatchState() != MatchState.PRE_MATCH) {
            player.teleport(getLobby().getMapDetails().getGameSpawn());
            player.setGameMode(GameMode.SURVIVAL);
            Utils.clearInventory(player);

            if (player.hasPermission("pzm.dev"))
                KitStorage.giveKit(player, 4);
            else
                KitStorage.giveKit(player, random.nextInt(3));
        }

        getScoreboard().onPlayerJoin(player);
    }
    
    public void startMatch() {
        if (!hasStarted()) {
            setMatchState(MatchState.IN_MATCH);

            for (Player matchPlayer : getLobby().getLobbyPlayers()) {
                matchPlayer.teleport(getLobby().getMapDetails().getGameSpawn());
                matchPlayer.setGameMode(GameMode.SURVIVAL);
                Utils.clearInventory(matchPlayer);

                //
                // remove on release
                //
                if (matchPlayer.hasPermission("pzm.dev"))
                    KitStorage.giveKit(matchPlayer, 4);
                else
                    KitStorage.giveKit(matchPlayer, random.nextInt(3));
            }

            setExpCountdown(0);
            getScoreboard().setCount(0);

            scheduleId = getLobby().getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(getLobby().getPlugin(), new Runnable() {

                int round = 1;
                int wave = 1;

                @Override
                public void run() {

                    boolean newRound = false;

                    if (getMatchState() != MatchState.IN_MATCH) {
                        getLobby().getPlugin().getServer().getScheduler().cancelTask(scheduleId);

                        return;
                    }

                    if (wave % getLobby().getMapDetails().getWavesPerRound() == 0 || wave == 1) {
                        getLobby().sendPlayersMessage("Round " + (round++) + " Has Begun!");

                        newRound = true;
                    }

                    if (getLobby().getMapDetails().getMaxZombieSpawnCount() == -1 || spawnedEntities.size() < getLobby().getMapDetails().getMaxZombieSpawnCount())
                        spawnWave(newRound);

                    getLobby().sendPlayersMessage("Wave " + (wave ++) + " Inbound!");
                    getScoreboard().update(wave - 1, round - 1, spawnedEntities.values());
                }

            }, 0, getLobby().getMapDetails().getWaveDuration() * 20);
        }
    }

    public void setExpCountdown(int countdown) {
        for (Player player : getLobby().getLobbyPlayers()) {
            player.setLevel(countdown);
            player.setExp(0);
        }
    }

    public void endMatch() {
        setMatchState(MatchState.POST_MATCH);

        getScoreboard().reset();

        for (Player matchPlayer : getLobby().getLobbyPlayers())
            Utils.clearInventory(matchPlayer);

        Utils.removeEntitiesBetween(new Location(getLobby().getMapDetails().getGameSpawn().getWorld(), getLobby().getMapDetails().getMinX(), getLobby().getMapDetails().getMinY(), getLobby().getMapDetails().getMinZ()), new Location(getLobby().getMapDetails().getGameSpawn().getWorld(), getLobby().getMapDetails().getMaxX(), getLobby().getMapDetails().getMaxY(), getLobby().getMapDetails().getMaxZ()));

        getLobby().getPlugin().getServer().getScheduler().cancelTask(scheduleId);
    }

    public void spawnWave(boolean newRound) {
        for (ZombieSpawnerData data : getLobby().getMapDetails().getZombieSpawnData()) {
            if (!zombieSpawnCount.containsKey(data.getSpawnerId()))
                zombieSpawnCount.put(data.getSpawnerId(), data.getStartZombieSpawnCount());
            else
                zombieSpawnCount.put(data.getSpawnerId(), zombieSpawnCount.get(data.getSpawnerId()) + data.getZombieIncrementalCount() + (newRound ? data.getRoundSpawnAddition() : 0));

            if (getLobby().getMapDetails().getMaxZombieSpawnCount() == -1 || spawnedEntities.size() < getLobby().getMapDetails().getMaxZombieSpawnCount()) {
                int zombieCount = getLobby().getMapDetails().getMaxZombieSpawnCount() < spawnedEntities.size() + zombieSpawnCount.get(data.getSpawnerId()) && getLobby().getMapDetails().getMaxZombieSpawnCount() != -1 ? getLobby().getMapDetails().getMaxZombieSpawnCount() - spawnedEntities.size() : zombieSpawnCount.get(data.getSpawnerId());

                for (int ignored = 0; ignored < zombieCount; ignored ++) {
                    Entity entity = data.spawnZombie();

                    spawnedEntities.put(entity.getUniqueId(), entity);
                }
            }
        }
    }

    public void onEntityDeath(EntityDeathEvent event) {
        if (spawnedEntities.containsKey(event.getEntity().getUniqueId()))
            spawnedEntities.remove(event.getEntity().getUniqueId());

        getScoreboard().update(-1, -1, spawnedEntities.values());
    }

    private enum MatchState {

        PRE_MATCH, IN_MATCH, POST_MATCH;

    }

}
