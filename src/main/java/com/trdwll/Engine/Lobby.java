package com.trdwll.Engine;

import com.trdwll.Game.Match;
import com.trdwll.Game.initGame;
import com.trdwll.Utilities.Utils;
import net.minecraft.util.io.netty.util.internal.ConcurrentSet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class Lobby implements Listener {

    private initGame plugin;

    private ConcurrentSet<Player> lobbyPlayers;
    private Lobby.LobbyState lobbyState;
    private MapDetails mapDetails;

    private Match match;

    private int scheduleId;
    private int countdownId;

    public Lobby(initGame plugin, MapDetails mapDetails) {
        this.plugin = plugin;
        this.mapDetails = mapDetails;
        this.lobbyPlayers = new ConcurrentSet<Player>();
        this.lobbyState = Lobby.LobbyState.PRE_GAME;
    }

    public initGame getPlugin() {
        return plugin;
    }

    public void setLobbyState(Lobby.LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }

    public Lobby.LobbyState getLobbyState() {
        return lobbyState;
    }

    public MapDetails getMapDetails() {
        return mapDetails;
    }

    public ConcurrentSet<Player> getLobbyPlayers() {
        return lobbyPlayers;
    }

    public boolean canPlayerJoin(Player player) {
        if (player.hasPermission("pzm.join.force")) {
            return match != null ? match.canPlayerJoin() : getLobbyState() != Lobby.LobbyState.IN_GAME && getLobbyPlayers().size() < getMapDetails().getMaxPlayers();
        } else {
            return getLobbyState() != Lobby.LobbyState.IN_GAME && getLobbyPlayers().size() < getMapDetails().getMaxPlayers();
        }
    }

    public boolean addPlayerToLobby(Player player) {
        if (canPlayerJoin(player) && !getLobbyPlayers().contains(player)) {
            getLobbyPlayers().add(player);

            player.teleport(getMapDetails().getLobbySpawn());
            checkLobbyStatus();

            if (match != null)
                match.onPlayerJoin(player);

            return true;
        }

        return false;
    }

    public boolean removePlayerFromLobby(Player player) {
        if (getLobbyPlayers().contains(player)) {
            getLobbyPlayers().remove(player);

            if (match != null) {
                Utils.clearInventory(player);
                match.getScoreboard().onPlayerLeave(player);
            }

            player.teleport(getPlugin().getSettings().getSpawn());
            Utils.message(Utils.PrefixType.DEBUG, "Tp'd to Spawn!", player);
            checkLobbyStatus();

            player.setScoreboard(plugin.getServer().getScoreboardManager().getMainScoreboard());

            return getLobbyState() != LobbyState.IN_GAME;
        }

        return false;
    }

    public void sendPlayersMessage(String message) {
        for (Player lobbyPlayer : getLobbyPlayers())
            Utils.message(message, lobbyPlayer);
    }

    public void sendPlayersMessage(Utils.PrefixType prefixType, String message) {
        for (Player lobbyPlayer : getLobbyPlayers())
            Utils.message(prefixType, message, lobbyPlayer);
    }

    private void checkLobbyStatus() {
        if (lobbyPlayers.size() >= getMapDetails().getMinPlayers() && getLobbyState() == Lobby.LobbyState.PRE_GAME) {
            match = new Match(this);

            plugin.getServer().getPluginManager().registerEvents(this, plugin);

            setLobbyState(Lobby.LobbyState.COUNTDOWN);

            countdownId = getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Runnable() {

                private int countdown = 10;

                @Override
                public void run() {
                    if (match != null && countdown > 0) {
                        match.setExpCountdown(countdown);
                        match.getScoreboard().setCount(countdown);

                        sendPlayersMessage((countdown--) + " SECONDS TILL START!");
                    } else
                        getPlugin().getServer().getScheduler().cancelTask(countdownId);
                }

            }, 0, 20);

            scheduleId = getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {

                @Override
                public void run() {
                    if (match != null && !match.hasStarted())
                        match.startMatch();
                    else
                        getPlugin().getServer().getScheduler().cancelTask(scheduleId);
                }

            }, 200);
        } else if (getLobbyPlayers().size() < getMapDetails().getMinPlayers() && getLobbyState() != Lobby.LobbyState.PRE_GAME) {
            endMatch();
        }
    }

    private void cancelSchedules() {
        getPlugin().getServer().getScheduler().cancelTask(scheduleId);
        getPlugin().getServer().getScheduler().cancelTask(countdownId);
    }

    public void endMatch() {
        if (match != null) {
            setLobbyState(Lobby.LobbyState.PRE_GAME);

            cancelSchedules();

            for (Player matchPlayer : getLobbyPlayers())
                removePlayerFromLobby(matchPlayer);

            match.endMatch();

            HandlerList.unregisterAll(this);

            match = null;
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (match != null)
            match.onEntityDeath(event);
    }

    public enum LobbyState {

        PRE_GAME, COUNTDOWN, IN_GAME;

    }

}
