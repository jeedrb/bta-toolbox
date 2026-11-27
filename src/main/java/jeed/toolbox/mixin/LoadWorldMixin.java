package jeed.toolbox.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenSelectWorld;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenSelectWorld.class)
public class LoadWorldMixin {

	private Minecraft mc = Minecraft.getMinecraft();
	private World world = mc.currentWorld;
	private LevelStorage storage = world.getLevelStorage();
	private String saveDir = "null";
	private String uuid = mc.thePlayer.uuid.toString();

	@Inject(
		method = "selectWorld",
		at = @At("TAIL") // after world is all loaded hopefully?
	)
	private void checkWorldAchievements(int i, CallbackInfo ci) {
		System.out.println(storage.getDataFile("").getParentFile().getParentFile().getName() + " " + uuid);
	}
}
