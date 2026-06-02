package cc.cassian.reliablenametags.mixin;

import cc.cassian.reliablenametags.ReliableNameTags;
import cc.cassian.reliablenametags.client.screen.RenameNameTagScreen;
import cc.cassian.reliablenametags.interfaces.RemovableNameTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void useNameTag(Level level, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
		Item item = (Item) (Object) this;
		if (item instanceof NameTagItem) {
			ItemStack itemStack = user.getItemInHand(hand);
			if (hand == InteractionHand.OFF_HAND) {
				cir.setReturnValue(InteractionResultHolder.pass(itemStack));
			}
			if (ReliableNameTags.CONFIG.enableRenameScreen) {
				if (level.isClientSide()) RenameNameTagScreen.open(user, itemStack);
				cir.setReturnValue(InteractionResultHolder.success(itemStack));
			}
		}
	}

	@Inject(method = "interactLivingEntity", at = @At("HEAD"), cancellable = true)
	public void interactShears(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		Item item = (Item) (Object) this;
		if (item instanceof ShearsItem) {
			if (ReliableNameTags.CONFIG.enableNameTagShearing && entity.hasCustomName() && user.isShiftKeyDown()) {
				((RemovableNameTag) entity).reliablenametags$removeNameAndNameTag();
				stack.hurtAndBreak(1, user, Player.getSlotForHand(hand));
				cir.setReturnValue(InteractionResult.SUCCESS);
			}
		}
	}
}
