package com.trdwll.Engine.commands;

import com.trdwll.Engine.Messages;
import com.trdwll.Game.initGame;
import com.trdwll.Utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadComand extends PZMCommand {

    @Override
    public String getName() {
        return "Reload";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "reload", "r" };
    }

    @Override
    public String[] getHelp() {
        return new String[] { Utils.translate(Messages.addPrefix("%prefix% &3" + getName() + " Help&8:")), Messages.getHelpFormat("Reload", "To Reload Configurations Use /pzm reload") };
    }

    @Override
    public boolean usesPermission() {
        return true;
    }

    @Override
    public String getPermission() {
        return "pzm.reload";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(Messages.getReloaded());
        initGame.getInstance().reload();

        return true;
    }

}
