package cc.cassian.reliablenametags;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
//? neoforge {
/*import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
*///?} else {
import net.fabricmc.loader.api.FabricLoader;
//?}

import java.nio.file.Path;

public class Platform {
	public static final TagKey<Item> SHEAR_TOOLS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "tools/shear"));

	public static Path getConfigDirectory() {
		//? fabric
		return FabricLoader.getInstance().getConfigDir();
		//? neoforge
		//return FMLPaths.CONFIGDIR.get();
	}

	public static boolean isModLoaded(String mod) {
		//? fabric
		return FabricLoader.getInstance().isModLoaded(mod);
		//? neoforge
		//return ModList.get().isLoaded(mod);
	}
}
