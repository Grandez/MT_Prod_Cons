package es.mpt.sgtic.geiser.prodcons.beans;


/**
 * Clase que devuelve los contadores de un hilo
 */
public class ResultThread extends ResultGlobal {
    public ResultThread incRead()     { read++;     return this;  }
    public ResultThread incWrite()    { write++;    return this;  }
    public ResultThread incCommits()  { commits++;  return this;  }
    public ResultThread incErrors()   { errors++;   return this;  }
    public ResultThread incWarnings() { warnings++; return this;  }

}
