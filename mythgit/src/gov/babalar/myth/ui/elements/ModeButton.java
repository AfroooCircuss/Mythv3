package gov.babalar.myth.ui.elements;


import gov.babalar.myth.setting.s.SettingMode;
import gov.babalar.myth.ui.frame.IFrame;
import gov.babalar.myth.ui.frame.TypeFrame;

import java.awt.*;

/**
 * ----------
 * 10/13/2023
 * 10:50 PM
 * ----------
 **/
public class ModeButton extends ClickableElement {
    public SettingMode mode;

    public ModeButton(int x, int y, SettingMode mode) {
        super(x, y);
        this.mode = mode;
    }

    @Override
    public String getText() {
        return mode.name +":"+ mode.getMode();
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public void clicked() {
        mode.switchMode();
    }

    @Override
    public Color getBackground() {
        return TypeFrame.NATURAL;
    }
}
