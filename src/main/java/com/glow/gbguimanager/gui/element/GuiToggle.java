package com.glow.gbguimanager.gui.element;

import com.glow.gbguimanager.baselib.math.Vector;
import com.glow.gbguimanager.render.DrawUtil;
import com.glow.gbguimanager.render.TextBuilder;
import com.glow.gbguimanager.baselib.Container;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class GuiToggle extends GuiWidget {

    private final Container<Boolean> toggleContainer;
    private final String title;

    protected float toggleFade = 0;

    public GuiToggle(Screen screen, String title, Container<Boolean> toggleContainer, Vector pos, Vector size) {
        super(screen,pos,size);
        this.toggleContainer = toggleContainer;
        this.title = title;
    }


    @Override
    protected void drawWidget(GuiGraphics graphics, Vector mousePos) {
        //Draw Background
        DrawUtil.drawRectangleRound(graphics, getPos(), getSize(), colorManager.getBackgroundColor());

        //Draw Fill
        DrawUtil.drawRectangleRound(graphics, getPos(), getSize(),new Color(colorManager.getWidgetColor().getRed(),colorManager.getWidgetColor().getGreen(),colorManager.getWidgetColor().getBlue(),(int) toggleFade));

        //Draw Text
        double scale = 1;
        if (DrawUtil.getFontTextWidth(title,fontRenderer) > getSize().getX() - 2) scale = (getSize().getX() - 2)/(DrawUtil.getFontTextWidth(title,fontRenderer) + 2);
        TextBuilder.start(getTitle(), getPos().getAdded(new Vector(2, 1 + getSize().getY() / 2 - DrawUtil.getFontTextHeight(fontRenderer) / 2)), Color.WHITE).scale((float)scale).draw(graphics);
    }


    @Override
    public void mousePressed(int button, int state, Vector mousePos) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && state == GLFW.GLFW_PRESS && isMouseOver()) {
            getContainer().set(!getContainer().get());
        }
    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {
        //NULL
    }


    @Override
    public void runAnimation(int speed) {
        super.runAnimation(speed);
        toggleFade = getNextFade(getContainer().get(), toggleFade, 0, 150, 2f * speed);
    }


    public String getTitle() {
        return title;
    }

    @Override
    public Container<Boolean> getContainer() {
        return toggleContainer;
    }

}
