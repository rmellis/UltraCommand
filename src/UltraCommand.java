package com.kierdavis.ultracommand;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraCommand extends JavaPlugin {
    private File commandsFile;
    private FileConfiguration commandsConfig;
    
    public void onEnable() {
        commandsFile = new File(getDataFolder(), "commands.yml");
        loadCustomCommands();
        
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        
        UltraCommandExecutor cmdExec = new UltraCommandExecutor(this);
        getCommand("ultracommand").setExecutor(cmdExec);
        getCommand("uc").setExecutor(cmdExec);
    }
    
    public void onDisable() {
        saveCustomCommands();
    }
    
    public void loadCustomCommands() {
        if (!commandsFile.exists()) {
            createCommandsFile();
            return;
        }
        
        commandsConfig = YamlConfiguration.loadConfiguration(commandsFile);
        getLogger().info("Loaded " + commandsFile.toString());
    }
    
    public void saveCustomCommands() {
        try {
            commandsConfig.save(commandsFile);
            getLogger().info("Saved " + commandsFile.toString());
        }
        catch (IOException e) {
            getLogger().severe("Could not save " + commandsFile.toString() + ": " + e.toString());
        }
    }
    
    public Set<String> getCustomCommands() {
        return getCommandsSection().getKeys(false);
    }
    
    public CustomCommand getCustomCommand(String name) {
        ConfigurationSection commandSection = getCommandsSection().getConfigurationSection(name.toLowerCase());
        if (commandSection == null) return null;
        
        CustomCommand cmd = new CustomCommand();
        List<String> l;
        
        l = commandSection.getStringList("text");
        if (l != null && l.size() > 0) cmd.setText(l);
        
        l = commandSection.getStringList("chat");
        if (l != null && l.size() > 0) cmd.setText(l);
        
        l = commandSection.getStringList("playerCommands");
        if (l != null && l.size() > 0) cmd.setPlayerCommands(l);
        
        l = commandSection.getStringList("consoleCommands");
        if (l != null && l.size() > 0) cmd.setConsoleCommands(l);
        
        return cmd;
    }
    
    public boolean addCustomCommand(String name) {
        ConfigurationSection commandsSection = getCommandsSection();
        name = name.toLowerCase();
        
        if (commandsSection.contains(name)) {
            return false;
        }
        
        ConfigurationSection commandSection = commandsSection.createSection(name);
        commandSection.set("text", new ArrayList<String>());
        commandSection.set("chat", new ArrayList<String>());
        commandSection.set("playerCommands", new ArrayList<String>());
        commandSection.set("consoleCommands", new ArrayList<String>());
        
        return true;
    }
    
    public boolean addText(String name, String s) {
        ConfigurationSection commandSection = getCommandsSection().getConfigurationSection(name.toLowerCase());
        if (commandSection == null) return false;
        
        commandSection.getStringList("text").add(s);
        return true;
    }
    
    public boolean addChat(String name, String s) {
        ConfigurationSection commandSection = getCommandsSection().getConfigurationSection(name.toLowerCase());
        if (commandSection == null) return false;
        
        commandSection.getStringList("chat").add(s);
        return true;
    }
    
    public boolean addPlayerCommand(String name, String s) {
        ConfigurationSection commandSection = getCommandsSection().getConfigurationSection(name.toLowerCase());
        if (commandSection == null) return false;
        
        commandSection.getStringList("playerCommands").add(s);
        return true;
    }
    
    public boolean addConsoleCommand(String name, String s) {
        ConfigurationSection commandSection = getCommandsSection().getConfigurationSection(name.toLowerCase());
        if (commandSection == null) return false;
        
        commandSection.getStringList("consoleCommands").add(s);
        return true;
    }
    
    private ConfigurationSection getCommandsSection() {
        ConfigurationSection commandsSection = commandsConfig.getConfigurationSection("commands");
        if (commandsSection == null) {
            commandsSection = commandsConfig.createSection("commands");
        }
        
        return commandsSection;
    }
    
    private void createCommandsFile() {
        File parent = commandsFile.getParentFile();
        
        try {
            if (!parent.exists()) {
                parent.mkdirs();
            }
            
            if (!commandsFile.exists()) {
                boolean b = commandsFile.createNewFile();
                if (b) {
                    getLogger().info("Created " + commandsFile.toString());
                }
            }
        }
        
        catch (IOException e) {
            getLogger().warning("Could not create " + commandsFile.toString() + ": " + e.toString());
        }
    }
}
