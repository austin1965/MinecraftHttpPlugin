package testing.testing.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MyEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    public MyEvent(boolean isAsync) {
        super(isAsync);
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
