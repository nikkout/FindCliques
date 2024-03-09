#from pypprof.net_http import start_pprof_server
import threading
import heapq
from numpy import random
import sys
f = open(sys.argv[1], "r")
o = open(sys.argv[2], "w")


#start_pprof_server(port=8081)

Edges = int(sys.argv[3])
Vertices = int(sys.argv[4])
weights = random.normal(70,15, size=Edges)

i=0
c = 0
array = {}
o.write("asd "+str(Vertices)+" "+str(Edges)+"\n")
#Read the graph from the file, format (vertex a, vertex b, weight). Create the lists.
for x in f:
    #t = (int(x.split(' ')[0]), int(x.split(' ')[1]), float(x.split(' ')[2].replace("\n", "")))
    t = (int(x.split('\t')[0]), int(x.split('\t')[1].replace("\n", "")), 1)
    #t2 = (int(x.split('\t')[1]), int(x.split('\t')[0].replace("\n", "")), 1)
    if not t[0] == t[1] and t[0]< t[1]:
        if weights[i] < 0:
            weights[i] = 1
        o.write(str(t[0])+" "+str(t[1])+" "+str(int(weights[i]))+"\n")
        i+=1
#print(array)
f.close()
o.close()
