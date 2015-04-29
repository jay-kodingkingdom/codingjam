package com.kodingkingdom.codingjam;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public enum CodingJamConfig {

	KODELEVELS("codingjam.kodelevels"),
	LEVELHEIGHT("codingjam.levelheight"),
	LEVELOFFSET("codingjam.leveloffset");
	
	
	public final String config;
	public ArrayList<KodeLevel> levels;
	public int levelHeight;
	public int levelOffset;
	
	private CodingJamConfig(String Config){
		config=Config;}
		
	public static void loadConfig(){
		CodingJamPlugin plugin = CodingJamPlugin.getPlugin();
		FileConfiguration config = plugin.getConfig();
		
		KODELEVELS.levels = new ArrayList<KodeLevel> ();
		
		try{
			KODELEVELS.levelHeight = config.getInt(LEVELHEIGHT.config);
			KODELEVELS.levelOffset = config.getInt(LEVELOFFSET.config);
			for (String codeLevel : config.getStringList(KODELEVELS.config)){
				KodeLevel level = KODELEVELS.new KodeLevel();
				level.modX1=config.getInt(codeLevel+".modX1");
				level.modY1=config.getInt(codeLevel+".modY1");
				level.modZ1=config.getInt(codeLevel+".modZ1");
				level.modX2=config.getInt(codeLevel+".modX2");
				level.modY2=config.getInt(codeLevel+".modY2");
				level.modZ2=config.getInt(codeLevel+".modZ2");
				level.wrongBook=new ItemStack(Material.BOOK_AND_QUILL);
				BookMeta codeMeta = (BookMeta)level.wrongBook.getItemMeta();
				codeMeta.setPages(config.getStringList(codeLevel+".wrongbook"));
				level.wrongBook.setItemMeta(codeMeta);
				level.rightBook=new ItemStack(Material.BOOK_AND_QUILL);
				codeMeta = (BookMeta)level.rightBook.getItemMeta();
				codeMeta.setPages(config.getStringList(codeLevel+".rightbook"));
				level.rightBook.setItemMeta(codeMeta);
				KODELEVELS.levels.add(level);}
			plugin.getLogger().info("Config successfully loaded");}
		
		catch(Exception e){
			plugin.getLogger().severe("Could not load config!");
			plugin.getLogger().severe("ERROR MESSAGE: "+e.getMessage());
			e.printStackTrace();
			config.set("codingjam.ERROR", true);}}



	public class KodeLevel{
		int modX1;int modY1;int modZ1;
		int modX2;int modY2;int modZ2;
		ItemStack wrongBook;
		ItemStack rightBook;}}
