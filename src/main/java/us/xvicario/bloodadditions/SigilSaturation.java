package us.xvicario.bloodadditions;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.items.interfaces.ArmourUpgrade;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Brian on 6/9/2014.
 */
public class SigilSaturation extends EnergyItems implements ArmourUpgrade {

    @SideOnly(Side.CLIENT)
    private static IIcon activeIcon;
    @SideOnly(Side.CLIENT)
    private static IIcon passiveIcon;

    public SigilSaturation() {
        super();
        this.maxStackSize = 1;
        setEnergyUsed(250);
        setCreativeTab(AlchemicalWizardry.tabBloodMagic);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("bloodadditions:SaturationSigil_Deactivated");
        this.activeIcon = iconRegister.registerIcon("bloodadditions:SaturationSigil_Activated");
        this.passiveIcon = this.itemIcon;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (stack.stackTagCompound == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound tag = stack.stackTagCompound;
        if (tag.getBoolean("isActive")) {
            return this.activeIcon;
        } else {
            return this.passiveIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        if (par1 == 1) {
            return this.activeIcon;
        } else {
            return this.passiveIcon;
        }
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add("Yum... Food!");
        if (!(par1ItemStack.stackTagCompound == null)) {
            if (par1ItemStack.stackTagCompound.getBoolean("isActive")) {
                par3List.add("Activated");
            } else {
                par3List.add("Deactivated");
            }
            par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        EnergyItems.checkAndSetItemOwner(itemstack, player);
        if (player.isSneaking()) {
            return itemstack;
        }
        if (itemstack.stackTagCompound == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound tag = itemstack.stackTagCompound;
        tag.setBoolean("isActive", !(tag.getBoolean("isActive")));
        if (tag.getBoolean("isActive")) {
            itemstack.setItemDamage(1);
            tag.setInteger("worldTimeDelay", (int)(world.getWorldTime() - 1) % 200);
            player.addPotionEffect(new PotionEffect(Potion.field_76443_y.id,3,1));
            if (!player.capabilities.isCreativeMode) {
            }
        } else {
            itemstack.setItemDamage(0);
        }
        return itemstack;
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer)entity;
        if (itemstack.stackTagCompound == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        if (itemstack.stackTagCompound.getBoolean("isActive")) {
            player.addPotionEffect(new PotionEffect(Potion.field_76443_y.id,3,1));
        }
        if (world.getWorldTime() % 200 == itemstack.stackTagCompound.getInteger("worldTimeDelay")) {

        }
    }

    @Override
    public void onArmourUpdate(World world, EntityPlayer entityPlayer, ItemStack itemStack) {
        if (itemStack.stackTagCompound == null) {
            itemStack.setTagCompound((new NBTTagCompound()));
        }
        entityPlayer.addPotionEffect(new PotionEffect(Potion.field_76443_y.id,3,1,true));
    }

    @Override
    public boolean isUpgrade() {
        return true;
    }

    @Override
    public int getEnergyForTenSeconds() {
        return 50;
    }
}
