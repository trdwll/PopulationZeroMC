package com.trdwll.Engine;

import com.trdwll.Game.initGame;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class MapSign extends LocationSerialized {

    private MapSignType signType;

    public MapSign() { }

    public MapSign(Location location, MapSignType signType, MapDetails details) {
        super(location);

        this.signType = signType;
    }

    public String getSignId() {
        return getWorld() + ":" + getX() + ":" + getY() + ":" + getZ();
    }

    public enum MapSignType implements Listener {

        JOIN_LOBBY("[&3Join&0]", "", "%lobby%", ""), UNKNOWN("", "", "", "");

        private String[] lines;

        private MapSignType(String lineOne, String lineTwo, String lineThree, String lineFour) {
            lines = new String[] { lineOne, lineTwo, lineThree, lineFour };
        }

        public static MapSignType getType(Block block) {
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();

                for (int i = 0; i < values().length - 1; i ++) {
                    MapSignType signType = values()[i];
                    boolean flag = false;

                    for (String str : signType.lines)
                        if (stripColour(str).equals("%lobby%"))
                            flag = true;
                        else if (stripColour(str).equalsIgnoreCase(stripColour(sign.getLines()[i])))
                            flag = true;
                        else
                            flag = false;

                    if (flag)
                        return signType;
                }
            }

            return UNKNOWN;
        }

        /* @EventHandler
        public void onBlockPlace(BlockPlaceEvent event) {
            if (event.getBlock() != null && getType(event.getBlock()) != UNKNOWN)
                getType(event.getBlock()).onSignPlace(event.getBlock());
        }

        private void onSignPlace(Block block) {
            MapDetails mapDetails = null;
            String details = mapDetails.getMapName();

            initGame.getInstance().getSignRegistry().registerSign(new MapSign(block.getLocation(), this, details));
        } */

        private static String stripColour(String string) {
            return ChatColor.stripColor(string);
        }

    }

}
