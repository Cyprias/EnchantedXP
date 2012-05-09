package com.cyprias.enchantedxp;

import java.io.File;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantedXP extends JavaPlugin {
	public static String chatPrefix = "§f[§aeXP§f] ";
	public Events events;
	private String stPluginEnabled = "§f%s §7v§f%s §7is enabled.";
	public void onEnable() {
		this.events = new Events(this);
		getServer().getPluginManager().registerEvents(this.events, this);
		info(String.format(this.stPluginEnabled, getDescription().getName(), getDescription().getVersion()));
	}

	public void info(String msg) {
		getServer().getConsoleSender().sendMessage(chatPrefix + msg);
	}
}