package com.austin.server;

import com.austin.server.tasks.ChangeDay;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import com.austin.server.handlers.ChangeDayHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class MinecraftHttpPlugin extends JavaPlugin implements Listener {
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


}
