public class QuickLockingBank implements Bank{
    @Override
    public boolean transfer(Account fromAccount, Account toAccount, int money){
        // Lock fromAccount lock
        try {
            fromAccount.lock();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try{
            if(fromAccount.getMoney() < money){
                //System.err.println("Not enough money");
                return false;
            }

            // Lock toAccount
            toAccount.lock();

            try{
                fromAccount.setMoney(fromAccount.getMoney() - money);
                toAccount.setMoney(toAccount.getMoney() + money);
                return true;
            }finally{
                toAccount.unlock();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            fromAccount.unlock();
        }
    }
}
