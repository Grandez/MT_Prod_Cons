package es.mpt.sgtic.geiser.prodcons.beans;

public class ResultGlobal {
    public int read;
    public int write;
    public int status;
    public int commits;
    public int errors;
    public int warnings;

    private long begin = System.currentTimeMillis();

    public int  getRead()             { return read;     }
    public int  getWrite()            { return write;    }
    public int  getErrors()           { return errors;   }
    public int  getWarnings()         { return warnings; }
    public int  getStatus()           { return status;   }
    public int  getCommits()          { return commits;  }

    public void setRead     (int value) { this.read     = value; }
    public void setWrite    (int value) { this.write    = value; }
    public void setErrors   (int value) { this.errors   = value; }
    public void setWarnings (int value) { this.warnings = value; }
    public void setCommits  (int value) { this.commits  = value; }

    public void addRead     (int value) { this.read    += value; }
    public void addWrite    (int value) { this.write   += value; }
    public void addErrors   (int value) { this.errors  += value; }
    public void addWarnings (int value) { this.errors  += value; }
    public void addCommits  (int value) { this.commits += value; }

    public ResultGlobal incRead()     { read++;     return this;  }
    public ResultGlobal incWrite()    { write++;    return this;  }
    public ResultGlobal incCommits()  { commits++;  return this;  }
    public ResultGlobal incErrors()   { errors++;   return this;  }
    public ResultGlobal incWarnings() { warnings++; return this;  }

    public void setStatus(int status) { this.status = status; }

    public synchronized ResultGlobal add(ResultThread thr)  {
        if (thr == null) return this;
        addRead     (thr.getRead());
        addWrite    (thr.getWrite());
        addErrors   (thr.getErrors());
        addWarnings (thr.getWarnings());
        addCommits  (thr.getCommits());

        return this;
    }
    public String getElapsed() {
        long amount = System.currentTimeMillis() - begin;
        int ms = (int) amount % 1000;
        amount /= 1000;
        int ss = (int) amount % 60;
        amount -= ss;
        amount = (int) amount / 60;
        int min = (int) amount % 60;
        int hour = (int) ((amount - min) / 60);
        return String.format("%1d:%02d:%02d.%03d", hour, min, ss, ms);
    }

    /*
     * Version con introspecccion

    public synchronized ResultGlobal add(ResultThread thr)  {
        Method[] methods = thr.getClass().getMethods();
        for (Method method : methods) {
             if (method.getName().startsWith("get") == false) continue;
             try {
                 Object result = method.invoke(thr);
                 String methodName = method.getName();
                 if (methodName.compareTo("getClass") == 0) continue;
                 String addMethodName = "add" + methodName.substring(3);  // Ejemplo: "getContadorA" -> "addContadorA"
                 Method addMethod = this.getClass().getMethod(addMethodName, int.class);
                 addMethod.invoke(this, result);
             } catch (Exception ex) {
                 // Do nothing. Solo son contadores
             }
        }
        return this;
    }

     */
}