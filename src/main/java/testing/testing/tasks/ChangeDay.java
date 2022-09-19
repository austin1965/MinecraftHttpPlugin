package testing.testing.tasks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;

public class ChangeDay extends BukkitRunnable {

    Plugin myPlugin;
    Queue<String> messages;

    public ChangeDay(Plugin myPlugin, Queue<String> messages) {
        this.myPlugin = myPlugin;
        this.messages = messages;
    }

    @Override
    public void run() {
        if (messages.size() > 0) {
            Bukkit.getServer().getWorld("world").setTime(0L);
            messages.poll();
        }
    }
    public Plugin getMyPlugin() {
        return myPlugin;
    }
}