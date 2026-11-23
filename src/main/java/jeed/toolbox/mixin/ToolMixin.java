package jeed.toolbox.mixin;

import jeed.toolbox.ToolboxOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ToolMixin {

	@Inject(
		method = "damageItem",
		at = @At(
			value = "FIELD"
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/core/item/ItemStack;stackSize:I",
				ordinal = 2 // second reference to (this.)stackSize
			),
			to = @At(
				value = "FIELD",
				target = "Lnet/minecraft/core/item/ItemStack;stackSize:I",
				ordinal = 3 // third reference to (this.)stackSize
			)
		)
	)
	private void onToolBreak(int damage, Entity entity, CallbackInfo ci) {
		if (ToolboxOptions.toolBreakSounds.value) {
			entity.world.playSoundAtEntity(null, Minecraft.getMinecraft().thePlayer, "random.glass", 1.0F, 0.5F);
		}
	}
}
