package com.yi_tian.item.custom;

import com.yi_tian.entity.llama.LlamaMageEntity;
import com.yi_tian.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class SpitExtractorItem extends Item {
    public SpitExtractorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity instanceof LlamaMageEntity mage){
            if(mage.spittime==0) {
                ItemStack itemStack = new ItemStack(ModItems.SPIT);
                user.getInventory().offerOrDrop(itemStack);
                mage.spittime=200;
                user.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                user.swingHand(hand);
                return ActionResult.success(user.getWorld().isClient);
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
