package es.mpt.sgtic.geiser.framework.tools;

public enum Messages {
     HELP(1, "Ayuda")
    ,BAD_PARM       (1001,"Parametro invalido: %s")
    ,MISSING_VALUE  (1002,"Falta el valor para el parametro: %s")
    ,BAD_DRIVER     (1003,"El driver es incorrecto: %s")
    ,BAD_PORT       (1004,"El puerto de la base de datos no es numerico")
    ,BAD_THREADS    (1005,"El numero de hilos no es numerico: %s")
    ,BAD_CHUNK      (1006,"Chunk no es numerico: %s")
    ,BAD_NUMBER     (1007, "Valor no numerico: %s")
    ,BAD_USER       (1009, "Se debe indicar el usuario y la password en la forma user:pwd")
    ,BAD_FILE       (1010, "No se ha podido procesar el fichero %s")

    ,DB_LASTS       (5001, "Obteniendo identificadores ultimos")
    ,GEISER         (2,"GEISER")
    ,RGECO          (3,"RGECO")
    ,NO_PROPS       (2001, "No se ha encontrado %s")
    ;

    private Integer code;
    private String  msg;

    private Messages(Integer code, String msg) {
        this.code = code;
        this.msg  = msg;
    }
    public int    getCode()    { return code; }
    public String getMessage() { return msg; }
}
