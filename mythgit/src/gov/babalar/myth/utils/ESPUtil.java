package gov.babalar.myth.utils;

import gov.babalar.myth.Client;
import gov.babalar.myth.Utility;
import gov.babalar.myth.utils.math.MathHelper;
import net.minecraft.DN;
import net.minecraft.DZ;
import net.minecraft.client.CC;
import net.minecraft.client.PM;
import net.minecraft.client.Pe;
import net.minecraft.uO;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

import net.minecraft.ct;
import static org.lwjgl.opengl.GL11.*;

public class ESPUtil {

    private static final FloatBuffer windPos = BufferUtils.createFloatBuffer(4);
    private static IntBuffer intBuffer;
    private static FloatBuffer floatBuffer1;
    private static FloatBuffer floatBuffer2;

    public static void setupBuffers()
    {
        int capacity = 16;
        intBuffer = ByteBuffer.allocateDirect(capacity << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        floatBuffer1 = ByteBuffer.allocateDirect(capacity << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatBuffer2 = ByteBuffer.allocateDirect(capacity << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    public static double[] getInterpolatedPos(DZ entity) {
        uO timer = null;
        try {
            Field field = Pe.class.getDeclaredField("en");
            field.setAccessible(true);
            timer = (uO)field.get(Client.mc);
        } catch (Exception field) {}
        PM renderManager = null;
        try {
            Field field = Pe.class.getDeclaredField("E");
            field.setAccessible(true);
            renderManager = (PM)field.get(Client.mc);
        } catch (Exception field) {}
        if(timer == null || renderManager == null)
        {
            String s = timer == null ? "timer" : "renderManager";
            throw new NullPointerException(String.format("%s is null!!!!!", s));
        }
        float ticks = timer.G;
        double posX = Utility.getPosX(entity);
        double posY = Utility.getPosY(entity);
        double posZ = Utility.getPosZ(entity);
        DN entity1 = entity;
        return new double[]{
                MathHelper.interpolate(entity1.pf, posX, ticks) - renderManager.Q,
                MathHelper.interpolate(entity1.A, posY, ticks) - renderManager.w,
                MathHelper.interpolate(entity1.s, posZ, ticks) - renderManager.X
        };
    }

    public static Vector4f getEntityPositionsOn2D(DN entity) {
        final double[] renderingEntityPos = getInterpolatedPos((DZ) entity);
        final double entityRenderWidth = entity.k.X().doubleValue() / 1.5;
        final ct bb = new ct(renderingEntityPos[0] - entityRenderWidth, renderingEntityPos[1],
                renderingEntityPos[2] - entityRenderWidth, renderingEntityPos[0] + entityRenderWidth,
                renderingEntityPos[1] + entity.b + (entity.O() ? -0.3 : 0.18),
                renderingEntityPos[2] + entityRenderWidth).n(0.15, 0.15, 0.15);

        final List<Vector3f> vectors = Arrays.asList(
                new Vector3f((float) bb.Q, (float) bb.G, (float) bb.I),
                new Vector3f((float) bb.Q, (float) bb.p, (float) bb.I),
                new Vector3f((float) bb.i, (float) bb.G, (float) bb.I),
                new Vector3f((float) bb.i, (float) bb.p, (float) bb.I),
                new Vector3f((float) bb.Q, (float) bb.G, (float) bb.J),
                new Vector3f((float) bb.Q, (float) bb.p, (float) bb.J),
                new Vector3f((float) bb.i, (float) bb.G, (float) bb.J),
                new Vector3f((float) bb.i, (float) bb.p, (float) bb.J));

        Vector4f entityPos = new Vector4f(Float.MAX_VALUE, Float.MAX_VALUE, -1.0f, -1.0f);
        CC sr = new CC(Client.mc);
        for (Vector3f vector3f : vectors) {
            int scaleFactor = sr.t();
            if(scaleFactor != 2) scaleFactor = 2;
            vector3f = projectOn2D(vector3f.x, vector3f.y, vector3f.z, scaleFactor);
            if (vector3f != null && vector3f.z >= 0.0 && vector3f.z < 1.0) {
                entityPos.x = Math.min(vector3f.x, entityPos.x);
                entityPos.y = Math.min(vector3f.y, entityPos.y);
                entityPos.z = Math.max(vector3f.x, entityPos.z);
                entityPos.w = Math.max(vector3f.y, entityPos.w);
            }
        }
        return entityPos;
    }

    public static Vector3f projectOn2D(float x, float y, float z, int scaleFactor) {
        glGetFloat(GL_MODELVIEW_MATRIX, floatBuffer1);
        glGetFloat(GL_PROJECTION_MATRIX, floatBuffer2);
        glGetInteger(GL_VIEWPORT, intBuffer);
        if (GLU.gluProject(x, y, z, floatBuffer1, floatBuffer2, intBuffer, windPos)) {
            return new Vector3f(windPos.get(0) / scaleFactor, (Utility.getDisplayHeight() - windPos.get(1)) / scaleFactor, windPos.get(2));
        }
        return null;
    }
}
