package jeed.toolbox.mixin;

import jeed.toolbox.ToolboxOptions;
import jeed.toolbox.ToolboxWorldAchievements;
import net.minecraft.client.Minecraft;
import net.minecraft.core.world.settings.WorldConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

	@Inject(
		method = "startWorld", // loading a world
		at = @At("TAIL") // after world is all loaded
	)
	private void startWorldAchievements(String worldDirName, CallbackInfo ci) {
		if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null && ToolboxOptions.worldAchievements.value) {
			ToolboxWorldAchievements.doWorldAchievements();
		}
	}

	@Inject(
		method = "createAndStartWorld", // creating and loading a world
		at = @At("TAIL") // after world is created and loaded
	)
	private void createWorldAchievements(WorldConfiguration worldConfiguration, CallbackInfo ci) {
		if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null && ToolboxOptions.worldAchievements.value) {
			ToolboxWorldAchievements.doWorldAchievements();
		}
	}
}
