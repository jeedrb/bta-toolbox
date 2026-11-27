package jeed.toolbox.mixin;

import jeed.toolbox.ToolboxOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.achievement.stat.StatsCounter;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Mixin(Minecraft.class)
public class MinecraftMixin {

//	private Minecraft mc = Minecraft.getMinecraft();
//	private World world = mc.currentWorld;
//	private LevelStorage storage = world.getLevelStorage();
//	private String saveDir = "null";
//	private String uuid = mc.thePlayer.uuid.toString();

	@Inject(
		method = "startWorld",
		at = @At("TAIL") // after world is all loaded hopefully?
	)
	private void doWorldAchievements(String worldDirName, CallbackInfo ci) {

//		System.out.println(storage.getDataFile("").getParentFile().getParentFile().getName() + " " + uuid);

		if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null && ToolboxOptions.worldAchievements.value) {

			Minecraft mc = Minecraft.getMinecraft();
			World world = mc.currentWorld;
			LevelStorage storage = world.getLevelStorage();
			String uuid = mc.thePlayer.uuid.toString();

			System.out.println(storage.getDataFile("").getParentFile().getParentFile().getName() + " " + uuid);

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

				mc.statsCounter.saveStats(); // save the new stats stuff, including a blank one if it's a new world!
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

//	private void checkWorldAchievements() {
//
//	}


}
