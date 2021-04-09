package me.srgantmoomoo;

import java.awt.Font;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.srgantmoomoo.postman.api.event.EventProcessor;
import me.srgantmoomoo.postman.api.proxy.CommonProxy;
import me.srgantmoomoo.postman.api.save.ClickGuiLoad;
import me.srgantmoomoo.postman.api.save.ClickGuiSave;
import me.srgantmoomoo.postman.api.save.ConfigStopper;
import me.srgantmoomoo.postman.api.save.SaveLoad;
import me.srgantmoomoo.postman.api.util.font.CustomFontRenderer;
import me.srgantmoomoo.postman.api.util.render.Cape;
import me.srgantmoomoo.postman.client.command.CommandManager;
import me.srgantmoomoo.postman.client.friend.FriendManager;
import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.module.ModuleManager;
import me.srgantmoomoo.postman.client.setting.SettingManager;
import me.srgantmoomoo.postman.client.ui.TabGui;
import me.srgantmoomoo.postman.client.ui.clickgui.ClickGui;
import me.srgantmoomoo.postmanplusplus.ModuleManagerPlusPlus;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/*
 * Written by @SrgantMooMoo on 11/17/20.
 */

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	public static ArrayList<Module> modules;
	
	int strong;
	int postman = strong;
	
	public static ModuleManager moduleManager;
	public static ModuleManagerPlusPlus moduleManagerPlusPlus;
	public static SettingManager settingManager;
	public static CommandManager commandManager;
	public static FriendManager friendManager;
	public static SaveLoad saveLoad;
	public static TabGui tabGui;
	public static Cape cape;
	public static ClickGui clickGui;
	public static EventProcessor eventProcessor;
	public static CustomFontRenderer customFontRenderer;
	public static ClickGuiSave clickGuiSave;
	public static ClickGuiLoad clickGuiLoad;
	
	public static final Logger log = LogManager.getLogger("postman");
	
	public static final EventBus EVENT_BUS = new EventManager();
	
	@Instance
	public static Main instance;
	public Main() { instance = this; }
	public static Main getInstance() { return instance; }
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public Object syncronize = new Object();

	public void fontInit() {

		customFontRenderer = new CustomFontRenderer(new Font("Comic Sans MS", Font.PLAIN, 18), false,false);
		printLog("custom font initialized.");
	}

	private void loadCfg() {
		
	}

	public void extClientInit() {
		MinecraftForge.EVENT_BUS.register(this);

		eventProcessor = new EventProcessor();
		printLog("postman event system initialized.");

		settingManager = new SettingManager();
		printLog("settings system initialized.");

		moduleManager = new ModuleManager();
		printLog("module system initialized.");
		
		moduleManagerPlusPlus = new ModuleManagerPlusPlus();
		printLog("module system initialized.");

		commandManager = new CommandManager();
		printLog("command system initialized.");
		
		friendManager = new FriendManager();
		printLog("friend system initialized.");

		cape = new Cape();
		printLog("capes initialized.");

		tabGui = new TabGui();
		printLog("tabgui initialized.");

		clickGui = new ClickGui();
		printLog("clickGui initialized.");

		saveLoad = new SaveLoad();
		printLog("configs initialized.");
		
		clickGuiSave = new ClickGuiSave();
		clickGuiLoad = new ClickGuiLoad();
		Runtime.getRuntime().addShutdownHook(new ConfigStopper());
		printLog("gui config initialized.");
		
		printLog("postman finished initializing.");

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// Create a thread with extClientInit
		Thread t = new Thread(this::extClientInit);
		// Start it
		t.start();
		// Run opengl
		fontInit();
		try {
			// Wait for extClientInit to finish
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("postman initialization finished.");
		// Start an async thread with loadCfg. I dunno why but, for some reasons, you cannot put this with
		// The other, if you do, it will create problems with CustomFontRenderer
		new Thread(this::loadCfg).start();

	}
	
	public void printLog(String text) {
		synchronized (syncronize) {
			log.info(text);
		}
	}
}