package gov.babalar.myth.utils.render;

import gov.babalar.myth.Client;
import gov.babalar.myth.Utility;
import net.minecraft.client.Pe;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;
import net.minecraft.client.eT;

import static org.lwjgl.opengl.GL11.*;

public class StencilUtil {
    static Pe mc = Client.mc;

    /*
     * Given to me by igs
     *                    */

    public static void checkSetupFBO(eT framebuffer) {
        if (framebuffer != null) {
            if (framebuffer.a > -1) {
                setupFBO(framebuffer);
                framebuffer.a = -1;
            }
        }
    }

    /**
     * @implNote Sets up the Framebuffer for Stencil use
     */

    public static void setupFBO(eT framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.a);
        final int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, Utility.getDisplayWidth(), Utility.getDisplayHeight());
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
    }

    /**
     * @implNote Initializes the Stencil Buffer to write to
     */
    public static void initStencilToWrite() {
        Utility.getFrameBuffer().b(false);
        checkSetupFBO(Utility.getFrameBuffer());
        glClear(GL_STENCIL_BUFFER_BIT);
        glEnable(GL_STENCIL_TEST);

        glStencilFunc(GL_ALWAYS, 1, 1);
        glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
        glColorMask(false, false, false, false);
    }

    /**
     * @param ref (usually 1)
     * @implNote Reads the Stencil Buffer and stencils it onto everything until
     * @see StencilUtil#uninitStencilBuffer()  is called
     */
    public static void readStencilBuffer(int ref) {
        glColorMask(true, true, true, true);
        glStencilFunc(GL_EQUAL, ref, 1);
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
    }

    public static void uninitStencilBuffer() {
        glDisable(GL_STENCIL_TEST);
    }
}
