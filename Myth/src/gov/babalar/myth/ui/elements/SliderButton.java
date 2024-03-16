package gov.babalar.myth.ui.elements;



import gov.babalar.myth.Utility;
import gov.babalar.myth.setting.s.SettingNumber;
import gov.babalar.myth.ui.frame.TypeFrame;
import gov.babalar.myth.utils.render.RoundedUtil;

import java.awt.*;

/**
 * ----------
 * 10/13/2023
 * 11:19 PM
 * ----------
 **/
public class SliderButton extends SettingButton {
    public SettingNumber settingNumber;
    public boolean drag;
    public SliderButton(int x, int y, SettingNumber settingNumber) {
        super(x, y);
        this.settingNumber = settingNumber;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if(drag)
        {
            final double min = settingNumber.min;
            final double max = settingNumber.max;
            final double inc = settingNumber.inc;
            final double valAbs = mouseX + 200 - (x + 1.0);
            double perc = valAbs / TypeFrame.width - 2.0;
            perc = Math.min(Math.max(0.0, perc), 1.0);
            final double valRel = (max - min) * perc;
            double val = min + valRel;
            val = Math.round(val * (1.0 / inc)) / (1.0 / inc);
            set(val);
        }
        RoundedUtil.roundedRect(x , y , TypeFrame.width , 20 , 0,TypeFrame.NATURAL);
        double sWidth = (TypeFrame.width - 2.0) * ((settingNumber.value - settingNumber.min) / (settingNumber.max - settingNumber.min));
       // RenderUtils.drawEpicGradient(x , y + 18, (int) sWidth, 2,255);
        Color color = new Color(50, 168, 164).darker();
        RoundedUtil.roundedRect(x , y + 18, (int) sWidth, 2,0,color);
        Utility.drawStringWithShadow(settingNumber.name + " " + settingNumber.value , x + 5 , y + 4 , -1);
    }

    @Override
    public void mouseClicked(int mX, int mY, int mouseButton) {
        super.mouseClicked(mX, mY, mouseButton);
        if(mX > x && mX < x + TypeFrame.width && mY > y && mY < y + 20)
        {
            final double min = settingNumber.min;
            final double max = settingNumber.max;
            final double inc = settingNumber.inc;
            final double valAbs = mX + 200 - (x + 1.0);
            double perc = valAbs / TypeFrame.width - 2.0;
            perc = Math.min(Math.max(0.0, perc), 1.0);
            final double valRel = (max - min) * perc;
            double val = min + valRel;
            val = Math.round(val * (1.0 / inc)) / (1.0 / inc);
            set(val);
            drag = true;
        }
    }

    @Override
    public void mouseReleased(int mX, int mY, int mouseButton) {
        super.mouseReleased(mX, mY, mouseButton);
        drag = false;

    }
    public void set(double val)
    {

        settingNumber.value = (Math.round(val * (1.0 / settingNumber.inc)) / (1.0 / settingNumber.inc));
    }
}
