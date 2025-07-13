package es.mpt.sgtic.geiser.tools.libreria.tools;
/**
 * Utilidades genericas:
 *   - Generadores de numeros pseudoaleatorios
 *   - Verificadores
 *   - Etc.
 */

import java.util.Random;

public class Tools {
    private Random rnd;
    private String zeroes = "000000000000000000000000000000000000";
    public Tools() { this.rnd = new Random(); }
    public String getNumber() {
        return String.format("%d", Math.abs(rnd.nextLong()));
    }

    /**
     * Obtiene una cadena aleatoria de numeros de longitud size
     * @param size longitud de la cadena
     * @return la cadena numerica
     */
    public String getNumber(int size) {
        long val = Math.abs(rnd.nextInt());
        val = (long) (val % Math.pow(10, size));
        // Java no soporta esto String.format("%0*ld", size, val);
        String data = String.format("%d", val);
        return zeroes.substring(0, size - data.length()) + data;
    }

    /**
     * Obtiene una cadena aleatoria de numeros de longitud size dentro del rango
     * @param size longitud de la cadena
     * @param range Rango de valores aceptados zero-based
     * @return la cadena numerica
     */
    public String getNumber(int size, int range) {
        long val = Math.abs(rnd.nextInt(range));
        val = (long) (val % Math.pow(10, size));
        // Java no soporta esto String.format("%0*ld", size, val);
        String data = String.format("%d", val);
        return zeroes.substring(0, size - data.length()) + data;
    }

    /**
     * Obtiene un numero aleatorio dentro del rango al cual se le suma la base
     * @param size longitud de la cadena
     * @param range Rango de valores aceptados zero-based
     * @param base  Base numerica
     * @return la cadena numerica
     */
    public String getNumber(int size, int range, int base) {
        long val = Math.abs(rnd.nextInt(range));
        val = (long) (val % Math.pow(10, size));
        val += base;
        // Java no soporta esto String.format("%0*ld", size, val);
        String data = String.format("%d", val);
        return zeroes.substring(0, size - data.length()) + data;
    }
    public int getInt(int range) {
        return rnd.nextInt(range);
    }
    public int getInt() {
        return rnd.nextInt();
    }

}
