package gov.babalar.myth.event;

import net.minecraft.Tz;

public class EventPacketSent extends Event {
    private Tz<?> packet;

    public EventPacketSent(Tz<?> packet) {
        this.packet = packet;
    }

    public void setPacket(Tz<?> packet) {
        this.packet = packet;
    }

    public Tz<?> getPacket() {
        return packet;
    }

}
