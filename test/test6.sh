{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 10000 2 1000 test_6.dat 3 1.3 2500 >res6; } 2>time6.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 10000 2 100000 test_6.dat 3 1.3 2500 >>res6; } 2>>time6.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 10000 2 1000000 test_6.dat 3 1.3 2500 >>res6; } 2>>time6.res

{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 20000 4 1000 test_6.dat 3 1.3 2500 >>res6; } 2>>time6.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 20000 4 100000 test_6.dat 3 1.3 2500 >>res6; } 2>>time6.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 20000 4 1000000 test_6.dat 3 1.3 2500 >>res6; } 2>>time6.res

{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 40000 8 1000 test_6.dat 3 1.3 2500 >>res6; } 2>>time6.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 40000 8 100000 test_6.dat 3 1.3 2500 >>res6; } 2>>time6.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 40000 8 1000000 test_6.dat 3 1.3 2500 >>res6; } 2>>time6.res

{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 80000 16 1000 test_6.dat 3 1.3 2500 >>res6; } 2>>time6.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 80000 16 100000 test_6.dat 3 1.3 2500 >>res6; } 2>>time6.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 80000 16 1000000 test_6.dat 3 1.3 2500 >>res6; } 2>>time6.res
