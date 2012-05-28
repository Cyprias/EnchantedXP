package com.cyprias.enchantedxp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

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
		int enchantXP = (int) (1.75D * Math.pow(lvlCost, 2.0D) + 5.0D * lvlCost);

		//Get player's current XP.
		int playerXP = getTotalExperience(p);
		
		//Get how much XP player will have minus the enchant cost.
		int adjustedXP = (playerXP - enchantXP);

		//Set the player's exp.
		setExp(p, adjustedXP);
		
		//Set event's level cost to 0 so Bukkit doesn't change player's level afterwards. 
		event.setExpLevelCost(0);
	}
	
	public static int getTotalExperience(Player player){
		// player.getTotalExperience() sometimes reports the wrong XP due to a bug with enchanting not updating player's total XP.
		double userLevel = player.getLevel() + player.getExp();
		return (int) Math.ceil(1.75D * Math.pow(userLevel, 2.0D) + 5.0D * userLevel);
	}
	
	public static void setExp(Player player, int amount){
		//Clear player's XP.
		player.setTotalExperience(0);
		player.setLevel(0);
		player.setExp(0.0F);

		//Give player XP amount. 
		player.giveExp(amount);
	}
	
}