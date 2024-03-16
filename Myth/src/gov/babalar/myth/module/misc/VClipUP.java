package gov.babalar.myth.module.misc;

import gov.babalar.myth.Utility;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.setting.s.SettingBool;
import gov.babalar.myth.setting.s.SettingNumber;
import net.minecraft.BlockPos;
import net.minecraft.DN;
import net.minecraft.fD;
import org.lwjgl.input.Keyboard;

public class VClipUP extends Module {

    public SettingNumber upValue = new SettingNumber(5 , 1, 10, 1, "Value");
    public SettingBool mode = new SettingBool(false, "Smart");
    public SettingNumber startValue = new SettingNumber(1 , 1, 9, 1, "Start Value");

    public VClipUP()
    {
        super(ModuleType.MISC, "VClip Up", Keyboard.KEY_UP);
        this.abstractSettings.add(upValue);
        this.abstractSettings.add(mode);
        this.abstractSettings.add(startValue);
    }

    @Override
    public void onEnable() {
        if(mode.value) {
            for (int i = (int) startValue.value; i < 11; i++) {
                final BlockPos belowBlockPos = new BlockPos(Utility.getPosX(), Utility.getPosY() + i, Utility.getPosZ());
                final BlockPos pos2 = new BlockPos(Utility.getPosX(), Utility.getPosY() + (i + 1), Utility.getPosZ());
                if(Utility.getBlockState(belowBlockPos) == null || Utility.getBlockState(pos2) == null) continue;
                if (Utility.getBlockState(belowBlockPos).N() instanceof fD && Utility.getBlockState(pos2).N() instanceof fD) {
                    ((DN) Utility.getThePlayer()).e(Utility.getPosX(), Utility.getPosY() + i, Utility.getPosZ());
                    this.toggle();
                    return;
                }
            }
        } else {
            ((DN) Utility.getThePlayer()).e(Utility.getPosX(), Utility.getPosY() + upValue.value, Utility.getPosZ());
        }
        this.toggle();
    }
}
