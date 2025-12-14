package com.github.mim1q.convenientnametags.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

import java.util.List;

public class ConvenientNameTagsConfig extends WrappedConfig {
  public int renameCost = 0;
  public boolean renameCostPerWholeStack = true;
  public boolean dropNameTagsOnDeath = true;
  public boolean dropNameTagsOnNameChange = true;
  public boolean enableNameTagShearing = true;
  public boolean enableRenameScreen = true;
  public boolean enableCraftingRecipe = true;
  public ValueList<String> denylist = ValueList.create("");
}
