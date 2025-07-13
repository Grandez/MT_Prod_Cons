# Productor / Cosumidor

Paquete que encapsula la ejecucion de "*tareas programadas*" implementando el paradigma <b>productor/consumidor</b> por colas de memoria
en un entorno multihilo

Una tarea que desee adherirse a este modelo debe implementar, ademas del proceso que ejecuta el **cron**:

1. Un fichero de configuracion/propiedades que implemente IProdConsConfig
2. Una clase "procesadora". Responsable de obtener la informacion a procesar
3. Una clase "consumidora". Responsable de procesar <b>un</b> item de esa informacion

##  Configuracion

Aparte de los datos de configuracion necesarios para **CRON** como gestor general
responsable de ir arrancando las tareas a intervalos prefijados
y de los propios de la tarea a ejecutar, se necesitan los siguientes parametros
para configurar el entorno:

- **threads**: Numero de hilos a gestionar
- **chunk**: Numero de procesos a realizar antes de persistir a la base de datos
- **lote**: Numero maximo de elementos a procesar en una sesion de la tarea
- **timer**: Opcional. maximo tiempo de ejecucion de la tarea en segundos

### Ejemplo:
Supongamos los siguientes valores:

threads = 5<br>
chunk = 50<br>
lote = 500

Cada vez que se ejecute la tarea:<br>

1. Se crearan cinco hilos para consumir
2. El productor leera un maximo de 500 elementos de la fuente
3. *Idealmente*, cada hilo procesará 100 (500 / 5) elementos
4. Cada vez que un hilo haya procesado 50 elementos realizará un commit


La tarea acabará cuando se hayan procesado todos los elementos o cuando se
alcance el tiempo maximo de ejecucion. Lo que suceda primero

> En un entorno en el que una tarea se ejecuta cada x minutos, y sin mayor información  
> al respecto, el ***timer*** se encarga de monitorizar el tiempo transcurrido y notificar a los hilos  
> que acaben de una ***forma controlada*** antes de que se cumpla el plazo establecido  
> en previsión de que, en función del "planificador", se solapen tareas

