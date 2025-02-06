import java.util.concurrent.*;

public class TaskedTernarySearch implements ParallelTernarySearch{

    @Override
    public Double[] findMinimum(Function[] f, double[] left, double[] right, int nThreads, TernarySearch rootFinder) {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        Double[] results = new Double[f.length];

        final int elementPerThread = Math.ceilDiv(f.length, nThreads);

        class TernaryTask implements Callable<Double[]> {
            final Function[] f;
            final double[] left;
            final double[] right;
            final TernarySearch rootFinder;
            final int elementPerThread;
            final int threadNo;

            TernaryTask(Function[] f, double[] left, double[] right,
                        TernarySearch rootFinder, int elementPerThread, int threadNo){
                this.f = f;
                this.left = left;
                this.right = right;
                this.rootFinder = rootFinder;
                this.elementPerThread = elementPerThread;
                this.threadNo = threadNo;
            }

            @Override
            public Double[] call() throws Exception {
                Double[] resultsOfThread = new Double[elementPerThread];
                for(int i = 0; i < elementPerThread; i++){
                    int currentIndex = (elementPerThread * threadNo) + i;
                    if(currentIndex >= f.length){continue;}
                    resultsOfThread[i] = rootFinder.findMinimum(f[currentIndex], left[currentIndex], right[currentIndex]);
                }
                return resultsOfThread;
            }
        }

//        for(int i = 0; i < f.length; i++){
//            Future<Double> f_result = executor.submit(new TernaryTask(f, left, right, rootFinder, i));
//            try{
//                    results[i] = f_result.get();
//            }catch (Exception ie){
//                System.err.println("Unable to get result of calculation");
//            }
//        }


        for (int i = 0; i < nThreads; i++) {
            final int indexOffset = (elementPerThread * i);
            Future<Double[]> f_result = executor.submit(new TernaryTask(f, left, right, rootFinder, elementPerThread, i));
            try{
                for (int k = 0; k < f_result.get().length; k++){
                    if(f_result.get()[k] == null){continue;}
                    results[k + indexOffset] = f_result.get()[k];
                }
            }catch (Exception ie){
                System.err.println("Unable to get result of calculation");
            }
        }

        executor.shutdown();
        try{
            executor.awaitTermination(60, TimeUnit.SECONDS);
        }catch (Exception e){/* InterruptedException caught */}

        return results;
    }
}
