package com.austin.server.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Queue;

public class ChangeDayHttpHandler extends ExtendedHttpHandler{


    public ChangeDayHttpHandler(Queue<String> messages) {
        super(messages);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logRequest(exchange);
        try {
            messages.add("test");
            logger.info("Added message to message queue.");
        }
        catch(Exception e) {
            logger.info(String.valueOf(e.getMessage()));
        }
        String response = "{}";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
        logResponse(exchange);
    }
}
