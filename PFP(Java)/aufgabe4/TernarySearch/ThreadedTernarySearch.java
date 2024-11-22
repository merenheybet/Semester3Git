public class ThreadedTernarySearch implements ParallelTernarySearch {
    @Override
    public Double[] findMinimum(Function[] f, double[] left, double[] right, int nThreads, TernarySearch rootFinder) {
        Thread[] minThreads = new Thread[nThreads];
        Double[] results = new Double[f.length];

        final int iterationCount = Math.floorDiv(f.length, nThreads);
        final int iterationCountRest = f.length - (iterationCount * nThreads);

        for (int i = 0; i < f.length; i++) {
            results[i] = rootFinder.findMinimum(f[i], left[i], right[i]);
        }

        return results;
    }
}

//        for (int j = 0; j < iterationCount; j++){
//            for(int i = 0; i < nThreads; i++){
//                final int index = (j * nThreads) + i;
//                minThreads[i] = new Thread(){
//                    @Override
//                    public void run() {
//                        results[index] = rootFinder.findMinimum(f[index], left[index], right[index]);
//                    }
//                };
//                minThreads[i].start();
//            }
//
//            for(int i = 0; i < nThreads; i++){
//                try{
//                    minThreads[i].join();
//                } catch (InterruptedException e) {
//
//                }
//            }
//        }
//
//        for(int j = 0 ; j < iterationCountRest ; j++){
//            final int index = (iterationCount * nThreads) + j;
//            minThreads[j] = new Thread(){
//                @Override
//                public void run() {
//                    results[index] = rootFinder.findMinimum(f[index], left[index], right[index]);
//                }
//            };
//            minThreads[j].start();
//        }
//
//        for(int j = 0 ; j < iterationCountRest ; j++){
//            try{
//                minThreads[j].join();
//            } catch (InterruptedException e) {
//
//            }
//        }
//
//        return results;
//    }
//}
