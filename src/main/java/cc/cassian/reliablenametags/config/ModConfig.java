package cc.cassian.reliablenametags.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

public class ModConfig extends WrappedConfig {
	@Comment("Cost of renaming items in the rename screen.")
	public int renameCost = 0;
	@Comment("Whether the rename cost should be for the whole stack, instead of a single item.")
	public boolean renameCostPerWholeStack = true;
	@Comment("Whether named mobs should drop their name tags.")
	public boolean dropNameTagsOnDeath = true;
	@Comment("Allowing renaming mobs to retrieve their name tags.")
	public boolean dropNameTagsOnNameChange = true;
	@Comment("Allow shearing mobs to retrieve their name tags.")
	public boolean enableNameTagShearing = true;
	@Comment("Allow renaming name tags by right-clicking them.")
	public boolean enableRenameScreen = true;
	//? if fabric || >26.1 {
	@Comment("Allow styling text with Placeholder API's QuickText format.")
	@Comment("Requires Placeholder API.")
	public boolean allowQuickText = true;
	//?}
	//? if =1.21.1 {
    /*@Comment("Backports the name tag recipe from 26.1")
    public boolean enableBackportedCraftingRecipe = true;
    *///?}
	@SuppressWarnings("unused")
	@Comment("Adds a recipe type for renaming items.")
	public boolean renameItemRecipe = true;

}
