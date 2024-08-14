package com.glow.gbguimanager.gui.element;

import com.glow.gbguimanager.baselib.math.Vector;
import com.glow.gbguimanager.render.DrawUtil;
import com.glow.gbguimanager.render.TextBuilder;
import com.glow.gbguimanager.baselib.Container;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class GuiSlider<T extends Number> extends GuiWidget {

    private final Container<T> valueContainer;
    private final String title;
    private final T min,max,step;

    private boolean sliding;

    private GuiSlider(Screen screen, String title, Vector pos, Vector size, Container<T> valueContainer, T min, T max, T step) {
        super(screen, pos, size);
        this.valueContainer = valueContainer;
        this.title = title;
        this.min = min;
        this.max = max;
        this.step = step;

        this.sliding = false;
    }

    @Override
    protected void drawWidget(GuiGraphics graphics, Vector mousePos) {
        if (sliding) updateSliderValue(mousePos);

        //Draw the background
        DrawUtil.drawRectangleRound(graphics, getPos(), getSize(), colorManager.getBackgroundColor());

        //Draw the slider fill
        double valueRange = getMax().doubleValue() - getMin().doubleValue();
        double sliderWidth = getSize().getX() / valueRange * (getContainer().get().doubleValue() - getMin().doubleValue());
        int border = (int) getSize().getX() / 40;
        DrawUtil.drawRectangleRound(graphics, getPos().getAdded(new Vector(border, border)), new Vector(sliderWidth - (sliderWidth > border ? border : 0), getSize().getY() - border * 2), new Color(colorManager.getWidgetColor().getRed(), colorManager.getWidgetColor().getGreen(), colorManager.getWidgetColor().getBlue(), 150), 1.5, false);

        //Draw the slider node
        int nodeWidth = (int) getSize().getY() / 4;
        double nodeX = sliderWidth - nodeWidth / 2f;
        if (nodeX + nodeWidth > getSize().getX()) nodeX = getSize().getX() - nodeWidth;
        if (nodeX < 0) nodeX = 0;
        DrawUtil.drawRectangleRound(graphics, getPos().getAdded(new Vector(nodeX, 0)), new Vector(nodeWidth, getSize().getY()), new Color(colorManager.getWidgetColor().getRed(), colorManager.getWidgetColor().getGreen(), colorManager.getWidgetColor().getBlue(), 255), 1.5, false);

        //Draw the slider text
        double scale = 1;
        boolean isInteger = getContainer().get() instanceof Integer;//getContainer().getType().equals("integer");
        String title = getTitle() + ": " + (isInteger ? getContainer().get().intValue() : String.format("%.2f", getContainer().get()));
        if (DrawUtil.getFontTextWidth(title,fontRenderer) + border + 1 > getSize().getX() - 2) scale = (getSize().getX() - 2)/(DrawUtil.getFontTextWidth(title,fontRenderer) + border + 2);
        TextBuilder.start( title, getPos().getAdded(new Vector(border + 1, 1 + getSize().getY() / 2 - DrawUtil.getFontTextHeight(fontRenderer) / 2)), Color.WHITE).scale((float) scale).dynamic().draw(graphics);
    }


    @Override
    public void mousePressed(int button, int state, Vector mousePos) {
        if (button != 0) return;
        switch (state) {
            case GLFW.GLFW_PRESS -> {
                if (isMouseOver()) setSliding(true);
            }
            case GLFW.GLFW_RELEASE -> {
                if (sliding) setSliding(false);
            }
        }
    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {
    }

    private void updateSliderValue(Vector mousePos) {
        double settingRange = getMax().doubleValue() - getMin().doubleValue();

        double sliderWidth = mousePos.getX() - getPos().getX();
        double sliderPercent = Math.clamp(sliderWidth, 0, getSize().getX()) / getSize().getX();

        double calculatedValue = getMin().doubleValue() + sliderPercent * settingRange;
        double val = roundDouble((((Math.round(calculatedValue / getStep().doubleValue())) * getStep().doubleValue())), 2); //Rounds the slider based off of the step val

        boolean isInteger = getContainer().get() instanceof Integer;//getContainer().getType().equals("integer");
        T value = isInteger ? (T) (Integer) (int) val : (T) (Double) val;
        getContainer().set(value);
    }

    public static double roundDouble(double number, int sigFigs) {
        String num = String.format("%." + sigFigs + "f", number);
        return Double.parseDouble(num);
    }


    public void setSliding(boolean sliding) {
        this.sliding = sliding;
    }


    public String getTitle() {
        return title;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T getStep() {
        return step;
    }

    @Override
    public Container<T> getContainer() {
        return valueContainer;
    }
}
