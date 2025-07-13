package es.mpt.sgtic.geiser.prodcons.interfaces;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/*
 * Metodos que debe tener un fichero de propiedades para
 * cargarlos en el objeto configuracion de Prod/Cons
 */
public interface IProdConsConfig {
    boolean isCronActivo();
    String  getCronUsuario();
    String  getCronNombre();
    String  getIdTabla();
    int     getThreads();  // Numero de hilos. 0 - Automatico, 1 - monohilo
    int     getTimer();    // Tiempo maximo de ejecucion en segundos
    int     getLote();     // Numero maximo de registros a procesar
    int     getChunk();     // Numero maximo de registros a procesar
    String  getCronExpresion();
    String  getEtiqueta();
    PriorityBlockingQueue<Long> getQueue();
    LinkedBlockingQueue<Object> getQLog();

    void setLote(String valor);
    void setChunk(String valor);
    void setHilos(String valor);

    void putBean(Long id, Object bean);
    void putTask(Long id);
}
