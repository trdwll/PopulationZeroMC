package com.trdwll.Game;

import com.trdwll.Engine.KitStorage;
import com.trdwll.Engine.Lobby;
import com.trdwll.Engine.MatchScoreboard;
import com.trdwll.Engine.ZombieSpawnerData;
import com.trdwll.Utilities.Utils;
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

    private int scheduleId = -1;

    public Match(Lobby lobby) {
        this.lobby = lobby;
        this.matchState = MatchState.PRE_MATCH;
        this.scoreboard = new MatchScoreboard(this);
        this.spawnedEntities = new HashMap<UUID, Entity>();
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

    public void startMatch() {
        if (!hasStarted()) {
            setMatchState(MatchState.IN_MATCH);

            Random random = new Random();

            for (Player matchPlayer : lobby.getLobbyPlayers()) {
                matchPlayer.teleport(lobby.getMapDetails().getGameSpawn());
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

            scheduleId = lobby.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(lobby.getPlugin(), new Runnable() {

                int round = 1;
                int wave = 1;
                int roundAddition = 0;

                int zombieCount = lobby.getMapDetails().getStartZombieSpawnCount();

                @Override
                public void run() {

                    if (getMatchState() != MatchState.IN_MATCH) {
                        lobby.getPlugin().getServer().getScheduler().cancelTask(scheduleId);

                        return;
                    }

                    if (wave % lobby.getMapDetails().getWavesPerRound() == 0 || wave == 1) {
                        lobby.sendPlayersMessage("Round " + (round ++) + " Has Begun!");

                        roundAddition += lobby.getMapDetails().getRoundSpawnAddition();
                        zombieCount += roundAddition;
                    }

                    if (lobby.getMapDetails().getMaxZombieSpawnCount() != -1 && zombieCount > lobby.getMapDetails().getMaxZombieSpawnCount())
                        zombieCount = lobby.getMapDetails().getMaxZombieSpawnCount();

                    spawnWave(zombieCount);

                    zombieCount += lobby.getMapDetails().getZombieIncrementalCount();

                    lobby.sendPlayersMessage("Wave " + (wave ++) + " Inbound!");

                    getScoreboard().update(wave - 1, round - 1, spawnedEntities.values());
                }

            }, 0, lobby.getMapDetails().getWaveDuration() * 20);
        }
    }

    public void setExpCountdown(int countdown) {
        for (Player player : lobby.getLobbyPlayers()) {
            player.setLevel(countdown);
            player.setExp(0);
        }
    }

    public void endMatch() {
        setMatchState(MatchState.POST_MATCH);

        getScoreboard().reset();

        for (Player matchPlayer : lobby.getLobbyPlayers())
            Utils.clearInventory(matchPlayer);

        Utils.removeEntitiesBetween(new Location(lobby.getMapDetails().getGameSpawn().getWorld(), lobby.getMapDetails().getMinX(), lobby.getMapDetails().getMinY(), lobby.getMapDetails().getMinZ()), new Location(lobby.getMapDetails().getGameSpawn().getWorld(), lobby.getMapDetails().getMaxX(), lobby.getMapDetails().getMaxY(), lobby.getMapDetails().getMaxZ()));

        lobby.getPlugin().getServer().getScheduler().cancelTask(scheduleId);
    }

    public void spawnWave(int zombieAmount) {
        int zombiesPerSpawn = lobby.getMapDetails().isZombieLocationSpread() ? zombieAmount / lobby.getMapDetails().getZombieSpawnData().size() : zombieAmount;

        for (ZombieSpawnerData data : lobby.getMapDetails().getZombieSpawnData())
            for (int ignored = 0; ignored < zombiesPerSpawn; ignored ++) {
                Entity entity = data.spawnZombie();

                spawnedEntities.put(entity.getUniqueId(), entity);
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
