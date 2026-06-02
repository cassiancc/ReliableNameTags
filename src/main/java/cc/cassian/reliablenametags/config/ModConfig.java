package cc.cassian.reliablenametags.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

public class ModConfig extends WrappedConfig {
  public int renameCost = 0;
  public boolean renameCostPerWholeStack = true;
  public boolean dropNameTagsOnDeath = true;
  public boolean dropNameTagsOnNameChange = true;
  public boolean enableNameTagShearing = true;
  public boolean enableRenameScreen = true;
  public boolean enableBackportedCraftingRecipe = true;
  public ValueList<String> denylist = ValueList.create("");
}
