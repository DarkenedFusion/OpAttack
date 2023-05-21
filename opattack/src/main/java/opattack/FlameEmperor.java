package opattack;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FlameEmperor implements Listener {
	
	
	@EventHandler
	public void createCircles(PlayerInteractEvent event) {
	    final Player player = event.getPlayer();
	    Action a = event.getAction();
	    
	    if (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)) {
	        if (player.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD)) {
	            player.sendMessage("Starting Flame ring sequence!");
	            

	            

	            // Start Sequence

	            // Create sphere
	            new BukkitRunnable() {
	                double tt = 0;
	                double radius = 5.0; // Radius of the sphere
	                double angle = 0;
	                double angleIncrement = Math.PI / 32; // Adjust the angle increment for more or fewer particles

	                public void run() {
	                    tt++;

	                    Location playerLocation = player.getLocation();

	                    // Calculate the center location for the sphere above the player's head
	                    Location centerLocation = playerLocation.clone().add(0, 10, 0); // Adjust the vertical offset as needed

	                    for (double theta = 0; theta <= Math.PI; theta += angleIncrement) {
	                        for (double phi = 0; phi < 2 * Math.PI; phi += angleIncrement) {
	                            double x = centerLocation.getX() + radius * Math.sin(theta) * Math.cos(phi);
	                            double y = centerLocation.getY() + radius * Math.cos(theta);
	                            double z = centerLocation.getZ() + radius * Math.sin(theta) * Math.sin(phi);
	                            Location particleLocation = new Location(centerLocation.getWorld(), x, y, z);

	                            // Display flame particles for the sphere
	                            centerLocation.getWorld().spawnParticle(Particle.FLAME, particleLocation, 0, 0, 0, 0, 0, null, true);

	                            // Add fire sparks effect within the sphere
	                            if (Math.random() < 0.15) { // Adjust the probability to control the frequency of fire sparks
	                                double spreadX = Math.random() * 0.9 - 0.3; // Adjust the spread range as needed
	                                double spreadY = Math.random() * 0.9 - 0.3;
	                                double spreadZ = Math.random() * 0.9 - 0.3;
	                                Location sparkLocation = particleLocation.clone().add(spreadX, spreadY, spreadZ);
	                                centerLocation.getWorld().spawnParticle(Particle.FLAME, sparkLocation, 1, 0, 0, 0, 0, null, true);
	                            }
	                        }
	                    }

	                    angle += 0.1; // Adjust the speed of rotation for the sphere

	                    if (tt >= 10 * 20) {
	                        this.cancel();
	                    }
	                }
	            }.runTaskTimer(Main.getInstance(), 0, 1); // Run the task every tick (approximately 0.05 seconds)
	        }
	    }
	}


}
