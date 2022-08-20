package armoyuplugin.armoyuplugin;

import armoyuplugin.armoyuplugin.commands.CreateNoteCommand;
import armoyuplugin.armoyuplugin.commands.NoteMenuCommand;
import armoyuplugin.armoyuplugin.menu.PlayerMenuUtility;
import armoyuplugin.armoyuplugin.utils.JsonUtility;
import armoyuplugin.armoyuplugin.utils.NoteStorageUtility;
import me.kodysimpson.simpapi.command.CommandList;
import me.kodysimpson.simpapi.command.CommandManager;
import me.kodysimpson.simpapi.command.SubCommand;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

public final class ARMOYUPlugin extends JavaPlugin {

        private static ARMOYUPlugin plugin;
    @Override
    public void onEnable() {
        System.out.println("[ARMOYU] ----Aktif----");
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        plugin = this;

        try {
            CommandManager.createCoreCommand(this, "note", "Create and list notes", "/note", new CommandList() {
                @Override
                public void displayCommandList(Player p, List<SubCommand> subCommandList) {
                    p.sendMessage("--------------------------------");
                    for (SubCommand subcommand : subCommandList){
                        p.sendMessage(subcommand.getSyntax() + " - " + subcommand.getDescription());
                    }
                    p.sendMessage("--------------------------------");
                }
            }, CreateNoteCommand.class, NoteMenuCommand.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        MenuManager.setup(getServer(), this, PlayerMenuUtility.class);

        try {
            NoteStorageUtility.loadNotes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try { JsonUtility.loadNotes(); } catch (IOException e) {    e.printStackTrace();   }
    }
    @Override
    public void onDisable() {
        System.out.println("[ARMOYU] ----Devre Dışı----");
    }

    public static ARMOYUPlugin getPlugin(){
        return plugin;
    }
}
