package testing.testing;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import testing.testing.events.MyEvent;
import testing.testing.tasks.ChangeDay;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class Testing extends JavaPlugin implements Listener {
    PluginLogger logger = new PluginLogger(this);
    Queue<String> messages = new ConcurrentLinkedQueue<String>();

    private HttpServer server = null;
    @Override
    public void onEnable() {
        //BukkitTask changeDayTask = new ChangeDay(this).runTaskTimer(this, 0L, 100L);

        try {
            server = HttpServer.create(new InetSocketAddress(12000), 0);
            server.createContext("/test", new MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
            logger.info("Web server started");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getServer().getPluginManager().registerEvents(this, this);
    }

    class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            logRequest(t);
            //messages.add("test");
            logger.info("before");
            try {
                //Bukkit.getServer().getWorld("world").setTime(0L);
                MyEvent event = new MyEvent(false);
                Bukkit.getPluginManager().callEvent(event);
            }
            catch(Exception e) {
                logger.info(String.valueOf(e.getMessage()));
            }
            logger.info("after");
            String response = "{}";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private void logRequest(HttpExchange exchange) {
            logger.info("-------------BEGIN REQUEST-------------");
            logger.info("Method:" + exchange.getRequestMethod());
            logger.info("URI:\n" + exchange.getRequestURI().toString());
            logger.info("Headers:\n" + exchange.getRequestHeaders().toString());
            logger.info("Body:\n" + exchange.getRequestBody().toString());
            logger.info("--------------END REQUEST--------------");
        }
    }
    @Override
    public void onDisable() {
        server.stop(0);
    }


    @EventHandler
    public void onMyEvent(MyEvent event) {
        getServer().getWorld("world").setTime(0L);

        if (event.isAsynchronous()) {
            //getServer().getScheduler().scheduleSyncDelayedTask(this, new ChangeDay(this));
            ChangeDay changeDay = new ChangeDay(this);
            changeDay.runTaskLater(changeDay.getMyPlugin(), 0L);
        }


    }


}
