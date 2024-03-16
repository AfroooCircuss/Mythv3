package gov.babalar.myth.ui.elements;


import gov.babalar.myth.Utility;
import gov.babalar.myth.ui.frame.TypeFrame;
import gov.babalar.myth.utils.render.RoundedUtil;

import java.awt.*;

/**
 * ----------
 * 10/13/2023
 * 10:47 PM
 * ----------
 **/
public abstract class ClickableElement extends SettingButton {
    public abstract String getText();
    public abstract void clicked();
    public abstract Color getBackground();

    public ClickableElement(int x, int y) {
        super(x, y);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        RoundedUtil.roundedRect(x , y , TypeFrame.width , 15 , 0,getBackground());
        Utility.drawStringWithShadow(getText() , x + 5, y + 4 , -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(mouseX > x && mouseX < x + TypeFrame.width && mouseY > y && mouseY < y + getHeight())
            clicked();
    }

}
