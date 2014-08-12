package com.trdwll.Engine.commands;

import com.trdwll.Engine.Lobby;
import com.trdwll.Engine.Messages;
import com.trdwll.Game.initGame;
import com.trdwll.Utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand extends PZMCommand {

    @Override
    public String getName() {
        return "Leave";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "leave", "l" };
    }

    @Override
    public String[] getHelp() {
        return new String[] { Utils.translate(Messages.addPrefix("%prefix% &3" + getName() + " Help&8:")), Messages.getHelpFormat("Leave", "To Leave a Match Use /pzm leave") };
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only!");

            return true;
        }

        Player player = (Player) sender;

        Utils.message(Utils.PrefixType.DEBUG, "Left lobby", player);

        for (Lobby lobby : initGame.getInstance().getLobbies())
            lobby.removePlayerFromLobby(player);

        return false;
    }

}
