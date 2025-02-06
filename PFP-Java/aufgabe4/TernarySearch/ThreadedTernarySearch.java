public class ThreadedTernarySearch implements ParallelTernarySearch {
    @Override
    public Double[] findMinimum(Function[] f, double[] left, double[] right, int nThreads, TernarySearch rootFinder) {
        Thread[] minThreads = new Thread[nThreads];
        Double[] results = new Double[f.length];

        final int elementPerThread = Math.ceilDiv(f.length, nThreads);

        class TernaryThread extends Thread{
            final Function[] f;
            final double[] left;
            final double[] right;
            Double[] results;
            final TernarySearch rootFinder;
            final int elementPerThread;
            final int threadNo;

            TernaryThread(Function[] f, double[] left, double[] right,
                          Double[] results, TernarySearch rootFinder,
                          int elementPerThread, int threadNo){
                this.f = f;
                this.left = left;
                this.right = right;
                this.results = results;
                this.rootFinder = rootFinder;
                this.elementPerThread = elementPerThread;
                this.threadNo = threadNo;
            }

            @Override
            public void run(){
                for(int i = 0; i < elementPerThread; i++){
                    int currentIndex = (elementPerThread * threadNo) + i;
                    if(currentIndex >= f.length){return;}
                    results[currentIndex] =
                            rootFinder.findMinimum(f[currentIndex], left[currentIndex], right[currentIndex]);
                }
            }
        }


        for(int i = 0; i < nThreads; i++){
            minThreads[i] = new TernaryThread(f, left, right, results, rootFinder, elementPerThread, i);
            minThreads[i].start();
        }

        for(int i = 0; i < nThreads; i++){
            try{
                minThreads[i].join();
            } catch (InterruptedException e) {/**/}
        }

        return results;
    }
}
