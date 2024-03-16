package gov.babalar.myth;

import com.mojang.authlib.GameProfile;
import net.minecraft.*;
import net.minecraft.LH;
import net.minecraft.P1;
import net.minecraft.Qu;
import net.minecraft.client.*;
import net.minecraft.client.Pe;
import net.minecraft.client.ec;
import net.minecraft.client.en;
import net.minecraft.client.hj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * ----------
 * 9/29/2023
 * 11:41 PM
 * ----------
 **/
public class Utility {
    public static Pe getMc()
    {
        try {
            Field f = Pe.class.getDeclaredField("eb");
            f.setAccessible(true);
            return (Pe)f.get(null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void log(String msg)
    {
        Pe.Q().info(msg);
    }
    public static kX getThePlayer()
    {
        return Client.mc.eS;
    }
    public static hj getFontRendererObj()
    {
        return Client.mc.er;
    }

    public static iz getTextureManager()
    {
        try {
            Field f = Pe.class.getDeclaredField("e2");
            f.setAccessible(true);
            return (iz) f.get(Client.mc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static net.minecraft.client.eT getFrameBuffer()
    {
        try {
            Field f = Pe.class.getDeclaredField("eP");
            f.setAccessible(true);
            return (eT) f.get(Client.mc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static CY getTheWorld()
    {
        return Client.mc.e9;
    }

    public static HX getBlockState(BlockPos pos)
    {
        try {
            Method m = filterMethodsFromReturn("B", P1.class, HX.class);
            return (HX) m.invoke(getTheWorld(), pos);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static net.minecraft.o0 getMovementInput()
    {
        return getThePlayer().Yz;
    }

    public static Ll getGameSettings()
    {
        return Client.mc.eu;
    }

    public static float getMIForward()
    {
        return getMovementInput().W;
    }

    public static float getMIStrafe()
    {
        return getMovementInput().a;
    }

    public static List<DZ> getEntityList()
    {
        return Client.mc.e9.P;
    }

    public static void setMotionX(double d) {
        getThePlayer().z = d;
    }

    public static void setMotionY(double d) {
        ((DN) getThePlayer()).u(d);
    }

    public static void setMotionZ(double d) {
        getThePlayer().O = d;
    }


    public static double getMotionX() {
        return getThePlayer().z;
    }

    public static double getMotionY() {
        return getThePlayer().E;
    }

    public static double getMotionZ() {
        return getThePlayer().O;
    }


    public static float getRotationYaw() {
        return getThePlayer().e;
    }

    public static float getRotationPitch() {
        return getThePlayer().B;
    }

    public static void setStepHeight(float f) {
        getThePlayer().Q = f;
    }

    public static void drawStringWithShadow(String s , float x , float y , int color)
    {
        getFontRendererObj().X(s , x , y , color , true);
    }
    public static void drawString(String s , float x , float y , int color)
    {
        getFontRendererObj().X(s , x , y , color , false);
    }

    public static double getPosX(DN entity) {
        return entity.l;
    }

    public static double getPosX() {
        return getPosX(getThePlayer());
    }

    public static double getPosY(DN entity) {
        return entity.ph;
    }

    public static double getPosY() {
        return getPosY(getThePlayer());
    }
    public static double getPosZ(DN entity) {
        return entity.p6;
    }

    public static double getPosZ() {
        return getPosZ(getThePlayer());
    }

    public static float getDistanceToEntity(DZ entityIn) {
        /*float f2 = (float) (getPosX() - getPosX(entityIn));
        float f1 = (float) (getPosY() - getPosY(entityIn));
        float f22 = (float) (getPosZ() - getPosZ(entityIn));
        return (float) Math.sqrt(f2 * f2 + f1 * f1 + f22 * f22);*/
        return getDistanceToEntity1(entityIn);
    }

    public static boolean isDead(DN entity)
    {
        return entity.C;
    }

    public static boolean isInvisible(DN entity)
    {
        try {
            Method m = filterMethodsFromReturn("m", DN.class, boolean.class);
            return (boolean) m.invoke(entity);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static Method filterMethodsFromReturn(String name, Class<?> clazz, Class<?> returnType)
    {
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if(declaredMethod.getName().equals(name))
                if(declaredMethod.getReturnType().equals(returnType))
                {
                    return declaredMethod;
                }
        }
        return null;
    }


    public static int getDisplayWidth()
    {
        return Client.mc.eI;
    }

    public static int getDisplayHeight()
    {
        return Client.mc.eg;
    }

    public static boolean isOnGround() {
        return getThePlayer().I();
    }

    public static float getDistanceToEntity1(DZ entityIn) {
        return (float) Math.sqrt(getThePlayer().G(entityIn));
    }

    public static void sendPacket(Tz<?> packetIn) {
        getThePlayer().YU.A(packetIn);
    }


    public static String getPlayerName(DZ entity) {
        GameProfile profile = null;
        try {
            Field field = DZ.class.getDeclaredField("Fe");
            field.setAccessible(true);
            profile = (GameProfile) field.get(entity);
        } catch (Exception exception) {}
        return profile.getName();
    }

    public static String getPlayerName() {
        return getPlayerName(getThePlayer());
    }

    public static float getHealth(DP entity) {
        return entity.j();
    }

    public static float getMaxHealth(DP entity) {
        return entity.k();
    }

    public static double[] scales()
    {
        CC cc = new CC(Client.mc);
        return new double[] {
                cc.K(), cc.n()
        };
    }

    public static boolean onPlayerRightClick(net.minecraft.f3 itemStack, BlockPos pos, Qu facing, er vec3)
    {
        return getPlayerController().v(getThePlayer(), getTheWorld(), itemStack, pos, facing, vec3);
    }

    public static ec getPlayerController()
    {
        return Client.mc.N;
    }

    public static f3 getStackInSlot(int slot)
    {
        try {
            Method stackInSlot = filterMethodsFromReturn("c", LH.class, f3.class);
            //return getThePlayer().Fc.c(slot);
            return (f3) stackInSlot.invoke(getThePlayer().Fc, slot);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public static int getFPS()
    {
        try {
            Field fps = Pe.class.getDeclaredField("F");
            fps.setAccessible(true);
            return fps.getInt(Client.mc);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getStringWidth(String s)
    {
        return getFontRendererObj().X(s);
    }
    public static boolean isAnyGuiOpen()
    {
        try {
            Field f = Client.mc.getClass().getDeclaredField("ee");
            return f.get(Client.mc) == null;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String decompress(byte[] compressedData) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            gzipInputStream.close();
            outputStream.close();

            return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static float getFallDistance() {
        return getThePlayer().g;
    }


    public static void setFallDistance(float fallDistance) {
        getThePlayer().g = fallDistance;
    }

    public static void displayGui(net.minecraft.client.en gui)
    {
        Client.mc.I(gui);
    }


    public static void drawCentered(String s, int x , int y , int color)
    {
        drawStringWithShadow(s , x - (getStringWidth(s) / 2) , y , color);
    }
}