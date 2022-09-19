package testing.testing.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Queue;
import java.util.logging.Logger;

public abstract class ExtendedHttpHandler implements HttpHandler {
    Logger logger = Logger.getLogger("ExtendedHttpHandler");
    Queue<String> messages;

    public ExtendedHttpHandler(Queue<String> messages) {
        this.messages = messages;
    }

    @Override
    public abstract void handle(HttpExchange exchange) throws IOException;


    void logRequest(HttpExchange exchange) {
        logger.info("-------------BEGIN REQUEST LOG-------------");
        logger.info("Method:" + exchange.getRequestMethod());
        logger.info("URI:\n" + exchange.getRequestURI().toString());
        logger.info("Headers:\n" + exchange.getRequestHeaders().toString());
        logger.info("Body:\n" + exchange.getRequestBody().toString());
        logger.info("--------------END REQUEST LOG--------------");
    }

    void logResponse(HttpExchange exchange) {
        logger.info("-------------BEGIN RESPONSE LOG-------------");
        logger.info("Code:" + exchange.getResponseCode());
        logger.info("Headers\n" + exchange.getResponseHeaders().toString());
        logger.info("Body:\n" + exchange.getResponseBody().toString());
        logger.info("--------------END RESPONSE LOG--------------");
    }

}
