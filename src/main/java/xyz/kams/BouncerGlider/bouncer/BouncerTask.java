package xyz.kams.BouncerGlider.bouncer;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.kams.BouncerGlider.Main;
import xyz.kams.BouncerGlider.glider.GliderManage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BouncerTask extends BukkitRunnable {

    private static Main plugin;

    public BouncerTask(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        List<Player> toRemove = new ArrayList<>();
        for (Player player : plugin.getBounced()) {
            if (player.getVelocity().getY() < 0) {
                GliderManage.setGlide(player);
                toRemove.add(player);
            }
        }
        plugin.getBounced().removeAll(toRemove);
    }

}
