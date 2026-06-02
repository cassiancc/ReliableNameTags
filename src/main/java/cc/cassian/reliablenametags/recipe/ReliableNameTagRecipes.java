package cc.cassian.reliablenametags.recipe;

import cc.cassian.reliablenametags.ReliableNameTags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ReliableNameTagRecipes {
	public static final RecipeSerializer<RenameRecipe> RENAME_RECIPE_SERIALIZER = register("rename_item", RenameRecipe.SERIALIZER);

	private static <T extends Recipe<?>> RecipeSerializer<T> register(String name, RecipeSerializer<T> serializer) {
		return Registry.register(
		  BuiltInRegistries.RECIPE_SERIALIZER,
		  ReliableNameTags.of(name),
		  serializer
		);
	}

	public static void touch() {

	}
}
