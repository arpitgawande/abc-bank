package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class to verify Account objects
 * @author Arpit Gawande
 *
 */
public class AccountTest {
	private static final double DOUBLE_DELTA = 1e-15;
	@Test
	public void testInterestEarned() {
        Account maxiSavingAccount = new Account(Account.MAXI_SAVINGS);
        maxiSavingAccount.deposit(1000.0);
        assertEquals(50.0, maxiSavingAccount.interestEarned(), DOUBLE_DELTA);
	}
}
