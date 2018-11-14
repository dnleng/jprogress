#!/usr/bin/env bash 
for i in $(seq 1 10)
do
    for n in $(seq 5 190)
    do
        java -Xms50g -Xmx50g -jar ../out/artifacts/jprogressor_jar/jprogressor.jar "kr-online-1-$n-$i.csv" "false" "1" "$n" "20" "always ( ( p ) or (eventually [0,100] ( always [0,10] p ) ) )"
    done 
done

