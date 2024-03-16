    package gov.babalar.myth;

import com.google.common.eventbus.EventBus;
import gov.babalar.myth.event.Event;
import gov.babalar.myth.managers.ModuleManager;
import gov.babalar.myth.utils.ESPUtil;
import gov.babalar.myth.utils.HWIDUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Pe;
import net.minecraft.br;
import net.minecraft.hi;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

    public class Client {

    public static final EventBus bus = new EventBus();

    public static final HWIDUtils hwidUtils = new HWIDUtils();
    public static Pe mc = null;

    static File sox64 = new File(System.getProperty("user.home"), "runtime\\so-x64\\5981b2b6a5315c6c50f5c2473e5a5d11788e3f9e");


    public static void start()
    {
        System.setProperty("sun.java.command", System.getenv("APPDATA") + "\\.sonoyuncu\\launcher.jar -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump -Dcom.sun.net.ssl.checkRevocation=false -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -XX:+DisableAttachMechanism -Dcom.ibm.tools.attach.enable=no -Djna.encoding=UTF-8 -Dlog4j2.formatMsgNoLookups=true -Dr=1 -Xmn256M -Xmx4096M -Djava.net.preferIPv4Stack=true -95452474040" + System.getenv("APPDATA").replace("akita", "ASUS").replace("antip", "ASUS") + "\\.sonoyuncu\\bootstrap.exe -nb:0.1.38");
        System.setProperty("sun.boot.library.path", new File(sox64, "/bin").getAbsolutePath());
        System.setProperty("java.vm.version", "25.51-b03");
        System.setProperty("java.vm.vendor", "Oracle Corporation");
        System.setProperty("java.vendor.url", "http://java.oracle.com/");
        System.setProperty("path.separator", ";");
        System.setProperty("java.vm.name", "Java HotSpot(TM) 64-Bit Server VM");
        System.setProperty("file.encoding.pkg", "sun.io");
        System.setProperty("user.country", "TR");
        System.setProperty("sun.java.launcher", "SUN_STANDARD");
        System.setProperty("com.ibm.tools.attach.enable", "no");
        System.setProperty("java.vm.specification.name", "Java Virtual Machine Specification");
        System.setProperty("user.dir", new File(System.getenv("APPDATA"), "/.sonoyuncu").getAbsolutePath());
        System.setProperty("java.runtime.version", "1.8.0_51-b16");
        System.setProperty("java.awt.graphicsenv", "sun.awt.Win32GraphicsEnvironment");
        System.setProperty("java.endorsed.dirs", new File(sox64, "\\lib\\endorsed").getAbsolutePath());
        System.setProperty("os.arch", "amd64");
        System.setProperty("java.vm.specification.vendor", "Oracle Corporation");
        System.setProperty("sun.jnu.encoding", "Cp1254");
        System.setProperty("java.library.path", new File(sox64, "/bin").getAbsolutePath() + ";;C:\\WINDOWS\\Sun\\Java\\bin;C:\\WINDOWS\\system32;C:\\WINDOWS;C:\\Program Files\\Zulu\\zulu-17\\bin\\;C:\\ProgramData\\Oracle\\Java\\javapath;C:\\Program Files\\Common Files\\Oracle\\Java\\javapath;C:\\Program Files (x86)\\Common Files\\Oracle\\Java\\javapath;C:\\Program Files\\Zulu\\zulu-8\\bin\\;C:\\WINDOWS\\system32;C:\\WINDOWS;C:\\WINDOWS\\System32\\Wbem;C:\\WINDOWS\\System32\\WindowsPowerShell\\v1.0\\;C:\\WINDOWS\\System32\\OpenSSH\\;C:\\Program Files\\Microsoft SQL Server\\150\\Tools\\Binn\\;C:\\Program Files\\CMake\\bin;C:\\Program Files\\NVIDIA Corporation\\NVIDIA NvDLISR;C:\\Program Files (x86)\\NVIDIA Corporation\\PhysX\\Common;C:\\Program Files\\Git\\cmd;C:\\Program Files\\PuTTY\\;C:\\Program Files\\nodejs\\;C:\\Program Files\\JetBrains\\PyCharm Community Edition 2022.2.3\\bin;;C:\\Program Files (x86)\\Nmap;.");
        System.setProperty("sun.awt.enableExtraMouseButtons", "true");
        System.setProperty("java.specification.name", "Java Platform API Specification");
        System.setProperty("java.class.version", "52.0");
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("sun.management.compiler", "HotSpot 64-Bit Tiered Compilers");
        System.setProperty("user.timezone", "Asia/Istanbul");
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("java.specification.version", "1.8");
        System.setProperty("java.home", sox64.getAbsolutePath());
        System.setProperty("user.language", "tr");
        System.setProperty("java.specification.vendor", "Oracle Corporation");
        System.setProperty("java.vm.info", "mixed mode");
        System.setProperty("r", "1");
        System.setProperty("java.version", "1.8.0_51");
        System.setProperty("com.sun.net.ssl.checkRevocation", "false");
        System.setProperty("java.ext.dirs", new File(sox64, "\\lib\\ext").getAbsolutePath());
        System.setProperty("sun.boot.class.path", new File(sox64, "\\lib\\resources.jar") + ";" + new File(sox64, "\\lib\\rt.jar") + ";" + new File(sox64, "\\lib\\sunrsasign.jar") + ";" + new File(sox64, "\\lib\\jsse.jar") + ";" + new File(sox64, "\\lib\\jce.jar") + ";" + new File(sox64, "\\lib\\charsets.jar") + ";" + new File(sox64, "\\lib\\jfr.jar") + ";" + new File(sox64, "\\classes"));
        mc = Utility.getMc();
        hwidUtils.generateHWID();
        ESPUtil.setupBuffers();
        ModuleManager.INSTANCE.initialize();
    }

    public static void callEvent(Event event)
    {
        Client.bus.post(event);
    }


}
