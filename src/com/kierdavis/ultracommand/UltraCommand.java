package com.kierdavis.ultracommand;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class UltraCommand extends JavaPlugin {
  private File commandsFile;
  
  private FileConfiguration commandsConfig;
  
  private BukkitTask saveCommandsTask;
  
  private boolean dirty;
  
  public void onEnable() {
    this.commandsFile = new File(getDataFolder(), "commands.yml");
    loadCustomCommands();
    getServer().getPluginManager().registerEvents(new PlayerListener(this), (Plugin)this);
    UltraCommandExecutor ultraCommandExecutor = new UltraCommandExecutor(this);
    getCommand("ultracommand").setExecutor(ultraCommandExecutor);
    getCommand("uc").setExecutor(ultraCommandExecutor);
    this.dirty = false;
    this.saveCommandsTask = (new SaveCommandsTask(this)).runTaskTimer((Plugin)this, 1200L, 1200L);
  }
  
  public void onDisable() {
    this.saveCommandsTask.cancel();
    saveCustomCommands();
  }
  
  public void loadCustomCommands() {
    if (!this.commandsFile.exists())
      createCommandsFile(); 
    this.commandsConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.commandsFile);
    getLogger().info("Loaded " + this.commandsFile.toString());
  }
  
  public void saveCustomCommands() {
    try {
      this.commandsConfig.save(this.commandsFile);
      this.dirty = false;
      getLogger().info("Saved " + this.commandsFile.toString());
    } catch (IOException iOException) {
      getLogger().severe("Could not save " + this.commandsFile.toString() + ": " + iOException.toString());
    } 
  }
  
  public Set<String> getCustomCommands() {
    return getCommandsSection().getKeys(false);
  }
  
  public CustomCommand getCustomCommand(String paramString) {
    ConfigurationSection configurationSection = getCommandSection(paramString);
    if (configurationSection == null)
      return null; 
    CustomCommand customCommand = new CustomCommand();
    List<String> list = configurationSection.getStringList("text");
    if (list != null && list.size() > 0)
      customCommand.setText(list); 
    list = configurationSection.getStringList("chat");
    if (list != null && list.size() > 0)
      customCommand.setText(list); 
    list = configurationSection.getStringList("playerCommands");
    if (list != null && list.size() > 0)
      customCommand.setPlayerCommands(list); 
    list = configurationSection.getStringList("consoleCommands");
    if (list != null && list.size() > 0)
      customCommand.setConsoleCommands(list); 
    return customCommand;
  }
  
  public boolean addCustomCommand(String paramString) {
    ConfigurationSection configurationSection1 = getCommandsSection();
    paramString = paramString.toLowerCase();
    if (configurationSection1.contains(paramString))
      return false; 
    ConfigurationSection configurationSection2 = configurationSection1.createSection(paramString);
    configurationSection2.set("text", new ArrayList());
    configurationSection2.set("chat", new ArrayList());
    configurationSection2.set("playerCommands", new ArrayList());
    configurationSection2.set("consoleCommands", new ArrayList());
    this.dirty = true;
    return true;
  }
  
  public boolean hasCustomCommand(String paramString) {
    return (getCommandSection(paramString) != null);
  }
  
  public boolean removeCustomCommand(String paramString) {
    ConfigurationSection configurationSection = getCommandsSection();
    paramString = paramString.toLowerCase();
    if (!configurationSection.contains(paramString))
      return false; 
    configurationSection.set(paramString, null);
    this.dirty = true;
    return true;
  }
  
  public boolean addText(String paramString1, String paramString2) {
    ConfigurationSection configurationSection = getCommandSection(paramString1);
    if (configurationSection == null)
      return false; 
    List<String> list = configurationSection.getStringList("text");
    list.add(paramString2);
    configurationSection.set("text", list);
    this.dirty = true;
    return true;
  }
  
  public boolean addChat(String paramString1, String paramString2) {
    ConfigurationSection configurationSection = getCommandSection(paramString1);
    if (configurationSection == null)
      return false; 
    List<String> list = configurationSection.getStringList("chat");
    list.add(paramString2);
    configurationSection.set("chat", list);
    this.dirty = true;
    return true;
  }
  
  public boolean addPlayerCommand(String paramString1, String paramString2) {
    ConfigurationSection configurationSection = getCommandSection(paramString1);
    if (configurationSection == null)
      return false; 
    List<String> list = configurationSection.getStringList("playerCommands");
    list.add(paramString2);
    configurationSection.set("playerCommands", list);
    this.dirty = true;
    return true;
  }
  
  public boolean addConsoleCommand(String paramString1, String paramString2) {
    ConfigurationSection configurationSection = getCommandSection(paramString1);
    if (configurationSection == null)
      return false; 
    List<String> list = configurationSection.getStringList("consoleCommands");
    list.add(paramString2);
    configurationSection.set("consoleCommands", list);
    this.dirty = true;
    return true;
  }
  
  public List<String> getText(String paramString) {
    ConfigurationSection configurationSection = getCommandSection(paramString);
    if (configurationSection == null)
      return null; 
    return configurationSection.getStringList("text");
  }
  
  public List<String> getChat(String paramString) {
    ConfigurationSection configurationSection = getCommandSection(paramString);
    if (configurationSection == null)
      return null; 
    return configurationSection.getStringList("chat");
  }
  
  public List<String> getPlayerCommands(String paramString) {
    ConfigurationSection configurationSection = getCommandSection(paramString);
    if (configurationSection == null)
      return null; 
    return configurationSection.getStringList("playerCommands");
  }
  
  public List<String> getConsoleCommands(String paramString) {
    ConfigurationSection configurationSection = getCommandSection(paramString);
    if (configurationSection == null)
      return null; 
    return configurationSection.getStringList("consoleCommands");
  }
  
  public boolean clearText(String paramString) {
    ConfigurationSection configurationSection = getCommandSection(paramString);
    if (configurationSection == null)
      return false; 
    configurationSection.set("text", new ArrayList());
    this.dirty = true;
    return true;
  }
  
  public boolean clearChat(String paramString) {
    ConfigurationSection configurationSection = getCommandSection(paramString);
    if (configurationSection == null)
      return false; 
    configurationSection.set("chat", new ArrayList());
    this.dirty = true;
    return true;
  }
  
  public boolean clearPlayerCommands(String paramString) {
    ConfigurationSection configurationSection = getCommandSection(paramString);
    if (configurationSection == null)
      return false; 
    configurationSection.set("playerCommands", new ArrayList());
    this.dirty = true;
    return true;
  }
  
  public boolean clearConsoleCommands(String paramString) {
    ConfigurationSection configurationSection = getCommandSection(paramString);
    if (configurationSection == null)
      return false; 
    configurationSection.set("consoleCommands", new ArrayList());
    this.dirty = true;
    return true;
  }
  
  private ConfigurationSection getCommandsSection() {
    ConfigurationSection configurationSection = this.commandsConfig.getConfigurationSection("commands");
    if (configurationSection == null)
      configurationSection = this.commandsConfig.createSection("commands"); 
    return configurationSection;
  }
  
  private ConfigurationSection getCommandSection(String paramString) {
    return getCommandsSection().getConfigurationSection(paramString.toLowerCase());
  }
  
  private void createCommandsFile() {
    File file = this.commandsFile.getParentFile();
    try {
      if (!file.exists())
        file.mkdirs(); 
      if (!this.commandsFile.exists()) {
        boolean bool = this.commandsFile.createNewFile();
        if (bool)
          getLogger().info("Created " + this.commandsFile.toString()); 
      } 
    } catch (IOException iOException) {
      getLogger().warning("Could not create " + this.commandsFile.toString() + ": " + iOException.toString());
    } 
  }
  
  public boolean isDirty() {
    return this.dirty;
  }
  
  public boolean doCommand(Player paramPlayer, String[] paramArrayOfString) {
    String str = paramArrayOfString[0];
    CustomCommand customCommand = getCustomCommand(str);
    if (customCommand != null) {
      String str1 = "ultracommand.commands." + str;
      if (!paramPlayer.hasPermission(str1) && !paramPlayer.hasPermission("ultracommand.commands.*")) {
        paramPlayer.sendMessage(ChatColor.YELLOW + "You don't have permission for this command (" + str1 + ")");
        return true;
      } 
      customCommand.execute(paramPlayer, Arrays.<String>copyOfRange(paramArrayOfString, 1, paramArrayOfString.length));
      return true;
    } 
    return false;
  }
}
