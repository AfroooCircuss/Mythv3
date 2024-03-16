package gov.babalar.myth.module.movement;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventMotion;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.setting.s.SettingMode;
import gov.babalar.myth.setting.s.SettingNumber;
import gov.babalar.myth.utils.MovementUtil;

public class Fly
        extends Module {
    public SettingMode mode = new SettingMode("Mode", new String[]{"Vanilla", "AAC"}, 0);
    public SettingNumber speed = new SettingNumber(2.0, 0.0, 9.9, 0.1, "Speed");
    public static int ticks;

    public Fly() {
        super(ModuleType.MOTION, "Fly", 33);
        this.abstractSettings.add(this.mode);
        this.abstractSettings.add(this.speed);
    }

    @Subscribe
    public void onMotionEvent(EventMotion event) {
        this.setSuffix(this.mode.getMode());
        if (this.mode.getMode().equals("Vanilla")) {
            MovementUtil.setSpeed(this.speed.value);
            Utility.setMotionY(Utility.getGameSettings().uw.f() ? 1.0 : (Utility.getGameSettings().um.f() ? -1.0 : -0.0625));
            return;
        }
        switch (ticks) {
            case 0: {
                Utility.setMotionY(0.4);
                ++ticks;
                break;
            }
            case 1: {
                MovementUtil.setSpeed(0.5);
                Utility.setMotionY(0.0);
                ++ticks;
                break;
            }
            case 2: {
                MovementUtil.setSpeed(1.0);
                Utility.setMotionY(0.0);
                --ticks;
            }
        }
    }
}
