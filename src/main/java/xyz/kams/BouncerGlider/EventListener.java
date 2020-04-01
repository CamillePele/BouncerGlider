package xyz.kams.BouncerGlider;

import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import xyz.kams.BouncerGlider.bouncer.BouncerManager;
import xyz.kams.BouncerGlider.glider.GliderManage;

import java.util.Map;

public class EventListener implements Listener {

    private static Main plugin;

    public EventListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerToggleSneakEvent(PlayerToggleSneakEvent event) {

        Player player = event.getPlayer();
        if (plugin.getGliding().containsKey(player)) {
            Map<String, Object> gliderEntitys = (Map<String, Object>) plugin.getGliding().get(player);

            Chicken chicken = (Chicken) gliderEntitys.get("chicken");
            if (chicken.getLocation().getBlock().getType()==Material.STATIONARY_WATER) {
                GliderManage.removeGlider(player);
            } else {
                chicken.addPassenger(player);
            }
        }
    }

    @EventHandler
    public void PlayerQuitEvent (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.getGliding().containsKey(player)) {
            GliderManage.removeGlider(player);
        }
    }

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getBlock().getType() == plugin.getBouncerPlate()  && player.getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() == plugin.getBouncerBlock()) {
            BouncerManager.bounce(player);
        }
    }

}
