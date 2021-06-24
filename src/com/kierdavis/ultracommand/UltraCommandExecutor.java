package com.kierdavis.ultracommand;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UltraCommandExecutor implements CommandExecutor {
  private UltraCommand plugin;
  
  public UltraCommandExecutor(UltraCommand paramUltraCommand) {
    this.plugin = paramUltraCommand;
  }
  
  public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString) {
    if (!paramCommandSender.hasPermission("ultracommand.configure")) {
      paramCommandSender.sendMessage(ChatColor.YELLOW + "You don't have permission for this command, If you believe you should ask an admin for the following permission. (ultracommand.configure)");
      return false;
    } 
    if (paramArrayOfString.length == 0) {
      printUsage(paramCommandSender);
      return false;
    } 
    String str = paramArrayOfString[0];
    String[] arrayOfString = Arrays.<String>copyOfRange(paramArrayOfString, 1, paramArrayOfString.length);
    if (str.equalsIgnoreCase("add"))
      return doAdd(paramCommandSender, arrayOfString); 
    if (str.equalsIgnoreCase("list"))
      return doList(paramCommandSender, arrayOfString); 
    if (str.equalsIgnoreCase("reload"))
      return doReload(paramCommandSender, arrayOfString); 
    if (str.equalsIgnoreCase("remove"))
      return doRemove(paramCommandSender, arrayOfString); 
    if (str.equalsIgnoreCase("save"))
      return doSave(paramCommandSender, arrayOfString); 
    printUsage(paramCommandSender);
    return false;
  }
  
  public void printUsage(CommandSender paramCommandSender) {
    paramCommandSender.sendMessage(ChatColor.YELLOW + "Usage (" + ChatColor.RED + "<required> [optional]" + ChatColor.YELLOW + ":");
    paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add " + ChatColor.RED + "<name>");
    paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add text " + ChatColor.RED + "<name> <text>");
    paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add chat " + ChatColor.RED + "<name> <chat>");
    paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add pcmd " + ChatColor.RED + "<name> <command>");
    paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add ccmd " + ChatColor.RED + "<name> <command>");
    paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc list [name]");
    paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc reload");
    paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc remove " + ChatColor.RED + "[text|chat|pcmd|ccmd] <name>");
    paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc save");
  }
  
  private boolean doAdd(CommandSender paramCommandSender, String[] paramArrayOfString) {
    boolean bool;
    String str4;
    if (paramArrayOfString.length < 1) {
      paramCommandSender.sendMessage(ChatColor.YELLOW + "Usage (" + ChatColor.RED + "<required> [optional]" + ChatColor.YELLOW + ":");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add " + ChatColor.RED + "<name>");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add text " + ChatColor.RED + "<name> <text>");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add chat " + ChatColor.RED + "<name> <chat>");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add pcmd " + ChatColor.RED + "<name> <command>");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add ccmd " + ChatColor.RED + "<name> <command>");
      return false;
    } 
    if (paramArrayOfString.length == 1) {
      String str = paramArrayOfString[0];
      boolean bool1 = this.plugin.addCustomCommand(str);
      if (bool1) {
        paramCommandSender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + str + ChatColor.YELLOW + " created.");
      } else {
        paramCommandSender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + str + ChatColor.YELLOW + " already exists.");
      } 
      return bool1;
    } 
    String str1 = paramArrayOfString[0];
    String str2 = paramArrayOfString[1];
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 2; b < paramArrayOfString.length; b++) {
      if (b != 2)
        stringBuilder.append(" "); 
      stringBuilder.append(paramArrayOfString[b]);
    } 
    String str3 = stringBuilder.toString();
    if (!this.plugin.hasCustomCommand(str2)) {
      bool = this.plugin.addCustomCommand(str2);
      if (bool) {
        paramCommandSender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + str2 + ChatColor.YELLOW + " created.");
      } else {
        paramCommandSender.sendMessage(ChatColor.RED + "Error: addCustomCommand returned false.");
        return false;
      } 
    } 
    if (str1.equalsIgnoreCase("text")) {
      bool = this.plugin.addText(str2, str3);
      str4 = "Text";
    } else if (str1.equalsIgnoreCase("chat")) {
      bool = this.plugin.addChat(str2, str3);
      str4 = "Chat";
    } else if (str1.equalsIgnoreCase("pcmd")) {
      bool = this.plugin.addPlayerCommand(str2, str3);
      str4 = "Player command";
    } else if (str1.equalsIgnoreCase("ccmd")) {
      bool = this.plugin.addConsoleCommand(str2, str3);
      str4 = "Console command";
    } else {
      paramCommandSender.sendMessage(ChatColor.YELLOW + "Usage (" + ChatColor.RED + "<required> [optional]" + ChatColor.YELLOW + ":");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add " + ChatColor.RED + "<name>");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add text " + ChatColor.RED + "<name> <text>");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add chat " + ChatColor.RED + "<name> <chat>");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add pcmd " + ChatColor.RED + "<name> <command>");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc add ccmd " + ChatColor.RED + "<name> <command>");
      return false;
    } 
    if (bool) {
      paramCommandSender.sendMessage(ChatColor.YELLOW + str4 + " added to command " + ChatColor.GREEN + str2 + ChatColor.YELLOW + ".");
    } else {
      paramCommandSender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + str2 + ChatColor.YELLOW + " does not exist.");
    } 
    return bool;
  }
  
  private boolean doList(CommandSender paramCommandSender, String[] paramArrayOfString) {
    if (paramArrayOfString.length == 0) {
      Set<String> set = this.plugin.getCustomCommands();
      if (set.size() == 0) {
        paramCommandSender.sendMessage(ChatColor.YELLOW + "No defined commands.");
      } else {
        Iterator<String> iterator = set.iterator();
        paramCommandSender.sendMessage(ChatColor.YELLOW + "Defined commands:");
        while (iterator.hasNext()) {
          String str1 = iterator.next();
          paramCommandSender.sendMessage("  " + ChatColor.YELLOW + "- " + ChatColor.GREEN + str1);
        } 
      } 
      return true;
    } 
    String str = paramArrayOfString[0];
    if (!this.plugin.hasCustomCommand(str)) {
      paramCommandSender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + str + ChatColor.YELLOW + " does not exist.");
      return false;
    } 
    List<String> list1 = this.plugin.getText(str);
    List<String> list2 = this.plugin.getChat(str);
    List<String> list3 = this.plugin.getPlayerCommands(str);
    List<String> list4 = this.plugin.getConsoleCommands(str);
    paramCommandSender.sendMessage(ChatColor.GREEN + str + ChatColor.YELLOW + ":");
    if (list1 == null || list1.size() == 0) {
      paramCommandSender.sendMessage("  " + ChatColor.YELLOW + "No text for this command.");
    } else {
      paramCommandSender.sendMessage("  " + ChatColor.YELLOW + "Text:");
      for (byte b = 0; b < list1.size(); b++)
        paramCommandSender.sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', list1.get(b))); 
    } 
    if (list2 == null || list2.size() == 0) {
      paramCommandSender.sendMessage("  " + ChatColor.YELLOW + "There is no chat defined for this command.");
    } else {
      paramCommandSender.sendMessage("  " + ChatColor.YELLOW + "Chat:");
      for (byte b = 0; b < list2.size(); b++)
        paramCommandSender.sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', list2.get(b))); 
    } 
    if (list3 == null || list3.size() == 0) {
      paramCommandSender.sendMessage("  " + ChatColor.YELLOW + "There are no player commands for this command.");
    } else {
      paramCommandSender.sendMessage("  " + ChatColor.YELLOW + "Player commands:");
      for (byte b = 0; b < list3.size(); b++)
        paramCommandSender.sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.GREEN + (String)list3.get(b)); 
    } 
    if (list4 == null || list4.size() == 0) {
      paramCommandSender.sendMessage("  " + ChatColor.YELLOW + "There are no console commands for this command.");
    } else {
      paramCommandSender.sendMessage("  " + ChatColor.YELLOW + "Console commands:");
      for (byte b = 0; b < list4.size(); b++)
        paramCommandSender.sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.GREEN + (String)list4.get(b)); 
    } 
    return true;
  }
  
  private boolean doReload(CommandSender paramCommandSender, String[] paramArrayOfString) {
    this.plugin.loadCustomCommands();
    paramCommandSender.sendMessage(ChatColor.YELLOW + "Commands configuration has been reloaded.");
    return true;
  }
  
  private boolean doRemove(CommandSender paramCommandSender, String[] paramArrayOfString) {
    boolean bool;
    String str3;
    if (paramArrayOfString.length < 1) {
      paramCommandSender.sendMessage(ChatColor.YELLOW + "Usage (" + ChatColor.RED + "<required> [optional]" + ChatColor.YELLOW + ":");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc remove " + ChatColor.RED + "[text|chat|pcmd|ccmd] <name>");
      return false;
    } 
    if (paramArrayOfString.length == 1) {
      String str = paramArrayOfString[0];
      boolean bool1 = this.plugin.removeCustomCommand(str);
      if (bool1) {
        paramCommandSender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + str + ChatColor.YELLOW + " removed.");
      } else {
        paramCommandSender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + str + ChatColor.YELLOW + " does not exist.");
      } 
      return bool1;
    } 
    String str1 = paramArrayOfString[0];
    String str2 = paramArrayOfString[1];
    if (str1.equalsIgnoreCase("text")) {
      bool = this.plugin.clearText(str2);
      str3 = "Text";
    } else if (str1.equalsIgnoreCase("chat")) {
      bool = this.plugin.clearChat(str2);
      str3 = "Chat";
    } else if (str1.equalsIgnoreCase("pcmd")) {
      bool = this.plugin.clearPlayerCommands(str2);
      str3 = "Player commands";
    } else if (str1.equalsIgnoreCase("ccmd")) {
      bool = this.plugin.clearConsoleCommands(str2);
      str3 = "Console commands";
    } else {
      paramCommandSender.sendMessage(ChatColor.YELLOW + "Usage (" + ChatColor.RED + "<required> [optional]" + ChatColor.YELLOW + ":");
      paramCommandSender.sendMessage("  " + ChatColor.DARK_RED + "/uc remove " + ChatColor.RED + "[text|chat|pcmd|ccmd] <name>");
      return false;
    } 
    if (bool) {
      paramCommandSender.sendMessage(ChatColor.YELLOW + str3 + " cleared for command " + ChatColor.GREEN + str2 + ChatColor.YELLOW + ".");
    } else {
      paramCommandSender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + str2 + ChatColor.YELLOW + " does not exist.");
    } 
    return bool;
  }
  
  private boolean doSave(CommandSender paramCommandSender, String[] paramArrayOfString) {
    this.plugin.saveCustomCommands();
    paramCommandSender.sendMessage(ChatColor.YELLOW + "Commands configuration saved successfully.");
    return true;
  }
}
