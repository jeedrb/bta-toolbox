package jeed.toolbox.mixin;

import jeed.toolbox.ToolboxOptions;
import net.minecraft.core.achievement.Achievements;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.SlotResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlotResult.class)
public class AchievementMixin {

	@Shadow
	private Player thePlayer; // need to grab the same Player object

	@Inject(
		method = "onTake",
		at = @At("TAIL")
	) // mixin at the end of the method
	private void fixAchievementIceCream(ItemStack itemStack, CallbackInfo ci) {
		if (ToolboxOptions.fixAchievements.value) {
			if (itemStack.getItem() instanceof ItemBucket && ItemBucket.STATE_ICECREAM.equals(ItemBucket.getState(itemStack))) { // straight clone of the working achievement in the 2026-10-28 nightly
				this.thePlayer.addStat(Achievements.CRAFT_ICECREAM, 1);
			}
		}
	}
}
