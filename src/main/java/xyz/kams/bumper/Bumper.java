package xyz.kams.bumper;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import xyz.kams.bumper.utils.GliderManage;
import xyz.kams.bumper.utils.GliderTask;

import java.util.HashMap;
import java.util.Map;

public final class Bumper extends JavaPlugin {

    HashMap<Player, Map<String, Object>> Gliding = new HashMap<Player, Map<String, Object>>();

    @Override
    public void onEnable() {
        Gliding = new HashMap<Player, Map<String, Object>>();

        this.getCommand("test").setExecutor(new testCommand());
        getServer().getPluginManager().registerEvents(new EventListener(this), this);

        GliderManage gliderManage = new GliderManage(this);

        BukkitTask gliderTask = new GliderTask(this).runTaskTimer(this, 1, 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public HashMap<Player, Map<String, Object>> getGliding() {
        return Gliding;
    }
}
