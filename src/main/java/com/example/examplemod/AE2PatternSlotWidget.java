package com.example.examplemod;

import javax.annotation.Nonnull;

import appeng.items.misc.ItemEncodedPattern;
import appeng.util.Platform;
import gregtech.api.gui.widgets.SlotWidget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class AE2PatternSlotWidget extends SlotWidget {
	
	public AE2PatternSlotWidget(IItemHandlerModifiable itemHandler, int slotIndex, int xPosition, int yPosition,
			boolean canTakeItems, boolean canPutItems) {
		super(itemHandler, slotIndex, xPosition, yPosition, canTakeItems, canPutItems);
		this.slotReference = new AE2WidgetSlotDelegate(itemHandler, slotIndex, xPosition, yPosition);
	}

	public AE2PatternSlotWidget(IItemHandlerModifiable itemHandler, int slotIndex, int xPosition, int yPosition) {
		this(itemHandler, slotIndex, xPosition, yPosition, true, true);
	}

	public void setXPosition(final int xPos) {
		this.getHandle().xPos = xPos;
	}

	public void setYPosition(final int yPos) {
		this.getHandle().yPos = yPos;
	}

	protected class AE2WidgetSlotDelegate extends SlotWidget.WidgetSlotDelegate {
		public AE2WidgetSlotDelegate(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(@Nonnull ItemStack stack) {
			return AE2PatternSlotWidget.this.canPutStack(stack) && super.isItemValid(stack);
		}

		@Override
		public boolean canTakeStack(EntityPlayer playerIn) {
			return AE2PatternSlotWidget.this.canTakeStack(playerIn) && super.canTakeStack(playerIn);
		}

		@Override
		public void putStack(@Nonnull ItemStack stack) {
			super.putStack(stack);
			if (changeListener != null) {
				changeListener.run();
			}
		}

		@Override
		public void onSlotChanged() {
			AE2PatternSlotWidget.this.onSlotChanged();
		}

		@Override
		public boolean isEnabled() {
			return AE2PatternSlotWidget.this.isEnabled();
		}

		@Override
		@Nonnull
		public ItemStack getStack() {
			if (this.getItemHandler().getSlots() <= this.getSlotIndex()) {
				return ItemStack.EMPTY;
			}

			return this.getDisplayStack();
		}

		public ItemStack getDisplayStack() {
			if (Platform.isClient()) {
				final ItemStack is = super.getStack();
				if (!is.isEmpty() && is.getItem() instanceof ItemEncodedPattern) {
					final ItemEncodedPattern iep = (ItemEncodedPattern) is.getItem();
					final ItemStack out = iep.getOutput(is);
					if (!out.isEmpty()) {
						return out;
					}
				}
			}
			return super.getStack();
		}
	}
}