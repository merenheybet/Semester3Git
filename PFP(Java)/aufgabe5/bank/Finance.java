import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Finance {
	static class transferThread extends Thread{
		private final Bank bank;
		private final int threadID;
		private final Account[] accArray;

		public transferThread(Bank bank, int threadID, Account[] accArray){
			this.bank = bank;
			this.threadID = threadID;
			this.accArray = accArray;
		}

		@Override
		public void run() {
			final Account fromAccount = accArray[threadID];
			final Account toAccount = accArray[(threadID + 1) % accArray.length];
			while(true){
				transferRandomMoney(this.threadID, this.bank, fromAccount, toAccount);
			}
		}
	}

	private static final Random rng = new Random(42);



	private static void transferRandomMoney(final int threadId, final Bank bank, final Account from,
			final Account to) {

		final int money = rng.nextInt(200) + 1;
		if (from != to) {
			if (bank.transfer(from, to, money)) {
				System.out.printf("[%d] Transfer from %s to %s successful%n", threadId, from, to);
			} else {
				System.out.printf("[%d] Transfer from %s to %s failed%n", threadId, from, to);
			}
		}
	}



	private static void runTransfers(final Bank bank, final int numThreads) {
		
		final Account[] accounts = new Account[numThreads];
		final Thread[] threads = new transferThread[numThreads];
		for (int i = 0; i < numThreads; ++i) {
			accounts[i] = new Account(rng.nextInt(150) + 50);
		}

		for(int i = 0; i < numThreads; i++){
			threads[i] = new transferThread(bank, i, accounts);
			threads[i].start();
		}
	}



	public static void main(String[] args) {
//		runTransfers(new DeadlockBank(), 2);
//		runTransfers(new QuickLockingBank(), 2);
//		runTransfers(new OrderedBank(), 2);
		runTransfers(new ManagedBank(), 2);
	}
}

