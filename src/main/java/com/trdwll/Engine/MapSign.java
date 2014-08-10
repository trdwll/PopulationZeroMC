package com.trdwll.Engine;

import org.bukkit.Location;

public class MapSign {

    private MapSignType signType;
    private LocationSerialized location;

    public MapSign() { }

    public MapSign(MapSignType signType, Location location) {
        this.signType = signType;
        this.location = new LocationSerialized(location);
    }

    public enum MapSignType {

        

    }

}
