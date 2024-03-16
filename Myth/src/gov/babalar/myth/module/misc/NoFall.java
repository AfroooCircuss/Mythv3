package gov.babalar.myth.module.misc;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventPacketSent;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.utils.other.ReflectionUtils;
import net.minecraft.br;

public class NoFall extends Module {
    public NoFall() {
        super(ModuleType.MISC, "NoFall", 0);
    }

    @Subscribe
    public void onPacketSend(EventPacketSent e)
    {
        if(e.getPacket() instanceof br)
        {
            br c03 = (br) e.getPacket();
            boolean onGround = (boolean) ReflectionUtils.getField(br.class, "N", c03);
            if (!onGround && Utility.getFallDistance() > 3) {
                ReflectionUtils.setField(br.class, "N", c03, true);
                Utility.setFallDistance(0.0f);
            }
        }
    }

}