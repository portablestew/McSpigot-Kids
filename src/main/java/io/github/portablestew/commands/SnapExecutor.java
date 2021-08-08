package io.github.portablestew.commands;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Command to randomly kill from all living entities
 */
public class SnapExecutor implements CommandExecutor {
    private final long StartEpochSeconds = Instant.now().getEpochSecond();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Parse args
        int snapPct = 50;
        try {
            if (args.length > 1) {
                sender.sendMessage("Invalid arguments.");
                return false;
            } else if (args.length > 0) {
                snapPct = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("Not a valid number.");
            return false;
        }

        // Check permissions
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.isOp()) {
                sender.sendMessage("Sorry, " + command.getName() + " is for operators only. Mwahahah!");
                return true;
            }
        }

        sender.getServer().getLogger().warning("Executing the snap! " + snapPct + "% decimation.");

        for (World world : sender.getServer().getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                // Seed with each entity id to predetermine each one's percentile
                Random rand = new Random(StartEpochSeconds ^ entity.getEntityId());
                int entityPct = rand.nextInt(100);
                if (snapPct > entityPct) {
                    entity.setHealth(0);

                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        player.sendMessage("Sorry " + player.getDisplayName()
                                + ", you were snapped. Please enjoy a free server drink on me! Score="
                                + entityPct + "/" + snapPct + ".");

                        // Give them a tasty consolation prize
                        ItemStack soda = new ItemStack(Material.POTION);
                        PotionMeta sodaMeta = (PotionMeta) soda.getItemMeta();
                        sodaMeta.setDisplayName("Snap Soda");
                        sodaMeta.setLore(new ArrayList<>(Arrays.asList("Sorry for the snap :(")));
                        sodaMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 60 * 20, 100), true);
                        soda.setItemMeta(sodaMeta);

                        player.getInventory().addItem(soda);
                    }
                }
            }
        }
        return true;
    }
}
