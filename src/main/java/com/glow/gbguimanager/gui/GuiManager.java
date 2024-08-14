package com.glow.gbguimanager.gui;

import com.glow.gbguimanager.render.ColorManager;
import com.glow.gbguimanager.render.FontRenderer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GuiManager {

    public static final Minecraft MC = Minecraft.getInstance();

    private static final GuiManager INSTANCE = new GuiManager();

    public static GuiManager getInstance() {
        return INSTANCE;
    }


    // -------------------------------------


    private final HashMap<String, GuiData> guiList;

    private final ArrayList<String> mappingCategories = new ArrayList<>();

    private FontRenderer fontRenderer;

    @Deprecated //TODO: Phase out this stupid color manager, I hate it
    public ColorManager colorManager = new ColorManager(ColorManager.Theme.LIGHTBLUE);


    public GuiManager() {
        this.guiList = new HashMap<>();
        this.fontRenderer = new FontRenderer("Arial", Font.PLAIN);
    }

    public GuiManager addGUI(BaseGUI gui, @Nullable KeyMapping mapping) {
        guiList.put(gui.getTitle().getString(), new GuiData(gui, mapping));
        if (mapping != null && !mappingCategories.contains(mapping.getCategory())) mappingCategories.add(mapping.getCategory());
        return this;
    }

    public void setFontRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }


    public BaseGUI getGUI(String title) {
        return guiList.get(title).gui();
    }

    public HashMap<String, GuiData> getGUIList() {
        return guiList;
    }

    public ArrayList<String> getMappingCategories() {
        return mappingCategories;
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }


    public record GuiData(BaseGUI gui, @Nullable KeyMapping mapping) {
    }
}