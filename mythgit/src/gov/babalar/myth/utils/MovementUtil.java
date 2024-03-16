package gov.babalar.myth.utils;

import java.lang.reflect.Field;

import gov.babalar.myth.Utility;
import gov.babalar.myth.utils.math.MathHelper;
import net.minecraft.BlockPos;

public class MovementUtil {
    public static boolean isMoving() {
        return Utility.getMIForward() != 0.0f || Utility.getMIStrafe() != 0.0f;
    }

    public static void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += (float)(forward > 0.0 ? -45 : 45);
            } else if (strafe < 0.0) {
                yaw += (float)(forward > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            } else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        } else if (strafe < 0.0) {
            strafe = -1.0;
        }
        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        Utility.setMotionX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        Utility.setMotionZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }
    
    public static void resetMotion(boolean y)
    {
        Utility.setMotionX(0.0D);
        Utility.setMotionZ(0.0D);
    	if(y) Utility.setMotionY(0.0D);
    }

    public static float getSpeed() {
        return (float)MovementUtil.getSpeed(Utility.getMotionX(), Utility.getMotionZ());
    }

    public static double getSpeed(double motionX, double motionZ) {
        return Math.sqrt(motionX * motionX + motionZ * motionZ);
    }

    public static void setSpeed(double moveSpeed) {
        /*if (!MovementUtil.isMoving()) {
            return;
        }*/
        MovementUtil.setSpeed(moveSpeed, Utility.getRotationYaw(), Utility.getMIStrafe(), Utility.getMIForward());
    }

    public static double getDirection() {
        return MovementUtil.getDirectionRotation(Utility.getRotationYaw(), Utility.getMIStrafe(), Utility.getMIForward());
    }

    public static double getDirectionRotation(float yaw, float pStrafe, float pForward) {
        float rotationYaw = yaw;
        if (pForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (pForward < 0.0f) {
            forward = -0.5f;
        } else if (pForward > 0.0f) {
            forward = 0.5f;
        }
        if (pStrafe > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (pStrafe < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    public static BlockPos getForwardBlockFromMovement(double length) {
        float yaw = (float)Math.toRadians(MovementUtil.getYawFromMovement());
        BlockPos fPos = new BlockPos(Utility.getPosX() + -Math.sin(yaw) * length, Utility.getPosY(), Utility.getPosZ() + Math.cos(yaw) * length);
        return fPos;
    }

    public static float getYawFromMovement() {
        return MovementUtil.getRotationFromPosition(Utility.getPosX() + Utility.getMotionX(), Utility.getPosZ() + Utility.getMotionZ(), Utility.getPosY())[0];
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Utility.getPosX();
        double zDiff = z - Utility.getPosY();
        double yDiff = y - Utility.getPosZ() - 1.2;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }
}

