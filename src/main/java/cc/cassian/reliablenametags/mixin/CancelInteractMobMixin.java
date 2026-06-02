package cc.cassian.reliablenametags.mixin;

import cc.cassian.reliablenametags.ReliableNameTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({
  AbstractChestedHorse.class,
  Horse.class,
  Mob.class,
  MushroomCow.class,
  Sheep.class,
  SnowGolem.class,
  Villager.class,
  WanderingTrader.class
})
public abstract class CancelInteractMobMixin extends Entity {
  private CancelInteractMobMixin(EntityType<? extends Mob> entityType, Level world) {
    super(entityType, world);
  }

  @Inject(method = "mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
  protected void cancelInteractMob(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
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
