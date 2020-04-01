package xyz.kams.bumper.utils;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.util.EulerAngle;
import xyz.kams.bumper.Bumper;

import java.util.HashMap;
import java.util.Map;

public class GliderManage {

    private static Bumper plugin;

    public GliderManage(Bumper plugin) {
        this.plugin = plugin;
    }

    public static Player setGlide(Player player) {
        Location loc = player.getLocation();

        Map<String, Object> gliderEntitys = new HashMap<String, Object>();

        Chicken chicken = loc.getWorld().spawn(loc, Chicken.class, c -> {
            //c.setAI(false);
        });
        chicken.addPassenger(player);
        gliderEntitys.put("chicken", chicken);

        ArmorStand left = loc.getWorld().spawn(loc, ArmorStand.class);
        configArmorStand(left);
        left.setHeadPose(new EulerAngle(0, 1.5708, 1.5708));
        left.setHelmet(getBanner());
        gliderEntitys.put("left", left);

        ArmorStand right = loc.getWorld().spawn(loc, ArmorStand.class);
        configArmorStand(right);
        right.setHeadPose(new EulerAngle(0, -1.5708, -1.5708));
        right.setHelmet(getBanner());
        gliderEntitys.put("right", right);

        ArmorStand center = loc.getWorld().spawn(loc, ArmorStand.class);
        configArmorStand(center);
        center.setHeadPose(new EulerAngle(3.14159, 0, 0));
        center.setHelmet(new ItemStack(Material.FENCE_GATE, 1));
        gliderEntitys.put("center", center);

        plugin.getGliding().put(player, gliderEntitys);


        return player;
    }

    public static void removeGlider(Player player) {
        if (plugin.getGliding().containsKey(player)) {
            Map<String, Object> gliderEntitys = (Map<String, Object>) plugin.getGliding().get(player);

            Chicken chicken = (Chicken) gliderEntitys.get("chicken");
            chicken.remove();

            ArmorStand left = (ArmorStand) gliderEntitys.get("left");
            left.remove();

            ArmorStand right = (ArmorStand) gliderEntitys.get("right");
            right.remove();

            ArmorStand center = (ArmorStand) gliderEntitys.get("center");
            center.remove();

            plugin.getGliding().remove(player);
        }
    }

    private static ItemStack getBanner() {
        ItemStack i = new ItemStack(Material.BANNER, 1);
        BannerMeta m = (BannerMeta)i.getItemMeta();

        m.setBaseColor(DyeColor.RED);
        i.setItemMeta(m);

        return i;
    }

    private static ArmorStand configArmorStand(ArmorStand armorStand) {
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);

        return armorStand;
    }

}
