package gov.babalar.myth.module.misc;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventMotion;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;

import gov.babalar.myth.setting.s.SettingNumber;
import gov.babalar.myth.utils.other.InventoryUtil;
import net.minecraft.Lg;
import net.minecraft.f3;

public class InvManager extends Module {

    private static final SettingNumber slotWeapon = new SettingNumber(1, 1, 9, 1, "Weapon Slot");


    public InvManager()
    {
        super(ModuleType.MISC, "InvManager", 0);
        abstractSettings.add(slotWeapon);
    }

    @Subscribe
    public void onMotion(EventMotion e)
    {
        getBestWeapon();
        //TODO: add drop bad items from tenacity added almost fully methods
    }


    private void getBestWeapon() {
        for (int i = 9; i < 45; i++) {
            f3 is = Utility.getThePlayer().FF.A(i).L();
            if (is != null && is.C() instanceof Lg && isBestWeapon(is) && InventoryUtil.getDamageScore(is) > 0) {
                InventoryUtil.swap(i, (int)slotWeapon.value - 36);
                break;
            }
        }
    }
    private void dropPots()
    {
        int pots = 0;
        for (int i = 9; i < 45; i++) {
            f3 is = Utility.getThePlayer().FF.A(i).L();
            if (is != null && is.toString().contains("instant")) {
                InventoryUtil.swap(i, (int)slotWeapon.value - 36);
                break;
            }
        }
    }

    private boolean isBestArmor(f3 stack, int type) {
        String typeStr = "";
        switch (type) {
            case 1:
                typeStr = "helmet";
                break;
            case 2:
                typeStr = "chestplate";
                break;
            case 3:
                typeStr = "leggings";
                break;
            case 4:
                typeStr = "boots";
                break;
        }
        if (stack.U().contains(typeStr)) {
            float prot = InventoryUtil.getProtScore(stack);
            for (int i = 5; i < 45; i++) {
                net.minecraft.t slot = Utility.getThePlayer().FF.A(i);
                if (slot.L() != null) {
                    f3 is = slot.L();
                    if (is.U().contains(typeStr) && InventoryUtil.getProtScore(is) > prot) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean isBestWeapon(f3 is) {
        if (is == null) return false;
        float damage = InventoryUtil.getDamageScore(is);
        for (int i = 9; i < 45; i++) {
            net.minecraft.t slot = Utility.getThePlayer().FF.A(i);
            if (slot.L() != null) {
                f3 is2 = slot.L();
                if (InventoryUtil.getDamageScore(is2) > damage && is2.C() instanceof Lg) {
                    return false;
                }
            }
        }
        return is.C() instanceof Lg;
    }

}
