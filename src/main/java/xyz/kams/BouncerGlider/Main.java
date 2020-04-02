package xyz.kams.BouncerGlider;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import xyz.kams.BouncerGlider.bouncer.BouncerManager;
import xyz.kams.BouncerGlider.bouncer.BouncerTask;
import xyz.kams.BouncerGlider.glider.GliderManage;
import xyz.kams.BouncerGlider.glider.GliderTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    HashMap<Player, Map<String, Object>> Gliding = new HashMap<Player, Map<String, Object>>();
    ArrayList<Player> Bounced = new ArrayList<Player>();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        Gliding = new HashMap<Player, Map<String, Object>>();
        Bounced = new ArrayList<Player>();

        getServer().getPluginManager().registerEvents(new EventListener(this), this);

        GliderManage gliderManage = new GliderManage(this);
        BouncerManager bouncerManager = new BouncerManager(this);

        BukkitTask gliderTask = new GliderTask(this).runTaskTimer(this, 1, 1);
        BukkitTask bouncerTask = new BouncerTask(this).runTaskTimer(this, 1, 10);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public HashMap<Player, Map<String, Object>> getGliding() {
        return Gliding;
    }

    public ArrayList<Player> getBounced() {
        return Bounced;
    }

}
