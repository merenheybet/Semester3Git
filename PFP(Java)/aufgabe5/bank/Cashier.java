import java.util.ArrayList;

public class Cashier {
    private final ArrayList<String> currentTransactions;

    public Cashier(){
        this.currentTransactions = new ArrayList<>();
    }

    public synchronized boolean transactionAllowed(Account fromAccount, Account toAccount){
        if (fromAccount.getId() < toAccount.getId()){
            if(currentTransactions.contains("" + fromAccount.getId() + toAccount.getId())){
                return false;
            }

            currentTransactions.add("" + fromAccount.getId() + toAccount.getId());
        }

        else{
            if(currentTransactions.contains("" + toAccount.getId() + fromAccount.getId())){
                return false;
            }

            currentTransactions.add("" + toAccount.getId() + fromAccount.getId());
        }
        return true;
    }

    public synchronized void completeTransaction(Account fromAccount, Account toAccount){
        if (fromAccount.getId() < toAccount.getId()){
            currentTransactions.remove("" + fromAccount.getId() + toAccount.getId());
        }
        else{
            currentTransactions.remove("" + toAccount.getId() + fromAccount.getId());
        }
    }
}
