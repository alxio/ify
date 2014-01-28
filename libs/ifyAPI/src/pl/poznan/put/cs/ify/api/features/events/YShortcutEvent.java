package pl.poznan.put.cs.ify.api.features.events;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YShortcutEvent extends YEvent {

	public final int recipeId;

	public YShortcutEvent(int senderRecipeId) {
		recipeId = senderRecipeId;
	}

	@Override
	public long getId() {
		return Y.Shortcut;
	}
}
