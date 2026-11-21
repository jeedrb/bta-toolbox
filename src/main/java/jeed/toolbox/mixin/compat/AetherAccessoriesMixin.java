package jeed.toolbox.mixin.compat;

import jeed.toolbox.ToolboxOptions;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.item.accessory.gloves.ItemGloves;


@Mixin(MobRendererPlayer.class)
public abstract class AetherAccessoriesMixin {

	@Inject(method = "getAccessory(Lnet/minecraft/core/entity/player/Player;I)Lnet/minecraft/core/item/ItemStack;", at = @At("RETURN"), cancellable = true, require = 0)
	private void hideAccessories(@NotNull Player player, int logicalSlot, CallbackInfoReturnable<ItemStack> cir) {
		if (ToolboxOptions.hideArmor.value && ((cir.getReturnValue() != null && cir.getReturnValue().getItem() instanceof ItemGloves) || ToolboxOptions.hideAetherAccessories.value)) { // logic for separately toggling gloves and accessories
			cir.setReturnValue(null);
		}
	}

}
