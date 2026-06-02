package cc.cassian.reliablenametags.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

public class ModConfig extends WrappedConfig {
  @Comment("Cost of renaming items in the rename screen.")
  public int renameCost = 0;
  public boolean renameCostPerWholeStack = true;
  public boolean dropNameTagsOnDeath = true;
  @Comment("Allowing renaming mobs to retrieve their name tags.")
  public boolean dropNameTagsOnNameChange = true;
  @Comment("Allow shearing mobs to retrieve their name tags.")
  public boolean enableNameTagShearing = true;
  @Comment("Allow renaming name tags by right clicking them.")
  public boolean enableRenameScreen = true;
  @SuppressWarnings("unused")
  @Comment("Backports the name tag recipe from 26.1")
  public boolean enableBackportedCraftingRecipe = true;
  @SuppressWarnings("unused")
  @Comment("Adds a recipe type for renaming items.")
  public boolean renameItemRecipe = true;
  @Comment("Mobs that cannot be renamed.")
  public ValueList<String> denylist = ValueList.create("");
}
