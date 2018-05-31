package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
	/* Changing delta to precision till 9 decimal point, sufficiently large for practical amount calculations */
    private static final double DOUBLE_DELTA = 1e-9;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    /* Modifying following test cases to calculate daily interest */
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.000273973, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account savingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

        savingAccount.deposit(1500.0);

        assertEquals(1.002739726, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxSavingsAccount() {
        Bank bank = new Bank();
        Account maxiSavingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingAccount));

        maxiSavingAccount.deposit(3000.0);

        assertEquals(0.410958904, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
