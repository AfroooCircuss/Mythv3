package gov.babalar.myth.module.visual;

import gov.babalar.myth.Utility;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import org.lwjgl.input.Keyboard;

/**
 * ----------
 * 10/1/2023
 * 7:25 PM
 * ----------
 **/
public class ClickGui extends Module {
    public ClickGui() {
        super(ModuleType.VISUAL, "ClickGui", Keyboard.KEY_RSHIFT);
    }
    public static final gov.babalar.myth.ui.UIScreen INSTANCE = new gov.babalar.myth.ui.UIScreen();
    @Override
    public void onEnable() {
        Utility.displayGui(INSTANCE);
        toggle();
    }
}
