package us.xvicario.bloodadditions;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.api.items.interfaces.ArmourUpgrade;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Brian on 6/9/2014.
 */
public class SigilHealth extends EnergyItems implements ArmourUpgrade {

    public SigilHealth() {
        super();
        this.maxStackSize = 1;
        setEnergyUsed(550);
        setCreativeTab(AlchemicalWizardry.tabBloodMagic);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("bloodadditions:HealthSigil");
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
        if (this.getCurrentEssence(player.getDisplayName()) < this.getEnergyUsed()) {
            player.attackEntityFrom(DamageSource.generic,4);
        } else {
            player.heal(1);
            EnergyItems.syphonBatteries(itemstack,player,getEnergyUsed());
        }
        return itemstack;
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer)entity;
    }

    @Override
    public void onArmourUpdate(World world, EntityPlayer entityPlayer, ItemStack itemStack) {
        if (itemStack.stackTagCompound == null) {
            itemStack.setTagCompound((new NBTTagCompound()));
        }
        if (isNearAltar(world, entityPlayer) && (entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().getItem() == ModItems.sacrificialDagger)) {
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 100, 99, true));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id,3,50,true));
        } else {
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id,3,1,true));
        }
    }

    @Override
    public boolean isUpgrade() {
        return true;
    }

    @Override
    public int getEnergyForTenSeconds() {
        return 250;
    }

    public boolean isNearAltar(World world, EntityPlayer player) {
        int posX = (int) Math.round(player.posX - 0.5f);
        int posY = (int) player.posY;
        int posZ = (int) Math.round(player.posZ - 0.5f);
        TEAltar altarEntity = getAltar(world, posX, posY, posZ);
        if (altarEntity == null) {
            return false;
        }
        return true;
    }

    public TEAltar getAltar(World world, int x, int y, int z) {
        TileEntity tileEntity = null;
        for (int i = -2; i <= 2; i++)  {
            for (int j = -2; j <= 2; j++)  {
                for (int k = -2; k <= 1; k++) {
                    tileEntity = world.getTileEntity(i + x, k + y, j + z);
                    if ((tileEntity instanceof TEAltar)) {
                        return (TEAltar) tileEntity;
                    }
                }
                if ((tileEntity instanceof TEAltar)) {
                    return (TEAltar) tileEntity;
                }
            }
            if ((tileEntity instanceof TEAltar)) {
                return (TEAltar) tileEntity;
            }
        }
        if ((tileEntity instanceof TEAltar)) {
            return (TEAltar) tileEntity;
        }
        return null;
    }
}
