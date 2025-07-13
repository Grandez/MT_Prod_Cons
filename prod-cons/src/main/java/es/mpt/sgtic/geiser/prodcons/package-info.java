/**
 * Paquete que encapsula la ejecucion de "tareas programadas"
 * <br>
 * Implementa el paradigma <b>productor/consumidor</b> por colas de memoria
 * en un entorno multihilo
 * <br>
 * Una tarea que desee adherirse a este modelo debe implementar, ademas
 * del proceso que ejecuta el cron:
 * <li>
 *     <ul>Un fichero de configuracion/propiedades que implemente IProdConsConfig</ul>
 *     <ul>Una clase "procesadora". Responsable de obtener la informacion a procesar</ul>
 *     <ul>Una clase "consumidora". Responsable de procesar <b>un</b> item de esa informacion</ul>
 * </li>
 * <h3>Configuracion</h3>
 * Aparte de los datos de configuracion necesarios para CRON y de los propios de la tarea,
 * se necesitan los siguientes parametros:
 * <li>
 *     <ul><b>threads</b>: Numero de hilos a gestionar</ul>
 *     <ul><b>chunk</b>: Numero de procesos a realizar antes de persistir a la base de datos</ul>
 *     <ul><b>lote</b>: Numero maximo de elementos a procesar en una sesion de la tarea </ul>
 *     <ul><b>timer</b>: Opcional. maximo tiempo de ejecucion de la tarea en segundos</ul>
 * </li>
 *
 * <p>
 *  Ejemplo:<br>
 *  Supongamos los siguientes valores:
 *  threads = 5<br>
 *  chunk = 50<br>
 *  lote = 500
 *
 *  Entonces, cada vez que se ejecute la tarea:<br>
 *  <li>
 *      <ul>Se crearan cinco hilos</ul>
 *      <ul>el prodcutor leera 500 elementos</ul>
 *      <ul>Idealmente cada hilo procesara 100 (500 / 5)</ul>
 *      <ul>Cada vez que un hilo haya procesado 50 hara commit</ul>
 *  </li>
 *  La tarea acabara cuando se hayan procesado todos los elementos
 *  o cuando se alcance el tiempo maximo de ejecucion.
 *  Lo que suceda primero
 *
 * @since 1.0
 * @author
 * @version 1.1
 */
