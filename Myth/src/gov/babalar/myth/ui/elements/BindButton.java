package gov.babalar.myth.ui.elements;

import gov.babalar.myth.module.Module;
import gov.babalar.myth.ui.frame.TypeFrame;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * ----------
 * 10/13/2023
 * 10:50 PM
 * ----------
 **/
public class BindButton extends ClickableElement {
    public Module module;

    public BindButton(int x, int y, Module module) {
        super(x, y);
        this.module = module;
    }
    public boolean hovering;
    @Override
    public String getText() {
        return hovering ? "Listening..." : "Bind: " + Keyboard.getKeyName(module.getKey());
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public void clicked() {
        hovering = !hovering;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if(hovering)
        {
            if(typedChar == '0')
                module.setKey(0);
            else
                module.setKey(keyCode);
            hovering = false;
        }
    }

    @Override
    public Color getBackground() {
        return TypeFrame.NATURAL;
    }
}
