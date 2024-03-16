package gov.babalar.myth.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.ui.frame.IFrame;
import gov.babalar.myth.ui.frame.TypeFrame;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * ----------
 * 10/13/2023
 * 9:02 PM
 * ----------
 **/
public class UIScreen extends net.minecraft.client.en {
    public static final IFrame[] frames = {
            new TypeFrame(100 , 100 , ModuleType.COMBAT),
            new TypeFrame(250 , 100 , ModuleType.MOTION),
            new TypeFrame(400 , 100 , ModuleType.VISUAL),
            new TypeFrame(550 , 100 , ModuleType.MISC),
    };

    /*
    drawScreen method
  */
    @Override
    public void Z(int i, int i1, float v) {
        super.Z(i, i1, v);
        for (IFrame frame : frames) {
            frame.drawScreen(i,i1,v);
        }
    }


    /*
        mouseReleased method
     */
    @Override
    protected void R(int i, int i1, int i2) {
        super.R(i, i1, i2);
        for (IFrame frame : frames) {
            frame.mouseReleased(i,i1,i2);
        }
    }

    /*
        keyTyped method
     */
    @Override
    protected void P(char c, int i) throws IOException {
        super.P(c, i);
        for (IFrame frame : frames) {
            frame.keyTyped(c,i);
        }
    }

    /*
        mouseClicked method
     */
    @Override
    protected void g(int i, int i1, int i2) throws IOException {
        super.g(i, i1, i2);
        for (IFrame frame : frames) {
            frame.mouseClicked(i,i1,i2);
        }
    }
}
