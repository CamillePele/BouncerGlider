package xyz.kams.BouncerGlider;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
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
            Map<String, Object> gliderEntitys = plugin.getGliding().get(player);

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
    public void PlayerJoinEvent (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.getGliding().containsKey(player)) {
            GliderManage.removeGlider(player);
        }
    }

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getBlock().getType() == Material.getMaterial(plugin.getConfig().getString("settings.bouncer.Plate_Block").toUpperCase())  && player.getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.getMaterial(plugin.getConfig().getString("settings.bouncer.Bottom_Block").toUpperCase())) {
            BouncerManager.bounce(player);
        }
    }

    @EventHandler
    public void onEntityClick(PlayerInteractEvent   event) {
        Player player = event.getPlayer();

        Material m = player.getItemInHand().getType();

        if(m == Material.FIREWORK && event.getAction() == Action.LEFT_CLICK_AIR) {
            if (plugin.getGliding().containsKey(player)) {
                Map<String, Object> gliderValues = plugin.getGliding().get(player);

                event.setCancelled(true);

                if (gliderValues.get("boost") != null && ( (Integer) gliderValues.get("boost") < 15 || (Integer) gliderValues.get("boost") > 5)) {
                    gliderValues.put("boost", 10);
                } else {
                    gliderValues.put("boost", 20);
                }

                if (player.getGameMode() != GameMode.CREATIVE) {
                    ItemStack hand = player.getInventory().getItemInHand();
                    hand.setAmount(hand.getAmount() - 1);
                    player.getInventory().setItemInHand(hand);
                }

                player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1.0f, 1.0f);
            }
        }
    }
}
