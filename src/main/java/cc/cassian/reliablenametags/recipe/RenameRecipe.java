package cc.cassian.reliablenametags.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.NameTagItem;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class RenameRecipe
	//? if >26 {
	extends NormalCraftingRecipe
	//?} else {
		/*extends CustomRecipe
	*///?}
{
	public static final MapCodec<RenameRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
	  i -> i.group(
		  CraftingBookCategory.CODEC.optionalFieldOf("category", CraftingBookCategory.MISC).forGetter(o -> o.bookInfo)
		)
		.apply(i, RenameRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, RenameRecipe> STREAM_CODEC = StreamCodec.composite(
	  CraftingBookCategory.STREAM_CODEC,
	  o -> o.bookInfo,
	  RenameRecipe::new
	);
	//? if >1.21.2 {
	public static final RecipeSerializer<RenameRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
	//?} else {
	/*public static final RecipeSerializer<RenameRecipe> SERIALIZER = new RecipeSerializer<>() {
		@Override
		public MapCodec<RenameRecipe> codec() {
			return MAP_CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, RenameRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	};
	*///?}

	private final CraftingBookCategory bookInfo;

	public RenameRecipe(
	  final CraftingBookCategory bookInfo
	) {
		super(
				//? if >26 {
				new CommonInfo(false),
				new CraftingBookInfo(bookInfo, "")
				//?} else {
				/*bookInfo
				*///?}
		);
		this.bookInfo = bookInfo;
	}

	@Override
	public boolean matches(final CraftingInput input, final Level level) {
		if (input.ingredientCount() < 2) {
			return false;
		} else {
			boolean hasTarget = false;
			boolean hasNameTag = false;

			for (int slot = 0; slot < input.size(); slot++) {
				ItemStack itemStack = input.getItem(slot);
				if (!itemStack.isEmpty()) {
					if (!(itemStack.getItem() instanceof NameTagItem)) {
						if (hasTarget) {
							return false;
						}
						hasTarget = true;
					} else {
						hasNameTag = true;
					}
				}
			}

			return hasNameTag && hasTarget;
		}
	}

	@Override
	public ItemStack assemble(final CraftingInput input
							  //? if =1.21.1
			//, HolderLookup.Provider registries
	) {
		Component customName = null;
		ItemStack targetStack = ItemStack.EMPTY;

		for (int slot = 0; slot < input.size(); slot++) {
			ItemStack itemStack = input.getItem(slot);
			if (!itemStack.isEmpty()) {
				if (itemStack.getItem() instanceof NameTagItem) {
					customName = itemStack.get(DataComponents.CUSTOM_NAME);
				} else {
					targetStack = itemStack;
				}
			}
		}

		if (!targetStack.isEmpty()) {
			ItemStack result = targetStack.copyWithCount(1);
			result.set(DataComponents.CUSTOM_NAME, customName);
			return result;
		} else {
			return ItemStack.EMPTY;
		}
	}

	//? if =1.21.1 {
	/*@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}
	*///?}

	@Override
	public RecipeSerializer<RenameRecipe> getSerializer() {
		return SERIALIZER;
	}

	//? if >26 {
	@Override
	protected PlacementInfo createPlacementInfo() {
		return PlacementInfo.NOT_PLACEABLE;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}
	//?}
}