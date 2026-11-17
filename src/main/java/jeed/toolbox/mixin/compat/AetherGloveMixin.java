package jeed.toolbox.mixin.compat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import jeed.toolbox.ToolboxOptions;
import net.minecraft.client.render.ImageParser;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import teamport.aether.item.ItemStaffCloud;
import teamport.aether.item.accessory.gloves.ItemGloves;
import teamport.aether.mixin.accessory.MobRendererPlayerMixinAccessoryRender;


@Mixin(MobRendererPlayer.class)
public abstract class AetherGloveMixin {

//	@Inject(method = "setUpGloves(Lnet/minecraft/core/entity/player/Player;FILteamport/aether/item/accessory/gloves/ItemGloves;I)Lorg/useless/dragonfly/models/entity/StaticEntityModel;", at = @At("HEAD"), cancellable = true)
//	private void hideGloves(Player player, float partialTick, int slot, ItemGloves gloves, int renderPass, CallbackInfoReturnable<StaticEntityModel> cir) {
//		if (ToolboxOptions.hideArmor.value) {
//			cir.setReturnValue(null);
//		}
//	}

//	@Inject(method = "setUpShield(Lnet/minecraft/core/entity/player/Player;FI)Lorg/useless/dragonfly/models/entity/StaticEntityModel;", at = @At("HEAD"), cancellable = true)
//	private void hidePendant

	@Inject(method = "getAccessory(Lnet/minecraft/core/entity/player/Player;I)Lnet/minecraft/core/item/ItemStack;", at = @At("HEAD"), cancellable = true, require = 0)
	private void hideAccessories(@NotNull Player player, int logicalSlot, CallbackInfoReturnable<ItemStack> cir) {
		if (ToolboxOptions.hideArmor.value) {
			cir.setReturnValue(null);
		}
	}

//	@Inject(method = "bindAetherCape(Lnet/minecraft/client/render/entity/MobRendererPlayer;Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/client/render/ImageParser;Lcom/llamalad7/mixinextras/injector/wrapoperation/Operation;Lnet/minecraft/client/render/tessellator/TessellatorGeneral;Lnet/minecraft/core/entity/player/Player;F)Z",
//	at = @At("HEAD"),
//	cancellable = true,
//	require = 0)
//	private boolean hideCape(MobRendererPlayer renderer, String url, String fallback, ImageParser parser, Operation<Boolean> original, TessellatorGeneral tessellator, Player player, float partialTick, CallbackInfoReturnable<Boolean> cir) {
//		return false;
//	}

//	@Inject(method = "getAccessoryModel(Lnet/minecraft/core/entity/player/Player;FFILcom/llamalad7/mixinextras/injector/wrapoperation/Operation;)Lorg/useless/dragonfly/models/entity/StaticEntityModel;", at = @At("HEAD"), cancellable = true)
//	private void hideAccessories2(Player entity, float brightness, float partialTick, int layer, Operation<StaticEntityModel> original, CallbackInfoReturnable<StaticEntityModel> cir) {
//		if (ToolboxOptions.hideArmor.value) {
//			cir.setReturnValue(null);
//		}
//	}


}
