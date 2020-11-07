package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.utils.Utils;

import java.text.DecimalFormat;

public class GuiIngameHook {

    @SuppressWarnings("unused")
    public static String insertSlayerPercentage(String s) {
        Utils utils = SkyblockAddons.getInstance().getUtils();
        if (!utils.isOnSkyblock() || SkyblockAddons.getInstance().getConfigValues().isDisabled(Feature.SLAYER_PERCENTAGE))
            return s;

        s = s.replace("\uD83C\uDF81", ""); // some strange chars causing isCorrectLine to be false

        boolean doTransform = utils.getSlayerQuest() != null && !utils.isSlayerBossAlive();
        boolean isCorrectLine = s.contains("/") && (s.contains("Combat X") || s.contains("Kills"));

        if (doTransform && isCorrectLine) {
            String[] split = s.trim().split(" ", 2);
            String[] split1 = split[0].trim().split("/", 2);

            double num = 0;
            double den = 1; // cant be zero because math
            try {
                num = Double.parseDouble(prepareParse(split1[0]));
                den = Double.parseDouble(prepareParse(split1[1]));
            } catch (NumberFormatException ex) {

            }

            double percent = 100 * num / den;

            return "§c" + new DecimalFormat("#####.0").format(percent) + "§7%" + " " + split[1];
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
