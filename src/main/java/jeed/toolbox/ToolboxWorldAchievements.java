package jeed.toolbox;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.achievement.stat.StatsCounter;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ToolboxWorldAchievements {

	public static void doWorldAchievements() {
//		System.out.println("HELLO IS THIS WORKING");

		Minecraft mc = Minecraft.getMinecraft();
		World world = mc.currentWorld;
		LevelStorage storage = world.getLevelStorage();
		String uuid = mc.thePlayer.uuid.toString();
		String saveDir = "null";

//		System.out.println(storage.getDataFile("").getParentFile().getParentFile().getName() + " " + uuid);

		if (mc.isMultiplayerWorld()) { // multiplayer
    		saveDir = GameSettings.LAST_SERVER.value + "_stats_" + uuid + "_unsent.dat";
		} else { // singleplayer
			saveDir = storage.getDataFile("").getParentFile().getParentFile().getName() + "_stats_" + uuid + "_unsent.dat";
		}
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
