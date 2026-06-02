package cc.cassian.reliablenametags.mixin;

import cc.cassian.reliablenametags.ReliableNameTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStand.class)
public abstract class ArmorStandMixin extends Entity {
  public ArmorStandMixin(EntityType<?> type, Level world) {
    super(type, world);
  }

  @Inject(method = "interactAt(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
  protected void cancelInteractAt(Player player, Vec3 hitPos, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
    ItemStack stack = player.getItemInHand(hand);
    boolean isNameTagApplicable =
      stack.is(Items.NAME_TAG)
      && stack.has(DataComponents.CUSTOM_NAME)
      && !stack.getHoverName().equals(this.getCustomName());
    boolean areShearsApplicable =
      ReliableNameTags.CONFIG.enableNameTagShearing
      && stack.is(ConventionalItemTags.SHEAR_TOOLS)
      && player.isShiftKeyDown();

    if (isNameTagApplicable || areShearsApplicable) {
      cir.setReturnValue(InteractionResult.PASS);
    }
  }
}
