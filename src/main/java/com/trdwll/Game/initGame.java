package com.trdwll.Game;

import java.io.File;
import java.util.*;

import com.trdwll.Engine.*;
import com.trdwll.Engine.commands.CommandRegistry;
import com.trdwll.Utilities.GsonFileUtils;
import net.minecraft.util.com.google.gson.Gson;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.trdwll.Utilities.Utils;

public class initGame extends JavaPlugin {

    private static initGame INSTANCE;

    private Map<String, Lobby> lobbies;
    private CommandRegistry commandRegistry;
    private MapSignRegistry signRegistry;

    private GeneralSettings settings;

    public void onEnable() {
        INSTANCE = this;

        saveResource("config.yml", false);

        GsonFileUtils.setPlugin(this);
        Messages.setPlugin(this);

        lobbies = new HashMap<String, Lobby>();
        // spawn = new Location(getServer().getWorld("world"), 2322, 4, -261);
        commandRegistry = new CommandRegistry();
        signRegistry = new MapSignRegistry();
        settings = GsonFileUtils.loadSettingsFromFile("GeneralSettings.json", false);

        try {
            getServer().getPluginManager().registerEvents(new initEngine(this), this);
            getServer().getPluginManager().registerEvents(MapSign.MapSignType.UNKNOWN, this);

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

    public MapSignRegistry getSignRegistry() {
        return signRegistry;
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