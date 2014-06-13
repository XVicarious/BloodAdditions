package us.xvicario.bloodadditions;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Brian on 6/9/2014.
 */
public class SigilPork extends EnergyItems {

    public SigilPork() {
        super();
        this.maxStackSize = 1;
        setEnergyUsed(0);
        setCreativeTab(AlchemicalWizardry.tabBloodMagic);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("bloodadditions:PorkSigil");
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add("Really expensive health!");
        if (!(par1ItemStack.stackTagCompound == null)) {
            par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        EnergyItems.checkAndSetItemOwner(itemstack, player);
        if (player.isSneaking()) {
            return itemstack;
        }
        if (!world.isRemote) {
            if (this.getCurrentEssence(player.getDisplayName()) >= this.getEnergyUsed()) {
                EntityPig pig = new EntityPig(world);
                pig.setPosition(player.posX, player.posY, player.posZ);
                world.joinEntityInSurroundings(pig);
                pig.setInvisible(false);
            }
        }
        return itemstack;
    }

}
