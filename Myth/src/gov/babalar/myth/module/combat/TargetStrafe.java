package gov.babalar.myth.module.combat;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Client;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventMotion;
import gov.babalar.myth.event.EventRender3D;
import gov.babalar.myth.managers.ModuleManager;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.utils.MovementUtil;
import gov.babalar.myth.utils.render.RenderUtil;
import net.minecraft.*;
import net.minecraft.client.PM;
import net.minecraft.client.kr;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;

public class TargetStrafe extends Module {

    private static int strafe = 1;


    public TargetStrafe()
    {
        super(ModuleType.COMBAT, "TargetStrafe", 0);
    }

    @Subscribe
    public void onMotion(EventMotion event) {
        if (TargetStrafe.canStrafe()) {
            if (Utility.getThePlayer().pQ) {
                strafe = -strafe;
            } else {
                if (Utility.getGameSettings().q.f()) {
                    strafe = 1;

                    if (Utility.getGameSettings().TZ.f()) {
                        strafe = -1;
                    }
                }
                strafe();
            }
        }
    }

    @Subscribe
    public void onRender3D(EventRender3D event) {
        this.drawCircle(5.0f, -16777216);
        this.drawCircle(3.0f, -1);
    }

    private void drawCircle(float lineWidth, int color) {
        DZ entity = KillAura.target;
        if (entity == null) {
            return;
        }
        uO timer = null;
        try {
            Field field = net.minecraft.client.Pe.class.getDeclaredField("en");
            field.setAccessible(true);
            timer = (uO)field.get(Client.mc);
        } catch (Exception field) {}
        net.minecraft.client.PM renderManager = null;
        try {
            Field field = net.minecraft.client.Pe.class.getDeclaredField("E");
            field.setAccessible(true);
            renderManager = (PM)field.get(Client.mc);
        } catch (Exception field) {}
        if(timer == null || renderManager == null)
        {
            String s = timer == null ? "timer" : "renderManager";
            throw new NullPointerException(String.format("%s is null!!!!!", s));
        }
        double x = entity.pf + (Utility.getPosX(entity) - entity.pf) * (double)timer.G - renderManager.Q;
        double y = entity.A + (Utility.getPosY(entity) - entity.A) * (double)timer.G - renderManager.w;
        double z = entity.s + (Utility.getPosZ(entity) - entity.s) * (double)timer.G - renderManager.X;
        GL11.glPushMatrix();
        RenderUtil.color(color, (float)(1.0f / 4.5 / 2.0));
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(lineWidth);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glBegin(3);
        double pi2 = Math.PI * 2;
        int i = 0;
        while (i <= 90) {
            GL11.glVertex3d(x + 1.0f * Math.cos((double)i * pi2 / 45.0), y, z + 1.0f * Math.sin((double)i * pi2 / 45.0));
            ++i;
        }
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    public static boolean strafe() {
        return TargetStrafe.strafe(MovementUtil.getSpeed());
    }

    public static boolean strafe(double moveSpeed) {
        if (TargetStrafe.canStrafe()) {
            MovementUtil.setSpeed(moveSpeed, TargetStrafe.getYaw(TargetStrafe.getPositionVector(KillAura.target)), strafe, !((double)Utility.getDistanceToEntity(KillAura.target) <= 4.5) ? 1 : 0);
            return true;
        }
        return false;
    }

    public static Lf getPositionVector(DN ent) {
        return new Lf(Utility.getPosX(ent), Utility.getPosY(ent), Utility.getPosZ(ent));
    }

    public static float getYaw(Lf to) {
        float x = (float)((double)to.k() - Utility.getPosX());
        float z = (float)((double)to.W() - Utility.getPosZ());
        float var1 = (float)(StrictMath.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float rotationYaw = Utility.getRotationYaw();
        return rotationYaw + TargetStrafe.wrapAngleTo180_float(var1 - rotationYaw);
    }

    public static float wrapAngleTo180_float(float value) {
        if ((value %= 360.0f) >= 180.0f) {
            value -= 360.0f;
        }
        if (value < -180.0f) {
            value += 360.0f;
        }
        return value;
    }


    public static boolean canStrafe() {
        if (!ModuleManager.INSTANCE.isToggled(TargetStrafe.class) || !MovementUtil.isMoving() || !Keyboard.isKeyDown(57)) {
            return false;
        }
        return ModuleManager.INSTANCE.isToggled(KillAura.class) && ModuleManager.INSTANCE.isToggled(TargetStrafe.class);
    }


    }
