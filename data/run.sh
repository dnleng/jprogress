#!/usr/bin/env bash
#for i in $(seq 69 190)
#do
#   for j in 20 40 60 80 100
#   do
#      java -Xms50g -Xmx50g -jar jprogressor.jar "kr-online-1-$i-F$j.csv" "false" "1" "$i" "$j"
#   done
#done


#for i in $(seq 1 10)
#do
#   for t in 1 5 inf
#   do
#      for n in 175 200 225 250 inf
#      do
#         java -Xms50g -Xmx50g -jar jprogressor.jar "kr-online-$t-$n-$i.csv" "false" "$t" "$n"
#      done
#   done
#done

#for i in $(seq 1 3)
#do
#   java -Xms50g -Xmx50g -jar jprogressor.jar "kr-offline-$i.csv" "true" "inf" "inf"
#done

for i in $(seq 4 10)
do
    for n in $(seq 5 190)
    do
        java -Xms50g -Xmx50g -jar jprogressor.jar "kr-exp-8/kr-online-1-$n-$i.csv" "false" "1" "$n" "20"
    done
done
