package com.kierdavis.ultracommand;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.ChatColor;

public class CustomCommand {
    private static Pattern SUB_PATTERN = Pattern.compile("\\$([aAdDpP]|\\d+)");
    
    private Logger logger;
    private Player player;
    private String[] args;
    private List<String> text;
    private List<String> chat;
    private List<String> playerCommands;
    private List<String> consoleCommands;
    private int reqArgs;
    
    public CustomCommand(Logger logger_) {
        logger = logger_;
        text = null;
        chat = null;
        playerCommands = null;
        consoleCommands = null;
        reqArgs = 0;
    }
    
    public void setPlayer(Player player_) {
        player = player;
    }
    
    public void setArgs(String[] args_) {
        args = args_;
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
    
    public void execute() {
        doSubs(text);
        doSubs(chat);
        doSubs(playerCommands);
        doSubs(consoleCommands);
        
        if (args.length < reqArgs) {
            player.sendMessage(ChatColor.YELLOW + "This command requires at least " + Integer.toString(reqArgs) + " arguments.");
            return;
        }
        
        execText();
        execChat();
        execPlayerCommands();
        execConsoleCommands();
    }
    
    private void execText() {
        if (text == null) return;
        
        for (int i = 0; i < text.size(); i++) {
            String s = text.get(i);
            s = ChatColor.translateAlternateColorCodes('&', s);
            player.sendMessage(s);
        }
    }
    
    private void execChat() {
        if (chat == null) return;
        
        for (int i = 0; i < chat.size(); i++) {
            String s = chat.get(i);
            s = ChatColor.translateAlternateColorCodes('&', s);
            player.chat(s);
        }
    }
    
    private void execPlayerCommands() {
        if (playerCommands == null) return;
        
        Server server = player.getServer();
        
        for (int i = 0; i < playerCommands.size(); i++) {
            String s = playerCommands.get(i);
            if (s.startsWith("/")) s = s.substring(1);
            logger.info("Command issued by " + player.getName() + " during processing: /" + s);
            server.dispatchCommand(player, s);
        }
    }
    
    private void execConsoleCommands() {
        if (consoleCommands == null) return;
        
        Server server = player.getServer();
        ConsoleCommandSender consoleSender = server.getConsoleSender();
        
        for (int i = 0; i < consoleCommands.size(); i++) {
            String s = consoleCommands.get(i);
            if (s.startsWith("/")) s = s.substring(1);
            logger.info("Command issued by console during processing: /" + s);
            server.dispatchCommand(consoleSender, s);
        }
    }
    
    private void doSubs(List<String> list) {
        if (list == null || list.size() == 0) return;
        
        StringBuffer buffer = new StringBuffer();
        
        for (int i = 0; i < args.length; i++) {
            if (i > 0) buffer.append(" ");
            buffer.append(args[i]);
        }
        
        String allArgs = buffer.toString();
        
        for (int i = 0; i < list.size(); i++) {
            // Re-use buffer
            buffer.setLength(0);
            
            Matcher matcher = SUB_PATTERN.matcher(list.get(i));
            while (matcher.find()) {
                String subType = matcher.group(0);
                String subValue = "";
                
                if (subType.equalsIgnoreCase("a")) {
                    subValue = allArgs;
                }
                else if (subType.equalsIgnoreCase("d")) {
                    subValue = player.getDisplayName();
                }
                else if (subType.equalsIgnoreCase("p")) {
                    subValue = player.getName();
                }
                else {
                    int argNum;
                    
                    try {
                        argNum = Integer.parseInt(subType);
                        subValue = args[argNum];
                    }
                    catch (NumberFormatException e) {} // This shouldn't happen as long as the regexp is valid.
                    catch (ArrayIndexOutOfBoundsException e) {} // Leave subValue blank, the required arguments check will notify the user.
                    
                    if (argNum > reqArgs) {
                        reqArgs = argNum;
                    }
                }
                
                matcher.appendReplacement(buffer, subValue);
            }
            
            matcher.appendTail(buffer);
            list.set(i, buffer.toString());
        }
    }
}
