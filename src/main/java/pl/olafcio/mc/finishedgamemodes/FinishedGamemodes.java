package pl.olafcio.mc.finishedgamemodes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class FinishedGamemodes extends JavaPlugin implements CommandExecutor {
    FileConfiguration config = getConfig();
    Logger log = getLogger();
    String prefix = null;

    /// Terminal colors {
    // of characters
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    // of characters background
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    /// }

    private void send(CommandSender sender, String message) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.isOnline()) {
                sender.sendMessage(PlaceholderAPI.setPlaceholders(p, coloredString(prefix + message)));
            } else {
                log.warning("Plugin tried to send a message to offline player!");
            }
        } else {
            sender.sendMessage(ChatColor.stripColor(config.getString(prefix + message)));
        }
    }

    private String coloredString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();

        config.options().copyDefaults(true);
        saveConfig();

        prefix = coloredString(config.getString("prefix"));

        List<Map<?, ?>> cmds = config.getMapList("commands");
        for (Map<?, ?> cmd : cmds) {
            getCommand(String.valueOf(cmd)).setDescription(coloredString(config.getString("commands." + cmd + ".description")));
            if (config.getBoolean("enable-prefix-in-command-usages")) {
                getCommand(String.valueOf(cmd)).setUsage(prefix + coloredString(config.getString("commands." + cmd + ".usage")));
            } else {
                getCommand(String.valueOf(cmd)).setUsage(coloredString(config.getString("commands." + cmd + ".usage")));
            }
            getCommand(String.valueOf(cmd)).setPermissionMessage(coloredString(config.getString("no-permission-message")));
        }

        log.info(ANSI_GREEN + "\n" +
                "███████╗ ██╗ ███╗░░██╗ ██╗ ░██████╗ ██╗░░██╗ ███████╗ ██████╗░\n" +
                "██╔════╝ ██║ ████╗░██║ ██║ ██╔════╝ ██║░░██║ ██╔════╝ ██╔══██╗\n" +
                "█████╗░░ ██║ ██╔██╗██║ ██║ ╚█████╗░ ███████║ █████╗░░ ██║░░██║\n" +
                "██╔══╝░░ ██║ ██║╚████║ ██║ ░╚═══██╗ ██╔══██║ ██╔══╝░░ ██║░░██║\n" +
                "██║░░░░░ ██║ ██║░╚███║ ██║ ██████╔╝ ██║░░██║ ███████╗ ██████╔╝\n" +
                "╚═╝░░░░░ ╚═╝ ╚═╝░░╚══╝ ╚═╝ ╚═════╝░ ╚═╝░░╚═╝ ╚══════╝ ╚═════╝░\n" +
                "\n" +
                "░██████╗░ ░█████╗░ ███╗░░░███╗ ███████╗ ███╗░░░███╗ ░█████╗░ ██████╗░ ███████╗ ░██████╗\n" +
                "██╔════╝░ ██╔══██╗ ████╗░████║ ██╔════╝ ████╗░████║ ██╔══██╗ ██╔══██╗ ██╔════╝ ██╔════╝\n" +
                "██║░███╗░ ███████║ ██╔████╔██║ █████╗░░ ██╔████╔██║ ██║░░██║ ██║░░██║ █████╗░░ ╚█████╗░\n" +
                "██║░░╚██╗ ██╔══██║ ██║╚██╔╝██║ ██╔══╝░░ ██║╚██╔╝██║ ██║░░██║ ██║░░██║ ██╔══╝░░ ░╚═══██╗\n" +
                "╚██████╔╝ ██║░░██║ ██║░╚═╝░██║ ███████╗ ██║░╚═╝░██║ ╚█████╔╝ ██████╔╝ ███████╗ ██████╔╝\n" +
                "░╚═════╝ ░╚═╝░░╚═╝ ╚═╝░░░░░╚═╝ ╚══════╝ ╚═╝░░░░░╚═╝ ░╚════╝░ ╚═════╝░ ╚══════╝ ╚═════╝ \n" +
                ANSI_BLUE + "Enabled!" + ANSI_RESET);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        super.onDisable();
    }

    private String getConfig(CommandSender sender, String translation) {
        String message = "";
        if (sender instanceof Player) {
            message = PlaceholderAPI.setPlaceholders((Player) sender, coloredString(config.getString(translation)));
        } else if (sender instanceof BlockCommandSender) {
            message = coloredString(config.getString(translation));
        } else {
            message = config.getString(translation);
        }
        return message;
    }

    private String getConfigString(String translation) {
        return coloredString(config.getString(translation));
    }

    private boolean hasPermission(CommandSender sender, String permission) {
        if (sender.hasPermission(config.getString("permissions." + permission))) {
            return true;
        } else {
            send(sender, getConfig(sender, "no-permission-message"));
            return false;
        }
    }

    private boolean CommandExecutor(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("finishedgamemodes") || label.equalsIgnoreCase("finishedgm") || label.equalsIgnoreCase("fgm")) {
            if (hasPermission(sender, "main-command")) {
                if (args.length == 0) {
                    send(sender, "§0§l--- §eFinishedGamemodes §0§l---§r");
                    send(sender, "§6/fgm reload §8- §eReload the plugin");
                    send(sender, "§6/fgm config §8- §eCustomize the plugin configuration in-game");
                } else if (args.length > 0) {
                    if (args[0] == "config") {
                        if (!hasPermission(sender, "customize-config-ingame")) {
                            return true;
                        }
                        send(sender, "§cThis command is not made yet, please use config.yml for customizing the plugin.");
                    } else if (args[0] == "reload") {
                        if (!hasPermission(sender, "reload-plugin")) {
                            return true;
                        }
                        reloadConfig();
                        send(sender, getConfig(sender, "commands.finishedgamemodes.translations.reloaded"));
                    }
                }
            }
        } else if (label.equalsIgnoreCase("gamemode") || label.equalsIgnoreCase("gm")) {
            if (!hasPermission(sender, "gamemode-command")) {
                return true;
            }
            if (args.length != 1 && args.length != 2) {
                return false;
            }
            Player p;
            if (args.length == 1) {
                p = Bukkit.getPlayer(args[0]);
                if (!p.isOnline()) {
                    send(sender, getConfig(sender, "player-not-online"));
                    return true;
                }
            } else {
                p = (Player) sender;
            }
            if (p == sender) {
                if (!hasPermission(sender, "set-self-gamemode")) {
                    return true;
                }
            } else {
                if (!hasPermission(sender, "set-other-gamemode")) {
                    return true;
                }
            }
            String gamemode = args[0].toLowerCase();
            if (gamemode.startsWith("0") || gamemode.startsWith("s") && !gamemode.startsWith("sp")) {
                if (!hasPermission(sender, "set-gamemode-survival")) {
                    return true;
                }
                p.setGameMode(GameMode.SURVIVAL);
                gamemode = getConfigString("commands.gamemode.translations.gamemodes.survival");
            } else if (gamemode.startsWith("2") || gamemode.startsWith("a")) {
                if (!hasPermission(sender, "set-gamemode-adventure")) {
                    return true;
                }
                p.setGameMode(GameMode.ADVENTURE);
                gamemode = getConfigString("commands.gamemode.translations.gamemodes.adventure");
            } else if (gamemode.startsWith("1") || gamemode.startsWith("c")) {
                if (!hasPermission(sender, "set-gamemode-creative")) {
                    return true;
                }
                p.setGameMode(GameMode.CREATIVE);
                gamemode = getConfigString("commands.gamemode.translations.gamemodes.creative");
            } else if (gamemode.startsWith("3") || gamemode.startsWith("sp")) {
                if (!hasPermission(sender, "set-gamemode-spectator")) {
                    return true;
                }
                p.setGameMode(GameMode.SPECTATOR);
                gamemode = getConfigString("commands.gamemode.translations.gamemodes.spectator");
            } else {
                send(sender, getConfig(p, "commands.gamemode.translations.unknown-gamemode").replace("[gamemode]", gamemode));
                return true;
            }
            if (p == sender) {
                send(sender, getConfig(p, "gamemode-set-own").replace("[gamemode]", gamemode));
            } else {
                send(sender, getConfig(p, "gamemode-set-other").replace("[player]", p.getName()).replace("[gamemode]", gamemode));
            }
        } else if (label.equalsIgnoreCase("gmt") || label.equalsIgnoreCase("gamemodetoggle") || label.equalsIgnoreCase("gmtoggle")) {
            if (!hasPermission(sender, "toggle-gamemode-command")) {
                return true;
            }
            if (args.length > 1) {
                return false;
            }
            Player p;
            if (args.length == 1) {
                p = Bukkit.getPlayer(args[0]);
                if (!p.isOnline()) {
                    send(sender, getConfig(sender, "player-not-online"));
                    return true;
                }
            } else {
                p = (Player) sender;
            }
            if (p == sender) {
                if (!hasPermission(sender, "set-self-gamemode")) {
                    return true;
                }
            } else {
                if (!hasPermission(sender, "set-other-gamemode")) {
                    return true;
                }
            }
            if (p.getGameMode() == GameMode.SURVIVAL) {
                if (!hasPermission(sender, "set-gamemode-creative")) {
                    return true;
                }
                p.setGameMode(GameMode.CREATIVE);
            } else if (p.getGameMode() == GameMode.CREATIVE) {
                if (!hasPermission(sender, "set-gamemode-adventure")) {
                    return true;
                }
                p.setGameMode(GameMode.ADVENTURE);
            } else if (p.getGameMode() == GameMode.ADVENTURE) {
                if (!hasPermission(sender, "set-gamemode-survival")) {
                    return true;
                }
                p.setGameMode(GameMode.SURVIVAL);
            }
            String _gamemode = null;
            GameMode gamemode = p.getGameMode();
            if (gamemode == GameMode.SURVIVAL) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.survival");
            } else if (gamemode == GameMode.ADVENTURE) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.adventure");
            } else if (gamemode == GameMode.CREATIVE) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.creative");
            } else if (gamemode == GameMode.SPECTATOR) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.spectator");
            }
            if (p == sender) {
                send(sender, getConfig(p, "gamemode-set-own").replace("[gamemode]", _gamemode));
            } else {
                send(sender, getConfig(p, "gamemode-set-other").replace("[player]", p.getName()).replace("[gamemode]", _gamemode));
            }
        } else if (label.equalsIgnoreCase("gms") || label.equalsIgnoreCase("survival") || label.equalsIgnoreCase("surv") || label.equalsIgnoreCase("gm0") || label.equalsIgnoreCase("gamemodesurvival") || label.equalsIgnoreCase("gamemode0")) {
            if (args.length > 1) {
                return false;
            }
            Player p;
            if (args.length == 1) {
                p = Bukkit.getPlayer(args[0]);
                if (!p.isOnline()) {
                    send(sender, getConfig(sender, "player-not-online"));
                    return true;
                }
            } else {
                p = (Player) sender;
            }
            if (p == sender) {
                if (!hasPermission(sender, "set-self-gamemode")) {
                    return true;
                }
            } else {
                if (!hasPermission(sender, "set-other-gamemode")) {
                    return true;
                }
            }
            if (!hasPermission(sender, "set-gamemode-survival")) {
                return true;
            }
            p.setGameMode(GameMode.SURVIVAL);
            String _gamemode = null;
            GameMode gamemode = p.getGameMode();
            if (gamemode == GameMode.SURVIVAL) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.survival");
            } else if (gamemode == GameMode.ADVENTURE) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.adventure");
            } else if (gamemode == GameMode.CREATIVE) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.creative");
            } else if (gamemode == GameMode.SPECTATOR) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.spectator");
            }
            if (p == sender) {
                send(sender, getConfig(p, "gamemode-set-own").replace("[gamemode]", _gamemode));
            } else {
                send(sender, getConfig(p, "gamemode-set-other").replace("[player]", p.getName()).replace("[gamemode]", _gamemode));
            }
        } else if (label.equalsIgnoreCase("gma") || label.equalsIgnoreCase("adventure") || label.equalsIgnoreCase("advent") || label.equalsIgnoreCase("gm2") || label.equalsIgnoreCase("gamemodeadventure") || label.equalsIgnoreCase("gamemode2")) {
            if (args.length > 1) {
                return false;
            }
            Player p;
            if (args.length == 1) {
                p = Bukkit.getPlayer(args[0]);
                if (!p.isOnline()) {
                    send(sender, getConfig(sender, "player-not-online"));
                    return true;
                }
            } else {
                p = (Player) sender;
            }
            if (p == sender) {
                if (!hasPermission(sender, "set-self-gamemode")) {
                    return true;
                }
            } else {
                if (!hasPermission(sender, "set-other-gamemode")) {
                    return true;
                }
            }
            if (!hasPermission(sender, "set-gamemode-adventure")) {
                return true;
            }
            p.setGameMode(GameMode.ADVENTURE);
            String _gamemode = null;
            GameMode gamemode = p.getGameMode();
            if (gamemode == GameMode.SURVIVAL) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.survival");
            } else if (gamemode == GameMode.ADVENTURE) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.adventure");
            } else if (gamemode == GameMode.CREATIVE) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.creative");
            } else if (gamemode == GameMode.SPECTATOR) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.spectator");
            }
            if (p == sender) {
                send(sender, getConfig(p, "gamemode-set-own").replace("[gamemode]", _gamemode));
            } else {
                send(sender, getConfig(p, "gamemode-set-other").replace("[player]", p.getName()).replace("[gamemode]", _gamemode));
            }
        } else if (label.equalsIgnoreCase("gmc") || label.equalsIgnoreCase("creative") || label.equalsIgnoreCase("create") || label.equalsIgnoreCase("gm1") || label.equalsIgnoreCase("gamemodecreative") || label.equalsIgnoreCase("gamemode1")) {
            if (args.length > 1) {
                return false;
            }
            Player p;
            if (args.length == 1) {
                p = Bukkit.getPlayer(args[0]);
                if (!p.isOnline()) {
                    send(sender, getConfig(sender, "player-not-online"));
                    return true;
                }
            } else {
                p = (Player) sender;
            }
            if (p == sender) {
                if (!hasPermission(sender, "set-self-gamemode")) {
                    return true;
                }
            } else {
                if (!hasPermission(sender, "set-other-gamemode")) {
                    return true;
                }
            }
            if (!hasPermission(sender, "set-gamemode-creative")) {
                return true;
            }
            p.setGameMode(GameMode.CREATIVE);
            String _gamemode = null;
            GameMode gamemode = p.getGameMode();
            if (gamemode == GameMode.SURVIVAL) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.survival");
            } else if (gamemode == GameMode.ADVENTURE) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.adventure");
            } else if (gamemode == GameMode.CREATIVE) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.creative");
            } else if (gamemode == GameMode.SPECTATOR) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.spectator");
            }
            if (p == sender) {
                send(sender, getConfig(p, "gamemode-set-own").replace("[gamemode]", _gamemode));
            } else {
                send(sender, getConfig(p, "gamemode-set-other").replace("[player]", p.getName()).replace("[gamemode]", _gamemode));
            }
        } else if (label.equalsIgnoreCase("gmsp") || label.equalsIgnoreCase("spectator") || label.equalsIgnoreCase("spec") || label.equalsIgnoreCase("gm3") || label.equalsIgnoreCase("gamemodespectator") || label.equalsIgnoreCase("gamemode3")) {
            if (args.length > 1) {
                return false;
            }
            Player p;
            if (args.length == 1) {
                p = Bukkit.getPlayer(args[0]);
                if (!p.isOnline()) {
                    send(sender, getConfig(sender, "player-not-online"));
                    return true;
                }
            } else {
                p = (Player) sender;
            }
            if (p == sender) {
                if (!hasPermission(sender, "set-self-gamemode")) {
                    return true;
                }
            } else {
                if (!hasPermission(sender, "set-other-gamemode")) {
                    return true;
                }
            }
            if (!hasPermission(sender, "set-gamemode-spectator")) {
                return true;
            }
            p.setGameMode(GameMode.SPECTATOR);
            String _gamemode = null;
            GameMode gamemode = p.getGameMode();
            if (gamemode == GameMode.SURVIVAL) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.survival");
            } else if (gamemode == GameMode.ADVENTURE) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.adventure");
            } else if (gamemode == GameMode.CREATIVE) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.creative");
            } else if (gamemode == GameMode.SPECTATOR) {
                _gamemode = getConfigString("commands.gamemode.translations.gamemodes.spectator");
            }
            if (p == sender) {
                send(sender, getConfig(p, "gamemode-set-own").replace("[gamemode]", _gamemode));
            } else {
                send(sender, getConfig(p, "gamemode-set-other").replace("[player]", p.getName()).replace("[gamemode]", _gamemode));
            }
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean result = CommandExecutor(sender, command, label, args);
        if (!result) {
            send(sender, getConfig(sender, "commands."+command.getName()+".usage"));
        }
        return true;
    }
}
