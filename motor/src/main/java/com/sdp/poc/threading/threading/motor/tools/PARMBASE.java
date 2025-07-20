package com.sdp.poc.threading.threading.motor.tools;

/**
 * Parametros que gestiona el motor de hilos
 * Notese que esta version solo usa una base de datos
 */
public enum PARMBASE implements PARM {
     HOST   ("HOST",  "Host de la base de datos", "localhost")
    ,PORT   ("PORT",  "Puesto de la base de datos", "1521")
    ,DBNAME ("DBNAME", "Nombre del esquema", "GEISER")
    ,THREADS("THREADS", "Numero de hilos en ejecucion", "automatico")
    ,CHUNK  ("CHUNK",  "Longitud de los bloques de procesamiento", "automatico")
    ,MODE   ("MODE", "Uso de maquina", "1 - Bajo, \033[1;30m 2 - Medio\033[0m, 3 - Alto")
    ,USER   ("USER", "Usuario y password de la base de datos en " + "\033[1;30m" + "formato: usuario,password" + "\033[0m", "GEISER,geiser")
    ,CHECK  ("CHECK","No ejecuta el proceso si no que muestra la configuracion (0) y datos que se procesaran (1)", "no tiene")
    ,TIMER  ("TIMER","Maximo tiempo de ejecucion del proceso", "0 - sin limite")
    ;

    public final   String parm;
    public final   String desc;
    public final   String def;


    PARMBASE(String parm, String desc, String def ) {
        this.parm = parm;
        this.desc = desc;
        this.def = def;
    }

}
