package cc.cassian.reliablenametags.mixin;

import cc.cassian.reliablenametags.ReliableNameTags;
import cc.cassian.reliablenametags.interfaces.RemovableNameTag;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements RemovableNameTag {
	private LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	//? if >26 {
	@Inject(method = "dropEquipment", at = @At("HEAD"))
	protected void dropInventory(ServerLevel level, CallbackInfo ci) {
		this.reliablenametags$removeNameAndNameTag();
	}
	//?} else {
  /*@Inject(method = "dropEquipment()V", at = @At("HEAD"))
  protected void dropInventory(CallbackInfo info) {
    this.reliablenametags$removeNameAndNameTag();
  }
  *///?}


	public void reliablenametags$removeNameAndNameTag() {
		if (this.hasCustomName() && ReliableNameTags.CONFIG.dropNameTagsOnDeath) {
			ItemStack nameTagItemStack = new ItemStack(Items.NAME_TAG);
			nameTagItemStack.set(DataComponents.CUSTOM_NAME, this.getCustomName());
			ItemEntity item = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), nameTagItemStack);
			this.level().addFreshEntity(item);
			this.setCustomName(null);
		}
	}
}
