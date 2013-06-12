package com.kierdavis.ultracommand;

import com.kierdavis.flex.FlexCommandContext;
import com.kierdavis.flex.FlexHandler;

public class AddCommandHandler {
    private UltraCommand plugin;
    
    public AddCommandHandler(UltraCommand plugin_) {
        plugin = plugin_;
    }
    
    @FlexHandler(value="ultracommand add", permission="ultracommand.configure")
    public boolean doAdd(FlexCommandContext ctx) {
        String name = ctx.getArg(0);
        boolean success = plugin.addCustomCommand(name);
        
        if (success) {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " created.");
        }
        else {
            ctx.getSender().sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " already exists!");
        }
        
        return success;
    }
    
    @FlexHandler(value="ultracommand add text", permission="ultracommand.configure")
    public boolean doAddText(FlexCommandContext ctx) {
        String name = ctx.getArg(0);
        if (!plugin.hasCustomCommand(name)) {
            if (!doAdd(ctx)) {
                return false;
            }
        }
        
        boolean success = plugin.addText(name, ctx.argsString(1));
        if (success) {
            sender.sendMessage(ChatColor.YELLOW + "Text added to command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
    
    @FlexHandler(value="ultracommand add chat", permission="ultracommand.configure")
    public boolean doAddChat(FlexCommandContext ctx) {
        String name = ctx.getArg(0);
        if (!plugin.hasCustomCommand(name)) {
            if (!doAdd(ctx)) {
                return false;
            }
        }
        
        boolean success = plugin.addChat(name, ctx.argsString(1));
        if (success) {
            sender.sendMessage(ChatColor.YELLOW + "Chat added to command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
    
    @FlexHandler(value="ultracommand add pcmd", permission="ultracommand.configure")
    public boolean doAddPcmd(FlexCommandContext ctx) {
        String name = ctx.getArg(0);
        if (!plugin.hasCustomCommand(name)) {
            if (!doAdd(ctx)) {
                return false;
            }
        }
        
        boolean success = plugin.addPlayerCommand(name, ctx.argsString(1));
        if (success) {
            sender.sendMessage(ChatColor.YELLOW + "Player command added to command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
    
    @FlexHandler(value="ultracommand add ccmd", permission="ultracommand.configure")
    public boolean doAddCcmd(FlexCommandContext ctx) {
        String name = ctx.getArg(0);
        if (!plugin.hasCustomCommand(name)) {
            if (!doAdd(ctx)) {
                return false;
            }
        }
        
        boolean success = plugin.addConsoleComand(name, ctx.argsString(1));
        if (success) {
            sender.sendMessage(ChatColor.YELLOW + "Console command added to command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
    
    @FlexHandler(value="ultracommand add usage", permission="ultracommand.configure")
    public boolean doAddUsage(FlexCommandContext ctx) {
        String name = ctx.getArg(0);
        if (!plugin.hasCustomCommand(name)) {
            if (!doAdd(ctx)) {
                return false;
            }
        }
        
        boolean success = plugin.setUsage(name, ctx.argsString(1));
        if (success) {
            sender.sendMessage(ChatColor.YELLOW + "Usage text added to command " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
        }
        
        return success;
    }
}
