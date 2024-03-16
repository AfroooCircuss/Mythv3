package gov.babalar.myth.module.visual;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventNameTagRender;
import gov.babalar.myth.event.EventRender2D;
import gov.babalar.myth.event.EventRender3D;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.setting.s.SettingBool;
import gov.babalar.myth.setting.s.SettingNumber;
import gov.babalar.myth.utils.ESPUtil;
import gov.babalar.myth.utils.render.RenderUtil;
import net.minecraft.DN;
import net.minecraft.DP;
import net.minecraft.DZ;
import net.minecraft.client.lU;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.hj;

public class ESP2D extends Module {

    private final Map<DN, Vector4f> entityPosition = new HashMap<>();
    
    private final NumberFormat df = new DecimalFormat("0.#");

    public SettingBool tagSetting = new SettingBool(true, "Tags");
    public SettingBool espSetting = new SettingBool(true, "ESP");


    public ESP2D() {
        super(ModuleType.VISUAL, "ESP2D", 0);
        abstractSettings.add(tagSetting);
        abstractSettings.add(espSetting);
        toggle();
    }

    @Subscribe
    public void onRenderNameTag(EventNameTagRender e)
    {
        if(tagSetting.value)
        {
            e.cancel();
        }
    }

    @Subscribe
    public void onRender3D(EventRender3D e)
    {
        entityPosition.clear();
        for (DN entity : Utility.getEntityList()) {
            if (shouldRender(entity))
                entityPosition.put(entity, ESPUtil.getEntityPositionsOn2D(entity));
        }
    }

    @Subscribe
    public void onRender2D(EventRender2D e) {
        for (DN entity : entityPosition.keySet()) {
            Vector4f pos = entityPosition.get(entity);
            tags2(e, entity, pos);
        }
    }

    //private final Color firstColor = new Color(100, 45, 188);
    private final Color firstColor = Hud.getThemeColors()[0];
    private final Color backgroundColor = new Color(10, 10, 10, 130);

    public void csESP(EventRender2D e, DN en, Vector4f pos) {
        /*
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosX;
        final double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosY);
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ;

        GL11.glPushMatrix();
        GL11.glTranslated(x, y - 0.2, z);
        GlStateManager.disableDepth();

        GL11.glRotated(-(mc.getRenderManager()).playerViewY, 0.0D, 1.0D, 0.0D);

        final float width = 1.1f;
        final float height = 2.2f;

        final float lineWidth = 0.07f;

        draw2DBox(width, height, lineWidth, 0.04f, new Color(0, 0, 0, 165));

        if (entity.hurtTime > 0)
            draw2DBox(width, height, lineWidth, 0, new Color(255, 30, 30, 255));
        else
            draw2DBox(width, height, lineWidth, 0, ThemeUtil.getThemeColor(amount, ThemeType.GENERAL, 0.5f));

        GlStateManager.enableDepth();
        GL11.glPopMatrix();*/
    }

    public void tags2(EventRender2D e, DN en, Vector4f pos) {
        float x = pos.getX(), y = pos.getY(), right = pos.getZ(), bottom = pos.getW();
        if(en instanceof DP)
        {
            DZ entity = (DZ) en;
            //hj mcFont = Utility.getFontRendererObj();
            if(tagSetting.value)
            {
                double fontScale = .75;
                float middle = x + ((right - x) / 2);
                float textWidth = 0;
                double fontHeight;
                float health = Utility.getHealth(entity);
                String name = Utility.getPlayerName(entity);
                StringBuilder text = new StringBuilder("§f" + name);

                float healthValue = health / Utility.getMaxHealth(entity);
                Color healthColor = healthValue > .75 ? new Color(66, 246, 123) : healthValue > .5 ? new Color(228, 255, 105) : healthValue > .35 ? new Color(236, 100, 64) : new Color(255, 65, 68);
                text.append(String.format(" §7[§r%s HP§7]", df.format(health)));
                //textWidth = mcFont.a(text.toString());
                textWidth = Utility.getStringWidth(text.toString());
                middle -= (textWidth * fontScale) / 2f;
                fontHeight = 9 * fontScale;

                GL11.glPushMatrix();
                GL11.glTranslated(middle, y - (fontHeight + 2), 0);
                GL11.glScaled(fontScale, fontScale, 1);
                GL11.glTranslated(-middle, -(y - (fontHeight + 2)), 0);
                RenderUtil.drawRect(middle - 3, (float) (y - (fontHeight + 7)), textWidth + 6, (fontHeight / fontScale) + 4, backgroundColor.getRGB());
                //bU.k(0);
                RenderUtil.resetColor();
                Utility.drawString(text.toString(), middle, (float) (y - (fontHeight + 4)), healthColor.getRGB());
                //mcFont.a(text.toString(), middle, (float) (y - (fontHeight + 4)), healthColor.getRGB());
                GL11.glPopMatrix();
            }
            if(espSetting.value)
            {
                float outlineThickness = .5f;
                RenderUtil.resetColor();
                // top
                RenderUtil.drawRect(x, y, (right - x), 1, firstColor.getRGB());
                // Left
                RenderUtil.drawRect(x, y, 1, bottom - y, firstColor.getRGB());
                // bottom
                RenderUtil.drawRect(x, bottom, right - x, 1, firstColor.getRGB());
                // Right
                RenderUtil.drawRect(right, y, 1, (bottom - y) + 1, firstColor.getRGB());

                // Outline

                // top
                RenderUtil.drawRect(x - .5f, y - outlineThickness, (right - x) + 2, outlineThickness,
                        Color.BLACK.getRGB());
                // Left
                RenderUtil.drawRect(x - outlineThickness, y, outlineThickness, (bottom - y) + 1, Color.BLACK.getRGB());
                // bottom
                RenderUtil.drawRect(x - .5f, (bottom + 1), (right - x) + 2, outlineThickness, Color.BLACK.getRGB());
                // Right
                RenderUtil.drawRect(right + 1, y, outlineThickness, (bottom - y) + 1, Color.BLACK.getRGB());

                // top
                RenderUtil.drawRect(x + 1, y + 1, (right - x) - 1, outlineThickness, Color.BLACK.getRGB());
                // Left
                RenderUtil.drawRect(x + 1, y + 1, outlineThickness, (bottom - y) - 1, Color.BLACK.getRGB());
                // bottom
                RenderUtil.drawRect(x + 1, (bottom - outlineThickness), (right - x) - 1, outlineThickness,
                        Color.BLACK.getRGB());
                // Right
                RenderUtil.drawRect(right - outlineThickness, y + 1, outlineThickness, (bottom - y) - 1,
                        Color.BLACK.getRGB());
            }
        }
    }
    
    private boolean shouldRender(DN entity) {
        if(Utility.isDead(entity) || Utility.isInvisible(entity))
        {
            return false;
        }
        if (entity instanceof DZ) {
            return entity != Utility.getThePlayer();
        }
        return true;
    }

}
