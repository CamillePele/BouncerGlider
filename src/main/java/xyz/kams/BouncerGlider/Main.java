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

    Double bouncerPowerFront;
    Double bouncerPowerHeight;
    Material bouncerPlate;
    Material bouncerBlock;

    Double gliderPowerFront;
    Double gliderPowerBottom;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        bouncerPowerFront = this.getConfig().getDouble("settings.bouncer.Power_Front");
        bouncerPowerHeight = this.getConfig().getDouble("settings.bouncer.Power_Height");
        bouncerPlate = Material.getMaterial(getConfig().getString("settings.bouncer.Plate_Block").toUpperCase());
        bouncerBlock = Material.getMaterial(getConfig().getString("settings.bouncer.Bottom_Block").toUpperCase());

        gliderPowerFront = this.getConfig().getDouble("settings.glider.Power_Front");
        gliderPowerBottom = -1 * this.getConfig().getDouble("settings.glider.Power_Bottom");

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

    public Double getGliderPowerBottom() {
        return gliderPowerBottom;
    }

    public Double getGliderPowerFront() {
        return gliderPowerFront;
    }

    public Material getBouncerBlock() {
        return bouncerBlock;
    }

    public Material getBouncerPlate() {
        return bouncerPlate;
    }

    public Double getBouncerPowerHeight() {
        return bouncerPowerHeight;
    }

    public Double getBouncerPowerFront() {
        return bouncerPowerFront;
    }
}
