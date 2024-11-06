import java.util.concurrent.*;

public class BasesExecutor {
    static class BaseConversionExecutor implements Runnable{
        int threadNumber;

        public BaseConversionExecutor(int threadNumber){
            this.threadNumber = threadNumber;
        }

        public void run(){
            // if threadNumber is bigger than 36, error: Cannot convert
            if(this.threadNumber > 36){
                System.err.println("Cannot convert to base: " + threadNumber);
                return;
            }

            int originalNumber = 1966;
            String convertedNumber = Integer.toString(originalNumber, threadNumber);
            System.out.println("1966 in base 10 equals to " + convertedNumber + " in base " + this.threadNumber);

        }
    }

    public static void main(String[] argv){
        int numberOfThreads = Integer.parseInt(argv[0]);

        ExecutorService exec = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 2; i <= numberOfThreads + 1; i++){
            // Create the threads with threadNNumbers from 0 - numberOfThreads
            // Start the threads with ThreadPool
            exec.execute(new BaseConversionExecutor(i));
        }

        // End the threads
        exec.shutdown();
        try{
            exec.awaitTermination(60, TimeUnit.SECONDS);
        }catch (Exception e){/* InterruptedException caught */}

    }
}


