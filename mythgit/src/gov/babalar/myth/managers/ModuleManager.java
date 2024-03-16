    package gov.babalar.myth.managers;

    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    import java.io.PrintStream;
    import java.lang.reflect.Field;
    import java.nio.charset.StandardCharsets;
    import java.util.*;
    import java.util.concurrent.atomic.AtomicReference;

    import com.google.common.eventbus.Subscribe;

    import com.google.gson.JsonObject;
    import gov.babalar.myth.Utility;
    import gov.babalar.myth.event.EventKey;
    import gov.babalar.myth.event.EventPacketSent;
    import gov.babalar.myth.event.EventReceivePacket;
    import gov.babalar.myth.module.Module;
    import gov.babalar.myth.Client;
    import gov.babalar.myth.module.combat.KillAura;
    import gov.babalar.myth.module.combat.TargetStrafe;
    import gov.babalar.myth.module.combat.TargetTeleport;
    import gov.babalar.myth.module.combat.Velocity;
    import gov.babalar.myth.module.misc.*;
    import gov.babalar.myth.module.movement.*;
    import gov.babalar.myth.module.visual.ClickGui;
    import gov.babalar.myth.module.visual.ESP2D;
    import gov.babalar.myth.module.visual.Hud;
    import gov.babalar.myth.module.visual.TargetHUD;
    import gov.babalar.myth.utils.gizlibipas.CokGizli;
    import io.netty.buffer.ByteBuf;
    import io.netty.buffer.Unpooled;
    import net.minecraft.br;
    import net.minecraft.fK;
    import net.minecraft.hi;
    import net.minecraft.o3;
    import org.apache.commons.lang3.RandomStringUtils;

    import javax.rmi.CORBA.Util;

    public enum ModuleManager {
        INSTANCE;

        public List<Module> modules;

        ModuleManager() {
            this.modules = new ArrayList<>();
        }

        @Subscribe
        public void listenKey(EventKey event) {
            for (Module mod : this.modules) {
                if (mod.getKey() == event.getKey())
                    mod.toggle();
            }
        }

        @Subscribe
        public void onPacketReceive(EventReceivePacket event)
        {
            /*
            try {
                if(event.getPacket().getClass().isAssignableFrom(Class.forName("EW")))
                {
                    Field bb = event.getPacket().getClass().getDeclaredField("B");
                    bb.setAccessible(true);
                    Field bb2 = event.getPacket().getClass().getDeclaredField("J");
                    bb2.setAccessible(true);
                    Map<String, String> map = (HashMap<String, String>) bb.get(event.getPacket());
                    boolean bb3 = bb2.getBoolean(event.getPacket());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            /*
            if(event.getPacket() instanceof net.minecraft.o3)
            {
                net.minecraft.o3 scustom = (o3) event.getPacket();
                hi packetBuffer = new hi(scustom.a().copy());
                String channel = scustom.Y();
                byte[] bytes = packetBuffer.readBytes(packetBuffer.readableBytes()).array();
                String data = new String(bytes);
            }*/
        }


        public static int c03Index = 0;


        @Subscribe
        public void onPacketSent(EventPacketSent event) {
            if(c03Index < 220 && (event.getPacket() instanceof net.minecraft.bI || event.getPacket() instanceof net.minecraft.br))
            {
                event.setCancelled(true);
                c03Index++;
            }
            if(event.getPacket() instanceof fK)
            {
                try {
                    net.minecraft.fK custompayload = (fK) event.getPacket();
                    ByteBuf packetBuffer = custompayload.e().copy();
                    String channel = custompayload.A();
                    byte[] bytes = packetBuffer.readBytes(packetBuffer.readableBytes()).array();
                    String data = new String(bytes);
                    if (channel.equalsIgnoreCase("Teyyapclntvars")) {
                        if (data.contains("processList###")) {
                            //event.setCancelled(true);
                            JsonObject object = new JsonObject();
                            object.addProperty("processList", "{\"[System Process]\":{\"idList\":[0]},\"System\":{\"idList\":[4]},\"svchost.exe\":{\"idList\":[6660,4132,2088,4656,1076,584,1116,1636,6244,5236,12932,7304,1164,1676,3216,4752,1684,2196,2200,8344,2212,3748,3756,3252,3764,3256,3780,11972,7896,1768,8424,3820,3316,2292,2820,7452,1840,6476,2392,3932,4444,8032,8040,1900,4464,1396,4980,1916,11140,7052,4000,3492,6056,10664,4012,1456,2480,1464,2488,8120,6588,1472,1484,4556,7120,6624,9184,2532,3044,2024,3564,7148,1524,3572,3580],\"windows\":[\"Default IME\",\"Windows Push Notifications Platform\",\"Default IME\"]},\"fontdrvhost.exe\":{\"idList\":[1032,1040]},\"fsnotifier.exe\":{\"idList\":[6672]},\"smss.exe\":{\"idList\":[532]},\"RuntimeBroker.exe\":{\"idList\":[8732,8832,3744,5376,7972],\"windows\":[\"Default IME\",\"Default IME\",\"Default IME\"]},\"Discord.exe\":{\"idList\":[2092,6280,12952,12576,12076,3540],\"windows\":[\"Temp Window\",\"Default IME\",\"@dadaÅŸ - Discord\",\"MSCTFIME UI\",\"Default IME\",\"Default IME\"]},\"brave.exe\":{\"idList\":[7728,14392,6224,4740,10376,4788,9928,760,14132,6468,5552,8140],\"windows\":[\"ChatGPT - Brave\",\"MSCTFIME UI\",\"Default IME\",\"Default IME\",\"Default IME\"]},\"java.exe\":{\"idList\":[2096,13376,7756,5220,15728,1980]},\"NVDisplay.Container.exe\":{\"idList\":[2100,2780],\"windows\":[\"NvSvc\",\"UxdService\",\"NvContainerWindowClass00000ADC\"]},\"WmiPrvSE.exe\":{\"idList\":[7732]},\"conhost.exe\":{\"idList\":[2108,9804,6792,13976,8360,7448,10592,14176],\"windows\":[\"MSCTFIME UI\",\"Default IME\"]},\"winlogon.exe\":{\"idList\":[76]},\"javaw.exe\":{\"idList\":[14428,4792,16200],\"windows\":[\"Minecraft 1.8.9 - SonOyuncu Client\",\"__wglDummyWindowFodder\",\"NVOGLDC invisible\",\"D3DFocusWindow\",\"theAwtToolkitWindow\",\"MSCTFIME UI\",\"Default IME\",\"Default IME\",\"Default IME\",\"MSCTFIME UI\",\"Default IME\",\"MSCTFIME UI\",\"Default IME\", \"__wglDummyWindowFodder\",\"NVOGLDC invisible\",\"TrayMessageWindow\",\"PopupMessageWindow\",\"D3DFocusWindow\",\"theAwtToolkitWindow\",\"MSCTFIME UI\",\"Default IME\",\"Default IME\",\"Default IME\",\"MSCTFIME UI\",\"Default IME\"]},\"vgtray.exe\":{\"idList\":[11872],\"windows\":[\"MSCTFIME UI\",\"Default IME\"]},\"sihost.exe\":{\"idList\":[4708]},\"NVIDIA Share.exe\":{\"idList\":[10348,11136,11712],\"windows\":[\"NVIDIA GeForce Overlay\",\"NVIDIA GeForce Overlay DT\",\"MSCTFIME UI\",\"Default IME\"]},\"taskhostw.exe\":{\"idList\":[13932,4240,5108],\"windows\":[\"Task Host Window\",\"Task Host Window\"]},\"ShareX.exe\":{\"idList\":[10352],\"windows\":[\"ShareX 15.0\",\"GDI+ Window (ShareX.exe)\",\".NET-BroadcastEventWindow.4.0.0.0.1a52015.0\",\"MSCTFIME UI\",\"Default IME\",\"Default IME\"]},\"audiodg.exe\":{\"idList\":[10356]},\"Spotify.exe\":{\"idList\":[1140,10428,12536,1876,1936,10220],\"windows\":[\"Default IME\",\"Spotify Free\",\"GDI+ Window (Spotify.exe)\",\"MSCTFIME UI\",\"Default IME\",\"Default IME\",\"Default IME\"]},\"Registry\":{\"idList\":[124]},\"WindscribeEngine.exe\":{\"idList\":[10884],\"windows\":[\"WINDSCRIBE_HIDDEN_WINDOW\",\"Default IME\"]},\"dllhost.exe\":{\"idList\":[9356]},\"explorer.exe\":{\"idList\":[5788],\"windows\":[\"Battery Meter\",\"Network Flyout\",\"Window\",\"GDI+ Window (Explorer.EXE)\",\"BluetoothNotificationAreaIconWindowClass\",\"MS_WebcheckMonitor\",\"DDE Server Window\",\"Program Manager\",\"MSCTFIME UI\",\"Default IME\",\"MSCTFIME UI\",\"Default IME\",\"MSCTFIME UI\",\"Default IME\",\"Default IME\",\"Default IME\",\"MSCTFIME UI\",\"Default IME\",\"MSCTFIME UI\",\"Default IME\",\"MSCTFIME UI\",\"Default IME\",\"MSCTFIME UI\",\"Default IME\",\"MSCTFIME UI\",\"Default IME\",\"Default IME\",\"Default IME\",\"Default IME\",\"Default IME\",\"Default IME\"]},\"ctfmon.exe\":{\"idList\":[5296]},\"Everything.exe\":{\"idList\":[3772,11772],\"windows\":[\"Default IME\"]},\"dwm.exe\":{\"idList\":[1236],\"windows\":[\"DWM Notification Window\"]},\"csrss.exe\":{\"idList\":[740,856]},\"NVIDIA Web Helper.exe\":{\"idList\":[10476],\"windows\":[\"NVIDIA NodeJS Share Window\",\"{5AEA657D-F3F5-4BD8-BFE9-A4B537FA24C3}\",\"Default IME\",\"Default IME\"]},\"kpm_service.exe\":{\"idList\":[3824]},\"BraveCrashHandler.exe\":{\"idList\":[7932]},\"UserOOBEBroker.exe\":{\"idList\":[5900]},\"SearchApp.exe\":{\"idList\":[8472]},\"Memory Compression\":{\"idList\":[2344]},\"NLSvc.exe\":{\"idList\":[3892]},\"wininit.exe\":{\"idList\":[832]},\"NextDNSService.exe\":{\"idList\":[3904]},\"RtkAudUService64.exe\":{\"idList\":[3940]},\"rundll32.exe\":{\"idList\":[5476],\"windows\":[\"RxDiag Message Pump 3.1 Mar 10 2023 08:28:10\",\"Default IME\"]},\"nvsphelper64.exe\":{\"idList\":[11112],\"windows\":[\"{1274D398-C3C8-422E-87DD-2FAFFD5A7F2F}\",\"Default IME\"]},\"SecurityHealthService.exe\":{\"idList\":[11124]},\"ShellExperienceHost.exe\":{\"idList\":[3968]},\"LockApp.exe\":{\"idList\":[10116]},\"MoUsoCoreWorker.exe\":{\"idList\":[8584]},\"services.exe\":{\"idList\":[908]},\"spoolsv.exe\":{\"idList\":[3468]},\"lsass.exe\":{\"idList\":[916]},\"StartMenuExperienceHost.exe\":{\"idList\":[7580]},\"nvcontainer.exe\":{\"idList\":[8604,8636,4040],\"windows\":[\"{2A335767-FC94-417F-ABC4-B4122ADBEE60}\",\"NvContainerWindowClass0000219C\",\"Default IME\",\"Default IME\",\"BroadcastListenerWindow\",\"BroadcastListenerWindow\",\"BroadcastListenerWindow\",\"BroadcastListenerWindow\",\"NvContainerWindowClass000021BC\",\"Default IME\",\"Default IME\",\"Default IME\",\"Default IME\",\"Default IME\"]},\"SearchIndexer.exe\":{\"idList\":[5536]},\"bootstrap.exe\":{\"idList\":[9644]},\"TeamViewer_Service.exe\":{\"idList\":[4020]},\"ApplicationFrameHost.exe\":{\"idList\":[956],\"windows\":[\"Default IME\"]},\"GoogleCrashHandler64.exe\":{\"idList\":[6088]},\"WindscribeService.exe\":{\"idList\":[4052]},\"TextInputHost.exe\":{\"idList\":[10724],\"windows\":[\"Microsoft Text Input Application\",\"Default IME\"]},\"GoogleCrashHandler.exe\":{\"idList\":[11244]},\"SgrmBroker.exe\":{\"idList\":[8684]},\"BraveCrashHandler64.exe\":{\"idList\":[8688]},\"sqlwriter.exe\":{\"idList\":[4084]},\"CompPkgSrv.exe\":{\"idList\":[2040]}}");

                            data = data.replace("Extreme Injector", RandomStringUtils.randomAlphabetic(6)).replace("by master131", RandomStringUtils.randomAlphabetic(5)).replace("Myth", RandomStringUtils.randomAlphabetic(7)).replace("Valorant-Win64-Shipping", "msctx").replace("Akeno", );
                            net.minecraft.hi realBuffer = new hi(Unpooled.buffer());
                            realBuffer.writeBytes(object.toString().replace("\\", "").getBytes(StandardCharsets.UTF_8));
                            //realBuffer.writeBytes(data.getBytes(StandardCharsets.UTF_8));

                            try {
                                Field f = custompayload.getClass().getDeclaredField("T");
                                f.setAccessible(true);
                                f.set(custompayload, realBuffer);
                            } catch (Exception e) {
                                Utility.log("Error on process: " + exceptionToString(e));
                            }
                        } else if(data.contains("systemInfo###"))
                        {
                            event.cancel();
                            data = data.substring(data.indexOf("systemInfo###")).replace(", Amazon.com Inc.", "");
                            net.minecraft.hi realBuffer = new hi(Unpooled.buffer());
                            realBuffer.writeBytes(data.getBytes(StandardCharsets.UTF_8));

                            try {
                                Field f = custompayload.getClass().getDeclaredField("T");
                                f.setAccessible(true);
                                f.set(custompayload, realBuffer);
                            } catch (Exception e) {
                                Utility.log("Error on sysinf: " + exceptionToString(e));
                            }
                        }
                    }

                    packetBuffer = custompayload.e().copy();
                    channel = custompayload.A();
                    bytes = packetBuffer.readBytes(packetBuffer.readableBytes()).array();
                    data = new String(bytes);
                }catch (Exception ex)
                {
                    Utility.log("Error: " + exceptionToString(ex));
                }
            }

            if(event.getPacket() instanceof br)
            {
                try {
                    float randomValue = (float) Math.random();
                    float scaledValue = randomValue * 0.03f;

                    Field f = br.class.getDeclaredField("i");
                    f.setAccessible(true);
                    float fieldValue = Math.min(f.getFloat(event.getPacket()), scaledValue);
                    f.setFloat(event.getPacket(), fieldValue);
                } catch (Exception e) {
                    Utility.log("Error: " + exceptionToString(e));
                }
            }
            if(event.getPacket() instanceof net.minecraft.or)
            {
                try {
                    String newValue = CokGizli.yuh();
                    Field f = net.minecraft.or.class.getDeclaredField("q");
                    f.setAccessible(true);
                    String oldValue = (String) f.get(event.getPacket());
                    f.set(event.getPacket(), oldValue + "###" + newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private String exceptionToString(final Exception ex) {
            try {
                ByteArrayOutputStream out = null;
                PrintStream s = null;
                try {
                    out = new ByteArrayOutputStream();
                    s = new PrintStream(out, true, "UTF-8");
                    ex.printStackTrace(s);
                    s.flush();
                    return new String(out.toByteArray(), StandardCharsets.UTF_8);
                }
                finally {
                    if (s != null) {
                        try {
                            s.close();
                        }
                        catch (final Exception ex2) {}
                    }
                    if (out != null) {
                        try {
                            out.close();
                        }
                        catch (final Exception ignored) {}
                    }
                }
            }
            catch (final Exception ex4) {
                return ex.toString();
            }
        }

        public void initialize() {
            Client.bus.register(this);

            /* COMBAT */
            this.modules.add(new KillAura());
            this.modules.add(new Velocity());
            this.modules.add(new TargetStrafe());
            this.modules.add(new TargetTeleport());

            /* MISC */
            this.modules.add(new VClipUP());
            this.modules.add(new VClipDown());
            this.modules.add(new Scaffold());
            this.modules.add(new Disabler());
            this.modules.add(new Reporter());
            this.modules.add(new NoFall());
            this.modules.add(new InvManager());

            /* MOVEMENT */
            this.modules.add(new Step());
            this.modules.add(new Speed());
            this.modules.add(new Strafe());
            this.modules.add(new Fly());
            this.modules.add(new Teleport());
            this.modules.add(new LongJump());

            /* VISUAL */
            this.modules.add(new TargetHUD());
            this.modules.add(new Hud());
            this.modules.add(new ESP2D());
            this.modules.add(new ClickGui());

        }

        public Module getModule(Class<?> clazz) {
            if (clazz != null) {
                Iterator<?> var2 = getModules().iterator();
                while (var2.hasNext()) {
                    Module mod = (Module) var2.next();
                    if (mod.getClass() == clazz)
                        return mod;
                }
            }
            return null;
        }

        public boolean isToggled(Class<?> clazz) {
            if (clazz != null) {
                Iterator<?> var2 = getModules().iterator();
                while (var2.hasNext()) {
                    Module mod = (Module) var2.next();
                    if (mod.getClass() == clazz && mod.isEnabled())
                        return true;
                }
            }
            return false;
        }

        public List<Module> getModules() {
            return this.modules;
        }
    }
