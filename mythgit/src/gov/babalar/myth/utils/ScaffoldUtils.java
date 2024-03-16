package gov.babalar.myth.utils;

import gov.babalar.myth.Client;
import gov.babalar.myth.Utility;
import gov.babalar.myth.module.misc.Scaffold;
import net.minecraft.*;

import java.util.Arrays;


public class ScaffoldUtils {

    public static er getHypixelVec3(BlockCache data) {
        BlockPos pos = data.getPosition();
        Qu face = data.getFacing();
        //double x = (double) pos.getX() + 0.5, y = (double) pos.getY() + 0.5, z = (double) pos.getZ() + 0.5;
        double x = (double) pos.X() + 0.5, y = (double) pos.W() + 0.5, z = (double) pos.k() + 0.5;
        if (face != Qu.UP && face != Qu.DOWN) {
            y += 0.5;
        } else {
            x += 0.3;
            z += 0.3;
        }
        if (face == Qu.WEST || face == Qu.EAST) {
            z += 0.15;
        }
        if (face == Qu.SOUTH || face == Qu.NORTH) {
            x += 0.15;
        }
        return new er(x, y, z);
    }

    public static double getYLevel() {
        return Utility.getPosY() - 1.0 >= Scaffold.keepYCoord && Math.max(Utility.getPosY(), Scaffold.keepYCoord)
                - Math.min(Utility.getPosY(), Scaffold.keepYCoord) <= 3.0 && !Utility.getGameSettings().uw.f()//Mapping: Utility.getGameSettings().uw.f() = mc.gameSettings.keyBindJump.isKeyDown()
                ? Scaffold.keepYCoord
                : Utility.getPosY() - 1.0;
    }

    public static BlockCache getBlockInfo() {
        final BlockPos belowBlockPos = new BlockPos(Utility.getPosX(), getYLevel(), Utility.getPosZ());
        //is block air check
        if (Utility.getBlockState(belowBlockPos).N() instanceof fD) {
            for (int x = 0; x < 4; x++) {
                for (int z = 0; z < 4; z++)
                    for (int i = 1; i > -3; i -= 2) {
                        //Mapping: blockPos.V == blockPos.add
                        final BlockPos blockPos = belowBlockPos.V(x * i, 0, z * i);
                        fH block = Utility.getBlockState(blockPos).N();
                        if (block instanceof fD) {
                            for (Qu direction : Qu.VALUES) {
                                final BlockPos blockPos1 = blockPos.k(direction, 1); //offset
                                final NE material = Utility.getBlockState(blockPos).N().M();
                                if(block.s(Utility.getTheWorld(), blockPos1, direction)) {
                                    return new BlockCache(blockPos1, direction.H());
                                }
                            }
                        }
                    }
            }
        }
        return null;
    }


    public static int getBlockSlot() {
        for (int i = 0; i < 9; i++) {
            //final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            final f3 itemStack = Utility.getThePlayer().Fc.i[i];
            //Mapping: itemStack.r == itemStack.stackSize
            if (itemStack != null && itemStack.C() instanceof ri && itemStack.r > 0) {
                final ri itemBlock = (ri) itemStack.C();
                if (isBlockValid(itemBlock.y())) {
                   return i;
                }
            }
        }
        return -1;
    }

    public static int getBlockCount() {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            final f3 itemStack = Utility.getThePlayer().Fc.i[i];
            //Mapping: itemStack.r == itemStack.stackSize
            if (itemStack != null && itemStack.C() instanceof ri && itemStack.r > 0) {
                final ri itemBlock = (ri) itemStack.C();
                if (isBlockValid(itemBlock.y())) {
                    count += itemStack.r;
                }
            }
        }
        return count;
    }

    private static boolean isBlockValid(final fH block) {
        //Mapping: block.q == block.isFullBlock
        return (block.q() || block.toString().contains("glass")) &&
                !block.toString().contains("sand") &&
                !block.toString().contains("gravel") &&
                !block.toString().contains("dispenser") &&
                !block.toString().contains("commandBlock") &&
                !block.toString().contains("musicBlock") &&
                !block.toString().contains("furnace") &&
                !block.toString().contains("workbench") &&
                !block.toString().contains("tnt") &&
                !block.toString().contains("dropper") &&
                !block.toString().contains("beacon");
    }


}
