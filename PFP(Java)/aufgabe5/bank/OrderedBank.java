public class OrderedBank implements Bank{
    @Override
    public boolean transfer(Account fromAccount, Account toAccount, int money) {
        int fromAccountID = fromAccount.getId();
        int toAccountID = toAccount.getId();

        if(fromAccountID < toAccountID){
            synchronized (fromAccount){
                if(fromAccount.getMoney() < money){
//                    System.err.println("Not enough money");
                    return false;
                }
                synchronized (toAccount){
                    fromAccount.setMoney(fromAccount.getMoney() - money);
                    toAccount.setMoney(toAccount.getMoney() + money);
                    return true;
                }
            }
        }

        else{
            synchronized (toAccount){
                synchronized (fromAccount){
                    if(fromAccount.getMoney() < money){
//                        System.err.println("Not enough money");
                        return false;
                    }
                    fromAccount.setMoney(fromAccount.getMoney() - money);
                    toAccount.setMoney(toAccount.getMoney() + money);
                    return true;
                }
            }
        }
    }
}
