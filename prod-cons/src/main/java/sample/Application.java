package sample;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.example");
        context.refresh();

        MultithreadedExecutor executor = context.getBean(MultithreadedExecutor.class);
        executor.runTasks();

        context.close();
    }
}
