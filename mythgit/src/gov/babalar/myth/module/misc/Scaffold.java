package gov.babalar.myth.module.misc;

import com.google.common.eventbus.Subscribe;
import gov.babalar.myth.Utility;
import gov.babalar.myth.event.EventMotion;
import gov.babalar.myth.module.Module;
import gov.babalar.myth.module.ModuleType;
import gov.babalar.myth.utils.BlockCache;
import gov.babalar.myth.utils.ScaffoldUtils;
import net.minecraft.DN;
import net.minecraft.Vw;
import net.minecraft.fz;
import org.lwjgl.input.Keyboard;

public class Scaffold extends Module {

    private int slot;
    private int prevSlot;
    public static double keepYCoord;
    private BlockCache blockCache, lastBlockCache;

    public Scaffold()
    {
        super(ModuleType.MISC, "Scaffold", Keyboard.KEY_X);
    }

    @Override
    public void onEnable() {
        lastBlockCache = null;
        if(Utility.getThePlayer() != null)
        {
            prevSlot = Utility.getThePlayer().Fc.b; //Mapping: Utility.getThePlayer().Fc.b = currentItem
            slot = Utility.getThePlayer().Fc.b;
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Utility.getThePlayer().Fc.b = prevSlot;
        super.onDisable();
    }
    public int t;
    @Subscribe
    public void onMotion(EventMotion e)
    {
        t++;
        if(Utility.getGameSettings().uw.f() && Utility.getMotionX() == 0 && Utility.getMotionZ() == 0)
        {
            if (t % 4 == 1) {
                Utility.setMotionY(0.4195464);
                ((DN)Utility.getThePlayer()).e(Utility.getPosX() - 0.035, Utility.getPosY(), Utility.getPosZ());
            } else if (t % 4 == 0) {
                Utility.setMotionY(-0.5);
                ((DN)Utility.getThePlayer()).e(Utility.getPosX() + 0.035, Utility.getPosY(), Utility.getPosZ());
            }
        }
        blockCache = ScaffoldUtils.getBlockInfo();
        if (blockCache != null) {
            lastBlockCache = ScaffoldUtils.getBlockInfo();
        } else {
            return;
        }

        int slot = ScaffoldUtils.getBlockSlot();
        if (blockCache == null || lastBlockCache == null || slot == -1)
            return;

        if (Utility.getFallDistance() == 0.0F) {
            keepYCoord = Math.floor(Utility.getPosY() - 1.0);
        }
        if(Utility.getThePlayer().Fc.b != slot)
            Utility.getThePlayer().Fc.b = slot;

        if (this.slot != slot) {
            this.slot = slot;
            Utility.sendPacket(new Vw(this.slot));//c09
        }

        if (Utility.onPlayerRightClick(Utility.getStackInSlot(this.slot), lastBlockCache.getPosition(), lastBlockCache.getFacing(), ScaffoldUtils.getHypixelVec3(lastBlockCache)))
        {
            Utility.sendPacket(new fz());//c0a
        }
        blockCache = null;
    }


}
