package com.glow.gbgui.mixin;

import com.glow.gbgui.gui.GuiManager;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class MixinKeyboardHandler {

    @Inject(method = "keyPress", at = @At(value = "RETURN"), cancellable = true)
    private void keyPress(long l, int key, int scancode, int state, int modifiers, CallbackInfo ci) {
        if (l == Minecraft.getInstance().getWindow().getWindow()) {
            if (state != GLFW.GLFW_PRESS) return;

            String mapping = InputConstants.getKey(key, state).getName();
            for (GuiManager.GuiData data : GuiManager.getInstance().getGUIList().values()) {
                if (data.mapping() == null) continue;
                if (data.mapping().saveString().equals(mapping)) {
                    data.gui().toggle();
                    continue;
                }
                if (key == GLFW.GLFW_KEY_ESCAPE && data.gui().isOpen()) data.gui().close(true);
            }
        }
    }

}