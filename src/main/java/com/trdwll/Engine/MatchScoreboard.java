package com.trdwll.Engine;

import com.trdwll.Game.Match;
import com.trdwll.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MatchScoreboard {

    private Match match;

    private Scoreboard scoreboard;
    private Objective objective;
    private Map<String, Team> playerTeams;

    public MatchScoreboard(Match match) {
        this.match = match;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboard.clearSlot(DisplaySlot.SIDEBAR);

        playerTeams = new HashMap<String, Team>();

        createTeams();
    }

    private void createTeams() {
        for (Player player : match.getLobby().getLobbyPlayers()) {
            Team team = scoreboard.registerNewTeam(player.getName());
            team.setPrefix(ChatColor.AQUA.toString());
            playerTeams.put(player.getName(), team);

            player.setScoreboard(scoreboard);
        }

        start();
    }

    private void start() {
        objective = scoreboard.registerNewObjective("obj", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Utils.translate("&8[&3Countdown&8]"));
    }

    public void setCount(int count) {
        if (count == 0) {
            scoreboard.resetScores("Time to Match:");

            return;
        }

        objective.getScore("Time to Match:").setScore(count);
    }

    public void update(int wave, int round, Collection<Entity> entities) {
        objective.setDisplayName(Utils.translate("&8[&3Stats&8]"));

        if (wave != -1 && round != -1) {
            objective.getScore(Utils.translate("&bWave&8:")).setScore(wave);
            objective.getScore(Utils.translate("&bRound&8:")).setScore(round);
        }

        objective.getScore(Utils.translate("&2Zombies&8:")).setScore(entities.size());
    }

    public void onPlayerLeave(Player player) {
        if (playerTeams.containsKey(player.getName())) {
            Team team = playerTeams.remove(player.getName());

            team.removePlayer(player);
        }
    }

    public void reset() {
        objective.setDisplaySlot(null);
        objective.unregister();
    }

}
