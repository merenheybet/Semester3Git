public class QuickLockingBank implements Bank{
    @Override
    public boolean transfer(Account fromAccount, Account toAccount, int money) {
        return false;
    }
}
