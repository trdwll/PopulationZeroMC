package com.trdwll.Engine.commands;

import com.trdwll.Engine.Lobby;
import com.trdwll.Engine.Messages;
import com.trdwll.Game.initGame;
import com.trdwll.Utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListCommand extends PZMCommand {

    private static final int LOBBIES_PER_PAGE = 5;

    @Override
    public String getName() {
        return "List";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "list", "info", "i", "status" };
    }

    @Override
    public String[] getHelp() {
        return new String[] { Utils.translate(Messages.addPrefix("%prefix% &3" + getName() + " Help&8:")), Messages.getHelpFormat("List", "To View Lobbies Use /pzm list [page]") };
    }

    @Override
    public boolean usesPermission() {
        return true;
    }

    @Override
    public String getPermission() {
        return "pzm.list";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.getPlayersOnly());

            return true;
        }

        Player player = (Player) sender;

        if (args.length == 3) {
            int page = 1;

            try {
                page = Integer.valueOf(args[2]);
            } catch (NumberFormatException e) { }

            sender.sendMessage(getHelp(page, player));

            return true;
        }

        sender.sendMessage(getHelp(1, player));

        return true;
    }

    public String[] getHelp(int page, Player player) {
        List<String> help = new ArrayList<String>();
        List<Lobby> lobbyList = new ArrayList<Lobby>(initGame.getInstance().getLobbies());
        int pages = lobbyList.size() / LOBBIES_PER_PAGE + 1;

        page = Math.min(page, lobbyList.size() / LOBBIES_PER_PAGE + 1) > 0 ? Math.min(page, lobbyList.size() / LOBBIES_PER_PAGE + 1) : 1;

        help.add(Messages.getHelp("Lobby Info", page, pages));

        int helpLength = 1;

        for (int i = helpLength; i < helpLength + LOBBIES_PER_PAGE; i ++)
            if (lobbyList.size() > i - helpLength + page * LOBBIES_PER_PAGE - LOBBIES_PER_PAGE && i - helpLength + page * LOBBIES_PER_PAGE - LOBBIES_PER_PAGE > -1) {
                Lobby lobby = lobbyList.get(i - helpLength + page * LOBBIES_PER_PAGE - LOBBIES_PER_PAGE);

                if (lobby == null)
                    continue;

                help.add(i, Messages.getHelpFormat(lobby.getMapDetails().getMapName(), lobby.canPlayerJoin(player) ? lobby.getLobbyState() == Lobby.LobbyState.PRE_GAME ? "&aTo Join Use /pzm j " + lobby.getMapDetails().getMapName() : lobby.getLobbyState() == Lobby.LobbyState.COUNTDOWN ? "&eTo Join Use /pzm j " + lobby.getMapDetails().getMapName() : "&cTo Join Use /pzm j " + lobby.getMapDetails().getMapName() : "&cLobby is Full!"));
            }

        return help.toArray(new String[help.size()]);
    }

}
