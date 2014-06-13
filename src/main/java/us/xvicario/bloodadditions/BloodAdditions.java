package us.xvicario.bloodadditions;

import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mod(modid = BloodAdditions.MODID, version = BloodAdditions.VERSION)
public class BloodAdditions
{
    public static final String MODID = "bloodadditions";
    public static final String VERSION = "0.1";

    public static Item sigilSaturation;
    public static Item sigilHealth;
    public static Item sigilPig;
    public static Item sigilBaseHealth;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        sigilSaturation = new SigilSaturation().setUnlocalizedName("sigilSaturation");
        sigilHealth = new SigilHealth().setUnlocalizedName("sigilHealth");
        sigilPig = new SigilPork().setUnlocalizedName("sigilPork");
        sigilBaseHealth = new SigilBaseHealth().setUnlocalizedName("baseHealth");
        GameRegistry.registerItem(sigilSaturation, "sigilSaturation");
        GameRegistry.registerItem(sigilHealth, "sigilHealth");
        GameRegistry.registerItem(sigilPig, "sigilPork");
        GameRegistry.registerItem(sigilBaseHealth, "baseHealth");

        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(sigilHealth), new ItemStack(sigilBaseHealth),5,200000,50,100,false);
        GameRegistry.addRecipe(new ItemStack(sigilBaseHealth), "bgb", "bdb", "dod", 'b', ModItems.demonBloodShard, 'g', new ItemStack(Items.golden_apple,1,1), 'd', ModItems.demonicSlate, 'o', ModItems.archmageBloodOrb);
    }
}
