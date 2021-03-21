package io.github.portablestew.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;

/**
 * Command to randomly kill from all living entities
 */
public class BigBoomExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Parse args
        int blastPower = 100;
        try {
            if (args.length > 1) {
                sender.sendMessage("Invalid arguments.");
                return false;
            } else if (args.length > 0) {
                blastPower = Integer.parseInt(args[0]);
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

        Entity entity = null;
        if (sender instanceof Entity) {
            entity = (Entity) sender;
        } else {
            sender.sendMessage("Sorry, " + command.getName() + " requires an entity.");
            return true;
        }

        // Kaboom
        entity.getWorld().createExplosion(entity.getLocation(), blastPower, true, true, entity);
        return true;
    }
}
