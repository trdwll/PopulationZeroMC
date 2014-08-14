package com.trdwll.Engine;

import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Zombie;

public class ZombieSpawnerData extends LocationSerialized {

    private boolean isPigZombie;

    private int startZombieSpawnCount;
    private int zombieIncrementalCount;

    private int roundSpawnAddition;

    public ZombieSpawnerData() { }

    public boolean isPigZombie() {
        return isPigZombie;
    }

    public int getStartZombieSpawnCount() {
        return startZombieSpawnCount;
    }

    public int getZombieIncrementalCount() {
        return zombieIncrementalCount;
    }

    public int getRoundSpawnAddition() {
        return roundSpawnAddition;
    }

    public String getSpawnerId() {
        return getWorld() + ":" + getX() + ":" + getY() + ":" + getZ();
    }

    public Zombie spawnZombie() {
        Zombie zombie = isPigZombie() ? getLocation().getWorld().spawn(getLocation(), PigZombie.class) : getLocation().getWorld().spawn(getLocation(), Zombie.class);

        if (zombie instanceof PigZombie) {
            ((PigZombie) zombie).setAnger(Integer.MAX_VALUE);
        }

        // TODO: Custom armour?

        return zombie;
    }

}
