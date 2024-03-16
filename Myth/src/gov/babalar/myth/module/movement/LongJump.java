package gov.babalar.myth.module.movement;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventMotion;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import net.minecraft.DN;

public class LongJump extends Module {
    public LongJump() {
        super(ModuleType.MOTION, "LongJump", 0);
    }
    public boolean tp;
    @Override
    public void onEnable() {
        super.onEnable();
        tp = false;
    }

    @Subscribe
    public void mot(EventMotion eventMotion) {
        if(Utility.getFallDistance() > 0.5 && !tp)
        {
            double x = Utility.getMotionX();
            double z = Utility.getMotionZ();
            double offX = 0;
            double offZ = 0;
            if(x > 0)
            {
                offX+=1.5;
            }else if(x < 0)
            {
                offX-=1.5;
            }
            if(z > 0) {
                offZ+=1.5;
            }else if (z < 0)
            {
                offZ-=1.5;
            }
            ((DN)Utility.getThePlayer()).e(Utility.getPosX() + offX, Utility.getPosY(), Utility.getPosZ() + offZ);
            tp = true;
        }
    }
}