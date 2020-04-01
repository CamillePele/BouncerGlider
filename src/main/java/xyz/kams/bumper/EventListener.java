package xyz.kams.bumper;

import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import xyz.kams.bumper.utils.GliderManage;

import java.util.Map;

public class EventListener implements Listener {

    private static Bumper plugin;

    public EventListener(Bumper plugin) {
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

}
