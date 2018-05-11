#!/usr/bin/env bash
#for i in $(seq 75 150)
#do
#   java -Xms50g -Xmx50g -jar jprogressor.jar "kr-online-1-$i.csv" "false" "1" "$i"
#done


for i in $(seq 1 10)
do
   for t in 1 5 inf
   do
      for n in 175 200 225 250 inf
      do
         java -Xms50g -Xmx50g -jar jprogressor.jar "kr-online-$t-$n-$i.csv" "false" "$t" "$n"
      done
   done
done
