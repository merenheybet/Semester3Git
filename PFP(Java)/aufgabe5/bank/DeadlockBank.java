public class DeadlockBank implements Bank{

    @Override
    public boolean transfer(Account fromAccount, Account toAccount, int money) {
        synchronized (fromAccount){
            if(fromAccount.getMoney() < money){
                //System.err.println("Not enough money");
                return false;
            }
            synchronized (toAccount){
                fromAccount.setMoney(fromAccount.getMoney() - money);
                toAccount.setMoney(toAccount.getMoney() + money);
                return true;
            }
        }
    }
}
