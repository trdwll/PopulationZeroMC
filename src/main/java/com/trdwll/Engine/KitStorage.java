package com.trdwll.Engine;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class KitStorage {
	public static void giveKit(Player player, int kitID) {
		PlayerInventory playerInventory = player.getInventory();
		
		switch (kitID) {
		case 0: // NOOB
			ItemStack noob1 = new ItemStack(Material.WOOD_SWORD, 1);
			ItemStack noob2 = new ItemStack(Material.BOW, 1);
			ItemStack noob3 = new ItemStack(Material.ARROW, 64);
			ItemStack noob4 = new ItemStack(Material.LEATHER_BOOTS, 1);
			ItemStack noob5 = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			ItemStack noob6 = new ItemStack(Material.LEATHER_HELMET, 1);
			ItemStack noob7 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			ItemStack noob8 = new ItemStack(Material.COOKED_BEEF, 32);
			
			playerInventory.clear();
			playerInventory.addItem(noob1, noob2, noob3, noob4, noob5, noob6, noob7, noob8);
			player.sendMessage("Noob kit given!");
			break;
		case 1: // ELITE
			ItemStack elite1 = new ItemStack(Material.STONE_SWORD, 1);
			ItemStack elite2 = new ItemStack(Material.BOW, 1);
			ItemStack elite3 = new ItemStack(Material.ARROW, 64);
			ItemStack elite4 = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
			ItemStack elite5 = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
			ItemStack elite6 = new ItemStack(Material.CHAINMAIL_HELMET, 1);
			ItemStack elite7 = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
			ItemStack elite8 = new ItemStack(Material.COOKED_BEEF, 64);
			
			ItemMeta eliteM1 = elite1.getItemMeta(); // SWORD
			ItemMeta eliteM2 = elite1.getItemMeta(); // BOW
			
			eliteM1.addEnchant(Enchantment.KNOCKBACK, 1, true);
			eliteM2.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			
			elite1.setItemMeta(eliteM1);
			elite2.setItemMeta(eliteM2);
			
			playerInventory.clear();
			playerInventory.addItem(elite1, elite2, elite3, elite4, elite5, elite6, elite7, elite8);
			player.sendMessage("Elite kit given!");
			break;
		case 2: // VETERAN
			ItemStack veteran1 = new ItemStack(Material.IRON_SWORD, 1);
			ItemStack veteran2 = new ItemStack(Material.BOW, 1);
			ItemStack veteran3 = new ItemStack(Material.ARROW, 1);
			ItemStack veteran4 = new ItemStack(Material.IRON_BOOTS, 1);
			ItemStack veteran5 = new ItemStack(Material.IRON_CHESTPLATE, 1);
			ItemStack veteran6 = new ItemStack(Material.IRON_HELMET, 1);
			ItemStack veteran7 = new ItemStack(Material.IRON_LEGGINGS, 1);
			ItemStack veteran8 = new ItemStack(Material.COOKED_BEEF, 64);
			ItemStack veteran9 = new ItemStack(Material.GOLDEN_APPLE, 32);
			
			ItemMeta veteranM1 = veteran1.getItemMeta(); // SWORD
			ItemMeta veteranM2 = veteran2.getItemMeta(); // BOW
			ItemMeta veteranM4 = veteran4.getItemMeta(); // BOOTS
			ItemMeta veteranM5 = veteran5.getItemMeta(); // CHESTPLATE
			ItemMeta veteranM6 = veteran6.getItemMeta(); // HELMET
			ItemMeta veteranM7 = veteran7.getItemMeta(); // LEGGINGS
			
			veteranM1.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
			veteranM1.addEnchant(Enchantment.DURABILITY, 1, true);
			veteranM2.addEnchant(Enchantment.DURABILITY, 1, true);
			veteranM2.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			veteranM4.addEnchant(Enchantment.DURABILITY, 1, true);
			veteranM5.addEnchant(Enchantment.DURABILITY, 1, true);
			veteranM6.addEnchant(Enchantment.DURABILITY, 1, true);
			veteranM7.addEnchant(Enchantment.DURABILITY, 1, true);
			
			veteran1.setItemMeta(veteranM1);
			veteran2.setItemMeta(veteranM2);
			veteran4.setItemMeta(veteranM4);
			veteran5.setItemMeta(veteranM5);
			veteran6.setItemMeta(veteranM6);
			veteran7.setItemMeta(veteranM7);
			
			playerInventory.clear();
			playerInventory.addItem(veteran1, veteran2, veteran3, veteran4, veteran5, veteran6, veteran7, veteran8, veteran9);
			player.sendMessage("Veteran kit given!");
			break;
		case 3: // GOD
			ItemStack god1 = new ItemStack(Material.DIAMOND_SWORD, 1);
			ItemStack god2 = new ItemStack(Material.BOW, 1);
			ItemStack god3 = new ItemStack(Material.ARROW, 1);
			ItemStack god4 = new ItemStack(Material.DIAMOND_BOOTS, 1);
			ItemStack god5 = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			ItemStack god6 = new ItemStack(Material.DIAMOND_HELMET, 1);
			ItemStack god7 = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
			ItemStack god8 = new ItemStack(Material.COOKED_BEEF, 64);
			ItemStack god9 = new ItemStack(Material.GOLDEN_APPLE, 64); // GOD APPLE
			
			ItemMeta godM1 = god1.getItemMeta(); // SWORD
			ItemMeta godM2 = god2.getItemMeta(); // BOW
			ItemMeta godM4 = god4.getItemMeta(); // BOOTS
			ItemMeta godM5 = god5.getItemMeta(); // CHESTPLATE
			ItemMeta godM6 = god6.getItemMeta(); // HELMET
			ItemMeta godM7 = god7.getItemMeta(); // LEGGINGS
			
			godM1.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
			godM1.addEnchant(Enchantment.DAMAGE_UNDEAD, 3, true);
			godM1.addEnchant(Enchantment.DURABILITY, 3, true);
			godM1.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
			
			godM2.addEnchant(Enchantment.DURABILITY, 1, true);
			godM2.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
			godM2.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			
			godM4.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
			godM4.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
			godM4.addEnchant(Enchantment.DURABILITY, 3, true);
			godM4.addEnchant(Enchantment.PROTECTION_PROJECTILE, 4, true);
			godM4.addEnchant(Enchantment.THORNS, 3, true);
			
			godM5.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
			godM5.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
			godM5.addEnchant(Enchantment.DURABILITY, 3, true);
			godM5.addEnchant(Enchantment.PROTECTION_PROJECTILE, 4, true);
			godM5.addEnchant(Enchantment.THORNS, 3, true);
			
			godM6.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
			godM6.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
			godM6.addEnchant(Enchantment.DURABILITY, 3, true);
			godM6.addEnchant(Enchantment.PROTECTION_PROJECTILE, 4, true);
			godM6.addEnchant(Enchantment.THORNS, 3, true);
			
			godM7.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
			godM7.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
			godM7.addEnchant(Enchantment.DURABILITY, 3, true);
			godM7.addEnchant(Enchantment.PROTECTION_PROJECTILE, 4, true);
			godM7.addEnchant(Enchantment.THORNS, 3, true);
			
			god1.setItemMeta(godM1);
			god2.setItemMeta(godM2);
			god4.setItemMeta(godM4);
			god5.setItemMeta(godM5);
			god6.setItemMeta(godM6);
			god7.setItemMeta(godM7);
			
			playerInventory.clear();
			playerInventory.addItem(god1, god2, god3, god4, god5, god6, god7, god8, god9);
			player.sendMessage("God kit given!");
			break;
		case 4: // DEV
			ItemStack dev1 = new ItemStack(Material.DIAMOND_SWORD, 1);
			ItemStack dev2 = new ItemStack(Material.BOW, 1);
			ItemStack dev3 = new ItemStack(Material.ARROW, 1);
			ItemStack dev4 = new ItemStack(Material.DIAMOND_BOOTS, 1);
			ItemStack dev5 = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			ItemStack dev6 = new ItemStack(Material.DIAMOND_HELMET, 1);
			ItemStack dev7 = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
			ItemStack dev8 = new ItemStack(Material.COOKED_BEEF, 64);
			ItemStack dev9 = new ItemStack(Material.GOLDEN_APPLE, 64); // dev APPLE
			
			ItemMeta devM1 = dev1.getItemMeta(); // SWORD
			ItemMeta devM2 = dev2.getItemMeta(); // BOW
			ItemMeta devM4 = dev4.getItemMeta(); // BOOTS
			ItemMeta devM5 = dev5.getItemMeta(); // CHESTPLATE
			ItemMeta devM6 = dev6.getItemMeta(); // HELMET
			ItemMeta devM7 = dev7.getItemMeta(); // LEGGINGS
			
			devM1.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
			devM1.addEnchant(Enchantment.DAMAGE_UNDEAD, 5, true);
			devM1.addEnchant(Enchantment.DURABILITY, 5, true);
			devM1.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
			
			devM2.addEnchant(Enchantment.DURABILITY, 5, true);
			devM2.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
			devM2.addEnchant(Enchantment.ARROW_INFINITE, 5, true);
			
			devM4.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
			devM4.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
			devM4.addEnchant(Enchantment.DURABILITY, 5, true);
			devM4.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5, true);
			devM4.addEnchant(Enchantment.THORNS, 5, true);
			
			devM5.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
			devM5.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
			devM5.addEnchant(Enchantment.DURABILITY, 5, true);
			devM5.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5, true);
			devM5.addEnchant(Enchantment.THORNS, 5, true);
			
			devM6.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
			devM6.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
			devM6.addEnchant(Enchantment.DURABILITY, 5, true);
			devM6.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5, true);
			devM6.addEnchant(Enchantment.THORNS, 5, true);
			
			devM7.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
			devM7.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
			devM7.addEnchant(Enchantment.DURABILITY, 5, true);
			devM7.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5, true);
			devM7.addEnchant(Enchantment.THORNS, 5, true);
			
			dev1.setItemMeta(devM1);
			dev2.setItemMeta(devM2);
			dev4.setItemMeta(devM4);
			dev5.setItemMeta(devM5);
			dev6.setItemMeta(devM6);
			dev7.setItemMeta(devM7);
			
			playerInventory.clear();
			playerInventory.addItem(dev1, dev2, dev3, dev4, dev5, dev6, dev7, dev8, dev9);
			player.sendMessage("Dev kit given!");
			break;
			
		} 
		
	}
}
