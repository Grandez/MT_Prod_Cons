package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MultithreadedExecutor {

    @Autowired
    private MyService myService;

    public void runTasks() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    myService.performDatabaseOperation();
                }
            });
        }

        executorService.shutdown();
    }
}
