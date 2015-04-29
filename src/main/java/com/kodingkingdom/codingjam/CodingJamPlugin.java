package com.kodingkingdom.codingjam;
import java.util.logging.Level;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CodingJamPlugin extends JavaPlugin implements Listener{
		
	CodingJam codingJam;
	
	@Override
    public void onEnable(){
    	codingJam=new CodingJam(this);
    	codingJam.Live();} 
    @Override
    public void onDisable(){codingJam.Die();}
    
    static CodingJamPlugin instance=null;
    public CodingJamPlugin(){
    	super();
    	instance=this;}
    public static CodingJamPlugin getPlugin(){
    	return instance;}
    public static void say(String msg){
    	instance.getLogger().log(Level.INFO//Level.FINE
    			, msg);}
    public static void debug(String msg){
    	instance.getLogger().log(Level.INFO//Level.FINE
    			, msg);}}