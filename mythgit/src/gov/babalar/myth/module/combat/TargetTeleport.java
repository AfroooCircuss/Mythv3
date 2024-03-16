package gov.babalar.myth.module.combat;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventMotion;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import net.minecraft.BlockPos;
import net.minecraft.DN;
import net.minecraft.fD;
import org.lwjgl.input.Keyboard;

public class TargetTeleport extends Module {
    public TargetTeleport() {
        super(ModuleType.COMBAT, "Target Teleport", Keyboard.KEY_GRAVE);
    }
    @Subscribe
    public void onMotion(EventMotion event) {
        if(KillAura.target != null)
        {
            double targetPosX = Utility.getPosX(KillAura.target);
            double targetPosY = Utility.getPosY(KillAura.target);
            double targetPosZ = Utility.getPosZ(KillAura.target);
            final BlockPos upperBlockPos = new BlockPos(targetPosX, targetPosY + 2, targetPosZ);
            final BlockPos downBlockPos = new BlockPos(targetPosX, targetPosY - 1, targetPosZ);

            try {
                if (Utility.getBlockState(upperBlockPos).N() instanceof fD) {
                    ((DN) Utility.getThePlayer()).e(targetPosX, targetPosY + 2, targetPosZ);
                } else if (Utility.getBlockState(downBlockPos).N() instanceof fD) {
                    ((DN) Utility.getThePlayer()).e(targetPosX, targetPosY - 1, targetPosZ);
                } else {
                    ((DN) Utility.getThePlayer()).e(targetPosX, targetPosY, targetPosZ);
                }
            }catch (Exception e)
            {
                ((DN)Utility.getThePlayer()).e(targetPosX, targetPosY, targetPosZ);
            }
        }
    }
}
