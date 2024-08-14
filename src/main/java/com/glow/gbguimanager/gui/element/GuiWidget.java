package com.glow.gbguimanager.gui.element;

import com.glow.gbguimanager.baselib.action.StopWatch;
import com.glow.gbguimanager.baselib.math.Vector;
import com.glow.gbguimanager.gui.BaseGUI;
import com.glow.gbguimanager.gui.GuiManager;
import com.glow.gbguimanager.render.ColorManager;
import com.glow.gbguimanager.render.DrawUtil;
import com.glow.gbguimanager.render.FontRenderer;
import com.glow.gbguimanager.render.TextBuilder;
import com.glow.gbguimanager.baselib.Container;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import java.awt.*;

import static com.glow.gbguimanager.gui.GuiManager.MC;

public abstract class GuiWidget {

    private final Screen screen;

    private String tooltip;

    protected Container<Vector> pos;
    protected Container<Vector> size;

    private boolean mouseOver;

    private boolean drawTooltip;
    private boolean drawHoverHighlight;

    protected float hoverFade;

    public final StopWatch hoverWatch;

    protected FontRenderer fontRenderer;

    protected ColorManager colorManager = GuiManager.getInstance().colorManager;//LitematicaRewrite.COLOR_MANAGER;

    public GuiWidget(Screen screen, Vector pos, Vector size) {
        this.screen = screen;
        this.pos = new Container<>(pos);
        this.size = new Container<>(size);
        this.drawTooltip = true;
        this.drawHoverHighlight = true;
        this.hoverFade = 0;
        this.hoverWatch = new StopWatch();
        this.fontRenderer = GuiManager.getInstance().getFontRenderer();
        this.tooltip = "";
    }

    public GuiWidget tooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public GuiWidget fontRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
        return this;
    }


    public void draw(GuiGraphics graphics, Vector mousePos) {
        this.mouseOver = updateMouseOver(mousePos);

        drawWidget(graphics, mousePos);

        if (drawHoverHighlight) drawHoverHighlight(graphics, mousePos);
        if (drawTooltip) {
            if (isMouseOver()) hoverWatch.start();
            else hoverWatch.stop();
            if (!tooltip.isEmpty()) drawTooltip(graphics,mousePos,tooltip);
        }
    }

    /**
     * The drawWidget method is to be overwritten by all extension widgets, containing their content, aside from the necessary widget features
     * @param graphics
     * @param mousePos
     */
    protected abstract void drawWidget(GuiGraphics graphics, Vector mousePos);

    public abstract void mousePressed(int button, int state, Vector mousePos);

    public abstract void keyPressed(int key, int scancode, int modifiers);


    public void runAnimation(int speed) {
        hoverFade = getNextFade(isMouseOver(), hoverFade, 0, 50, speed);
    }

    protected boolean updateMouseOver(Vector mousePos) {
        boolean mouseOverX = mousePos.getX() >= getPos().getX() && mousePos.getX() <= getPos().getX() + getSize().getX();
        boolean mouseOverY = mousePos.getY() >= getPos().getY() && mousePos.getY() <= getPos().getY() + getSize().getY();
        if (mouseOverX && mouseOverY && getScreen() instanceof BaseGUI gui) {
            gui.hoveredElements.add(this);
            return mouseOver = (!gui.prevHoveredElements.isEmpty() && gui.prevHoveredElements.getLast() == this);
        }
        return mouseOver = false;
    }


    private void drawHoverHighlight(GuiGraphics graphics, Vector mousePos) {
        DrawUtil.drawRectangleRound(graphics, getPos(), getSize(), new Color(255, 255, 255, (int) hoverFade));
    }

    public void drawTooltip(GuiGraphics graphics, Vector mousePos, String description) {
        if (isMouseOver() && hoverWatch.hasTimePassedS(.5)) {
            Vector pos = mousePos.getAdded(6, -8).clone();
            if (pos.getX() + DrawUtil.getFontTextWidth(description,fontRenderer) + 2 > MC.getWindow().getGuiScaledWidth()) pos.setX(MC.getWindow().getGuiScaledWidth() - DrawUtil.getFontTextWidth(description,fontRenderer) - 2);

            DrawUtil.drawRectangleRound(graphics, pos, new Vector(DrawUtil.getFontTextWidth(description,fontRenderer) + 4, DrawUtil.getFontTextHeight(fontRenderer) + 3), colorManager.getBackgroundColor());
            TextBuilder.start(description, pos.getAdded(2, 2), Color.WHITE).draw(graphics);
        }
    }


    protected static float getNextFade(boolean condition, float fade, int min, int max, float speed) {
        if (condition) {
            if (fade < max) fade += speed;
        } else {
            if (fade > min) fade -= speed;
        }
        return Math.clamp(fade,0,255);
    }


    public void setPos(Vector vector) {
        pos.set(vector);
    }

    public void setSize(Vector vector) {
        this.size.set(vector);
    }

    public void setHoverHighlightVisible(boolean drawHoverHighlight) {
        this.drawHoverHighlight = drawHoverHighlight;
    }

    public void setTooltipVisible(boolean drawTooltip) {
        this.drawTooltip = drawTooltip;
    }


    public Vector getPos() {
        return pos.get();
    }

    public Vector getSize() {
        return size.get();
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public Screen getScreen() {
        return screen;
    }

    /**
     * Every widget is capable of having its own "Container"
     * This value is encompassed in a container class so that the class may be overwritten for settings/savings purposes
     * @return
     */
    public Container<?> getContainer() {
        return null;
    }

}
