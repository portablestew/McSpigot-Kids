package io.github.portablestew.commands;

import java.util.Random;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

/**
 * Command to randomly kill from all living entities
 */
public class SnapExecutor implements CommandExecutor {
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
                sender.sendMessage("Sorry, " + command.getName() + " is for operators only.");
                return true;
            }
        }

        sender.getServer().getLogger().warning("Executing the snap! " + snapPct + "% decimation.");

        for (World world : sender.getServer().getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                // Seed with each entity id to predetermine each one's percentile
                Random rand = new Random(entity.getEntityId());
                int entityPct = rand.nextInt(100);
                if (snapPct > entityPct) {
                    entity.setHealth(0);
                }
            }
        }
        return true;
    }
}
