{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 6000 2 1000 test_2.dat 3 1.05 1300 >res2; } 2>time2.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 6000 2 100000 test_2.dat 3 1.05 1300 >>res2; } 2>>time2.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 6000 2 1000000 test_2.dat 3 1.05 1300 >>res2; } 2>>time2.res

{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 12000 4 1000 test_2.dat 3 1.05 1300 >>res2; } 2>>time2.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 12000 4 100000 test_2.dat 3 1.05 1300 >>res2; } 2>>time2.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 12000 4 1000000 test_2.dat 3 1.05 1300 >>res2; } 2>>time2.res

{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 24000 8 1000 test_2.dat 3 1.05 1300 >>res2; } 2>>time2.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 24000 8 100000 test_2.dat 3 1.05 1300 >>res2; } 2>>time2.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 24000 8 1000000 test_2.dat 3 1.05 1300 >>res2; } 2>>time2.res

{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 48000 16 1000 test_2.dat 3 1.05 1300 >>res2; } 2>>time2.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 48000 16 100000 test_2.dat 3 1.05 1300 >>res2; } 2>>time2.res
{ time numactl --cpunodebind=1 --membind=1 java -jar -Xmx30G -Xms30G -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+DisableExplicitGC demo.jar 48000 16 1000000 test_2.dat 3 1.05 1300 >>res2; } 2>>time2.res
