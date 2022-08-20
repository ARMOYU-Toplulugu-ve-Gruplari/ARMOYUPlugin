package armoyuplugin.armoyuplugin.commands;

import armoyuplugin.armoyuplugin.menu.menus.NoteMenu;
import me.kodysimpson.simpapi.command.CommandManager;
import me.kodysimpson.simpapi.command.SubCommand;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.entity.Player;

import java.util.List;

public class NoteMenuCommand extends SubCommand {
    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public String getDescription() {
        return "Open the note GUI menu";
    }

    @Override
    public String getSyntax() {
        return "/noerwiwjriwerte menu";
    }

    @Override
    public void perform(Player p, String[] args) {
        try {
            MenuManager.openMenu(NoteMenu.class, p);
        } catch (MenuManagerException | MenuManagerNotSetupException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}