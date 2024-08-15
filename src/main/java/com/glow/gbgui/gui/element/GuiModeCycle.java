package com.glow.gbgui.gui.element;

import com.glow.gbgui.baselib.math.Vector;
import com.glow.gbgui.render.DrawUtil;
import com.glow.gbgui.render.TextBuilder;
import com.glow.gbgui.baselib.Container;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GuiModeCycle<T> extends GuiWidget {

    private final Container<T> container;

    private final String title;

    private final ArrayList<T> modes;

    private boolean pressingR;
    private boolean pressingL;

    private int arrayNumber;

    @SafeVarargs
    public GuiModeCycle(Screen screen, String title, Vector pos, Vector size, Container<T> container, T... modes) {
        super(screen, pos, size);
        this.container = container;
        this.title = title;
        this.modes = (ArrayList<T>) Arrays.asList(modes);
    }

    @Override
    protected void drawWidget(GuiGraphics graphics, Vector mousePos) {
        //Draw Background
        DrawUtil.drawRectangleRound(graphics,getPos(),getSize(), colorManager.getBackgroundColor());

        //Draw Mode Arrows
        DrawUtil.drawArrowHead(graphics,getPos().getAdded(2,2),(float)getSize().getY()-4,getColorL(),false,true);
        DrawUtil.drawArrowHead(graphics,getPos().getAdded(-2 + getSize().getX() - 3,2),(float)getSize().getY()-4,getColorR(),false,false);

        //Draw the text
        double scale = 1;
        String text = title + ": " + getContainer().get();
        if (DrawUtil.getFontTextWidth(text,fontRenderer) > getSize().getX() - 2) scale = (getSize().getX() - 2)/(DrawUtil.getFontTextWidth(text,fontRenderer) + 2);
        TextBuilder.start(text,getPos().getAdded(getSize().getX()/2 - DrawUtil.getFontTextWidth(text,fontRenderer)/2,1 + getSize().getY()/2 - DrawUtil.getFontTextHeight(fontRenderer)/2),Color.WHITE).scale((float)scale).draw(graphics);
    }

    @Override
    public void mousePressed(int button, int action, Vector mousePos) {
        switch (button) {
            case GLFW.GLFW_MOUSE_BUTTON_RIGHT -> {
                switch (action) {
                    case GLFW.GLFW_PRESS -> {
                        if (isMouseOver()) pressingR = true;
                    }
                    case GLFW.GLFW_RELEASE -> {
                        if (isMouseOver()) switchMode(true);
                        pressingR = false;
                    }
                }
            }

            case GLFW.GLFW_MOUSE_BUTTON_LEFT -> {
                switch (action) {
                    case GLFW.GLFW_PRESS -> {
                        if (isMouseOver()) pressingL = true;
                    }
                    case GLFW.GLFW_RELEASE -> {
                        if (isMouseOver()) switchMode(false);
                        pressingL = false;
                    }
                }
            }
        }
    }

    private void switchMode(boolean forward) {
        int increment = forward ? 1 : -1;
        int wrapAroundIndex = forward ? getModes().size() : - 1;
        int endIndex = forward ? 0 : getModes().size() - 1;

        int arrayNumber = getModes().indexOf(getContainer().get()) + increment;
        getContainer().set(getModes().get(arrayNumber == wrapAroundIndex ? endIndex : arrayNumber));
    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {
    }

    @Override
    public void runAnimation(int speed) {
        super.runAnimation(speed);
        //TODO: Add a left/right scroll animation when changing modes
    }

    public Color getColorR() {
        if (pressingR) {
            int[] colVal = {colorManager.getWidgetColor().getRed(), colorManager.getWidgetColor().getGreen(), colorManager.getWidgetColor().getBlue()};
            for (int i = 0; i < colVal.length; i++) {
                colVal[i] = (int)Math.clamp(colVal[i] - 50,0,255);
            }
            return new Color(colVal[0],colVal[1],colVal[2],colorManager.getWidgetColor().getAlpha());
        } else {
            return colorManager.getWidgetColor();
        }
    }

    public Color getColorL() {
        if (pressingL) {
            int[] colVal = {colorManager.getWidgetColor().getRed(), colorManager.getWidgetColor().getGreen(), colorManager.getWidgetColor().getBlue()};
            for (int i = 0; i < colVal.length; i++) {
                colVal[i] = (int)Math.clamp(colVal[i] - 50,0,255);
            }
            return new Color(colVal[0],colVal[1],colVal[2],colorManager.getWidgetColor().getAlpha());
        } else {
            return colorManager.getWidgetColor();
        }
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<T> getModes() {
        return modes;
    }

    @Override
    public Container<T> getContainer() {
        return container;
    }
}
