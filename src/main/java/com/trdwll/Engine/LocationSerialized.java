package com.trdwll.Engine;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSerialized {

    private double x, y, z;
    private String world;

    public LocationSerialized() { }

    public LocationSerialized(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.world = location.getWorld().getName();
    }

    public Location getLocation() {
        return Bukkit.getWorld(world) != null ? new Location(Bukkit.getWorld(world), x, y, z) : null;
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

}
