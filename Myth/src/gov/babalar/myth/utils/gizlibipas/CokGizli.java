package gov.babalar.myth.utils.gizlibipas;

import com.google.gson.Gson;
import gov.babalar.myth.Utility;
import gov.babalar.myth.utils.HWIDUtils;
import net.minecraft.client.Pe;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CokGizli {

    public static final Gson gson = new Gson();

        /*
localhost###{"token":"sl","suid":"06670U535562","minecraftCHC":"a163409ce959fc705c77aa65c331630128f0e3ad","launcherCHC":"eb76b206ec6614abd450b736e2dd5eff65dd1229","sig":"*?*98eeaf04-777a-48*?*QiGyOJ6ji7ydfWwhipgxNzfJ+ZDJFyuJ7QAWduz7EuKSFht9zGuOT+6Z/+oYXsibrgr8vfNeNZ32QX9GJW7B3MBk13S0zvawclZhoU95b7jfijhFkCowJkyITXU1HzPhm8utbetZXhlesBbHYCkEWg==
            */

    public static String yuh()
    {
        String randomC = random();
        //JsonSOGameRequest soGameRequest = new JsonSOGameRequest(getMinecraftCHC(), getLauncherCHC(), "sl", HWIDUtils.getHwid(), "*?*98eeaf04-777a-48*?*QiGyOJ6ji7ydfWwhipgxNzfJ+ZDJFyuJ7QAWduz7EuKSFht9zGuOT+6Z/+oYXsibrgr8vfNeNZ32QX9GJW7B3MBk13S0zvawclZhoU95b7jfijhFkCowJkyITXU1HzPhm8utbetZXhlesBbHYCkEWg==");
        JsonSOGameRequest soGameRequest = new JsonSOGameRequest(getMinecraftCHC(), getLauncherCHC(), "sl", getHWID(), sig(randomC));
        return gson.toJson(soGameRequest);
    }

    public static String sig(String random)
    {
        try {
            String key = randomKey(16);
            long now = getTime();
            long now500 = (now + 500L);
            String string = random + now + random + now500 + random + hashCodes(random);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, secretKeySpec, new IvParameterSpec(new byte[16]));
            String encrypted = Base64.getEncoder().encodeToString(cipher.doFinal(string.getBytes(StandardCharsets.UTF_8)));
            String out = random + key + random + encrypted;
            return out;
        }catch(Exception e)
        {
            e.printStackTrace();
            Pe.Q().error(e);
        }
        return "err";
    }

    public static String hashCodes(String random) {
        return "1304836502" + random + "977993101" + random + "887623108" + random + "581810360" + random + "706277948" + random + "1475134758";
    }

    public static String random()
    {
        return (new Random().nextInt(2) == 1 ? "===" : "?=?");
    }

    public static long getTime() {
        return new Date().getTime();
    }

    public static String randomKey(int n) {
        return UUID.randomUUID().toString().substring(0, n);
    }

    public static String getMinecraftCHC() {
        String chc = "error";
        try {
            URL url = new URL("https://launcher.sonoyuncu.network/minecraft/versions/sonoyuncu/1.8.9-Optifine-Ultra_.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String allLines = Arrays.toString(reader.lines().toArray());
           // System.out.println(allLines);
            return allLines.substring(allLines.indexOf("\"client\": {")).split("\"sha1\": \"")[1].split("\",")[0];
            /*MCJson mcJson = gson.fromJson(reader, MCJson.class);
            System.out.println(gson.toJson(mcJson));
            return mcJson.DownloadsObject.getClient().getSha1();*/
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return chc;
    }

    public static String getLauncherCHC() {
        AtomicReference<String> chc = new AtomicReference<>("error");
        try {
            URL url = new URL("https://launcher.sonoyuncu.network/bootstrap.new.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            reader.lines().forEach(s -> {
                if(s.contains("launcher_jar_checksum")) {
                    chc.set(s.split("\"launcher_jar_checksum\": \"")[1].split("\",")[0]);
                }
            });
            return chc.get();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return chc.get();
    }

    public static String xd = null;
    public static String getHWID() {
        if (xd == null) {
            try {
                String[] cmd = new String[]{"wmic", "DISKDRIVE", "get", "SerialNumber"};
                ArrayList<String> arrayList;
                Process process = Runtime.getRuntime().exec(cmd);
                Scanner scanner = new Scanner(process.getInputStream());
                arrayList = new ArrayList<String>();
                process.getOutputStream().close();
                while (scanner.hasNext()) {
                    arrayList.add(scanner.next());
                }
                process.getInputStream().close();
                scanner.close();
                process.destroy();
                if (arrayList.get(0).equalsIgnoreCase("serialnumber")) {
                    if (arrayList.size() >= 3) {
                        String string3 = arrayList.get(1).trim();
                        String string2 = arrayList.get(2).trim();
                        xd =  string3 + string2;
                    } else {
                        String string3 = arrayList.get(1).trim();
                        xd = string3;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return xd;
        }
        return xd;
    }
}
