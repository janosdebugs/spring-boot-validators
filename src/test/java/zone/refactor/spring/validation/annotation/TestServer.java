package zone.refactor.spring.validation.annotation;

import kong.unirest.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class TestServer {
    private static Thread thread = null;

    public static synchronized void start() {
        if (thread != null && thread.isAlive()) {
            return;
        }
        thread = new Thread(() -> {
            SpringApplication application = new SpringApplication(TestApplication.class);
            ConfigurableApplicationContext context = application.run();
            context.stop();
        });
        thread.setDaemon(true);
        thread.start();
        boolean connected = false;
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                thread.interrupt();
                throw new RuntimeException(e);
            }
            try {
                Unirest.get("http://localhost:8080").asString();
                connected = true;
            } catch (Exception ignored) {
            }
        }
        if (!connected) {
            throw new RuntimeException("Server failed to start.");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(thread::stop));
    }

    public static synchronized void stop() {
        if (thread == null || thread.isAlive()) {
            return;
        }
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            thread.stop();
        }
    }
}
