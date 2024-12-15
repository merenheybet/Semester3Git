import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LCSQueue extends LCS{
    private final int numOfThreads;

    public LCSQueue(int numOfThreads){
        this.numOfThreads = numOfThreads;
    }

    static class Calculation implements Runnable{
        private final int[][] matrix;
        private final char[] x;
        private final char[] y;
        private final int fromRow;
        private final int toRow;
        private final LCS lcs;
        private BlockingQueue<int[]>[] queues;
        private final int threadID;

        public Calculation(int[][] matrix, char[] x, char[] y, int fromRow, int toRow,
                           LCS lcs, BlockingQueue<int[]>[] queues, int threadID) {
            this.matrix = matrix;
            this.x = x;
            this.y = y;
            this.fromRow = fromRow;
            this.toRow = toRow;
            this.lcs = lcs;
            this.queues = queues;
            this.threadID = threadID;
        }

        @Override
        public void run() {
            // if it is the first iteration wait for the queue.
            if(fromRow > 1){
                try {
                    queues[threadID].take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


            for (int i = fromRow; i <= toRow; i++) {
                for (int j = 1; j < x.length + 1; j++) {
                    lcs.computeValue(x, y, matrix, i, j);
                }
            }
            // Add to the queue of next thread if i = last row of current thread and not the last row of matrix.
            // Previously had them inside the for loop, then realized that i could just write it at the beginning and
            // the end.

            // IF toRow(the last row of the current thread) isn't the allerletzte row of matrix, add it to the queue of
            // the next thread
            if(toRow <= y.length -1 ){
                try {
                    queues[threadID + 1].put(matrix[toRow]);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public char[] longestCommonSubsequence(char[] x, char[] y) {
        int[][] matrix = new int[y.length + 1][x.length + 1];

        // Create the threads and queues
        BlockingQueue<int[]>[] queues = new ArrayBlockingQueue[numOfThreads];
        Thread[] threads = new Thread[numOfThreads];

        //Determine the number of rows in each thread excluding the 0th row
        int rowsPerThread = y.length/ numOfThreads;

        for(int i = 0; i < numOfThreads; i++){
            queues[i] = new ArrayBlockingQueue<>(1);

            int fromRow = i * rowsPerThread + 1;
            int toRow = fromRow + rowsPerThread - 1; // inclusive

            // the lastThread is responsible for all remaining rows at the end
            if(i == numOfThreads - 1){
                toRow = y.length;
            }

            threads[i] = new Thread(new Calculation(matrix, x, y, fromRow, toRow, this, queues, i));
            threads[i].start();
        }

        for(Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return readMatrix(matrix,x,y);
    }
}
