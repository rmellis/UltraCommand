package com.kierdavis.ultracommand;

import java.util.Arrays;
import java.util.Iterator;
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
        if (args.length == 0) {
            printUsage(sender);
            return false;
        }
        
        String name = args[0];
        String[] args = Arrays.copyOfRange(args, 1, args.length);
        
        if (name.equalsIgnoreCase("add")) return doAdd(sender, args);
        if (name.equalsIgnoreCase("list")) return doList(sender, args);
        
        printUsage(sender);
        return false;
    }

    public void printUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Usage (" + ChatColor.RED + "<required> [optional]" + ChatColor.YELLOW + ":");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add " + ChatColor.RED + "<name>");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add text " + ChatColor.RED + "<text>");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add chat " + ChatColor.RED + "<chat>");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add pcmd " + ChatColor.RED + "<command>");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc add ccmd " + ChatColor.RED + "<command>");
        sender.sendMessage("  " + ChatColor.DARK_RED + "/uc list");
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
            if (i == 2) restBuilder.append(" ");
            restBuilder.append(args[i]);
        }
        
        String rest = restBuilder.toString();
        boolean success;
        
        if (subcmd.equalsIgnoreCase("text")) {
            success = plugin.addText(name, rest);
        }
        else if (subcmd.equalsIgnoreCase("chat")) {
            success = plugin.addChat(name, rest);
        }
        else if (subcmd.equalsIgnoreCase("pcmd")) {
            success = plugin.addPlayerCommand(name, rest);
        }
        else if (subcmd.equalsIgnoreCase("ccmd")) {
            success = plugin.addConsoleCommand(name, rest);
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
            sender.sendMessage(ChatColor.YELLOW + "Item added to command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
    
    private boolean doList(CommandSender sender, String[] args) {
        Iterator<String> it = plugin.getCustomCommands().iterator();
        sender.sendMessage(ChatColor.YELLOW + "Defined commands:");
        
        while (it.hasNext()) {
            String name = (String) it.next();
            sender.sendMessage("  " + ChatColor.YELLOW + "- " + ChatColor.GREEN + name);
        }
        
        return true;
    }
}
