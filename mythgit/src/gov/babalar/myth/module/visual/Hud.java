package gov.babalar.myth.module.visual;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventRender2D;
import gov.babalar.myth.managers.ModuleManager;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.setting.s.SettingMode;
import gov.babalar.myth.utils.render.ColorUtil;
import gov.babalar.myth.utils.render.RenderUtil;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.lwjgl.opengl.GL11;
import java.text.SimpleDateFormat;

public class Hud
        extends Module {
    private final NumberFormat df = new DecimalFormat("0.#");
    public SettingMode mode = new SettingMode("Mode", new String[]{"Old", "Exhibition","Crest"}, 0);
    private static final Color color1 = new Color(71, 148, 253);
    private static final Color color2 = new Color(71, 253, 160);
    private final Comparator<Object> SORT_METHOD = Comparator.comparingDouble(m -> {
        Module mod = (Module)m;
        String key = mod.getSuffix().isEmpty() ? mod.getName() : mod.getName() + " [" + mod.getSuffix() + "]";
        return Utility.getStringWidth(key);
    }).reversed();
    static Color[] blueTheme = new Color[]{new Color(102, 255, 209), new Color(6, 149, 255)};
    static Color[] redTheme = new Color[]{new Color(255, 7, 7), new Color(246, 127, 127)};
    static Color[] redTheme_TEST = new Color[]{new Color(255, 7, 7), new Color(0, 0, 0)};
   static Color[] colors = new Color[]{new Color(204, 102, 255), new Color(89, 6, 255)};

    public Hud() {
        super(ModuleType.VISUAL, "Hud", 0);
        this.abstractSettings.add(this.mode);
    }

    @Subscribe
    public void onRender2DEvent(EventRender2D event) {
        this.setSuffix(this.mode.getMode());
        switch (this.mode.getMode()) {
            case "Old": {
                this.drawOld();
                break;
            }
            case "Exhibition": {
                this.drawExhi();
                break;
            }
            case "Crest": {
                this.drawCrest();
                break;
            }
        }
    }

    public void drawExhi() {
        String text = String.format("§e§l§n§oMyth Client§r [%s] [%s] ", Utility.getFPS(), this.df.format((Math.abs(Utility.getMotionX()) + Math.abs(Utility.getMotionZ())) / 2.0));
        Utility.drawStringWithShadow(text, 5.0f, 5.0f, Color.white.getRGB());
        GL11.glPushMatrix();
        List<Module> mods = ModuleManager.INSTANCE.getModules().stream().filter(Module::isEnabled).sorted(this.SORT_METHOD).collect(Collectors.toList());
        double[] scales = Utility.scales();
        int y = 5;
        int count = 0;
        for (Module mod : mods) {
            String key = mod.getSuffix().isEmpty() ? mod.getName() : mod.getName() + " [" + mod.getSuffix() + "]";
            int width = Utility.getStringWidth(key);
            int index = count * 20;
            Utility.drawStringWithShadow(key, (int)scales[0] - width - 5, y, RenderUtil.getRainbowAsRGB((1800 + index) % 2600, index));
            y += 14;
            ++count;
        }
        GL11.glPopMatrix();
    }
    public void drawCrest()
    {
        String time = new SimpleDateFormat("hh:mm a").format(new Date());
        if (time.startsWith("0")) {
            time = time.replaceFirst("0", "");
        }
        double[] scales = Utility.scales();
        Utility.drawStringWithShadow("§4C§rrest §7" + time, 2.0f, 2.0f, 0x696969);
        List<Module> mods = ModuleManager.INSTANCE.getModules().stream().filter(Module::isEnabled).sorted(this.SORT_METHOD).collect(Collectors.toList());
        int y = 2;
        for (Module mod : mods) {
            String key = mod.getSuffix().isEmpty() ? mod.getName() : mod.getName() + String.format("§4 [%s]" , mod.getSuffix());
            int width = Utility.getStringWidth(key);
            Utility.drawStringWithShadow(key, (int)scales[0] - width - 2, y, 0x696969);
            y += 10;
        }
    }

    public void drawOld() {
        Color[] colors = Hud.getThemeColors();
        double[] scales = Utility.scales();
        GL11.glPushMatrix();
        String text = String.format("myth client [%s] ", Utility.getFPS());
        RenderUtil.drawRoundedRect(2.0, 2.0, Utility.getStringWidth(text) + 14, 14.0, new Color(0, 0, 0, 160).getRGB());
        Hud.drawTestColorsex(text, 10.0f, 5.5f, 0);
        String build = "Build: LATEST";
        Hud.drawTestColorsex(build, (float)(scales[0] - (double)Utility.getStringWidth(build) - 10.0), (float)(scales[1] - 15.0), 15);
        //String user = String.format("User: %s", System.getProperty("user.name"));
        //Hud.drawTestColorsex(user, (float)(scales[0] - (double)Utility.getStringWidth(user) - 10.0), (float)(scales[1] - 25.0), 15);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        List<Module> mods = ModuleManager.INSTANCE.getModules().stream().filter(Module::isEnabled).sorted(this.SORT_METHOD).collect(Collectors.toList());
        int y = 5;
        int count = 0;
        for (Module mod : mods) {
            String key = mod.getSuffix().isEmpty() ? mod.getName() : mod.getName() + " [" + mod.getSuffix() + "]";
            int width = Utility.getStringWidth(key);
            int index = count * 20;
            Color textcolor = ColorUtil.interpolateColorsBackAndForth(20, index, colors[0], colors[1], true);
            RenderUtil.rect((int)scales[0] - width - 7, y - 3, width + 5, 12.0, false, ColorUtil.applyOpacity(new Color(10, 10, 10), 0.7f));
            Utility.drawStringWithShadow(key, (int)scales[0] - width - 5, y, textcolor.getRGB());
            y += 12;
            ++count;
        }
        GL11.glPopMatrix();
    }

    public static Color getThemeColor(float colorOffset) {
        return Hud.getThemeColor(colorOffset, 1.0f);
    }

    public static Color getThemeColor(float colorOffset, float timeMultiplier) {
        float colorOffsetMultiplier = 2.2f;
        double timer = (double)System.currentTimeMillis() / 1.0E8 * (double)timeMultiplier * 400000.0;
        double factor = (Math.sin(timer + (double)((colorOffset *= colorOffsetMultiplier) * 0.55f)) + 1.0) * 0.5;
        return Hud.mixColors(color1, color2, factor);
    }

    public static Color mixColors(Color color1, Color color2, double percent) {
        double inverse_percent = 1.0 - percent;
        int redPart = (int)((double)color1.getRed() * percent + (double)color2.getRed() * inverse_percent);
        int greenPart = (int)((double)color1.getGreen() * percent + (double)color2.getGreen() * inverse_percent);
        int bluePart = (int)((double)color1.getBlue() * percent + (double)color2.getBlue() * inverse_percent);
        return new Color(redPart, greenPart, bluePart);
    }

    public static void drawTestColorsex(String s, float d, float e, int offset) {
        Color[] colors = Hud.getThemeColors();
        for (int i = 0; i < s.length(); ++i) {
            String sdd = String.valueOf(s.charAt(i));
            int color = ColorUtil.interpolateColorsBackAndForth(20, i * 15, colors[0], colors[1], true).getRGB();
            Utility.drawStringWithShadow(sdd, d, e, color);
            d += (float)Utility.getStringWidth(sdd);
        }
    }

    public static Color[] getThemeColors() {
        //return new Color[]{Hud.getThemeColor(20.0f, 1.0f), Hud.getThemeColor(45.0f, 1.0f)}; //real
        return redTheme;
    }

}
