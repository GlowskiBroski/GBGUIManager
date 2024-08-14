package com.glow.gbguimanager.mixin;

import com.glow.gbguimanager.GBGUIManager;
import com.glow.gbguimanager.gui.GuiManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

@Mixin(Options.class)
public abstract class MixinOptions {

    @Shadow @Final @Mutable public KeyMapping[] keyMappings;

    /**
     * Custom Keybind addition for HUD and Module GUI
     * @param minecraft
     * @param file
     * @param ci
     */
    @Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    private void updateKeyMappings(Minecraft minecraft, File file, CallbackInfo ci) {
        ArrayList<KeyMapping> newMappings = new ArrayList<>();
        newMappings.addAll(Arrays.asList(keyMappings));
        for (GuiManager.GuiData data : GuiManager.getInstance().getGUIList().values()) newMappings.add(data.mapping());
        keyMappings = newMappings.toArray(new KeyMapping[0]);
    }
}