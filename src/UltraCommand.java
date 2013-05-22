package com.kierdavis.ultracommand;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraCommand extends JavaPlugin {
    private Map<String, CustomCommand> commands;
    private File commandsFile;
    
    public void onEnable() {
        commands = new HashMap<String, CustomCommand>();
        commandsFile = new File(getDataFolder(), "commands.yml");
        
        loadCustomCommands();
        getLogger().info("Loaded " + Integer.toString(commands.size()) + " commands.");
        
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }
    
    public void onDisable() {
        
    }
    
    public CustomCommand getCustomCommand(String name) {
        return commands.get(name.toLowerCase());
    }
    
    public void addCustomCommand(String name, CustomCommand cmd) {
        commands.put(name.toLowerCase(), cmd);
    }
    
    public void removeCustomCommand(String name) {
        commands.remove(name);
    }
    
    public void createCommandsFile() {
        File parent = commandsFile.getParentFile();
        
        try {
            if (!parent.exists()) {
                parent.mkdirs();
            }
            
            if (!commandsFile.exists()) {
                boolean b = commandsFile.createNewFile();
                if (b) {
                    plugin.getLogger().info("Created " + commandsFile.toString());
                }
            }
        }
        
        catch (IOException e) {
            plugin.getLogger().warning("Could not create " + commandsFile.toString() + ": " + e.toString());
        }
    }
    
    public void loadCustomCommands() {
        if (!commandsFile.exists()) {
            createCommandsFile();
            return;
        }
        
        FileConfiguration commandsConfig = YamlConfiguration.loadConfiguration(commandsFile);
        ConfigurationSection commandsSection = commandsConfig.getConfigurationSection("commands");
        Iterator<String> keys = commandsSection.getKeys(false).iterator();
        
        commands.clear();
        
        while (keys.hasNext()) {
            String cmdName = (String) keys.next();
            ConfigurationSection commandSection = commandsSection.getConfigurationSection(cmdName);
            CustomCommand cmd = new CustomCommand();
            
            List<String> l;
            int i;
            
            l = commandSection.getStringList("text");
            if (l != null && l.size() > 0) {
                for (i = 0; i < l.size(); i++) {
                    cmd.addText(l.get(i));
                }
            }
            
            l = commandSection.getStringList("chat");
            if (l != null && l.size() > 0){
                for (i = 0; i < l.size(); i++) {
                    cmd.addChat(l.get(i));
                }
            }
            
            l = commandSection.getStringList("playerCommands");
            if (l != null && l.size() > 0){
                for (i = 0; i < l.size(); i++) {
                    cmd.addPlayerCommand(l.get(i));
                }
            }
            
            l = commandSection.getStringList("consoleCommands");
            if (l != null && l.size() > 0){
                for (i = 0; i < l.size(); i++) {
                    cmd.addConsoleCommand(l.get(i));
                }
            }
            
            addCustomCommand(cmdName, cmd);
        }
    }
}
