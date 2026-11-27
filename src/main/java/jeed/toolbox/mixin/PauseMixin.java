package jeed.toolbox.mixin;

import jeed.toolbox.ToolboxOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.ScreenPause;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionString;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ScreenPause.class)
public class PauseMixin {

	private Minecraft mc = Minecraft.getMinecraft();
	private World world = mc.currentWorld;
	private LevelStorage storage = world.getLevelStorage();
	private String saveDir = "null";
	private String uuid = mc.thePlayer.uuid.toString();



	@Inject(
		method = "buttonClicked", // method that does things when you click buttons on the pause screen
		at = @At("HEAD")
	)
	private void saveWorldAchievements(ButtonElement button, CallbackInfo ci) {
		if (button.id == 1 && ToolboxOptions.worldAchievements.value) { // pause screen quit button, if option enabled


			if (mc.isMultiplayerWorld()) { // multiplayer
				saveDir = GameSettings.LAST_SERVER.value;

				System.out.println("bye friends");

			} else { // singleplayer
				saveDir = storage.getDataFile("").getParentFile().getParentFile().getName();

				System.out.println("bye ig");
//				System.out.println(mc.currentWorld.getLevelData().getWorldName() + " " + storage.getDataFile("").getParentFile().getParentFile().getName());

			}

			saveAchievements(saveDir + "_stats_" + uuid + "_unsent.dat");
		}
	}

	@Unique
	private void saveAchievements(String saveFile) {
		System.out.println(saveFile);
	}
}
