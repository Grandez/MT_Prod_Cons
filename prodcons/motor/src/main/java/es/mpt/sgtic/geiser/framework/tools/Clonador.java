package es.mpt.sgtic.geiser.framework.tools;

import java.lang.reflect.Field;

public class Clonador {
    /*
     * Dos objetos con los mismos atributos
     * Para controlar otros o excluir unos, customizar por nombre de atributo
     */
   public static <T> T clone(Object origen, Class<T> target) throws Exception {
      Field[] campos = null;
      Field   dest   = null;

      T destino = target.getDeclaredConstructor().newInstance();

      Class<?> from = origen.getClass();
      Class<?> to   = destino.getClass();

      try {
         while (from != null) {
            campos = from.getDeclaredFields();
            for (Field campo : campos) {
                 campo.setAccessible(true);
                 dest = to.getDeclaredField(campo.getName());
                 dest.setAccessible(true);
                 dest.set(destino, campo.get(origen));
            }
            from = from.getSuperclass();
            to   = to.getSuperclass();
        }
     } catch (Exception e) {
         System.out.println("Fallo clonando"); // No debe fallar, pero hay que sacar el error
     }
     return destino;
  }
}
