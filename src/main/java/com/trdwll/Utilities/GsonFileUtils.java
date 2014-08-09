package com.trdwll.Utilities;

import com.trdwll.Engine.MapDetails;
import com.trdwll.Game.initGame;
import net.minecraft.util.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class GsonFileUtils {

    private static initGame plugin;

    public static void setPlugin(initGame plugin) {
        GsonFileUtils.plugin = plugin;
    }

    public static MapDetails loadMapDetailsFromFile(String resource) {
        return loadMapDetailsFromFile(resource, false);
    }

    public static MapDetails loadMapDetailsFromFile(String resource, boolean replace) {
        plugin.saveResource(resource, replace);

        return loadMapDetailsFromFile(new File(plugin.getDataFolder(), resource));
    }

    public static MapDetails loadMapDetailsFromFile(File file) {
        try {
            String json = FileUtils.readFileToString(file);

            return Utils.getGson().fromJson(json, MapDetails.class);
        } catch (IOException e) {
            plugin.getLogger().severe("[GsonFileUtils] Failed to Load MapDetails for a Map!");
            e.printStackTrace();

            return null;
        }
    }

}
