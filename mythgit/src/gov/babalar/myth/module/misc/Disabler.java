package gov.babalar.myth.module.misc;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventPacketSent;
import gov.babalar.myth.managers.ModuleManager;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import net.minecraft.br;
import net.minecraft.y;
import org.apache.logging.log4j.LogManager;


import java.lang.reflect.Field;

import static net.minecraft.df.OPEN_INVENTORY;

/**
 * ----------
 * 11/2/2023
 * 7:03 PM
 * ----------
 **/
public class Disabler extends Module {
    public Disabler() {
        super(ModuleType.MISC, "Disabler", 0);
        toggle();
    }


    @Subscribe
    public void event(EventPacketSent eventPacketSent)
    {
        if(eventPacketSent.getPacket() instanceof net.minecraft.H8)
        {
            try {
                Field f = net.minecraft.H8.class.getDeclaredField("E");
                f.setAccessible(true);
                net.minecraft.df df = (net.minecraft.df) f.get(eventPacketSent.getPacket());
                if(df.equals(OPEN_INVENTORY))
                {
                    eventPacketSent.cancel();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {
        ModuleManager.c03Index = 0;
    }
}
