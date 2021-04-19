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
	
	public Main() {
		instance = this;
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void init (FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

		eventProcessor = new EventProcessor();
		log.info("postman event system initialized.");

		settingManager = new SettingManager();
		log.info("settings system initialized.");

		moduleManager = new ModuleManager();
		log.info("module system initialized.");
		
		moduleManagerPlusPlus = new ModuleManagerPlusPlus();
		log.info("modulemanagerplusplus initialized.");

		commandManager = new CommandManager();
		log.info("command system initialized.");
		
		friendManager = new FriendManager();
		log.info("friend system initialized.");

		cape = new Cape();
		log.info("capes initialized.");

		clickGui = new ClickGui();
		log.info("clickGui initialized.");
		
		clickGuiSave = new ClickGuiSave();
		clickGuiLoad = new ClickGuiLoad();
		Runtime.getRuntime().addShutdownHook(new ConfigStopper());
		saveLoad = new SaveLoad();
		log.info("configs initialized.");
		
		log.info("postman initialization finished.");
	
	}
}