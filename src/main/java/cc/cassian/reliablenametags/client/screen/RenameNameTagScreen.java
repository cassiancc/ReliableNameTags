package cc.cassian.reliablenametags.client.screen;

import cc.cassian.reliablenametags.ReliableNameTags;
import cc.cassian.reliablenametags.network.RenameNameTagPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RenameNameTagScreen extends Screen {

  private static final String TITLE_KEY = "gui.reliablenametags.title";
  private static final String EXPERIENCE_REQUIRED_KEY = "gui.reliablenametags.experience_required";
  private static final String NOT_ENOUGH_EXPERIENCE_KEY = "gui.reliablenametags.not_enough_experience";
  private static final String APPLY_KEY = "gui.reliablenametags.apply";
  private static final String CLEAR_KEY = "gui.reliablenametags.clear";
  private static final String CANCEL_KEY = "gui.reliablenametags.cancel";
  private static final String APPLY_TOOLTIP_KEY = "gui.reliablenametags.apply.tooltip";
  private static final String CLEAR_TOOLTIP_KEY = "gui.reliablenametags.clear.tooltip";
  private static final String CANCEL_TOOLTIP_KEY = "gui.reliablenametags.cancel.tooltip";

  private EditBox textField;
  private Button applyButton;
  private Button clearButton;
  private Button cancelButton;

  private final LocalPlayer player;
  private final ItemStack itemStack;

  public RenameNameTagScreen(LocalPlayer player, ItemStack stack) {
    super(Component.translatable(TITLE_KEY));
    this.player = player;
    this.itemStack = stack;
  }

  @Override
  protected void init() {
    if (this.minecraft == null) {
      return;
    }

    this.clearWidgets();

    int halfWidth = this.width / 2;
    int halfHeight = this.height / 2;

    // New name input field
    this.textField = new EditBox(
      this.minecraft.font,
      halfWidth - 100,
      halfHeight - 10,
      200,
      20,
      Component.empty()
    );
    this.textField.setMaxLength(50);
    this.textField.setResponder(this::onTextChanged);
    this.addRenderableWidget(this.textField);

    var costMultiplier = ReliableNameTags.CONFIG.renameCostPerWholeStack ? 1 : itemStack.getCount();
    var cost = ReliableNameTags.CONFIG.renameCost * costMultiplier;
    var applyTooltip = cost <= player.experienceLevel
      ? Component.translatable(APPLY_TOOLTIP_KEY)
      : Component.translatable(NOT_ENOUGH_EXPERIENCE_KEY).withStyle(ChatFormatting.RED);
    this.applyButton = Button.builder(Component.translatable(APPLY_KEY), this::onButtonClicked)
      .bounds(halfWidth - 101, halfHeight + 14, 20, 20)
      .tooltip(Tooltip.create(applyTooltip))
      .build();
    this.applyButton.active = canApply();
    this.addRenderableWidget(applyButton);

    this.clearButton = Button.builder(Component.translatable(CLEAR_KEY), this::onButtonClicked)
      .bounds(halfWidth - 79, halfHeight + 14, 20, 20)
      .tooltip(Tooltip.create(Component.translatable(CLEAR_TOOLTIP_KEY)))
      .build();
    this.clearButton.active = false;
    this.addRenderableWidget(clearButton);

    this.cancelButton = Button.builder(Component.translatable(CANCEL_KEY), this::onButtonClicked)
      .bounds(halfWidth - 57, halfHeight + 14, 20, 20)
      .tooltip(Tooltip.create(Component.translatable(CANCEL_TOOLTIP_KEY)))
      .build();
    this.addRenderableWidget(cancelButton);

    // Set default input text to current name
    if (this.itemStack.has(DataComponents.CUSTOM_NAME)) {
      this.textField.setValue(this.itemStack.getHoverName().getString());
      this.clearButton.active = true;
    }
    this.setInitialFocus(this.textField);
  }

  // Enter: Apply
  // Shift + Enter: Clear
  @Override
  public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    // 257 - Enter Key
    if (keyCode == 257) {
      // 1 - Shift Modifier
      if (modifiers == 1 && this.clearButton.active) {
        this.onButtonClicked(this.clearButton);
        return true;
      }
      if (this.applyButton.active) {
        this.onButtonClicked(this.applyButton);
        return true;
      }
    }
    return super.keyPressed(keyCode, scanCode, modifiers);
  }

  private void onTextChanged(String string) {
    this.applyButton.active = canApply();
  }

  private boolean canApply() {
    String currentName = this.itemStack.has(DataComponents.CUSTOM_NAME) ? this.itemStack.getHoverName().getString() : "";
    String newName = this.textField.getValue();
    var costMultiplier = ReliableNameTags.CONFIG.renameCostPerWholeStack ? 1 : this.itemStack.getCount();
    var cost = ReliableNameTags.CONFIG.renameCost * costMultiplier;
    return !(currentName.equals(newName) || newName.isBlank() || player.experienceLevel < cost);
  }

  private void onButtonClicked(Button button) {
    if (button == this.applyButton) {
      this.sendRenamePacket(this.textField.getValue());
    } else if (button == this.clearButton) {
      this.sendRenamePacket("");
    }
    this.onClose();
  }

  private void sendRenamePacket(String customName) {
    ClientPlayNetworking.send(new RenameNameTagPayload(customName));
  }

  @Override
  public void render(GuiGraphics drawContext, int mouseX, int mouseY, float delta) {
    int halfWidth = this.width / 2;
    int halfHeight = this.height / 2;

    this.renderBackground(drawContext, mouseX, mouseY, delta);
    this.font.drawInBatch(
      this.getTitle(),
      halfWidth - 100.0F,
      halfHeight - 22.0F,
      0xFFFFFF,
      true,
      drawContext.pose().last().pose(),
      drawContext.bufferSource(),
      Font.DisplayMode.NORMAL,
      0,
      0x0000F0
    );
    var costMultiplier = ReliableNameTags.CONFIG.renameCostPerWholeStack ? 1 : this.itemStack.getCount();
    var cost = ReliableNameTags.CONFIG.renameCost * costMultiplier;
    if (cost > 0) {
      var canAfford = player.experienceLevel >= cost;
      var text = Component.translatable(EXPERIENCE_REQUIRED_KEY, cost).withStyle(canAfford ? ChatFormatting.GREEN : ChatFormatting.RED);
      var textWidth = font.width(text);
      drawContext.fill(
        halfWidth + 100 - textWidth - 3,
        halfHeight - 24,
        halfWidth + 101,
        halfHeight - 13,
        0x80000000
      );
      font.drawInBatch(
        text,
        halfWidth + 99 - textWidth,
        halfHeight - 22.0F,
        0xFFFFFF,
        true,
        drawContext.pose().last().pose(),
        drawContext.bufferSource(),
        Font.DisplayMode.NORMAL,
        0,
        0x0000F0
      );
    }
    super.render(drawContext, mouseX, mouseY, delta);
  }

  @Override
  public boolean isPauseScreen() {
    return false;
  }

  public static void open(Player player, ItemStack stack) {
    Minecraft.getInstance().setScreen(new RenameNameTagScreen((LocalPlayer) player, stack));
  }
}
