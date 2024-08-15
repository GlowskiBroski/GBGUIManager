package com.glow.gbgui.gui;

import com.glow.gbgui.baselib.math.Vector;
import com.glow.gbgui.gui.element.GuiWidget;
import com.glow.gbgui.render.DrawUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static com.glow.gbgui.GBGUI.MC;

public abstract class BaseGUI extends Screen {

    private final ArrayList<GuiWidget> guiElementList = new ArrayList<>();

    public LinkedList<GuiWidget> prevHoveredElements = new LinkedList<>();
    public LinkedList<GuiWidget> hoveredElements = new LinkedList<>();

    //TODO: Add Hint Option Back
    private final double hintFade = 0;
    private final boolean hintFadeIn = true;

    private float screenFadeIn = 0;

    public BaseGUI(String title) {
        super(Component.translatable(title));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Vector mousePos = new Vector(mouseX, mouseY);

        //SET FADE IN
        if (screenFadeIn < 1) screenFadeIn += .1f;
        guiGraphics.setColor(1f, 1f, 1f, screenFadeIn);

        //DRAW BLUR
        boolean shouldDrawBlur = true;
        if (shouldDrawBlur) {
            MC.gameRenderer.processBlurEffect(200);
            MC.getMainRenderTarget().bindWrite(false);
        }

        //DRAW BACKGROUND GRAY
        DrawUtil.drawRectangle(guiGraphics, Vector.NULL(), getSize(), new Color(0, 0, 0, 100));

        //DRAW ALL ELEMENTS
        for (GuiWidget element : getGuiElementList()) {
            element.runAnimation(9);
            element.draw(guiGraphics, mousePos);
        }

        //UPDATE HOVERED ELEMENTS
        prevHoveredElements = ((LinkedList<GuiWidget>) hoveredElements.clone());
        hoveredElements.clear();
    }

    @Override
    public boolean keyPressed(int key, int scancode, int modifiers) {
        boolean ret = super.keyPressed(key, scancode, modifiers);
        for (GuiWidget element : getGuiElementList()) {
            element.keyPressed(key, scancode, modifiers);
        }
        return ret;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        Vector mousePos = new Vector(x, y);
        for (GuiWidget element : getGuiElementList()) {
            element.mousePressed(button, 1, mousePos);
        }
        return super.mouseClicked(x, y, button);
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        Vector mousePos = new Vector(x, y);
        for (GuiWidget element : getGuiElementList()) {
            element.mousePressed(button, 0, mousePos);
        }
        return super.mouseReleased(x, y, button);
    }

    public abstract void onOpen();

    public abstract void onClose();

    public void open() {
        onOpen();
        MC.setScreen(this);
    }

    public void close(boolean escaped) {
        MC.setScreen(null);
        screenFadeIn = 0;
        if (!escaped) onClose();
    }

    public void close() {
        this.close(false);
    }

    public void toggle() {
        if (isOpen()) {
            close();
        } else if (MC.screen == null) {
            open();
        }
    }


    public void addGuiElements(GuiWidget... elements) {
        guiElementList.addAll(Arrays.asList(elements));
    }


    public Vector getSize() {
        return new Vector(this.width, this.height);
    }

    public boolean isOpen() {
        return MC.screen == this;
    }

    public ArrayList<GuiWidget> getGuiElementList() {
        return guiElementList;
    }


}
