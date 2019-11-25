package zone.refactor.spring.validation.annotation;

import kong.unirest.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class TestServer {
    private static Thread thread = null;
    private static SpringApplication application = null;
    private static ConfigurableApplicationContext context = null;

    public static synchronized boolean isRunning() {
        if (application != null && context != null && context.isRunning() && context.isActive()) {
            return true;
        }
        try {
            Unirest.get("http://localhost:8080").asString();
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public static synchronized void start() {
        if (isRunning()) {
            return;
        }
        application = new SpringApplication(TestApplication.class);
        context = application.run();
        boolean connected = false;
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                thread.interrupt();
                throw new RuntimeException(e);
            }
            connected = isRunning();
            if (connected) {
                break;
            }
        }
        if (!connected) {
            throw new RuntimeException("Server failed to start.");
        }
    }

    public static synchronized void stop() {
        if (context != null) {
            context.stop();
            context.close();
            context = null;
        }
        if (thread == null || !thread.isAlive()) {
            return;
        }
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            thread.stop();
            try {
                thread.join();
            } catch (InterruptedException e1) {
                throw new RuntimeException(e1);
            }
        }
        thread = null;
    }
}
