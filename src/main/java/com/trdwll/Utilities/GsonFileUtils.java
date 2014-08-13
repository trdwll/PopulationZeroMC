package com.trdwll.Utilities;

import com.trdwll.Engine.GeneralSettings;
import com.trdwll.Engine.Lobby;
import com.trdwll.Engine.MapDetails;
import com.trdwll.Game.initGame;
import net.minecraft.util.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GsonFileUtils {

    private static initGame plugin;

    public static void setPlugin(initGame plugin) {
        GsonFileUtils.plugin = plugin;
    }

    public static List<Lobby> loadAllLobbiesFromDirectory(File directory) {
        List<Lobby> lobbies = new ArrayList<Lobby>();

        for (File file : directory.listFiles()) {
            if (file.getAbsolutePath().endsWith(".json"))
                lobbies.add(new Lobby(plugin, loadMapDetailsFromFile(file)));
        }

        return lobbies;
    }

    public static MapDetails loadMapDetailsFromFile(String file, boolean resource) {
        return loadMapDetailsFromFile(file, resource, false);
    }

    public static MapDetails loadMapDetailsFromFile(String file, boolean resource, boolean replace) {
        if (resource)
            plugin.saveResource(file, replace);

        return loadMapDetailsFromFile(new File(plugin.getDataFolder(), file));
    }

    public static MapDetails loadMapDetailsFromFile(File file) {
        return (MapDetails) loadFromJsonFile(file, MapDetails.class);
    }

    public static GeneralSettings loadSettingsFromFile(String file, boolean resource) {
        return loadSettingsFromFile(file, resource, false);
    }

    public static GeneralSettings loadSettingsFromFile(String file, boolean resource, boolean replace) {
        if (resource)
            plugin.saveResource(file, replace);

        return loadSettingsFromFile(new File(plugin.getDataFolder(), file));
    }

    public static GeneralSettings loadSettingsFromFile(File file) {
        return (GeneralSettings) loadFromJsonFile(file, GeneralSettings.class);
    }

    public static Object loadFromJsonFile(File file, Class<?> clz) {
        try {
            String json = FileUtils.readFileToString(file);

            return Utils.getGson().fromJson(json, clz);
        } catch (IOException e) {
            plugin.getLogger().severe("[GsonFileUtils] Failed to Load MapDetails for a Map!");
            e.printStackTrace();

            return null;
        }
    }

}
