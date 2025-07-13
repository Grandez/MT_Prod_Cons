package es.mpt.sgtic.geiser.tools.libreria.processes;

import es.mpt.sgtic.geiser.tools.libreria.base.exceptions.LibException;

/**
 * Interfaz para los diferentes procesos principales
 * que se pueden ejecutar desde Main
 *
 * Se denomina IProceso para evitar colisiones de nombres
 */


public interface IProceso {
    int run () throws LibException;
}
