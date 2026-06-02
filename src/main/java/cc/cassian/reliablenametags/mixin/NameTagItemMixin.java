package cc.cassian.reliablenametags.mixin;

import cc.cassian.reliablenametags.ReliableNameTags;
import cc.cassian.reliablenametags.interfaces.RemovableNameTag;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NameTagItem.class)
public abstract class NameTagItemMixin extends Item {
	public NameTagItemMixin(Properties settings) {
		super(settings);
	}

	@Inject(method = "interactLivingEntity(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
	public void useOnEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		if (stack.getHoverName().equals(entity.getCustomName())) {
			cir.setReturnValue(InteractionResult.PASS);
			return;
		}
		if (
				ReliableNameTags.CONFIG.dropNameTagsOnNameChange
						&& !user.level().isClientSide()
						&& stack.has(DataComponents.CUSTOM_NAME)
						&& entity.isAlive()
		) {
			((RemovableNameTag) entity).reliablenametags$removeNameAndNameTag();
		}
	}
}
