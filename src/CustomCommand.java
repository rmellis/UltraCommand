package com.kierdavis.ultracommand;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.ChatColor;

public class CustomCommand {
    private static Pattern ARG_PATTERN = Pattern.compile("\\$(\\d+)");
    
    private List<String> text;
    private List<String> chat;
    private List<String> playerCommands;
    private List<String> consoleCommands;
    
    public CustomCommand() {
        text = null;
        chat = null;
        playerCommands = null;
        consoleCommands = null;
    }
    
    public void setText(List<String> l) {
        text = l;
    }
    
    public void setChat(List<String> l) {
        chat = l;
    }
    
    public void setPlayerCommands(List<String> l) {
        playerCommands = l;
    }
    
    public void setConsoleCommands(List<String> l) {
        consoleCommands = l;
    }
    
    public void addText(String s) {
        if (text == null) text = new ArrayList<String>();
        text.add(s);
    }
    
    public void addChat(String s) {
        if (chat == null) chat = new ArrayList<String>();
        chat.add(s);
    }
    
    public void addPlayerCommand(String s) {
        if (playerCommands == null) playerCommands = new ArrayList<String>();
        playerCommands.add(s);
    }
    
    public void addConsoleCommand(String s) {
        if (consoleCommands == null) consoleCommands = new ArrayList<String>();
        consoleCommands.add(s);
    }
    
    public void execute(Player player, String[] args) {
        if (!checkArgs(player, text, args.length)) return;
        if (!checkArgs(player, chat, args.length)) return;
        if (!checkArgs(player, playerCommands, args.length)) return;
        if (!checkArgs(player, consoleCommands, args.length)) return;
        
        execText(player, args);
        execChat(player, args);
        execPlayerCommands(player, args);
        execConsoleCommands(player, args);
    }
    
    private void execText(Player player, String[] args) {
        if (text == null) return;
        
        for (int i = 0; i < text.size(); i++) {
            String s = text.get(i);
            s = doSubs(s, player, args);
            s = ChatColor.translateAlternateColorCodes('&', s);
            player.sendMessage(s);
        }
    }
    
    private void execChat(Player player, String[] args) {
        if (chat == null) return;
        
        for (int i = 0; i < chat.size(); i++) {
            String s = chat.get(i);
            s = doSubs(s, player, args);
            s = ChatColor.translateAlternateColorCodes('&', s);
            player.chat(s);
        }
    }
    
    private void execPlayerCommands(Player player, String[] args) {
        if (playerCommands == null) return;
        
        Server server = player.getServer();
        
        for (int i = 0; i < playerCommands.size(); i++) {
            String s = playerCommands.get(i);
            s = doSubs(s, player, args);
            if (s.startsWith("/")) s = s.substring(1);
            server.dispatchCommand(player, s);
        }
    }
    
    private void execConsoleCommands(Player player, String[] args) {
        if (consoleCommands == null) return;
        
        Server server = player.getServer();
        ConsoleCommandSender consoleSender = server.getConsoleSender();
        
        for (int i = 0; i < consoleCommands.size(); i++) {
            String s = consoleCommands.get(i);
            s = doSubs(s, player, args);
            if (s.startsWith("/")) s = s.substring(1);
            server.dispatchCommand(consoleSender, s);
        }
    }
    
    private static String doSubs(String s, Player player, String[] args) {
        StringBuilder argStr = new StringBuilder();
        
        for (int i = 0; i < args.length; i++) {
            s = s.replaceAll("\\$" + Integer.toString(i + 1), Matcher.quoteReplacement(args[i]));
            
            if (i > 0) argStr.append(" ");
            argStr.append(args[i]);
        }
        
        s = s.replaceAll("\\$p", Matcher.quoteReplacement(player.getName()));
        s = s.replaceAll("\\$d", Matcher.quoteReplacement(player.getDisplayName()));
        s = s.replaceAll("\\$a", Matcher.quoteReplacement(argStr.toString()));
        
        return s;
    }
    
    private boolean checkArgs(Player player, List<String> items, int numArgs) {
        // TODO: Implement efficiently.
        // Should find the maximum argument sub within items, and use it to
        // validate the number of arguments passed. If the validation fails,
        // false is returned after a message is send to the player, else true
        // is returned.
        return true;
    }
}
