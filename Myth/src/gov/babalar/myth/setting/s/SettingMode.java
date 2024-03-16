package gov.babalar.myth.setting.s;

import gov.babalar.myth.setting.AbstractSetting;

/**
 * ----------
 * 11/4/2023
 * 1:32 PM
 * ----------
 **/
public class SettingMode extends AbstractSetting {
    public int index;
    public String name;
    public String[] values;
    public SettingMode(String name, String[] value, int index) {
        this.name = name;
        this.values = value;
        this.index = index;
    }

    public String getMode() {
        return values[index];
    }
    public void switchMode()
    {
        int indexUpper = index + 1;
        if(indexUpper >= values.length)
        {
            index = 0;
            return;
        }
        index = indexUpper;
    }
}
