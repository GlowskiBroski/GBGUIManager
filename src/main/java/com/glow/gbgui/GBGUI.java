package com.glow.gbgui;

import com.glow.gbgui.gui.GuiManager;
import com.glow.gbgui.test.TestGui;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class GBGUI implements ModInitializer {

	public static final Minecraft MC = Minecraft.getInstance();


	@Override
	public void onInitialize() {
		//GuiManager.getInstance().addGUI(new TestGui(),new KeyMapping("Test Gui Mapping", GLFW.GLFW_KEY_ENTER,"Gui Manager"));
	}


}