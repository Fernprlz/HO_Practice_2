#!/bin/sh

# Authors: Fernando Pérez Lozano & Antonio Viñuela Pérez

# Comprobamos que el input sea correcto, el script es subsceptible a fallos
# como la introduccion de dos parametros no válidos. Solo se comprueba que
# el número de argumentos sea el esperado, o si se invoca la ayuda.

if [ "$#" = 1 ] && [ $1 = "--help" ]
  then
    echo "- En el primer argumento se espera el nombre del archivo con extensión .prob."
    echo "- Este archivo deberá estar ubicado en el mismo directorio que este script y que el archivo Main.java."
    echo "- En el segundo argumento se espera el número de la heurística: \"1\" o \"2\"."
    echo "\n\tEjemplo de entrada: \"./BusRouting.sh problema1.prob 2\"\n"
    exit 1
elif [ "$#" = 2 ]
  then
    # Compilamos los archivos de java
    cd ..
    javac parte_2/Util.java
    javac parte_2/Guagua.java
    javac parte_2/Parada.java
    javac parte_2/Estado.java
    javac parte_2/Main.java

    java parte_2/Main "parte_2/"$1 $2

    # Al final de la ejecucion borramos los archivos .class generados.
    rm parte_2/Util.class
    rm parte_2/Guagua.class
    rm parte_2/Parada.class
    rm parte_2/Estado.class
    rm parte_2/ByHeuristics.class
    rm parte_2/Main.class

    exit 1
else
    echo "Error: Argumentos inválidos."
    echo "Entrada esperada: \"./BusRouting.sh <problema.prob> <heurística>\""
    echo "Use el argumento \"--help\" para más información."
    exit 0
fi
