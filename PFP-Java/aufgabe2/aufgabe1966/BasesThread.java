
public class BasesThread {
    static class BaseConversionThread extends Thread{
        int threadNumber;

        public BaseConversionThread(int threadNumber){
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

        Thread[] threads = new BaseConversionThread[numberOfThreads];

        for (int i = 2; i <= numberOfThreads + 1; i++){
            // Create the threads with threadNNumbers from 0 - numberOfThreads
            // Start the threads
            // index = i-2 because the base starts from 2, which should be at threads[0]
            threads[i-2] = new BaseConversionThread(i);
            threads[i-2].start();
        }

        for (Thread t : threads){
            // End the threads
            try{
                t.join();
            }catch (Exception e){/* InterruptedException caught */}
        }
    }
}
