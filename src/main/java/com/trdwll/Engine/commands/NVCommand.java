package com.trdwll.Engine.commands;

import com.trdwll.Engine.Messages;
import com.trdwll.Utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NVCommand extends PZMCommand {

    @Override
    public String getName() {
        return "Night Vision";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "nv", "nightvision" };
    }

    @Override
    public String[] getHelp() {
        return new String[] { Utils.translate(Messages.addPrefix("%prefix% &3" + getName() + " Help&8:")), Messages.getHelpFormat("NV", "To Get Night Vision Use /pzm nv") };
    }

    @Override
    public boolean usesPermission() {
        return true;
    }

    @Override
    public String getPermission() {
        return "pzm.nv";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only!");

            return true;
        }

        sender.sendMessage(Messages.getNightVisionAdded());

        return true;
    }

}
