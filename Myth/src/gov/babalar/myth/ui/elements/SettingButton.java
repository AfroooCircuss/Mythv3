package gov.babalar.myth.ui.elements;


import gov.babalar.myth.ui.frame.IFrame;

/**
 * ----------
 * 10/13/2023
 * 10:56 PM
 * ----------
 **/
public class SettingButton implements IFrame {
    public int x;
    public int y;

    public SettingButton(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getHeight()
    {
        return 15;
    }
}
