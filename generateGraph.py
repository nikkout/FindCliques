#from pypprof.net_http import start_pprof_server
import threading
import heapq
from numpy import random

#start_pprof_server(port=8081)

#Edges = 183831
#Edges = 34681189
Edges = 25571
weights = random.normal(70, 15, Edges)

f = open("Email", "r")
i=0
c = 0
array = {}
#Read the graph from the file, format (vertex a, vertex b, weight). Create the lists.
for x in f:
    if c % 2 != 0:
        c+=1
        continue
    c+=1
    #t = (int(x.split(' ')[0]), int(x.split(' ')[1]), float(x.split(' ')[2].replace("\n", "")))
    t = (int(x.split(' ')[0]), int(x.split(' ')[1].replace("\n", "")), 1)
    t2 = (int(x.split(' ')[1]), int(x.split(' ')[0].replace("\n", "")), 1)
    if not t[0] == t[1] and (t2[0], t2[1]) not in array: 
        array[(t[0], t[1])]=weights[i]*10
        i+=1
#print(array)
f = open("EmailGen_half.data", "w")

for key1, key2 in array:
    print(key1)
    print(key2)
    f.write(str(key1)+"\t"+str(key2)+"\t"+str(array[(key1, key2)])+"\n")
f.close()
