public class ThreadedTernarySearch implements ParallelTernarySearch{
    @Override
    public Double[] findMinimum(Function[] f, double[] left, double[] right, int nThreads, TernarySearch rootFinder) {
        Thread[] minThreads = new Thread[nThreads];
        Double[] results = new Double[f.length];

        for(int i = 0; i < f.length; i++){
            final int finalI = i;
            minThreads[i] = new Thread(){
                @Override
                public void run() {
                    results[finalI] = rootFinder.findMinimum(f[finalI], left[finalI], right[finalI]);
                }
            };
            minThreads[i].start();
        }

        for(int i = 0; i< f.length; i++){
            try{
                minThreads[i].join();
            } catch (InterruptedException e) {
                //
            }
        }
    }
}
