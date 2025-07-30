package com.sdp.poc.threading.base.system;

import java.lang.management.ManagementFactory;
import java.security.SecureRandom;

public class PID {
    private static int  counter = 1;
    private static long ppid    = 0;

    /**
     * Genera un id unico cuasi universal para un proceso
     * Parte 1: centesimas de segundo desde 2025/01/01
     * Parte 2: pid del proceso
     * Parte 3: Un numero aleatorio seguro
     *
     * Se produce la colision entre dos maquinas si:
     * 1.- Se crea un proceso en la misma centesima de tiempo
     * 2.- La secuencia de pids es la misma en las dos maquinas
     * 3.- El ruido y otros factores de aleatoriedad es la misma
     *
     * @return un identificador de proceso universalmente unico
     */
    public static long getpid() {
        // Long son 19 posiciones, desde 2025 son 11
        // 10 + 5 + 2 = 17
        long base = 1735686000000L;
        long epoch = System.currentTimeMillis() - base;
        epoch /= 10;

        // En java 9 y superior usar el metodo correcto
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String spid = name.split("@")[0];
        Integer ipid = Integer.parseInt(spid);

        epoch *= 10000;
        epoch += (ipid % 10000);
        epoch *= 100;
        SecureRandom random = new SecureRandom();
        int numero = random.nextInt();
        ppid = epoch + (numero % 100);
        return ppid;
    }

    /**
     * Genera un identificador unico cuasi universal
     * Se basa en el pid existente
     * @return un identificador unico
     */
    public static long getuid() {
        if (ppid == 0) getpid();
        return (ppid * 100) + ++counter;
    }
}
