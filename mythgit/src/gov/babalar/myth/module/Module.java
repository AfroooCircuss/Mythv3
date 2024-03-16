package gov.babalar.myth.module;

import gov.babalar.myth.Client;
import gov.babalar.myth.setting.AbstractSetting;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * ----------
 * 9/30/2023
 * 12:20 AM
 * ----------
 **/
public abstract class Module extends Client {
    private boolean enabled;
    private ModuleType type;
    private String name;
    private String suffix = "";
    private int key;
    public ArrayList<AbstractSetting> abstractSettings = new ArrayList<>();
    public Module(ModuleType type, String name, int key) {
        this.type = type;
        this.name = name;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void toggle()
    {
        this.enabled = !this.enabled;
        if (this.enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void onEnable() {
        bus.register(this);
    }

    public void onDisable() {
        bus.unregister(this);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ModuleType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
