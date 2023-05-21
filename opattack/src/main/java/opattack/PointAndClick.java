package opattack;

import java.util.HashMap;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class PointAndClick implements Listener {
	
	private HashMap<Player, Entity> targetedEntities = new HashMap<>();

	
	@EventHandler
	public void detectEnemy(PlayerInteractEvent event) {
	    Player player = event.getPlayer();
	    Action a = event.getAction();

	    
	    if (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)) {
	        if (player.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
	        	
	        	 Vector direction = player.getLocation().getDirection();

	             // Set the maximum distance for the ray trace
	             double maxDistance = 50; // Adjust the distance as needed

	             // Perform the ray trace
	             RayTraceResult result = player.getWorld().rayTrace(player.getEyeLocation(), direction, maxDistance,
	                     FluidCollisionMode.NEVER, true, 0.0, entity -> entity instanceof LivingEntity && !entity.equals(player));
	             // Check if the ray trace hit a living entity
	             if (result != null && result.getHitEntity() instanceof LivingEntity && result != player) {
	                 LivingEntity targetEntity = (LivingEntity) result.getHitEntity();
	                 targetedEntities.put(player, result.getHitEntity());
	                 // Perform your desired action here
	                 player.sendMessage("You are looking at a living entity: " + targetEntity.getName());
	                 
	                 
	                 new BukkitRunnable() {
	                	 double t = 0;
	                	 
	                	 public void run() {
	                		 t++;
	                		 
	                		 targetEntity.setGlowing(true);
	                		 
	                		 
	                		 if(t > 20 * 3) {
	                			 targetEntity.setGlowing(false);
	                			 this.cancel();
	                		 }
	                		 
	                	 }
	                	 
	                 }.runTaskTimer(Main.getInstance(), 0, 1);
	             }
	        	
	        	
	        	
	        	
	        }
	    }
	    
	    if (a.equals(Action.LEFT_CLICK_AIR) || a.equals(Action.LEFT_CLICK_BLOCK)) {
	        if (player.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
	            if (targetedEntities.containsKey(player)) {
	                LivingEntity entity = (LivingEntity) targetedEntities.get(player);
	                if (entity.isGlowing()) {
	                	entity.setAI(false);
	                    entity.setGravity(false);
	                    
	                    Vector direction = player.getEyeLocation().getDirection(); // Get the direction the player is facing
	                    double distance = 3.0; // Adjust the distance from the player's eyes
	                    
	                    Location teleportLocation = player.getEyeLocation().add(direction.multiply(distance));
	                    entity.teleport(teleportLocation);
	                    
	                    player.sendMessage(entity.getLocation().toString());
	                    
	                    new BukkitRunnable() {
		                	 double t = 0;
		                	 
		                	 public void run() {
		                		 t++;
		                		 
		                		 
		                		 
		                		 if(t >= 60) {
		                			 this.cancel();
		     	                	 entity.setAI(true);
		                			 double speed = 2.0; // Adjust the desired speed of the entity's flight
		                             Vector direction = player.getLocation().getDirection().normalize().multiply(speed);
		                             entity.setGravity(true);
		                             entity.setVelocity(direction);
		                		 }
		                		 
		                	 }
		                	 
		                 }.runTaskTimer(Main.getInstance(), 0, 1);
	                }
	            }
	        }
	    }

	}
	

	
	
	
	

}
