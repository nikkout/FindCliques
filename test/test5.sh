{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 7500 2 1000 test_5.dat 3 1.05 2500 >res5; } 2>time5.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 7500 2 100000 test_5.dat 3 1.05 2500 >>res5; } 2>>time5.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 7500 2 1000000 test_5.dat 3 1.05 2500 >>res5; } 2>>time5.res

{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 15000 4 1000 test_5.dat 3 1.05 2500 >>res5; } 2>>time5.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 15000 4 100000 test_5.dat 3 1.05 2500 >>res5; } 2>>time5.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 15000 4 1000000 test_5.dat 3 1.05 2500 >>res5; } 2>>time5.res

{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 30000 8 1000 test_5.dat 3 1.05 2500 >>res5; } 2>>time5.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 30000 8 100000 test_5.dat 3 1.05 2500 >>res5; } 2>>time5.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 30000 8 1000000 test_5.dat 3 1.05 2500 >>res5; } 2>>time5.res

{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 60000 16 1000 test_5.dat 3 1.05 2500 >>res5; } 2>>time5.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 60000 16 100000 test_5.dat 3 1.05 2500 >>res5; } 2>>time5.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 60000 16 1000000 test_5.dat 3 1.05 2500 >>res5; } 2>>time5.res
