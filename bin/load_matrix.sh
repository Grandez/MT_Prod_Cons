#!/bin/sh

source /p/POC/MT_Prod_Cons/bin/copy.sh

copiar

for size in `seq 100 100 10000`; do
    for threads in `seq 1 50` ; do
        echo Procesando matrix de $size " - " $threads;
        java -jar 03_matrix_fat.jar -r $size -t $threads
    done
done
