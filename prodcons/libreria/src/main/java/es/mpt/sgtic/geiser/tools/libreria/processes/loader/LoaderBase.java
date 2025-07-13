package es.mpt.sgtic.geiser.tools.libreria.processes.loader;

import es.mpt.sgtic.geiser.tools.libreria.base.Config;
import es.mpt.sgtic.geiser.tools.libreria.tools.Tools;

public abstract class LoaderBase {
    protected Config cfg;
    protected Tools rnd = new Tools();

    public LoaderBase() {
        cfg = Config.getInstance();
    }

}
