package xyz.kams.BouncerGlider.glider;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
            Map<String, Object> gliderValues = (Map<String, Object>) hm.getValue();
            Chicken chicken = (Chicken) gliderValues.get("chicken");
            if(player.getVehicle() == chicken) {

                if (player.getItemInHand().getType() == Material.FIREWORK) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Left click to boost"));
                }

                Vector vector;
                if (chicken.getLocation().getBlock().getType()==Material.STATIONARY_WATER) {
                    vector = player.getLocation().getDirection().multiply(plugin.getConfig().getDouble("settings.glider.Power_Front")).setY(0);
                }  else if (gliderValues.get("boost") != null && (Integer) gliderValues.get("boost") > 0) { //BOOST FUNCTION

                    double x = (Integer) gliderValues.get("boost")*(Math.PI/20); //MAP Tick to pi (ex: 20 = 3.1415... and 0 = 0)

                    vector = player.getLocation().getDirection().multiply(1.5+5*Math.pow(Math.sin(x), 2)).setY(0); //Use Sinus Function to do smooth speed

                    gliderValues.replace("boost", (Integer) gliderValues.get("boost") - 1);
                    System.out.println(gliderValues.get("boost"));
                }
                else {
                    vector = player.getLocation().getDirection().multiply(plugin.getConfig().getDouble("settings.glider.Power_Front")).setY(-1 * plugin.getConfig().getDouble("settings.glider.Power_Bottom"));
                }
                chicken.setVelocity(vector);
                chicken.getLocation().setDirection(player.getLocation().getDirection());

                ArmorStand left = (ArmorStand) gliderValues.get("left");
                left.teleport(player.getLocation());

                ArmorStand right = (ArmorStand) gliderValues.get("right");
                right.teleport(player.getLocation());


                ArmorStand center = (ArmorStand) gliderValues.get("center");
                center.teleport(player.getLocation());

                //player.getLocation().getWorld().playEffect(player.getLocation().add(0, 1, 0), Effect.SMOKE, 20);

                if (chicken.isOnGround()) {
                    GliderManage.removeGlider(player);
                }
            }
            else {
                chicken.addPassenger(player);
            }
        }
    }
}
