// Decompiled with: CFR 0.152
// Class Version: 7
package gov.babalar.myth.utils.gizlibipas;

import com.google.gson.annotations.Expose;

public class JsonSOGameRequest {
    @Expose
    private String minecraftCHC;
    @Expose
    private String launcherCHC;
    @Expose
    private String token;
    @Expose
    private String suid;
    @Expose
    private String sig;

    public JsonSOGameRequest(String minecraftCHC, String launcherCHC, String token, String suid, String sig) {
        this.minecraftCHC = minecraftCHC;
        this.launcherCHC = launcherCHC;
        this.token = token;
        this.suid = suid;
        this.sig = sig;
    }

    public String getMinecraftChecksum() {
        return this.minecraftCHC;
    }

    public String getLauncherChecksum() {
        return this.launcherCHC;
    }

    public String getToken() {
        return this.token;
    }

    public String getSUID() {
        return this.suid;
    }

    public String d() {
        return this.sig;
    }
}
