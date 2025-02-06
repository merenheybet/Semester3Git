public class QuickLockingBank implements Bank{
    // Fehlerhaft
    @Override
    public boolean transfer(Account fromAccount, Account toAccount, int money){
        // Lock fromAccount lock
        try {
            // da es nur ein Bank-Objekt gibt.
            synchronized (this){fromAccount.lock();}
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try{
            if(fromAccount.getMoney() < money){
                //System.err.println("Not enough money");
                return false;
            }

            // Lock toAccount
            // da es nur ein Bank-Objekt gibt.
            synchronized (this){toAccount.lock();}
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
