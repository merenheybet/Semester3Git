// Diese Implementation ist einfach so dumm, bitte nicht bewerten.


//import java.util.concurrent.Exchanger;
//
//public class LCSExchanger extends LCS{
//    private final int numOfThreads;
//
//    public LCSExchanger(int numOfThreads){
//        this.numOfThreads = numOfThreads;
//    }
//
//    // Divide the whole problem to numOfThreads rows and calculate each group separately
//    // as soon as they receive the necessary informations from other threads
//    static class CalculationThread implements Runnable{
//        private final int[][] matrix;
//        private final char[] x;
//        private final char[] y;
//        private final int fromRow;
//        private final int toRow;
//        private final Exchanger<int[]> prevExchanger;
//        private final Exchanger<int[]> nextExchanger;
//        private final LCSExchanger lcs;
//
//        public CalculationThread(int[][] matrix, char[] x, char[] y, int fromRow, int toRow,
//                                 Exchanger<int[]> prevExchanger, Exchanger<int[]> nextExchanger,LCSExchanger lcs) {
//            this.matrix = matrix;
//            this.x = x;
//            this.y = y;
//            this.fromRow = fromRow;
//            this.toRow = toRow;
//            this.prevExchanger = prevExchanger;
//            this.nextExchanger = nextExchanger;
//            this.lcs = lcs;
//        }
//
//        @Override
//        public void run() {
//            try {
//                int[] dummyRow = new int[x.length + 1];
//                // First row exchange with the previous thread
//                if (prevExchanger != null) {
//                    if(fromRow > 0){
//                        prevExchanger.exchange(matrix[fromRow]);
//                    }
//                    else{
//                        prevExchanger.exchange(dummyRow);
//                    }
//                }
//
//                for (int i = fromRow; i < toRow; i++) {
//                    for (int j = 1; j < x.length + 1; j++) {
//                        lcs.computeValue(x, y, matrix, i, j);
//                    }
//                }
//
//                // Last row exchange with the next thread
//                if (nextExchanger != null && toRow < matrix.length) {
//                    if(toRow - 1 >= 0){
//                        nextExchanger.exchange(matrix[toRow - 1]);
//                    }
//                    else{
//                        nextExchanger.exchange(dummyRow);
//                    }
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//
//    @Override
//    public char[] longestCommonSubsequence(char[] x, char[] y) {
//        int[][] matrix = new int[y.length + 1][x.length + 1];
//
//        Exchanger<int[]>[] exchangers = new Exchanger[numOfThreads];
//        for (int i = 0; i < numOfThreads; i++) {
//            exchangers[i] = new Exchanger<>();
//        }
//
//        Thread[] threads = new Thread[numOfThreads];
//
//        // divide rows to the threads, all remaining rows will be allocated to the lastThread
//        int rowPerThread = (y.length + 1) / (this.numOfThreads);
//
//        for(int i = 0; i < numOfThreads; i++){
//            int fromRow = i * rowPerThread;
//            int toRow = fromRow + rowPerThread;
//            // if it is the last thread, set the toRow to y.length + 1
//            if(i == numOfThreads - 1){
//                toRow = y.length + 1;
//            }
//
//            Exchanger<int[]> prevExchanger = (i == 0) ? null : exchangers[i - 1];
//            Exchanger<int[]> nextExchanger = (i == numOfThreads - 1) ? null : exchangers[i];
//
//            threads[i] = new Thread(new CalculationThread(matrix,x,y,fromRow,toRow,prevExchanger, nextExchanger, this));
//            threads[i].start();
//
//
//        }
//
//        for(Thread thread : threads){
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        return readMatrix(matrix,x,y);
//    }
//}
