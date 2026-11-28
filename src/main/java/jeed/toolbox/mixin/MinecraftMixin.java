package jeed.toolbox.mixin;

import jeed.toolbox.ToolboxOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.achievement.stat.StatsCounter;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelStorage;
import net.minecraft.core.world.settings.WorldConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Mixin(Minecraft.class)
public class MinecraftMixin {

	@Inject(
		method = "startWorld", // loading a world
		at = @At("TAIL") // after world is all loaded
	)
	private void startWorldAchievements(String worldDirName, CallbackInfo ci) {
		if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null && ToolboxOptions.worldAchievements.value) {
			doWorldAchievements();
		}
	}

	@Inject(
		method = "createAndStartWorld", // creating and loading a world
		at = @At("TAIL") // after world is created and loaded
	)
	private void createWorldAchievements(WorldConfiguration worldConfiguration, CallbackInfo ci) {
		if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null && ToolboxOptions.worldAchievements.value) {
			doWorldAchievements();
		}
	}

	@Unique
	private void doWorldAchievements() {
		Minecraft mc = Minecraft.getMinecraft();
		World world = mc.currentWorld;
		LevelStorage storage = world.getLevelStorage();
		String uuid = mc.thePlayer.uuid.toString();

//		System.out.println(storage.getDataFile("").getParentFile().getParentFile().getName() + " " + uuid);

		String saveDir = storage.getDataFile("").getParentFile().getParentFile().getName() + "_stats_" + uuid + "_unsent.dat";
		System.out.println(saveDir);

		mc.statsCounter.wipe("I want to delete all the saved stats");

		// create directories and files
		try {
			Path dirPath = Paths.get("stats/" + uuid);
			Files.createDirectories(dirPath);

			Path originalStats = Paths.get("stats/stats_" + uuid + "_unsent.dat");
			Path worldStats = Paths.get("stats/" + uuid + "/" + saveDir);


			if (Files.exists(worldStats)) {
				Files.copy(worldStats, originalStats, StandardCopyOption.REPLACE_EXISTING); // copy over the saved worldStats if it exists
				mc.statsCounter = new StatsCounter(mc.session, mc.getMinecraftDir()); // load a fresh StatsCounter into memory
			}

			mc.statsCounter.saveStats(); // save the new stats stuff
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
