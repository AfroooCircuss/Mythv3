package gov.babalar.myth.ui.frame;

/**
 * ----------
 * 10/13/2023
 * 9:03 PM
 * ----------
 **/
public interface IFrame {
    default void drawScreen(int mouseX, int mouseY, float partialTicks){}
    default void mouseClicked(int mouseX, int mouseY, int mouseButton){}
    default void mouseReleased(int mouseX, int mouseY, int mouseButton){}
    default void keyTyped(char typedChar, int keyCode){}
}
