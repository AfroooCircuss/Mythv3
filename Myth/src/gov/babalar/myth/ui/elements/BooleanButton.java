package gov.babalar.myth.ui.elements;

import gov.babalar.myth.setting.s.SettingBool;
import gov.babalar.myth.ui.frame.TypeFrame;

import java.awt.*;

/**
 * ----------
 * 10/13/2023
 * 10:50 PM
 * ----------
 **/
public class BooleanButton extends ClickableElement {
    public SettingBool settingBool;

    public BooleanButton(int x, int y, SettingBool settingBool) {
        super(x, y);
        this.settingBool = settingBool;
    }

    @Override
    public String getText() {
        return settingBool.name;
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public void clicked() {
        settingBool.value = (!settingBool.value);
    }

    @Override
    public Color getBackground() {
        return settingBool.value ? new Color(50, 168, 164).darker() : TypeFrame.NATURAL;
    }
}
