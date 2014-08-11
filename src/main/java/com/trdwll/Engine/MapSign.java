package com.trdwll.Engine;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class MapSign extends LocationSerialized {

    private MapSignType signType;

    public MapSign() { }

    public MapSign(Location location, MapSignType signType) {
        super(location);

        this.signType = signType;
    }

    public enum MapSignType {

        JOIN_LOBBY("[PZM]", "Join", "%lobby%", ""), UNKNOWN("", "", "", "");

        private String lineOne, lineTwo, lineThree, lineFour;

        private MapSignType(String lineOne, String lineTwo, String lineThree, String lineFour) {
            this.lineOne = lineOne;
            this.lineTwo = lineTwo;
            this.lineThree = lineThree;
            this.lineFour = lineFour;
        }

        public String getLineOne() {
            return lineOne;
        }

        public String getLineTwo() {
            return lineTwo;
        }

        public String getLineThree() {
            return lineThree;
        }

        public String getLineFour() {
            return lineFour;
        }

        /* public static MapSignType getType(Block block) {
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();

                for (int i = 0; i < values().length - 1; i ++) {
                    MapSignType signType = values()[i];

                    if (stripColour())
                }
            }

            return UNKNOWN;
        } */

        private static String stripColour(String string) {
            return ChatColor.stripColor(string);
        }

    }

}
