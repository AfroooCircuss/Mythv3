package gov.babalar.myth.setting.s;

import gov.babalar.myth.setting.AbstractSetting;

/**
 * ----------
 * 10/1/2023
 * 8:36 PM
 * ----------
 **/
public class SettingNumber extends AbstractSetting
{
    public double value;
    public double min;
    public double max;
    public double inc;
    public String name;

    public SettingNumber(double value, double min, double max, double inc, String name) {
        this.value = value;
        this.min = min;
        this.max = max;
        this.inc = inc;
        this.name = name;
    }
}
