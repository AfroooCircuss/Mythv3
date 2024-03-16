package gov.babalar.myth.ui.frame;


import gov.babalar.myth.Utility;
import gov.babalar.myth.managers.ModuleManager;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.ui.elements.ModuleButton;
import gov.babalar.myth.utils.render.RenderUtil;
import gov.babalar.myth.utils.render.RoundedUtil;


import java.awt.*;
import java.util.ArrayList;

/**
 * ----------
 * 10/13/2023
 * 9:08 PM
 * ----------
 **/
public class TypeFrame implements IFrame {
    public int x;
    public int y;
    public static final int width = 100;
    public static final Color NATURAL = new Color(34, 33, 38,255);
    public ModuleType modType;
    public boolean dragging = false;
    public ArrayList<ModuleButton> modules = new ArrayList<>();
    public TypeFrame(int x, int y, ModuleType modType) {
        this.x = x;
        this.y = y;
        this.modType = modType;
        ModuleManager.INSTANCE.modules.stream().filter(module -> module.getType() == modType).forEach(module -> modules.add(new ModuleButton(0,0 , module)));
        int i = 0;
        for (ModuleButton module : modules) {
            module.x = this.x;
            module.y = this.y + 18 + i;
            i+=module.getHeight();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        IFrame.super.drawScreen(mouseX, mouseY, partialTicks);
        if(dragging)
        {
            this.x = mouseX - 5;
            this.y = mouseY - 5;
        }
        int i = 0;
        for (ModuleButton module : modules) {
            module.x = this.x;
            module.y = this.y + 18 + i;
            i+=module.getHeight();
        }
        RoundedUtil.roundedRect(x-1 , y , width+2 , 18 + i + 4 , 8 , NATURAL);
        Utility.drawCentered(modType.name() , (int) ((float) x + (width / 2)), y + 7 , -1);
        for (IFrame module : modules) {
            module.drawScreen(mouseX,mouseY,partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        IFrame.super.mouseClicked(mouseX, mouseY, mouseButton);
        dragging = (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + 18);
        for (IFrame module : modules) {
            module.mouseClicked(mouseX,mouseY,mouseButton);
        }

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        IFrame.super.mouseReleased(mouseX, mouseY, mouseButton);
        dragging = false;
        for (IFrame module : modules) {
            module.mouseReleased(mouseX,mouseY,mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        IFrame.super.keyTyped(typedChar, keyCode);
        for (IFrame module : modules) {
            module.keyTyped(typedChar,keyCode);
        }
    }
}
