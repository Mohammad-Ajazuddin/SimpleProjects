import java.util.*;

class InsufficientFundsException extends Exception{
    public String toString()
    {
        return "Insufficient Funds. Try with lesser amount!";
    }
}

class CredentialsMismatchException extends Exception{
    public String toString()
    {
        return "Username or password is incorrect. Try again!";
    }
}

class BankAccount
{
    private double amount;
    private String branch;
    private String username;
    private String password;

    BankAccount(String branch, String username, String password, double amount)
    {
        this.username = username;
        this.password = password;
        this.amount = amount;
        this.branch = branch;
    }

    boolean login(String username,String password)
    {
        return this.username.equals(username) && this.password.equals(password);
    }

    void deposit(double amount)
    {
        this.amount+=amount;
        System.out.println(amount+" Depsoited");
        System.out.println("Updated Balance "+this.amount);
    }

    double withdraw(double amount) throws InsufficientFundsException
    {
        if(this.amount<amount)
        {
            throw new InsufficientFundsException();
        }
        else{
            this.amount-=amount;
        }
        System.out.println(amount+" Withdrawn");
        return this.amount;
    }

    void display()
    {
        System.out.println("Your Balance "+this.amount);
    }
}

public class BankManagementSystem
{
    public static void operations(BankAccount[] accounts, BankAccount currentAccount, boolean isLoggedIn, int attempts)
    {
        if(attempts==0)
        {
            System.out.println("All attempts failed. Autoexited");
            System.exit(0);
        }
        Scanner s = new Scanner(System.in);
        System.out.println("LOGIN");
        System.out.println("Enter username");
        String username = s.next();
        System.out.println("Enter password");
        String password = s.next();

        while(!isLoggedIn)
        {

            for(BankAccount account : accounts)
            {
                if(account.login(username, password))
                {
                    currentAccount = account;
                    isLoggedIn = true;
                    break;
                }
            }

            if(!isLoggedIn)
            {
                try{
                    throw new CredentialsMismatchException();
                }
                catch(CredentialsMismatchException e)
                {
                    System.out.println(e);
                    attempts--;
                    System.out.println("Remaining attempts "+attempts);
                    operations(accounts, currentAccount, isLoggedIn, attempts);
                }
            }
        }
        System.out.println("Login Successful");

        while(true)
        {
            System.out.println("Choose your Option");
            System.out.println("1. CREDIT 2. DEBIT 3. DISPLAY BALANCE 4. LOGOUT 5.EXIT");
            int choice = s.nextInt();
            double amount = 0;
            switch(choice)
            {
                case 1:
                    System.out.println("Enter amount to be credited");
                    amount = s.nextDouble();
                    currentAccount.deposit(amount);
                    break;
                case 2:
                    System.out.println("Enter amount to be debied");
                    amount = s.nextDouble();
                    try{
                       amount = currentAccount.withdraw(amount);
                       System.out.println("Remaining Balance "+amount);
                    }
                    catch(InsufficientFundsException e)
                    {
                        System.out.println(e);
                    }
                    break;
                case 3:
                    currentAccount.display();
                    break;
                case 4:
                    isLoggedIn = false;
                    currentAccount = null;
                    System.out.println("Logout successful");
                    attempts = 3;
                    operations(accounts, currentAccount, isLoggedIn, attempts);
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid entry!");
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter No. of accounts");
        int n = sc.nextInt();
        BankAccount[] accounts = new BankAccount[n];
        for(int i=0;i<n;i++)
        {
            System.out.println("Enter Information for Account "+(i+1));
            System.out.println("Enter Branch name");
            String branch = sc.next();
            System.out.println("Create a username");
            String username = sc.next();
            System.out.println("Create a password");
            String password = sc.next();
            System.out.println("Initial amount");
            double amount = sc.nextDouble();
            accounts[i] = new BankAccount(branch, username, password, amount);
        }

        BankAccount currentAccount = null;
        boolean isLoggedIn = false;
        int attempts = 3;
        
        operations(accounts,currentAccount,isLoggedIn,attempts);
    }
}
