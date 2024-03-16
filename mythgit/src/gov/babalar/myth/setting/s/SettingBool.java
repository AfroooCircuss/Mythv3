package gov.babalar.myth.setting.s;

import gov.babalar.myth.setting.AbstractSetting;

/**
 * ----------
 * 10/1/2023
 * 8:37 PM
 * ----------
 **/
public class SettingBool extends AbstractSetting
{
    public boolean value;
    public String name;

    public SettingBool(boolean value, String name) {
        this.value = value;
        this.name = name;
    }
}
