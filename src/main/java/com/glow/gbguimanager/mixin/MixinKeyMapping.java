package com.glow.gbguimanager.mixin;

import com.glow.gbguimanager.gui.GuiManager;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(KeyMapping.class)
public class MixinKeyMapping {

    @Shadow @Final private static Map<String, Integer> CATEGORY_SORT_ORDER;

    @Inject(method = "compareTo(Lnet/minecraft/client/KeyMapping;)I", at = @At(value = "TAIL"), cancellable = true)
    private void compareTo(KeyMapping keyMapping, CallbackInfoReturnable<Integer> cir) {
        for (String category : GuiManager.getInstance().getMappingCategories()) {
            if (!CATEGORY_SORT_ORDER.containsKey(category)) {
                int index = 0;
                for (Integer i : CATEGORY_SORT_ORDER.values()) if (i > index) index = i;
                index++;
                CATEGORY_SORT_ORDER.put(category, index);
            }
        }
    }

}