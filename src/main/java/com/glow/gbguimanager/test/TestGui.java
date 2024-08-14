package com.glow.gbguimanager.test;

import com.glow.gbguimanager.baselib.math.Vector;
import com.glow.gbguimanager.gui.BaseGUI;
import com.glow.gbguimanager.gui.element.GuiButton;

public class TestGui extends BaseGUI {

    GuiButton button = new GuiButton(this,"Title",new Vector(100,100),new Vector(200,60),(g) -> {
        System.out.println("Pressed!");
    });

    public TestGui() {
        super("TestGUI");
        addGuiElements(button);
    }


    @Override
    public void onOpen() {
        System.out.println("Gui Opened!");
    }

    @Override
    public void onClose() {
        System.out.println("Gui Closed!");
    }

}
