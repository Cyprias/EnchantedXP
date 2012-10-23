package com.cyprias.enchantedxp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import com.cyprias.enchantedxp.VersionChecker.VersionCheckerEvent;

public class Events implements Listener {
	private EnchantedXP plugin;

	public Events(EnchantedXP plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onEnchantItem(EnchantItemEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player p = event.getEnchanter();

		//Get enchant level and XP cost. 
		int lvlCost = event.getExpLevelCost();
		int enchantXP = levelTotalExp(lvlCost); //(int) (1.75D * Math.pow(lvlCost, 2.0D) + 5.0D * lvlCost);

		//Get player's current XP.
		int playerXP = getTotalExperience(p);
		
		//Get how much XP player will have minus the enchant cost.
		int adjustedXP = (playerXP - enchantXP);

		//Set the player's exp.
		setExp(p, adjustedXP);

		//Set event's level cost to 0 so Bukkit doesn't change player's level afterwards. 
		event.setExpLevelCost(0);
	}

	public int getTotalExperience(Player player){
		// player.getTotalExperience() sometimes reports the wrong XP due to a bug with enchanting not updating player's total XP.
		return levelTotalExp(player.getLevel() + player.getExp());
	}
	
	public int levelTotalExp(double level){
		//double userLevel = player.getLevel() + player.getExp();
		if (level <= 16)
			return (int) Math.ceil(17*level);
		
		if (level <= 31)
			return (int) Math.ceil((1.5 * Math.pow(level, 2)) - (29.5 * level) + 360);
		
		
		return (int) Math.ceil((3.5 * Math.pow(level, 2)) - (151.5 * level) + 2220);
	}
	
	public static void setExp(Player player, int amount){
		//Clear player's XP.
		player.setTotalExperience(0);
		player.setExp(0.0F);
		player.setLevel(0);

		//Give player XP amount. 
		player.giveExp(amount);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onVersionCheckerEvent(VersionCheckerEvent event) {
		if (event.getPluginName() == plugin.getName()) {
			VersionChecker.versionInfo info = event.getVersionInfo(0);
			Object[] args = event.getArgs();

			String curVersion = plugin.getDescription().getVersion();

			if (args.length == 0) {

				int compare = VersionChecker.compareVersions(curVersion, info.getTitle());
				// plugin.info("curVersion: " + curVersion +", title: " +
				// info.getTitle() + ", compare: " + compare);
				if (compare < 0) {
					plugin.info("We're running v" + curVersion + ", v" + info.getTitle() + " is available");
					plugin.info(info.getLink());
				}

				return;
			}
		}
	}
}