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
		implementation 'com.github.glowskibroski:GBGUIManager:1.0.0'
		modImplementation 'com.github.glowskibroski:GBGUIManager:1.0.0'
	}

You're done! Run gradle to implement the project, and you have full access to the library