package xyz.kams.BouncerGlider.bouncer;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.kams.BouncerGlider.Main;

public class BouncerManager {

    private static Main plugin;

    public BouncerManager(Main plugin) {
        this.plugin = plugin;
    }

    public static void bounce(Player player) {
        if (plugin.getBounced().contains(player)) return;
        Vector vector = player.getLocation().getDirection().multiply(plugin.getConfig().getDouble("settings.bouncer.Power_Front")).setY(plugin.getConfig().getDouble("settings.bouncer.Power_Height"));
        player.setVelocity(vector);
        player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1.0f, 1.0f);
        plugin.getBounced().add(player);
    }
}
