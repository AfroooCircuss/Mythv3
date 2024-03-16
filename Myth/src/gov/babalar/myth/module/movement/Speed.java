package gov.babalar.myth.module.movement;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventMotion;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.setting.s.SettingBool;
import gov.babalar.myth.setting.s.SettingMode;
import gov.babalar.myth.setting.s.SettingNumber;
import gov.babalar.myth.utils.MovementUtil;
import org.lwjgl.input.Keyboard;

/**
 * ----------
 * 9/30/2023
 * 2:29 AM
 * ----------
 **/
public class Speed extends Module {
    public SettingMode mode = new SettingMode("Mode" , new String[]
            {
                    "Vanilla","Bunny"
            } , 0);
    public Speed() {
        super(ModuleType.MOTION, "Speed", Keyboard.KEY_V);
        this.abstractSettings.add(mode);
    }
    public static int ticks;
    @Subscribe
    public void onMotionEvent(EventMotion event)
    {
        setSuffix(mode.getMode());
        if (!MovementUtil.isMoving())
            return;
        if(mode.getMode().equals("Vanilla")) {
            MovementUtil.setSpeed(1.6);
            return;
        }
        bhop:{
            if(Utility.getMotionY() != 0) {
                double amp = System.currentTimeMillis() % 100 == 0 ? 2.3 : 1;
                MovementUtil.setSpeed(MovementUtil.getSpeed() * amp);
            }
        }
    }
}
