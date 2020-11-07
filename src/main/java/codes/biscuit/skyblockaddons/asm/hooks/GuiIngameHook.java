package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.utils.Utils;

import java.text.DecimalFormat;

public class GuiIngameHook {

    @SuppressWarnings("unused")
    public static String insertSlayerPercentage(String s) {
        Utils utils = SkyblockAddons.getInstance().getUtils();
        if (!utils.isOnSkyblock() || !SkyblockAddons.getInstance().getConfigValues().isEnabled(Feature.SLAYER_PERCENTAGE))
            return s;

        s = s.replace("\uD83C\uDF81", ""); // some strange chars causing isCorrectLine to be false
        s = s.replace("\uD83C\uDFC0", ""); // some strange chars causing double parsing to fail

        boolean doTransform = utils.getSlayerQuest() != null && !utils.isSlayerBossAlive();
        boolean isCorrectLine = s.contains("/") && (s.contains("Combat X") || s.contains("Kills"));


        if (doTransform && isCorrectLine) {
            String[] split = s.trim().split(" ", 2);
            String[] split1 = split[0].trim().split("/", 2);

            double num;
            double den; // cant be zero because math
            try {
                num = Double.parseDouble(prepareParse(split1[0]));
                den = Double.parseDouble(prepareParse(split1[1]));
            } catch (NumberFormatException ex) {
                SkyblockAddons.getLogger().warn("[SkyblockAddons] Slayer percentage feature encountered an error. Does the scoreboard look normal?", ex);
                return s;
            }

            double percent = 100 * num / den;

            return "§c" + new DecimalFormat("#####.0").format(percent) + "§7%" + " " + split[1].replaceFirst("Combat X|Combat XP|Kills", "XP");
        } else
            return s;
    }

    private static String prepareParse(String in) {
        return in.trim().replaceAll("§.", "")
                .replaceAll("k", "000")
                .replace(",", "")
                .replace("(", "")
                .replace(")", "")
                ;
    }

}
