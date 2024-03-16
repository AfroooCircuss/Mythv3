package gov.babalar.myth.event;

import net.minecraft.Tz;

public class EventReceivePacket extends Event {

    private Tz<?> packet;

    public EventReceivePacket(Tz<?> packet) {
        this.packet = packet;
    }

    public Tz<?> getPacket() {
        return packet;
    }
}
