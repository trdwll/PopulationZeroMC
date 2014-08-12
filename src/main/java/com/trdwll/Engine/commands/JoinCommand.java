package com.trdwll.Engine.commands;

import com.trdwll.Engine.Lobby;
import com.trdwll.Engine.Messages;
import com.trdwll.Game.initGame;
import com.trdwll.Utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand extends PZMCommand {

    @Override
    public String getName() {
        return "Join";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "join", "j" };
    }

    @Override
    public String[] getHelp() {
        return new String[] { Utils.translate(Messages.addPrefix("%prefix% &3" + getName() + " Help&8:")), Messages.getHelpFormat("By ID", "To Join a Lobby by ID Use /pzm join <ID>"), Messages.getHelpFormat("By Name", "To Join a Lobby by Name Use /pzm join <name>") };
    }

    @Override
    public boolean usesPermission() {
        return true;
    }

    @Override
    public String getPermission() {
        return "pzm.join";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only!");

            return true;
        }

        Player player = (Player) sender;

        if (args.length == 2) {
            Integer id = null;

            try {
                id = Integer.valueOf(args[1]) - 1;
            } catch (NumberFormatException e) { }

            if (id != null) {
                if (id >= 0 && id <= initGame.getInstance().getLobbies().size() && !initGame.getInstance().getLobbies().isEmpty())
                    if (initGame.getInstance().getLobbies().toArray(new Lobby[initGame.getInstance().getLobbies().size()])[id].canPlayerJoin())
                        initGame.getInstance().getLobbies().toArray(new Lobby[initGame.getInstance().getLobbies().size()])[id].addPlayerToLobby(player);
                    else
                        sender.sendMessage("Lobby Full");

                return true;
            }
        }

        if (args.length >= 2) {
            String name = patchArgs(args).toLowerCase();

            if (initGame.getInstance().getNamedLobbies().containsKey(name))
                if (initGame.getInstance().getNamedLobbies().get(name).canPlayerJoin())
                    initGame.getInstance().getNamedLobbies().get(name).addPlayerToLobby(player);
                else
                    sender.sendMessage("Lobby Full");
            else
                sender.sendMessage("Invalid Lobby");

            return true;
        }

        sender.sendMessage(getHelp());

        return true;
    }

    private String patchArgs(String[] args) {
        StringBuilder builder = new StringBuilder(args.length >= 2 ? args[1] : "");

        if (args.length > 2)
            for (int i = 2; i < args.length; i ++)
                builder.append(" ").append(args[i]);

        return builder.toString();
    }

}
