package gov.babalar.myth.module.misc;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventMotion;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.utils.player.ChatUtil;
import net.minecraft.DZ;
import net.minecraft.y;

public class Reporter extends Module {

    List<String> reported = new ArrayList<>();

    public Reporter() {
        super(ModuleType.MISC, "Reporter", 0);
    }


    @Subscribe
    public void onUpdate(EventMotion e)
    {
        setSuffix("Reported: " + reported.size());
        for (DZ entity : Utility.getEntityList()) {
            String name = Utility.getPlayerName(entity);
            if (entity != Utility.getThePlayer() && !reported.contains(name))
            {
                reported.add(name);
                String message = "/report hile " + name;
                ChatUtil.send(message);
            }
        }
    }
}