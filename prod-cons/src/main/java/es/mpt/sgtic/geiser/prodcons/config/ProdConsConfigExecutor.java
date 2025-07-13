package es.mpt.sgtic.geiser.prod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ProdConsConfigExecutor {
    @Bean
    public ExecutorService taskExecutor() {
//        int threads = ProdConsConfig.getInstance().threads;
        return Executors.newFixedThreadPool(5); // No se usa
    }

}
