package me.salamijack.HookShot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;



public class Main extends JavaPlugin{

	private static final Logger log = Logger.getLogger("Minecraft");
	 private final HookShotPlayerListener playerListener = new HookShotPlayerListener(this);
	    private final HookShotBlockListener blockListener = new HookShotBlockListener(this);
	    private final HookShotEntityListener entityListener = new HookShotEntityListener(this);
	    private final HookShotVehicleListener vehicleListener = new HookShotVehicleListener(this);
	    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	    public final HashMap<Player, ArrayList<Block>> HookShotUsers = new HashMap<Player, ArrayList<Block>>();
	    public static PermissionHandler permissionHandler;
	    private Permissions permissions;
	    public boolean hookCost;
	    public int hookItem;
	    public int pullItem;
	    public int pullCost;
	    

	    public void onDisable() {
	        System.out.println("Goodbye world!");
	    }

	    public void onEnable() {
	        PluginManager pm = getServer().getPluginManager();
	        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.BLOCK_PHYSICS, blockListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.BLOCK_CANBUILD, blockListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.PLAYER_TOGGLE_SNEAK, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.PLAYER_ITEM_HELD, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Highest, this);	  
	        pm.registerEvent(Event.Type.VEHICLE_EXIT, vehicleListener, Priority.High, this);
	        pm.registerEvent(Event.Type.PROJECTILE_HIT, entityListener, Priority.High, this);
	        
	        HookShotPermissions.initialize(getServer());
	        setupPermissions();
	        PluginDescriptionFile pdfFile = this.getDescription();
	        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	        
	        Configuration config = getConfiguration();
	        config.load();
	        hookCost = config.getBoolean(" hook-cost", true);
	        hookItem = config.getInt(" hook-item", 262);
	        pullItem = config.getInt(" pull-item", 287);
	        pullCost = config.getInt(" pull-costitem", 287);
	        config.save();
	    }

	    public boolean isDebugging(final Player player) {
	        if (debugees.containsKey(player)) {
	            return debugees.get(player);
	        } else {
	            return false;
	        }
	    }

	    public void setDebugging(final Player player, final boolean value) {
	        debugees.put(player, value);
	    }
	    
	    
	    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	    {
	    	boolean result = false;
	    	if(commandLabel.equalsIgnoreCase("SalamiTest"))
	    	{
	    		toggleSalamiTest((Player) sender);
	    		result = true;
	    	}
	    	return result;
	    	
	    }
	    
	    public void toggleSalamiTest(Player player)
	    {
	    	if(enabled(player))
	    	{
	    		this.HookShotUsers.remove(player);
	    		player.sendMessage("Salami Test Disabled");
	    		
	    	}
	    	else
	    	{
	    		this.HookShotUsers.put(player, null);
	    		player.sendMessage("Salami Test Enabled");
	    	}
	    }
	    
	    public boolean enabled(Player player)
	    {
	    	return this.HookShotUsers.containsKey(player);
	    }
	    
	    public void setupPermissions()
	    {
	    	Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
	    	if(this.permissionHandler == null)
	    	{
	    		if (permissionsPlugin != null)
	    		{
	    			this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
	    		}
	    		else {
	    		}
	    	}
	    }
	    
	    
	    public Permissions getPermissions() {
	    	return permissions;
	    	}
	    	
	    	
	}

	
