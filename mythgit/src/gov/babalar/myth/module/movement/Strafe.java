package gov.babalar.myth.module.movement;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventMotion;
import gov.babalar.myth.event.EventRender2D;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.utils.MovementUtil;
import org.lwjgl.input.Keyboard;

/**
 * ----------
 * 9/30/2023
 * 2:29 AM
 * ----------
 **/
public class Strafe extends Module {
    public static int ticks = 0;
    public Strafe() {
        super(ModuleType.MOTION, "Strafe", Keyboard.KEY_U);
        setSuffix("Vanilla");
    }

    @Subscribe
    public void onMotionEvent(EventMotion event)
    {
        MovementUtil.setSpeed(MovementUtil.getSpeed());
    }
}
