package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    /*
     * Accounts of a customer.
     * It is assumed for the sake of assignment that; the customer can have only one account for each account type.
     */
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }
    
    /**
     * Get account by using account type.
     * @param accountType
     * @return
     */
    public Account getAccount(int accountType) {
    	for(Account account: accounts) {
    		if(account.getAccountType() == accountType) {
    			return account;
    		}	
    	}
    	return null;    	
    }
    
    /**
     * 
     * @param fromAccount: account from which money has to be taken 
     * @param toAccount: account to which money has to be deposited
     * @param amount: transaction amount
     */
    public void transfer(Account fromAccount, Account toAccount, double amount) {
    	fromAccount.withdraw(amount);
    	toAccount.deposit(amount);
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
