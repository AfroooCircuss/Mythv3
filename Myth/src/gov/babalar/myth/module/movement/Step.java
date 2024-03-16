package gov.babalar.myth.module.movement;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventMotion;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import org.lwjgl.input.Keyboard;

/**
 * ----------
 * 9/30/2023
 * 2:29 AM
 * ----------
 **/
public class Step extends Module {
    public Step() {
        super(ModuleType.MOTION, "Step", 0);
        setSuffix("10");
    }

    @Subscribe
    public void onMotionEvent(EventMotion event)
    {
        Utility.setStepHeight(10f);
    }

    @Override
    public void onDisable() {
        Utility.setStepHeight(0.6f);
        super.onDisable();
    }
}
