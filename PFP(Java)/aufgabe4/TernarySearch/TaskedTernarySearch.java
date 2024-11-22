import java.util.concurrent.*;

public class TaskedTernarySearch implements ParallelTernarySearch{

    @Override
    public Double[] findMinimum(Function[] f, double[] left, double[] right, int nThreads, TernarySearch rootFinder) {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        Double[] results = new Double[f.length];

        class TernaryTask implements Callable<Double> {
            final Function[] f;
            final double[] left;
            final double[] right;
            final TernarySearch rootFinder;
            final int index;

            TernaryTask(Function[] f, double[] left, double[] right, TernarySearch rootFinder, int index){
                this.f = f;
                this.left = left;
                this.right = right;
                this.rootFinder = rootFinder;
                this.index = index;
            }

            @Override
            public Double call() throws Exception {
                return rootFinder.findMinimum(f[index], left[index], right[index]);
            }
        }

        final int iterationCount = Math.floorDiv(f.length, nThreads);
        final int iterationCountRest = f.length - (iterationCount * nThreads);

//        for(int i = 0; i < f.length; i++){
//            Future<Double> f_result = executor.submit(new TernaryTask(f, left, right, rootFinder, i));
//            try{
//                    results[i] = f_result.get();
//            }catch (Exception ie){
//                System.err.println("Unable to get result of calculation");
//            }
//        }

        for (int j = 0; j < iterationCount; j++) {
            for (int i = 0; i < nThreads; i++) {
                final int index = (j * nThreads) + i;
                Future<Double> f_result = executor.submit(new TernaryTask(f, left, right, rootFinder, index));
                try{
                    results[index] = f_result.get();
                }catch (Exception ie){
                    System.err.println("Unable to get result of calculation");
                }
            }

        }

        for(int i = 0; i < iterationCountRest ; i++){
            final int index = (iterationCount * nThreads) + i;
            Future<Double> doubleFuture = executor.submit(new TernaryTask(f, left, right, rootFinder, index));
            try{
                results[index] = doubleFuture.get();
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
