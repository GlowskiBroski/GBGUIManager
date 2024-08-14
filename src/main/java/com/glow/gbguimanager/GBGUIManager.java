package com.glow.gbguimanager;

import com.glow.gbguimanager.gui.GuiManager;
import com.glow.gbguimanager.test.TestGui;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class GBGUIManager implements ModInitializer {

	@Override
	public void onInitialize() {
		GuiManager.getInstance().addGUI(new TestGui(),new KeyMapping("Test Gui Mapping", GLFW.GLFW_KEY_ENTER,"Gui Manager"));
	}


}