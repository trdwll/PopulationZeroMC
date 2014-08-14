package com.trdwll.Engine;

import java.util.HashMap;
import java.util.Map;

public class MapSignRegistry {

    private Map<String, MapSign> signs;
    private Map<String, String> matchSigns;

    public MapSignRegistry() {
        this.signs = new HashMap<String, MapSign>();
    }

    public void registerSign(MapSign sign) {
        if (!signs.containsKey(sign.getSignId())) {
            signs.put(sign.getSignId(), sign);
            matchSigns.put("", sign.getSignId());
        }
    }

}
