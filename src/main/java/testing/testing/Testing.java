package testing.testing;

import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import testing.testing.handlers.ChangeDayHttpHandler;
import testing.testing.tasks.ChangeDay;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class Testing extends JavaPlugin implements Listener {
    PluginLogger logger = new PluginLogger(this);
    Queue<String> messages = new ConcurrentLinkedQueue<>();
    private HttpServer server = null;
    @Override
    public void onEnable() {
        BukkitTask changeDayTask = new ChangeDay(this, messages).runTaskTimer(this, 0L, 100L);
        try {
            server = HttpServer.create(new InetSocketAddress(12000), 0);
            server.createContext("/test", new ChangeDayHttpHandler(messages));
            server.setExecutor(null); // creates a default executor
            server.start();
            logger.info("Web server started");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        server.stop(0);
    }

//    public class ChangeDay extends BukkitRunnable {
//
//        Plugin myPlugin;
//
//        public ChangeDay(Plugin myPlugin) {
//            this.myPlugin = myPlugin;
//        }
//
//        @Override
//        public void run() {
//            if (messages.size() > 0) {
//                Bukkit.getServer().getWorld("world").setTime(0L);
//                messages.poll();
//            }
//        }
//        public Plugin getMyPlugin() {
//            return myPlugin;
//        }
//    }
}
