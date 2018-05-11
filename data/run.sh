for i in $(seq 75 150)
do
   java -Xms50g -Xmx50g -jar jprogressor.jar "kr-online-1-$i.csv" "false" "1" "$i"
done 
