# GBGUIManager

GBGUIManager, in full: GlowskiBroski GUI Manager, is an easy to set up GUI library for Minecraft Fabric.
It is used to create simple GUI systems for any mod.


# What does it come with?

GBGUI ships with easy to set up widgets including:
Button, ModeSelector, Slider, TextField, and Toggle.
The library allows for custom widgets to be created when implemented in a project

# How do I include this library in my project?

First, you need to set jitpack as a repository source. Add this to your build.gradle

    repositories {
        maven { url 'https://jitpack.io' }
    }

Next, add a dependency for the module to your build.gradle

    dependencies {
		implementation 'com.github.glowskibroski:GBGUIManager:-SNAPSHOT'
		modImplementation 'com.github.glowskibroski:GBGUIManager:-SNAPSHOT'
	}

You're done! Run gradle to implement the project, and you have full access to the library

# How do I create a GUI

Create a new class, and have that class extend the BaseGUI Class

	import com.glow.gbgui.gui.BaseGUI;

	public class NewGUI extends BaseGUI {
 	}

Add widgets to your GUI with the addElements(); method inside of the constructor of the GUI class

 	public NewGUI(String title) {
  		super(title);
    	addElements(new GuiButton(...));
     	}

After setting up your GUI, go to your mod initializer, and add the GUI to the GuiManager. You may also add a key mapping. If you prefer to have no mapping, type "null" as the second parameter

	@Override
 	public void onInitialize() {
		GuiManager.getInstance()
  			.addGUI(new NewGUI("Title1"), new KeyMapping("Mapping Name 1", GLFW.KEY ID, "Category Name"))
  			.addGUI(new AnotherGUI("Title2"), null)
  			.addGUI(new MoreGUI("Title3"), new KeyMapping("Mapping Name 3", GLFW.KEY ID, "Category Name"))
  			.addGUI(new LookAnotherOne("Title4"), null)
     			...
  	}
	
