package opattack;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main instance;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		this.getServer().getPluginManager().registerEvents(new FlameEmperor(), this);
		this.getServer().getPluginManager().registerEvents(new PointAndClick(), this);
		this.getServer().getPluginManager().registerEvents(new FireDragon(), this);

		
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static Main getInstance() {
        return instance;
    }

}
