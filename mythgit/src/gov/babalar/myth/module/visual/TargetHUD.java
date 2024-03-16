package gov.babalar.myth.module.visual;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventRender2D;
import gov.babalar.myth.event.EventRender3D;
import gov.babalar.myth.managers.ModuleManager;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.module.combat.KillAura;
import gov.babalar.myth.utils.ESPUtil;
import gov.babalar.myth.utils.Pair;
import gov.babalar.myth.utils.math.MathHelper;
import gov.babalar.myth.utils.render.ColorUtil;
import gov.babalar.myth.utils.render.GLUtil;
import gov.babalar.myth.utils.render.RenderUtil;
import gov.babalar.myth.utils.render.StencilUtil;
import net.minecraft.DN;
import net.minecraft.DP;
import net.minecraft.DZ;
import net.minecraft.client.CC;
import net.minecraft.client.eT;
import net.minecraft.client.kb;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;
import java.lang.reflect.Field;
import net.minecraft.client.eR; //Mapping: Gui
import net.minecraft.client.kP;
import java.util.HashMap;
import java.util.Map;

public class TargetHUD extends Module {

    public TargetHUD()
    {
        super(ModuleType.VISUAL, "TargetHUD", 0);
        toggle();
    }

    private final Map<DN, Vector4f> entityPosition = new HashMap<>();
    private KillAura killAura;
    private DZ target;
    private Vector4f targetVector;
    private int width;
    private int height = 70;

    @Subscribe
    public void onRender3DEvent(EventRender3D event) {
        /*
        entityPosition.clear();
        if (killAura == null) {
            killAura = (KillAura) ModuleManager.INSTANCE.getModule(KillAura.class);
        }*/
        entityPosition.clear();
        if (killAura == null) {
            killAura = (KillAura) ModuleManager.INSTANCE.getModule(KillAura.class);
        }
        for(final DZ entity : killAura.targets)
        {
            entityPosition.putIfAbsent(entity, ESPUtil.getEntityPositionsOn2D(entity));
        }
        /*
        for(DN entity : killAura.targets)
        {
            entityPosition.put(entity, ESPUtil.getEntityPositionsOn2D(entity));
        }*/
    }


    @Subscribe
    public void onRender(EventRender2D e)
    {
        try {
            if (killAura == null) {
                killAura = (KillAura) ModuleManager.INSTANCE.getModule(KillAura.class);
            }
            target = null;
            if (KillAura.target != null) {
                target = KillAura.target;
            }
            if (target != null) {

                //RenderUtil.scaleStart(x + getWidth() / 2f, y + getHeight() / 2f, (float) (.5 + openAnimation.getOutput().floatValue()) * trackScale);
                //float alpha = Math.min(1, openAnimation.getOutput().floatValue() * 2);
                switch (killAura.mode.getMode()) {
                    case "Single":
                    {
                        float trackScale = 1;
                        targetVector = entityPosition.get(target);
                        if(targetVector == null)
                        {
                            return;
                        }
                        float newWidth = (targetVector.getZ() - targetVector.getX()) * 1.4f;
                        trackScale = Math.min(1, newWidth / getWidth());

                        Pair<Float, Float> coords = getTrackedCoords();
                        float x = coords.getFirst();
                        float y = coords.getSecond();

                        GL11.glPushMatrix();
                        renderHUD((int) x, (int) y, 1, target);
                        GL11.glPopMatrix();
                        break;
                    }
                    case "Multi": {
                        for (DZ targetP : killAura.targets) {
                            float trackScale = 1;
                            targetVector = entityPosition.get(targetP);
                            if(targetVector == null)
                            {
                                return;
                            }
                            float newWidth = (targetVector.getZ() - targetVector.getX()) * 1.4f;
                            trackScale = Math.min(1, newWidth / getWidth());

                            Pair<Float, Float> coords = getTrackedCoords();
                            float x = coords.getFirst();
                            float y = coords.getSecond();
                            GL11.glPushMatrix();
                            renderHUD((int) x, (int) y, 1, targetP);
                            GL11.glPopMatrix();
                        }
                        break;
                    }
                }
                //RenderUtil.scaleEnd();

            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void renderHUD(int x, int y, float alpha, DZ target)
    {

        int textColor = ColorUtil.applyOpacity(-1, alpha);
        width = Math.max(155, Utility.getStringWidth(Utility.getPlayerName(target)) + 75);
        height = 50;
        RenderUtil.setAlphaLimit(0);

        Color c = ColorUtil.brighter(new Color(30, 30, 30), .65f);
        float colorAlpha = .8f * alpha;
        RenderUtil.drawRoundedRect(x, y, getWidth(), getHeight(), 3, ColorUtil.applyOpacity(c, colorAlpha).getRGB());

        int size = 38;
        if (target instanceof kP) {
            StencilUtil.initStencilToWrite();
            RenderUtil.circleNoSmoothRGB(x + 10, y + (getHeight() / 2f) - (size / 2f), size, -1);
            StencilUtil.readStencilBuffer(1);
            RenderUtil.resetColor();
            RenderUtil.setAlphaLimit(0);

            RenderUtil.color(textColor);
            renderPlayerModelTexture(x + 10, y + (getHeight() / 2) - (size / 2), size, size, (kP) target);
            StencilUtil.uninitStencilBuffer();
        } else {
            //EFontUtil.tenacityBoldFont32.drawCenteredString("?", x + 30, y + 25 - 9 / 2, textColor);
            Utility.drawCentered("?", x + 30, y + 25 - 9 / 2, textColor);
        }
        Utility.drawCentered(Utility.getPlayerName( target),x + 10 + size + ((getWidth() - (10 + size)) / 2), y + 10, textColor);

        //float healthPercentage = (Utility.getHealth(target) + Entity.getAbsorptionAmount(target)) / (Utility.getMaxHealth(target) + Entity.getAbsorptionAmount(target));
        float healthPercentage = (Utility.getHealth(target) + 0) / (Utility.getMaxHealth(target) + 0);
        float healthBarWidth = getWidth() - (size + 30);

        float newHealthWidth = (healthBarWidth) * healthPercentage;
       // animatedHealthBar.animate(newHealthWidth, 18);



        RenderUtil.drawRoundedRect(x + 20 + size, y + 25, healthBarWidth, 4, 2, ColorUtil.applyOpacity(Color.BLACK, .3f * alpha).getRGB());
        RenderUtil.drawRoundedRect(x + 20 + size, y + 25, newHealthWidth, 4, 2,  ColorUtil.applyOpacity(Color.WHITE, alpha).getRGB());
        //RenderUtil.roundedRect(x + 20 + size, y + 25, healthBarWidth, 4, 2,  ColorUtil.applyOpacity(Color.BLACK, .3f * alpha));
        //RenderUtil.roundedRect(x + 20 + size, y + 25, animatedHealthBar.getOutput(), 4, 2, ColorUtil.applyOpacity(Color.WHITE, alpha));
        //RenderUtil.roundedRect(x + 20 + size, y + 25, newHealthWidth, 4, 2, ColorUtil.applyOpacity(Color.WHITE, alpha));

        String healthText = MathHelper.DF_0.format(healthPercentage * 100) + "%";

        Utility.drawCentered(healthText + " - " + Math.round(Utility.getDistanceToEntity(target)) + "m", x + 10 + size + ((getWidth() - (10 + size)) / 2), y + 35, textColor);

        //EFontUtil.tenacityFont18.drawCenteredString(healthText + " - " + Math.round(Utility.getDistanceToEntity(target)) + "m", x + 10 + size + ((getWidth() - (10 + size)) / 2f), y + 35, textColor);
    }

    public static void renderPlayerModelTexture(int x, int y, float width, float height, kP target) {
        GLUtil.startBlend();
        //iz
        Utility.getTextureManager().E(target.R());
        eR.L( x, y, 8.0F, 8.0F, 8, 8, (int) width, (int) height, 64.0F, 64.0F);
        GLUtil.endBlend();
    }

    private Pair<Float, Float> getTrackedCoords() {
        CC sr = new CC(mc);
        float width = getWidth(), height = getHeight();
        float x = targetVector.getX(), y = targetVector.getY();
        float entityWidth = (targetVector.getZ() - targetVector.getX());
        float entityHeight = (targetVector.getW() - targetVector.getY());
        float middleX = x + entityWidth / 2f - width / 2f;
        float middleY = y + entityHeight / 2f - height / 2f;
        return Pair.of(x + entityWidth - (width / 4f), middleY);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }


}
