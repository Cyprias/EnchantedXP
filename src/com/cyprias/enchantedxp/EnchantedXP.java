package com.cyprias.enchantedxp;

import java.io.File;
import java.io.IOException;

import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantedXP extends JavaPlugin {
	public static String chatPrefix = "§f[§aeXP§f] ";
	public Events events;
	private String stPluginEnabled = "§f%s §7v§f%s §7is enabled.";
	public VersionChecker versionChecker;
	public Config config;
	
	public void onEnable() {
		config = new Config(this);
		this.events = new Events(this);
		getServer().getPluginManager().registerEvents(this.events, this);
		
		this.versionChecker = new VersionChecker(this, "http://dev.bukkit.org/server-mods/enchantedxp/files.rss");
		
		if (Config.checkNewVersionOnStartup == true)
			this.versionChecker.retreiveVersionInfo();
		
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {}
		
		info(String.format(this.stPluginEnabled, getDescription().getName(), getDescription().getVersion()));
	}

	public void info(String msg) {
		getServer().getConsoleSender().sendMessage(chatPrefix + msg);
	}
}