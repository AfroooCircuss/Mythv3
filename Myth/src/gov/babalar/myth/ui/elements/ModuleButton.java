package gov.babalar.myth.ui.elements;


import gov.babalar.myth.Utility;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.setting.AbstractSetting;
import gov.babalar.myth.setting.s.SettingBool;
import gov.babalar.myth.setting.s.SettingMode;
import gov.babalar.myth.setting.s.SettingNumber;
import gov.babalar.myth.ui.frame.IFrame;
import gov.babalar.myth.ui.frame.TypeFrame;
import gov.babalar.myth.utils.render.RoundedUtil;

import java.awt.*;
import java.util.ArrayList;

/**
 * ----------
 * 10/13/2023
 * 9:30 PM
 * ----------
 **/
public class ModuleButton implements IFrame {
    public int x;
    public int y;
    public Module module;
    public int tick;
    public boolean shouldShow;
    public ArrayList<SettingButton> settings = new ArrayList<>();
    public ModuleButton(int x, int y, Module module) {
        this.x = x;
        this.y = y;
        this.module = module;
        for (AbstractSetting setting : module.abstractSettings) {
            if(setting instanceof SettingBool) {
                settings.add(new BooleanButton(0,0, (SettingBool) setting));
            }
            if(setting instanceof SettingMode) {
                settings.add(new ModeButton(0,0, (SettingMode) setting));
            }
            if(setting instanceof SettingNumber)
            {
                settings.add(new SliderButton(0,0,(SettingNumber) setting));
            }
        }
        settings.add(new BindButton(0,0,module));
        syncPos();
    }
    public void syncPos()
    {
        int i = 0;
        for (SettingButton setting : settings) {
            setting.x = this.x;
            setting.y = this.y+15+i;
            i+=setting.getHeight();
        }
    }
    public int getHeight()
    {
        if(!shouldShow)
            return 15;
        int i = 15;
        for (SettingButton setting : settings) {
            i+=setting.getHeight();
        }
        return i;
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        syncPos();
        if(module.isEnabled() && tick < 255)
            tick+=5;
        if(!module.isEnabled() && tick > 0)
            tick-=5;
        IFrame.super.drawScreen(mouseX, mouseY, partialTicks);
        RoundedUtil.roundedRect(x , y , TypeFrame.width , 15 , 0,TypeFrame.NATURAL.darker());
        RoundedUtil.roundedRect(x , y , TypeFrame.width , 15 , 0,new Color(50, 168, 164,tick));
       /* GlStateManager.pushMatrix();
        RenderUtils.drawEpicGradient(x , y , (int) (TypeFrame.width), (int) (15 * (tick * 0.01)), 255);
        GlStateManager.popMatrix(); */

        Utility.drawStringWithShadow(module.getName() , x + 5, y + 4 , -1);
        if(shouldShow)
        {
            for (SettingButton setting : settings) {
                setting.drawScreen(mouseX,mouseY,partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        IFrame.super.mouseClicked(mouseX, mouseY, mouseButton);
        if(mouseX > x && mouseX < x + TypeFrame.width && mouseY > y && mouseY < y + 15) {
            switch (mouseButton) {
                case 0: {
                    module.toggle();
                    break;
                }
                case 1: {
                    shouldShow = !shouldShow;
                }
            }
        }
        if(shouldShow)
        {
            for (SettingButton setting : settings) {
                setting.mouseClicked(mouseX,mouseY,mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        IFrame.super.mouseReleased(mouseX, mouseY, mouseButton);
        if(shouldShow)
        {
            for (SettingButton setting : settings) {
                setting.mouseReleased(mouseX,mouseY,mouseButton);
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        IFrame.super.keyTyped(typedChar, keyCode);
        if(shouldShow)
        {
            for (SettingButton setting : settings) {
                setting.keyTyped(typedChar,keyCode);
            }
        }
    }
}
