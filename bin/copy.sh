#/bin/sh
ROOT=/p/POC/MT_Prod_Cons
copiar() {
  for f in `find /p/POC -name "*.jar" -not -path "./bin/*"` ; do
       cp $f $ROOT/bin/`basename $f`;
   done
}

