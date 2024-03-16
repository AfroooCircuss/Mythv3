package gov.babalar.myth.utils.player;


import gov.babalar.myth.Utility;

import net.minecraft.tB; //CHAT COMPONENT TEXT
import net.minecraft.y;

public class ChatUtil {

    public static void print(boolean prefix, String message) {
        if (Utility.getThePlayer() != null) {
            if (prefix) message = "§sMyth §8» §f" + message;
            Utility.getThePlayer().b(new tB(message));
        }
    }
    public static void print(Object o) {
        print(true, String.valueOf(o));
    }

    public static void send(String message) {
        if (Utility.getThePlayer() != null) {
            Utility.sendPacket(new y(message));
        }
    }

}
