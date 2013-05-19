package com.kierdavis.ultracommand;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraCommand extends JavaPlugin {
    public Map<String, CustomCommand> commands;
    
    public void onEnable() {
        commands = new HashMap<String, CustomCommand>();
        
        CustomCommand c = new CustomCommand();
        c.addText("&eSome text.");
        c.addChat("&dSome chat.");
        c.addCommand("/tell $p Some player comand.");
        c.addConsoleCommand("/say Some server command.");
        commands.put("test", c);
        
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }
    
    public void onDisable() {
        
    }
}
