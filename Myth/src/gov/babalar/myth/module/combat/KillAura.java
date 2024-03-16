package gov.babalar.myth.module.combat;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventMotion;
import gov.babalar.myth.event.EventPacketSent;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.setting.s.SettingBool;
import gov.babalar.myth.setting.s.SettingMode;
import gov.babalar.myth.setting.s.SettingNumber;
import gov.babalar.myth.utils.TimerUtil;
import net.minecraft.DZ;
import net.minecraft.TW;
import net.minecraft.V8;
import net.minecraft.uy;
import org.lwjgl.input.Keyboard;

import javax.rmi.CORBA.Util;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {

        public static DZ target;
        public List<DZ> targets = new ArrayList<>();
        public static boolean attacking;
        public SettingMode mode = new SettingMode("Mode",new String[]{
           "Single","Multi"
        },0);

        public SettingNumber rangeSetting = new SettingNumber(6.2 , 0 , 6.2 , 0.5 , "Range");

        public final TimerUtil timer = new TimerUtil();

        public KillAura() {
                super(ModuleType.COMBAT, "Aura", Keyboard.KEY_R);
                abstractSettings.add(mode);
                abstractSettings.add(rangeSetting);
        }


        @Subscribe
        public void onMotionEvent(EventMotion e)
        {
                setSuffix(String.format("%s", mode.getMode()));
                sortTargets();
                if (!this.targets.isEmpty()) {
                        switch (mode.getMode())
                        {
                                case "Single": {

                                        target = this.targets.get(0);
                                        attacking = true;
                                        attackPlayer(target);
                                        break;
                                }
                                case "Multi": {
                                        List<DZ> limitedTargets = targets.stream().limit(5).collect(Collectors.toList());
                                        for (DZ player : limitedTargets) {
                                                target = player;
                                                attacking = true;
                                                attackPlayer(target);
                                        }
                                        break;
                                }
                        }

                }
        }


        public void sortTargets() {
                this.targets.clear();
                for(DZ entity : Utility.getEntityList())
                {
                        if (Utility.getDistanceToEntity(entity) < rangeSetting.value && entity != Utility.getThePlayer())
                                this.targets.add(entity);
                }
                this.targets.sort(Comparator.comparingDouble(Utility::getDistanceToEntity));
        }

        @Override
        public void onDisable() {
                target = null;
                targets.clear();
                attacking = false;
                super.onDisable();
        }



        public void attackPlayer(DZ target)
        {
                try {
                        TW attackPacket = new TW(target, V8.ATTACK);
                        Field field = TW.class.getDeclaredField("Z");
                        field.setAccessible(true);
                        field.setDouble(attackPacket, Math.min(field.getDouble(attackPacket), 4 - (Math.random() * 0.06)));
                        Utility.sendPacket(attackPacket);
                }catch (Exception e)
                {
                        e.printStackTrace();
                }
        }

}