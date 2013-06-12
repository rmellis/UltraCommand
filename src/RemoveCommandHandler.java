package com.kierdavis.ultracommand;

import com.kierdavis.flex.FlexCommandContext;
import com.kierdavis.flex.FlexHandler;
import org.bukkit.ChatColor;

public class RemoveCommandHandler {
    private UltraCommand plugin;
    
    public RemoveCommandHandler(UltraCommand plugin_) {
        plugin = plugin_;
    }
    
    @FlexHandler(value="ultracommand remove", permission="ultracommand.configure")
    public boolean doRemove(FlexCommandContext ctx) {
        String name = getArg(0);
        boolean success = plugin.removeCustomCommand(name);
        
        if (success) {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " removed.");
        }
        else {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
    
    @FlexHandler(value="ultracommand remove text", permission="ultracommand.configure")
    public boolean doRemoveText(FlexCommandContext ctx) {
        String name = getArg(0);
        boolean success = plugin.clearText(name);
        
        if (success) {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Text cleared for command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
    
    @FlexHandler(value="ultracommand remove chat", permission="ultracommand.configure")
    public boolean doRemoveChat(FlexCommandContext ctx) {
        String name = getArg(0);
        boolean success = plugin.clearChat(name);
        
        if (success) {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Chat cleared for command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
    
    @FlexHandler(value="ultracommand remove pcmd", permission="ultracommand.configure")
    public boolean doRemovePcmd(FlexCommandContext ctx) {
        String name = getArg(0);
        boolean success = plugin.clearPlayerCommands(name);
        
        if (success) {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Player commands cleared for command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
    
    @FlexHandler(value="ultracommand remove ccmd", permission="ultracommand.configure")
    public boolean doRemoveCcmd(FlexCommandContext ctx) {
        String name = getArg(0);
        boolean success = plugin.clearConsoleCommands(name);
        
        if (success) {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Console commands cleared for command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
    
    @FlexHandler(value="ultracommand remove usage", permission="ultracommand.configure")
    public boolean doRemoveUsage(FlexCommandContext ctx) {
        String name = getArg(0);
        boolean success = plugin.setUsage(name, null);
        
        if (success) {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Usage cleared for command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
}
