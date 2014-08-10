package com.trdwll.Engine;

import org.bukkit.Location;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Zombie;

public class ZombieSpawnerData extends LocationSerialized {

    private boolean isPigZombie;

    public ZombieSpawnerData() { }

    public ZombieSpawnerData(Location location, boolean isPigZombie) {
        super(location);

        this.isPigZombie = isPigZombie;
    }

    // Hai

    public Zombie spawnZombie() {
        Zombie zombie = isPigZombie ? getLocation().getWorld().spawn(getLocation(), PigZombie.class) : getLocation().getWorld().spawn(getLocation(), Zombie.class);

        // TODO: Custom armour?

        return zombie;
    }

}
