import java.lang.*;
import java.util.*;

class CredentialMismatchException extends Exception
{
    public String toString()
    {
        return "Credentials Mismatch Try Again";
    }
}

class WithdrawExceededException extends Exception
{
    public String toString()
    {
        return "WithdrawLimitExceeded --Low Balance";
    }
}

class BankAccounts
{
    double balance;
    String username;
    String password;
    String branch;
    private static int AcId;
    BankAccounts(String branch,String username,String password,double balance)
    {
        this.branch=branch; 
        this.username=username;
        this.password=password;
        this.balance=balance;        
    }
    public void login(BankAccounts[] Ac,int n) throws CredentialMismatchException
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter username and password To Login");
        String uname=sc.next();
        String pwd=sc.next();
        int flag=0;
        for(int i=0;i<n;i++)
        {
            if(uname.equals(Ac[i].username) && pwd.equals(Ac[i].password))
            {
                AcId=i;
                flag=1;
                System.out.println("Login Successful");
                break;
            }
        }
        if(flag==0)
        {
            throw new CredentialMismatchException();
        }
    }
    public void credit(BankAccounts[] Ac,double amt)
    {
        Ac[AcId].balance+=amt;
        System.out.println(amt+" added Successfully");
    }

    public double debit(BankAccounts[] Ac,double amt) throws WithdrawExceededException
    {
        if(Ac[AcId].balance<amt)
        {
            throw new WithdrawExceededException();
        }
        else
        {
            Ac[AcId].balance-=amt;
        }
        return Ac[AcId].balance;
    }
    public void displayBalance(BankAccounts[] Ac)
    {
        System.out.println("Available Balance "+Ac[AcId].balance);
    }
    public void exit()
    {
        AcId=-1;
        System.exit(0);
    }
}

public class BankManagementSystem
{
    public static void userLogin(BankAccounts[] Ac,int n)
    {
        int flag=0;
        while(flag==0)
        {
            try{
                Ac[0].login(Ac,n);
                flag=1;
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        BankManagementSystem.callMethods(Ac,n);
    }
    public static void callMethods(BankAccounts[] Ac,int n)
    {
        Scanner sc=new Scanner(System.in);
        while(true)
        {
            System.out.println("1. Credit 2. Debit 3. Display Balance 4. Exit 5. Login as Different User");
            double amt;
            int ch=sc.nextInt();
            if(ch==1)
            {
                System.out.println("Enter amount to be credited ");
                amt=sc.nextDouble();
                Ac[0].credit(Ac,amt);
            }
            else if(ch==2)
            {
                System.out.println("Enter amount to be Debited ");
                amt=sc.nextDouble();
                try{
                    System.out.println(amt+"  Debited. Remaining Balace "+Ac[0].debit(Ac,amt)); 
                }
                catch(Exception e)
                {
                    System.out.println(e);
                    continue;
                }
            }
            else if(ch==3)
            {
                Ac[0].displayBalance(Ac);
            }
            else if(ch==4)
            {
                System.out.println("Do you want to exit? 1.Yes 2. Continue");
                if(sc.nextInt()==1)
                {
                    Ac[0].exit();
                }
                else{
                    BankManagementSystem.callMethods(Ac,n);
                }
            }
            else if(ch==5)
            {
                BankManagementSystem.userLogin(Ac, n);
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter No. of accounts");
        int n=sc.nextInt();
        BankAccounts[] Ac=new BankAccounts[n];
        for(int i=0;i<n;i++)
        {
            System.out.println("Enter Branch Username Password Initial Balance for Account "+(i+1));
            Ac[i]=new BankAccounts(sc.next(),sc.next(),sc.next(),sc.nextDouble());
        }
        BankManagementSystem.userLogin(Ac,n);
        // BankManagementSystem.callMethods(Ac,n);
    }
}