package gov.babalar.myth.utils.other;

import gov.babalar.myth.Utility;
import net.minecraft.HG;
import net.minecraft.Pc;
import net.minecraft.f3;
import net.minecraft.Lg;
import net.minecraft.L4;

public class InventoryUtil {


    public static void swap(int slot, int hSlot)
    {
        Utility.getPlayerController().m(getWindowID(), slot, hSlot, 2, Utility.getThePlayer());
    }

    public static void drop(int slot) {
        Utility.getPlayerController().m(0, slot, 1, 4, Utility.getThePlayer());
    }

    public static void click(int slot, int mouseButton, boolean shiftClick) {
        Utility.getPlayerController().m(getWindowID(), slot, mouseButton, shiftClick ? 1 : 0, Utility.getThePlayer());
    }

    public static float getProtScore(net.minecraft.f3 stack) {
        float prot = 0;
        if (stack.C() instanceof net.minecraft.rE) {
            net.minecraft.rE armor = (net.minecraft.rE) stack.C();
            prot += armor.V + ((100 - armor.V) * HG.L(Pc.p.O, stack)) * 0.0075F;
            prot += HG.L(Pc.W.O, stack) / 100F;
            prot += HG.L(Pc.X.O, stack) / 100F;
            prot += HG.L(Pc.H.O, stack) / 100F;
            prot += HG.L(Pc.E.O, stack) / 25.F;
            prot += HG.L(Pc.P.O, stack) / 100F;
        }
        return prot;
    }

    public static float getDamageScore(f3 stack) {
        if (stack == null || stack.C() == null) return 0;

        float damage = 0;
        net.minecraft.r4 item = stack.C();

        if (item instanceof Lg) {
            damage += ((Lg) item).e();
        } else if (item instanceof L4) {
            damage += item.y();
        }

        damage += HG.L(Pc.j.O, stack) * 1.25F +
                HG.L(Pc.C.O, stack) * 0.1F;

        return damage;
    }


    /*
    ÜÞENÝNCE BEN
     */
    public static boolean isWhitelisted(f3 itemStack)
    {
        String itemStackName = itemStack.toString().toLowerCase();
        return itemStackName.contains("applegold") || itemStackName.contains("tnt") || itemStackName.contains("chestplate") || itemStackName.contains("sword") || itemStackName.contains("leggings") || itemStackName.contains("pearl") | itemStackName.contains("helmet") || itemStackName.contains("boots") || itemStackName.contains("tile.");
    }

    public static int getWindowID()
    {
        return Utility.getThePlayer().FF.W;
    }

}
