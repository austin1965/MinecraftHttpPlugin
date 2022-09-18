package testing.testing.tasks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import testing.testing.Testing;

public class ChangeDay extends BukkitRunnable {

    Plugin myPlugin;

    public ChangeDay(Plugin myPlugin) {
        this.myPlugin = myPlugin;
    }

    @Override
    public void run() {
        Bukkit.getServer().getWorld("world").setTime(0L);
    }

    public Plugin getMyPlugin() {
        return myPlugin;
    }
}
