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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
		at = @At("HEAD") // do it at the start so the game hasn't left the world yet
	)
	private void saveWorldAchievements(ButtonElement button, CallbackInfo ci) {
		if (button.id == 1 && ToolboxOptions.worldAchievements.value) { // pause screen quit button, if option enabled

			mc.statsCounter.saveStats();

			if (mc.isMultiplayerWorld()) { // multiplayer
				saveDir = GameSettings.LAST_SERVER.value.replace(":", "_"); // get the last joined IP, replace colon with underscore
			} else { // singleplayer
				saveDir = storage.getDataFile("").getParentFile().getParentFile().getName();
			}

			saveAchievements(saveDir + "_stats_" + uuid + "_unsent.dat");
		}
	}

	@Unique
	private void saveAchievements(String saveFile) {
		System.out.println(saveFile);

		// create directories and files
		try {
			Path dirPath = Paths.get("stats/" + uuid);
			Files.createDirectories(dirPath);

			Path originalStats = Paths.get("stats/stats_" + uuid + "_unsent.dat");
			Path worldStats = Paths.get("stats/" + uuid + "/" + saveFile);

			Files.copy(originalStats, worldStats, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
