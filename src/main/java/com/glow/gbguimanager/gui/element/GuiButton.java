package com.glow.gbguimanager.gui.element;

import com.glow.gbguimanager.baselib.math.Vector;
import com.glow.gbguimanager.render.DrawUtil;
import com.glow.gbguimanager.render.TextBuilder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class GuiButton extends GuiWidget {

    private String title;
    private ActionParams action;
    private boolean pressing;

    public GuiButton(Screen screen, String title, Vector pos, Vector size, ActionParams action) {
        super(screen, pos, size);
        this.title = title;
        this.action = action;
        this.pressing = false;
    }

    @Override
    protected void drawWidget(GuiGraphics graphics, Vector mousePos) {
        //Draw Background
        DrawUtil.drawRectangleRound(graphics, getPos(), getSize(), getColor());

        //Draw Text
        double scale = 1;
        if (DrawUtil.getFontTextWidth(getTitle(),fontRenderer) > getSize().getX() - 2) scale = (getSize().getX() - 2)/(DrawUtil.getFontTextWidth(getTitle(),fontRenderer) + 2);
        Vector pos = getPos().getAdded(new Vector(getSize().getX() / 2 - DrawUtil.getFontTextWidth(getTitle(),fontRenderer) / 2, 1 + getSize().getY() / 2 - DrawUtil.getFontTextHeight(fontRenderer) / 2));
        TextBuilder.start(getTitle(),pos,Color.WHITE).scale((float)scale).draw(graphics);
    }

    @Override
    public void mousePressed(int button, int state, Vector mousePos) {
        if (button != 0) return;
        switch (state) {
            case GLFW.GLFW_PRESS -> {
                if (isMouseOver())
                    pressing = true;
            }
            case GLFW.GLFW_RELEASE -> {
                pressing = false;
                if (isMouseOver()) getAction().run(mousePos, button);
            }
        }
    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setAction(ActionParams action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    private ActionParams getAction() {
        return action;
    }

    public Color getColor() {
        if (pressing) {
            int[] colVal = {colorManager.getBaseColor().getRed(), colorManager.getBaseColor().getGreen(), colorManager.getBaseColor().getBlue()};
            for (int i = 0; i < colVal.length; i++) {
                //colVal[i] = (int)MathUtil.getBoundValue(colVal[i] - 50,0,255);
                colVal[i] = (int)Math.clamp(colVal[i] - 50,0,255);
            }
            return new Color(colVal[0],colVal[1],colVal[2],colorManager.getBaseColor().getAlpha());
        } else {
            return colorManager.getBaseColor();
        }
    }


    @FunctionalInterface
    public interface ActionParams {
        void run(Object... params);
    }

}
