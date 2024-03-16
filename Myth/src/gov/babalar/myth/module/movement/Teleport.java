package gov.babalar.myth.module.movement;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventPacketSent;
import gov.babalar.myth.event.EventReceivePacket;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.utils.other.ReflectionUtils;
import gov.babalar.myth.utils.player.ChatUtil;
import net.minecraft.DN;
import net.minecraft.DZ;
import net.minecraft.br;
import net.minecraft.oN;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;

/**
 * ----------
 * 11/2/2023
 * 11:53 PM
 * ----------
 **/
public class Teleport extends Module {
    public Teleport() {
        super(ModuleType.MOTION, "Teleport", 0);
    }

    public static boolean isTeleported = false;

    public static boolean waitingResponse = true;
    double toTeleportX;
    double toTeleportY;
    double toTeleportZ;

    @Override
    public void onEnable() {
        List<DZ> closestEntities = Utility.getEntityList();
        if(closestEntities.size() == 0) {
            toggle();
            return;
        }
        closestEntities.sort(Comparator.comparingDouble(Utility::getDistanceToEntity));
        for(DZ entity : closestEntities)
        {
            if (Utility.getDistanceToEntity(entity) < 120 && entity != Utility.getThePlayer())
            {
                ChatUtil.print(true, String.format("§eTrying teleport to §a%s!", Utility.getPlayerName(entity)));
                toTeleportX = Utility.getPosX(entity);
                toTeleportY = Utility.getPosY(entity);
                toTeleportZ = Utility.getPosZ(entity);
                //waitingResponse = true;
                for(int i = 0; i < 320; i++) {
                    if(Utility.getPosX(entity) != toTeleportX) toTeleportX = Utility.getPosX(entity);
                    if(Utility.getPosY(entity) != toTeleportY) toTeleportY = Utility.getPosY(entity);
                    if(Utility.getPosZ(entity) != toTeleportZ) toTeleportZ = Utility.getPosZ(entity);
                    ((DN) Utility.getThePlayer()).e(toTeleportX, toTeleportY, toTeleportZ);
                }
                ((DN) Utility.getThePlayer()).e(toTeleportX, toTeleportY, toTeleportZ);
                /*
                if (!waitingResponse) {
                    if (isTeleported) {
                        ChatUtil.print(true, String.format("§aSuccessfully teleported to §e%s!", Utility.getPlayerName(entity)));
                    } else {
                        ChatUtil.print(true, "§cFailed to teleport!");
                    }
                }*/
                break;
            }
        }
        toggle();
    }


}