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
    
    public UltraCommandExecutor(UltraCommand plugin_) {
        plugin = plugin_;
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("ultracommand.configure")) {
            sender.sendMessage(ChatColor.YELLOW + "You don't have permission for this command (ultracommand.configure)");
            return false;
        }
        
        if (args.length == 0) {
            printUsage(sender);
            return false;
        }
        
        String name = args[0];
        String[] remainingArgs = Arrays.copyOfRange(args, 1, args.length);
        
        if (name.equalsIgnoreCase("add")) return doAdd(sender, remainingArgs);
        if (name.equalsIgnoreCase("list")) return doList(sender, remainingArgs);
        if (name.equalsIgnoreCase("remove")) return doRemove(sender, remainingArgs);
        
        printUsage(sender);
        return false;
    }

    public void printUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Usage (" + ChatColor.RED + "<required> [optional]" + ChatColor.YELLOW + ":");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add " + ChatColor.RED + "<name>");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add text " + ChatColor.RED + "<name> <text>");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add chat " + ChatColor.RED + "<name> <chat>");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add pcmd " + ChatColor.RED + "<name> <command>");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add ccmd " + ChatColor.RED + "<name> <command>");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc list [name]");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc remove " + ChatColor.RED + "[text|chat|pcmd|ccmd] <name>");
    }
    
    private boolean doAdd(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.YELLOW + "Usage (" + ChatColor.RED + "<required> [optional]" + ChatColor.YELLOW + ":");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add " + ChatColor.RED + "<name>");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add text " + ChatColor.RED + "<name> <text>");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add chat " + ChatColor.RED + "<name> <chat>");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add pcmd " + ChatColor.RED + "<name> <command>");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add ccmd " + ChatColor.RED + "<name> <command>");
            return false;
        }
        
        if (args.length == 1) {
            String name = args[0];
            boolean success = plugin.addCustomCommand(name);
            
            if (success) {
                sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " created.");
            }
            else {
                sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " already exists!");
            }
            
            return success;
        }
        
        String subcmd = args[0];
        String name = args[1];
        StringBuilder restBuilder = new StringBuilder();
        
        for (int i = 2; i < args.length; i++) {
            if (i != 2) restBuilder.append(" ");
            restBuilder.append(args[i]);
        }
        
        String rest = restBuilder.toString();
        boolean success;
        String thing;
        
        if (subcmd.equalsIgnoreCase("text")) {
            success = plugin.addText(name, rest);
            thing = "Text";
        }
        else if (subcmd.equalsIgnoreCase("chat")) {
            success = plugin.addChat(name, rest);
            thing = "Chat";
        }
        else if (subcmd.equalsIgnoreCase("pcmd")) {
            success = plugin.addPlayerCommand(name, rest);
            thing = "Player command";
        }
        else if (subcmd.equalsIgnoreCase("ccmd")) {
            success = plugin.addConsoleCommand(name, rest);
            thing = "Console command";
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Usage (" + ChatColor.RED + "<required> [optional]" + ChatColor.YELLOW + ":");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add " + ChatColor.RED + "<name>");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add text " + ChatColor.RED + "<name> <text>");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add chat " + ChatColor.RED + "<name> <chat>");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add pcmd " + ChatColor.RED + "<name> <command>");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add ccmd " + ChatColor.RED + "<name> <command>");
            return false;
        }
            
        if (success) {
            sender.sendMessage(ChatColor.YELLOW + thing + " added to command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
    
    private boolean doList(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Set<String> cmds = plugin.getCustomCommands();
            
            if (cmds.size() == 0) {
                sender.sendMessage(ChatColor.YELLOW + "No defined commands.");
            }
            
            else {
                Iterator<String> it = cmds.iterator();
                sender.sendMessage(ChatColor.YELLOW + "Defined commands:");
                
                while (it.hasNext()) {
                    String name = (String) it.next();
                    sender.sendMessage("  " + ChatColor.YELLOW + "- " + ChatColor.GREEN + name);
                }
            }
            
            return true;
        }
        
        String name = args[0];
        
        if (!plugin.hasCustomCommand(name)) {
            sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
            return false;
        }
        
        List<String> text = plugin.getText(name);
        List<String> chat = plugin.getChat(name);
        List<String> playerCommands = plugin.getPlayerCommands(name);
        List<String> consoleCommands = plugin.getConsoleCommands(name);
        
        sender.sendMessage(ChatColor.GREEN + name + ChatColor.YELLOW + ":");
        
        if (text == null || text.size() == 0) {
            sender.sendMessage("  " + ChatColor.YELLOW + "No text for this command.");
        }
        else {
            sender.sendMessage("  " + ChatColor.YELLOW + "Text:");
            for (int i = 0; i < text.size(); i++) {
                sender.sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', text.get(i)));
            }
        }
        
        if (chat == null || chat.size() == 0) {
            sender.sendMessage("  " + ChatColor.YELLOW + "No chat for this command.");
        }
        else {
            sender.sendMessage("  " + ChatColor.YELLOW + "Chat:");
            for (int i = 0; i < chat.size(); i++) {
                sender.sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', chat.get(i)));
            }
        }
        
        if (playerCommands == null || playerCommands.size() == 0) {
            sender.sendMessage("  " + ChatColor.YELLOW + "No player commands for this command.");
        }
        else {
            sender.sendMessage("  " + ChatColor.YELLOW + "Player commands:");
            for (int i = 0; i < playerCommands.size(); i++) {
                sender.sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.GREEN + playerCommands.get(i));
            }
        }
        
        if (consoleCommands == null || consoleCommands.size() == 0) {
            sender.sendMessage("  " + ChatColor.YELLOW + "No console commands for this command.");
        }
        else {
            sender.sendMessage("  " + ChatColor.YELLOW + "Console commands:");
            for (int i = 0; i < consoleCommands.size(); i++) {
                sender.sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.GREEN + consoleCommands.get(i));
            }
        }
        
        return true;
    }
    
    private boolean doRemove(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.YELLOW + "Usage (" + ChatColor.RED + "<required> [optional]" + ChatColor.YELLOW + ":");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc remove " + ChatColor.RED + "[text|chat|pcmd|ccmd] <name>");
            return false;
        }
        
        if (args.length == 1) {
            String name = args[0];
            boolean success = plugin.removeCustomCommand(name);
            
            if (success) {
                sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " removed.");
            }
            else {
                sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
            }
            
            return success;
        }
        
        String subcmd = args[0];
        String name = args[1];
        
        boolean success;
        String things;
        
        if (subcmd.equalsIgnoreCase("text")) {
            success = plugin.clearText(name);
            things = "Text";
        }
        else if (subcmd.equalsIgnoreCase("chat")) {
            success = plugin.clearChat(name);
            things = "Chat";
        }
        else if (subcmd.equalsIgnoreCase("pcmd")) {
            success = plugin.clearPlayerCommands(name);
            things = "Player commands";
        }
        else if (subcmd.equalsIgnoreCase("ccmd")) {
            success = plugin.clearConsoleCommands(name);
            things = "Console commands";
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Usage (" + ChatColor.RED + "<required> [optional]" + ChatColor.YELLOW + ":");
            sender.sendMessage("  " + ChatColor.DARK_RED + "/uc remove " + ChatColor.RED + "[text|chat|pcmd|ccmd] <name>");
            return false;
        }
            
        if (success) {
            sender.sendMessage(ChatColor.YELLOW + things + " cleared for command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
}
