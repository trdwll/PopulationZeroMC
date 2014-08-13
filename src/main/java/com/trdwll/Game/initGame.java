package com.trdwll.Game;

import java.io.File;
import java.util.*;

import com.trdwll.Engine.GeneralSettings;
import com.trdwll.Engine.Messages;
import com.trdwll.Engine.commands.CommandRegistry;
import com.trdwll.Utilities.GsonFileUtils;
import net.minecraft.util.com.google.gson.Gson;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.trdwll.Engine.Lobby;
import com.trdwll.Engine.initEngine;
import com.trdwll.Utilities.Utils;

public class initGame extends JavaPlugin {

    private static initGame INSTANCE;

    private Map<String, Lobby> lobbies;
    private CommandRegistry commandRegistry;

    private GeneralSettings settings;

    public void onEnable() {
        INSTANCE = this;

        saveResource("config.yml", false);

        GsonFileUtils.setPlugin(this);
        Messages.setPlugin(this);

        lobbies = new HashMap<String, Lobby>();
        // spawn = new Location(getServer().getWorld("world"), 2322, 4, -261);
        commandRegistry = new CommandRegistry();
        settings = GsonFileUtils.loadSettingsFromFile("GeneralSettings.json", true, true);

        try {
            this.getServer().getPluginManager().registerEvents(new initEngine(this), this);
            System.out.print(Utils.prefixOk + "Successful startup!");
        }
        catch (Exception e) {
            System.out.print(Utils.prefixError + "Error starting up!");
        }

        reload();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                for (World world : getServer().getWorlds()) {
                    if (world.getTime() < 14000 || world.getTime() >= 22000)
                        world.setTime(14000);
                }
            }

        }, 20, 20);
    }

    public GeneralSettings getSettings() {
        return settings;
    }

    public static initGame getInstance() {
        return INSTANCE;
    }

    public void reload() {
        for (Lobby lobby : new ArrayList<Lobby>(getLobbies()))
            lobby.endMatch();

        lobbies.clear();

        for (Lobby lobby : GsonFileUtils.loadAllLobbiesFromDirectory(new File(getDataFolder(), "MapDetails")))
            lobbies.put(lobby.getMapDetails().getMapName().toLowerCase(), lobby);

        reloadConfig();
    }

    public Collection<Lobby> getLobbies() {
        return lobbies.values();
    }

    public Map<String, Lobby> getNamedLobbies() {
        return lobbies;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return commandRegistry.onCommand(sender, cmd, label, args);
    }

}