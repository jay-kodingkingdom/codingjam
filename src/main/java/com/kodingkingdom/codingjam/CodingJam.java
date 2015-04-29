package com.kodingkingdom.codingjam;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.kodingkingdom.codingjam.CodingJamConfig.KodeLevel;
import com.worldcretornica.plotme.Plot;
import com.worldcretornica.plotme.PlotManager;

public class CodingJam{	
		
	CodingJamPlugin plugin;

	public void Live(){
		CodingJamConfig.loadConfig();
		Listener codingJamListener = new Listener(){
			@EventHandler(priority=EventPriority.MONITOR)
			public void onClickButton(PlayerInteractEvent e){
				try{
					if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
						e.getClickedBlock().getWorld().getName().equals("CodingJam")){
						if (e.getClickedBlock().getType().equals(Material.WOOD_BUTTON)){
							KodeLevel level = CodingJamConfig.KODELEVELS.levels.get((e.getPlayer().getLocation().getBlockY()-CodingJamConfig.KODELEVELS.levelOffset)/CodingJamConfig.KODELEVELS.levelHeight);
							e.getPlayer().getInventory().setItemInHand(level.wrongBook.clone());}
						else if (e.getClickedBlock().getType().equals(Material.STONE_BUTTON)){
							KodeLevel level = CodingJamConfig.KODELEVELS.levels.get((e.getPlayer().getLocation().getBlockY()-CodingJamConfig.KODELEVELS.levelOffset)/CodingJamConfig.KODELEVELS.levelHeight);
							ItemStack userBook = e.getPlayer().getItemInHand();
							if (Material.BOOK_AND_QUILL.equals(userBook.getType())){
								String userKode = "";
								for (String page : ((BookMeta)userBook.getItemMeta()).getPages()) {
									userKode+=page;}
								String rightKode = "";
								for (String page : ((BookMeta)level.rightBook.getItemMeta()).getPages()) {
									rightKode+=page;}
								if (userKode.replaceAll("§0", "").toLowerCase().replaceAll("\\s", "").equals(
										rightKode.replaceAll("§0", "").toLowerCase().replaceAll("\\s", ""))){
									for (int x = (level.modX1>level.modX2?level.modX2:level.modX1);
									x<=(level.modX1<level.modX2?level.modX2:level.modX1);
									x++){
										for (int y = (level.modY1>level.modY2?level.modY2:level.modY1);
										y<=(level.modY1<level.modY2?level.modY2:level.modY1);
										y++){
											for (int z = (level.modZ1>level.modZ2?level.modZ2:level.modZ1);
										z<=(level.modZ1<level.modZ2?level.modZ2:level.modZ1);
										z++){
												e.getClickedBlock().getRelative(x,y,z).setType(Material.AIR);}}}
									e.getPlayer().sendMessage("SUCCESS! YOU HAVE BROKEN THIS LEVEL!");
									if (level.equals(CodingJamConfig.KODELEVELS.levels.get(CodingJamConfig.KODELEVELS.levels.size()-1))){
										e.getPlayer().sendMessage("wooHOO! ALL LEVELS BLOWN AWAY!");
										World world = e.getClickedBlock().getWorld();
										world.playEffect(e.getClickedBlock().getLocation(), Effect.FIREWORKS_SPARK, 5);
										world.playEffect(e.getClickedBlock().getLocation(), Effect.GHAST_SHOOT, 5);
										world.playEffect(e.getClickedBlock().getLocation(), Effect.EXPLOSION_HUGE, 0);
										world.playEffect(e.getClickedBlock().getLocation(), Effect.PORTAL, 0);}}
								else {
									e.getPlayer().sendMessage("Ugh.... Maybe you should try again...");}}}}}
				catch(IndexOutOfBoundsException ex){}
				catch(Exception ex){}}};
		Bukkit.getServer().getPluginManager().registerEvents(codingJamListener, plugin);
		CommandExecutor codingJamCmd = new CommandExecutor(){
			public boolean onCommand(CommandSender sender, Command command,
					String label, String[] args) {
				
				if (sender instanceof Player){
					Player player = (Player)sender;
					brk: for (World world : Bukkit.getWorlds()){
						if (PlotManager.isPlotWorld(world) &&
								world.getName().equals("CodingJam")){
							for (Plot plot : PlotManager.getPlots(world).values()) {
								if (plot.owner.equalsIgnoreCase("CodingJam")&&
									(plot.isDenied(player.getName())||
									plot.isAllowed(player.getName()))) {
									player.teleport(PlotManager.getPlotHome(world, plot));
									break brk;}}}}}
				return true;}};
		plugin.getCommand("codingjam").setExecutor(codingJamCmd);
		plugin.getCommand("cj").setExecutor(codingJamCmd);}
	public void Die(){}
	
	CodingJam(CodingJamPlugin Plugin){
		plugin=Plugin;}}

