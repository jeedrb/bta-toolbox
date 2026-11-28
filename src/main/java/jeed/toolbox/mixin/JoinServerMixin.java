package jeed.toolbox.mixin;

import jeed.toolbox.ToolboxOptions;
import jeed.toolbox.ToolboxWorldAchievements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.PacketHandlerClient;
import net.minecraft.core.net.packet.PacketLogin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PacketHandlerClient.class)
public class JoinServerMixin {

	@Inject(
		method = "handleLogin",
		at = @At("TAIL")
	)
	private void serverWorldAchievements(PacketLogin packetLogin, CallbackInfo ci) {
		if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null && ToolboxOptions.worldAchievements.value) {
			ToolboxWorldAchievements.doWorldAchievements();

		}
	}
}
