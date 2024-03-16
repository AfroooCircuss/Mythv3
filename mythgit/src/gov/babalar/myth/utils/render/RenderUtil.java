package gov.babalar.myth.utils.render;

import gov.babalar.myth.Utility;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;


public class RenderUtil {


    public static int getRainbowAsRGB(final double delay, final double offset) {
        return getRainbowAsColor(delay, offset).getRGB();
    }

    public static Color getRainbowAsColor(double delay, double offset) {
        float hue = (float)((System.currentTimeMillis() + offset) % delay);
        hue /= delay;
        return Color.getHSBColor(hue, 0.2f, 0.9f);
    }

    public static void drawFlowRainbow(final String s, float d, float e , int offset) {
        for (int i = 0; i < s.length(); ++i) {
            final String sdd = String.valueOf(s.charAt(i));
            Utility.drawStringWithShadow(sdd, d, e, getRainbowAsRGB(2200, (-25 + offset) * i * 3));
            d += Utility.getStringWidth(sdd);
        }
    }


    public static void drawRect(double x, double y, double width, double height, int color) {
        GLUtil.setup2DRendering(() -> GLUtil.render(GL11.GL_QUADS, () -> {
            RenderUtil.color(color);
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x, y + height);
            GL11.glVertex2d(x + width, y + height);
            GL11.glVertex2d(x + width, y);
        }));
    }

    public static void drawSelectionRoundedRect(double x, double y, double width, double height, int color) {
        int radius1 = 0;
        int radius2 = 4;
        int radius3 = 0;
        int radius4 = 0;
        double x2 = x + width;
        double  y2 = y + height;
        glColor4f(1.0f,  1.0f,  1.0f, 1.0f);
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);

        GL11.glPushMatrix();
        GL11.glBegin(9);
        color(color, (float) (color >> 24 & 0xFF) / 255.0f);
        for (int i = 0; i <= 90; i += 1) {
            GL11.glVertex2d(x + radius1 + Math.sin(i * 3.141592653589793 / 180.0) * (radius1 * -1.0), y + radius1 + Math.cos(i * 3.141592653589793 / 180.0) * (radius1 * -1.0));
        }
        for (int i = 90; i <= 180; i += 1) {
            GL11.glVertex2d(x + radius2 + Math.sin(i * 3.141592653589793 / 180.0) * (radius2 * -1.0), y2 - radius2 + Math.cos(i * 3.141592653589793 / 180.0) * (radius2 * -1.0));
        }
        for (int i = 0; i <= 90; i += 1) {
            GL11.glVertex2d(x2 - radius3 + Math.sin(i * 3.141592653589793 / 180.0) * radius3, y2 - radius3 + Math.cos(i * 3.141592653589793 / 180.0) * radius3);
        }
        for (int i = 90; i <= 180; i += 1) {
            GL11.glVertex2d(x2 - radius4 + Math.sin(i * 3.141592653589793 / 180.0) * radius4, y + radius4 + Math.cos(i * 3.141592653589793 / 180.0) * radius4);
        }
        GL11.glEnd();
        GL11.glPopMatrix();

        glEnable(GL_TEXTURE_2D);
        GLUtil.endBlend();
        //glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glColor4f(1.0f,  1.0f,  1.0f, 1.0f);
    }

    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        GLUtil.setup2DRendering(true);
        glEnable(GL_LINE_SMOOTH);
        glShadeModel(GL_SMOOTH);
        glPushMatrix();
        glBegin(GL_QUADS);
        color(startColor);
        glVertex2d(left, top);
        glVertex2d(left, bottom);
        color(endColor);
        glVertex2d(right, bottom);
        glVertex2d(right, top);
        glEnd();
        glPopMatrix();
        glDisable(GL_LINE_SMOOTH);
        GLUtil.end2DRendering();
        resetColor();
    }

    public static void drawGradientRectBordered(double left, double top, double right, double bottom, double width, int startColor, int endColor, int borderStartColor, int borderEndColor) {
        drawGradientRect(left + width, top + width, right - width, bottom - width, startColor, endColor);
        drawGradientRect(left + width, top, right - width, top + width, borderStartColor, borderEndColor);
        drawGradientRect(left, top, left + width, bottom, borderStartColor, borderEndColor);
        drawGradientRect(right - width, top, right, bottom, borderStartColor, borderEndColor);
        drawGradientRect(left + width, bottom - width, right - width, bottom, borderStartColor, borderEndColor);
    }

    public static void drawRoundedRect(double x, double y, double width, double height, int radius, int color) {
        double x2 = x + width;
        double y2 = y + height;
        GLUtil.setup2DRendering(true);
        glEnable(GL_LINE_SMOOTH);
        //glShadeModel(GL_SMOOTH);
        glPushMatrix();
        glBegin(GL_POLYGON);
        color(color, (float) (color >> 24 & 0xFF) / 255.0f);
        for (int i = 0; i <= 90; i += 1) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y + radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 90; i <= 180; i += 1) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 0; i <= 90; i += 1) {
            GL11.glVertex2d(x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        for (int i = 90; i <= 180; i += 1) {
            GL11.glVertex2d(x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        glEnd();
        glPopMatrix();
        glDisable(GL_LINE_SMOOTH);
        GLUtil.end2DRendering();
        resetColor();
        //glColor4f(1.0f,  1.0f,  1.0f, 1.0f);
        //glEnable(GL_BLEND);
        //glDisable(GL_TEXTURE_2D);
        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        //glEnable(GL_LINE_SMOOTH);

        /*
        GL11.glPushMatrix();
        GL11.glBegin(9);
        color(color, (float) (color >> 24 & 0xFF) / 255.0f);
        for (int i = 0; i <= 90; i += 1) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y + radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 90; i <= 180; i += 1) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 0; i <= 90; i += 1) {
            GL11.glVertex2d(x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        for (int i = 90; i <= 180; i += 1) {
            GL11.glVertex2d(x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        GL11.glEnd();
        GL11.glPopMatrix();

        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glColor4f(1.0f,  1.0f,  1.0f, 1.0f);*/
    }

    public static void drawRoundedRect(double x, double y, double width, double height, int color) {
        RenderUtil.drawRoundedRect(x, y, width, height, 6, color);
    }

    public static void rrect(double x, double y, double width, double height, int color) {
        int radius1 = 0;
        int radius2 = 0;
        int radius3 = 0;
        int radius4 = 0;
        double x2 = x + width;
        double  y2 = y + height;
        glColor4f(1.0f,  1.0f,  1.0f, 1.0f);
        GLUtil.startBlend();
        //glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);

        GL11.glPushMatrix();
        GL11.glBegin(9);
        color(color, (float) (color >> 24 & 0xFF) / 255.0f);
        for (int i = 0; i <= 90; i += 1) {
            GL11.glVertex2d(x + radius1 + Math.sin(i * 3.141592653589793 / 180.0) * (radius1 * -1.0), y + radius1 + Math.cos(i * 3.141592653589793 / 180.0) * (radius1 * -1.0));
        }
        for (int i = 90; i <= 180; i += 1) {
            GL11.glVertex2d(x + radius2 + Math.sin(i * 3.141592653589793 / 180.0) * (radius2 * -1.0), y2 - radius2 + Math.cos(i * 3.141592653589793 / 180.0) * (radius2 * -1.0));
        }
        for (int i = 0; i <= 90; i += 1) {
            GL11.glVertex2d(x2 - radius3 + Math.sin(i * 3.141592653589793 / 180.0) * radius3, y2 - radius3 + Math.cos(i * 3.141592653589793 / 180.0) * radius3);
        }
        for (int i = 90; i <= 180; i += 1) {
            GL11.glVertex2d(x2 - radius4 + Math.sin(i * 3.141592653589793 / 180.0) * radius4, y + radius4 + Math.cos(i * 3.141592653589793 / 180.0) * radius4);
        }
        GL11.glEnd();
        GL11.glPopMatrix();

        glEnable(GL_TEXTURE_2D);
        GLUtil.endBlend();
        glDisable(GL_LINE_SMOOTH);
        glColor4f(1.0f,  1.0f,  1.0f, 1.0f);
    }

    public static void color(int color, float alpha) {
        float r = (float) (color >> 16 & 0xFF) / 255.0f;
        float g = (float) (color >> 8 & 0xFF) / 255.0f;
        float b = (float) (color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, alpha);
    }

    public static void color(int color) {
        RenderUtil.color(color, (float) (color >> 24 & 0xFF) / 255.0f);
    }

    public static void resetColor() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }


    public static void roundedRect(final double x, final double y, double width, double height, final double edgeRadius, final Color color) {
        final double halfRadius = edgeRadius / 2;
        width -= halfRadius;
        height -= halfRadius;

        float sideLength = (float) edgeRadius;
        sideLength /= 2;
        start();
        if (color != null)
            color(color);
        begin(GL11.GL_TRIANGLE_FAN);

        {
            for (double i = 180; i <= 270; i++) {
                final double angle = i * (Math.PI * 2) / 360;
                vertex(x + (sideLength * Math.cos(angle)) + sideLength, y + (sideLength * Math.sin(angle)) + sideLength);
            }
            vertex(x + sideLength, y + sideLength);
        }

        end();
        stop();

        sideLength = (float) edgeRadius;
        sideLength /= 2;
        start();
        if (color != null)
            color(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        begin(GL11.GL_TRIANGLE_FAN);

        {
            for (double i = 0; i <= 90; i++) {
                final double angle = i * (Math.PI * 2) / 360;
                vertex(x + width + (sideLength * Math.cos(angle)), y + height + (sideLength * Math.sin(angle)));
            }
            vertex(x + width, y + height);
        }

        end();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        stop();

        sideLength = (float) edgeRadius;
        sideLength /= 2;
        start();
        if (color != null)
            color(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        begin(GL11.GL_TRIANGLE_FAN);

        {
            for (double i = 270; i <= 360; i++) {
                final double angle = i * (Math.PI * 2) / 360;
                vertex(x + width + (sideLength * Math.cos(angle)), y + (sideLength * Math.sin(angle)) + sideLength);
            }
            vertex(x + width, y + sideLength);
        }

        end();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        stop();

        sideLength = (float) edgeRadius;
        sideLength /= 2;
        start();
        if (color != null)
            color(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        begin(GL11.GL_TRIANGLE_FAN);

        {
            for (double i = 90; i <= 180; i++) {
                final double angle = i * (Math.PI * 2) / 360;
                vertex(x + (sideLength * Math.cos(angle)) + sideLength, y + height + (sideLength * Math.sin(angle)));
            }
            vertex(x + sideLength, y + height);
        }

        end();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        stop();

        // Main block
        rect(x + halfRadius, y + halfRadius, width - halfRadius, height - halfRadius, color);

        // Horizontal bars
        rect(x, y + halfRadius, edgeRadius / 2, height - halfRadius, color);
        rect(x + width, y + halfRadius, edgeRadius / 2, height - halfRadius, color);

        // Vertical bars
        rect(x + halfRadius, y, width - halfRadius, halfRadius, color);
        rect(x + halfRadius, y + height, width - halfRadius, halfRadius, color);
    }

    public static void rect(final double x, final double y, final double width, final double height, final Color color) {
        rrect(x, y, width, height, color.getRGB());
    }

    public static void rect(final double x, final double y, final double width, final double height, final boolean filled, final Color color) {

        rrect(x , y, width , height , color.getRGB());
    }

    public static void start() {
        GLUtil.startBlend();
        //enable(GL11.GL_BLEND);
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        disable(GL11.GL_TEXTURE_2D);
        disable(GL11.GL_CULL_FACE);
        disable(GL11.GL_ALPHA_TEST);
        disable(GL11.GL_DEPTH_TEST);
        //GlStateManager.disableAlpha();
        //GlStateManager.disableDepth();
    }

    public static void stop() {
        //GlStateManager.enableAlpha();
        //GlStateManager.enableDepth();
        //enable(GL11.GL_ALPHA_TEST);
        enable(GL11.GL_DEPTH_TEST);
        enable(GL11.GL_CULL_FACE);
        enable(GL11.GL_TEXTURE_2D);
        GLUtil.endBlend();
        //disable(GL11.GL_BLEND);
        color(Color.white);
    }

    public static void begin(final int glMode) {
        GL11.glBegin(glMode);
    }

    public static void end() {
        GL11.glEnd();
    }

    public static void vertex(final double x, final double y) {
        GL11.glVertex2d(x, y);
    }

    public static void setAlphaLimit(float limit) {
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, limit * 0.01f);
    }

    public static void color(Color color) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    public static void color(final double red, final double green, final double blue, final double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }

    public static void enable(final int glTarget) {
        GL11.glEnable(glTarget);
    }

    public static void disable(final int glTarget) {
        GL11.glDisable(glTarget);
    }
    public static void drawFlowRect(double x , double y , double width , double height , int offset)
    {
        for(double d = 0; d < width; d++)
        {
            rect(x , y , d , height , getRainbowAsColor(2000, offset * d * -15));
        }
    }
    public static void drawInterpolateFlow(String key , int x , int y , int amp)
    {
        for (int i = 0; i < key.length(); ++i) {
            final String sdd = String.valueOf(key.charAt(i));
            Color textcolor = ColorUtil.interpolateColorsBackAndForth(360, i * amp, new Color(229, 141, 234), ColorUtil.interpolateColorC(getRainbowAsColor(1800 , 3) , new Color(98, 11, 234),5f) , false);
            Utility.drawStringWithShadow(sdd, x , y, textcolor.getRGB());
            x += Utility.getStringWidth(sdd);
        }

    }

    public static void circleNoSmoothRGB(double x, double y, double radius, int color) {
        radius /= 2;
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_CULL_FACE);
        color(color);
        glBegin(GL_TRIANGLE_FAN);

        for (double i = 0; i <= 360; i++) {
            double angle = (i * (Math.PI * 2)) / 360;
            glVertex2d(x + (radius * Math.cos(angle)) + radius, y + (radius * Math.sin(angle)) + radius);
        }

        glEnd();
        glEnable(GL_CULL_FACE);
        glEnable(GL_TEXTURE_2D);
    }

}