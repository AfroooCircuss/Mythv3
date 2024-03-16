/*
 * Decompiled with CFR 0.151.
 */
package gov.babalar.myth.event;


public class EventKey
extends Event {
    private final int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}

