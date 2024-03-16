package gov.babalar.myth.module.combat;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.event.EventReceivePacket;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.module.movement.Strafe;
import net.minecraft.HY;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {

    public Velocity() {
        super(ModuleType.COMBAT, "Velocity", 0);
        toggle();
        setSuffix("Zero");
    }


    @Subscribe
    public void onPacketReceive(EventReceivePacket e)
    {

        if(e.getPacket() instanceof HY)
        {
            e.cancel();
        }
    }

}
