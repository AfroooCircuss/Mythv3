package gov.babalar.myth.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class HWIDUtils {

    private static String hwid;


    public void generateHWID()
    {
        hwid = RandomStringUtils.randomAlphabetic(12);
    }

    public static String getHwid() {
        return hwid;
    }
}
