package com.trdwll.Engine;

import com.trdwll.Game.Match;
import com.trdwll.Game.initGame;
import com.trdwll.Utilities.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Lobby{

    private initGame plugin;

    private List<Player> lobbyPlayers;
    private Lobby.LobbyState lobbyState;
    private MapDetails mapDetails;

    private Match match;

    private int scheduleId;
    private int countdownId;

    public Lobby(initGame plugin, MapDetails mapDetails) {
        this.plugin = plugin;
        this.mapDetails = mapDetails;
        this.lobbyPlayers = new ArrayList<Player>();
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

    public List<Player> getLobbyPlayers() {
        return lobbyPlayers;
    }

    public boolean canPlayerJoin() {
        return getLobbyState() != Lobby.LobbyState.IN_GAME && getLobbyPlayers().size() < getMapDetails().getMaxPlayers();
    }

    public boolean addPlayerToLobby(Player player) {
        if (canPlayerJoin() && !getLobbyPlayers().contains(player)) {
            getLobbyPlayers().add(player);

            player.teleport(getMapDetails().getLobbySpawn());
            checkLobbyStatus();

            return true;
        }

        return false;
    }

    public boolean removePlayerFromLobby(Player player) {
        if (getLobbyPlayers().contains(player)) {
            getLobbyPlayers().remove(player);

            if (match != null)
                Utils.clearInventory(player);

            player.teleport(getPlugin().spawn);
            Utils.message(Utils.PrefixType.DEBUG, "Tp'd to Spawn!", player);
            checkLobbyStatus();

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

            setLobbyState(Lobby.LobbyState.COUNTDOWN);

            countdownId = getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Runnable() {

                private int countdown = 10;

                @Override
                public void run() {
                    if (match != null && countdown > 0)
                        sendPlayersMessage((countdown --) + " SECONDS TILL START!");
                    else
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

    private void endMatch() {
        if (match != null) {
            setLobbyState(Lobby.LobbyState.PRE_GAME);

            cancelSchedules();

            for (Player matchPlayer : getLobbyPlayers())
                removePlayerFromLobby(matchPlayer);

            match.endMatch();

            match = null;
        }
    }

    public enum LobbyState {

        PRE_GAME, COUNTDOWN, IN_GAME;

    }

}
