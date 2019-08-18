package com.favouritedragon.arcaneessentials;

import com.favouritedragon.arcaneessentials.proxy.IProxy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import electroblob.wizardry.spell.Spell;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;

@Mod(modid = ArcaneEssentials.MODID, name = ArcaneEssentials.NAME, version = ArcaneEssentials.VERSION, dependencies="required-after:ebwizardry")
public class ArcaneEssentials
{
    public static final String MODID = "arcane_essentials";
    public static final String NAME = "Arcane Essentials";
    public static final String VERSION = "1.0";
    public static final String MC_VERSION = "1.12.2";

    public static final String CLIENT = "com.favouritedragon.arcaneessentials.client.ClientProxy";
	public static final String SERVER = "com.favouritedragon.arcaneessentials.proxy.ServerProxy";

    private static Logger logger;

    @Mod.Instance(ArcaneEssentials.MODID)
    public static ArcaneEssentials instance;

	@SidedProxy(clientSide = ArcaneEssentials.CLIENT, serverSide = ArcaneEssentials.SERVER)
	public static IProxy proxy;


	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
        proxy.registerRender();
        RegisterHandler.registerAll();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.Init(event);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		for(Spell spell : Spell.getSpells(Spell.allSpells)) {

			try {

				FileWriter writer = new FileWriter("generated\\" + spell.getRegistryName().getPath() + ".json");

				JsonObject json = new JsonObject();

				JsonObject enabled = new JsonObject();

				enabled.addProperty("books", true);
				enabled.addProperty("scrolls", true);
				enabled.addProperty("wands", true);
				enabled.addProperty("npcs", true);
				enabled.addProperty("dispensers", true);
				enabled.addProperty("commands", true);
				enabled.addProperty("treasure", true);
				enabled.addProperty("trades", true);
				enabled.addProperty("looting", true);

				json.add("enabled", enabled);

				json.addProperty("tier", spell.tier.getUnlocalisedName());
				json.addProperty("element", spell.element.name());
				json.addProperty("type", spell.type.name());

				json.addProperty("cost", spell.cost);
				json.addProperty("chargeup", 0);
				json.addProperty("cooldown", spell.cooldown);

				json.add("base_properties", new JsonObject());

				gson.toJson(json, writer);

				writer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		}

    @EventHandler
	public void postInit(FMLInitializationEvent event) {
		proxy.postInit(event);
	}


}
