package xyz.kams.BouncerGlider.glider;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.kams.BouncerGlider.Main;

import java.util.Map;

public class GliderTask extends BukkitRunnable {

    private static Main plugin;

    public GliderTask(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Map.Entry hm : plugin.getGliding().entrySet()) {
            //System.out.println("Key: "+hm.getKey() + " & Value: " + hm.getValue());
            Player player = (Player) hm.getKey();
            if(player.getVehicle() instanceof Chicken) {
                Map<String, Object> gliderEntitys = (Map<String, Object>) hm.getValue();

                Chicken chicken = (Chicken) gliderEntitys.get("chicken");
                Vector vector;
                if (chicken.getLocation().getBlock().getType()==Material.STATIONARY_WATER) {
                    vector = player.getLocation().getDirection().multiply(plugin.getGliderPowerFront()).setY(0);
                } else {
                    vector = player.getLocation().getDirection().multiply(plugin.getGliderPowerFront()).setY(plugin.getGliderPowerBottom());
                }
                chicken.setVelocity(vector);
                chicken.getLocation().setDirection(player.getLocation().getDirection());


                ArmorStand left = (ArmorStand) gliderEntitys.get("left");
                left.teleport(player.getLocation());

                ArmorStand right = (ArmorStand) gliderEntitys.get("right");
                right.teleport(player.getLocation());


                ArmorStand center = (ArmorStand) gliderEntitys.get("center");
                center.teleport(player.getLocation());

                //player.getLocation().getWorld().playEffect(player.getLocation().add(0, 1, 0), Effect.SMOKE, 20);

                if (chicken.isOnGround()) {
                    GliderManage.removeGlider(player);
                }
            }
        }
    }
}
