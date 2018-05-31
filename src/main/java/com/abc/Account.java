package com.abc;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	/*
	 * Account class could have account id to uniquely identify an account,
	 * but due to nature of the assignment, and the time in consideration, we will
	 * consider account type as unique identifier.
	 */
	private final int accountType;
	/*
	 * Encapsulating transaction fields with private access modifier
	 */
	private List<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}

	public double interestEarned() {
		double amount = sumTransactions();
		switch (accountType) {
		case SAVINGS:
			if (amount <= 1000)
				return amount * 0.001;
			else
				return 1 + (amount - 1000) * 0.002;
			// case SUPER_SAVINGS:
			// if (amount <= 4000)
			// return 20;
		/* Updating interest rate calculation for Maxi-Savings accounts
		 * Interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
		 */
		case MAXI_SAVINGS:
			if(isWithdrawalLast10Days()) {
				return amount * 0.001;
			} else {
				return amount * 0.05;
			}
		default:
			return amount * 0.001;
		}
	}

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.getAmount();
		return amount;
	}
	
	/**
	 * Check if last withdrawal date is within 10 days.
	 * As we are using list to store transactions, 
	 * logic of retrieving transaction from the last are used,
	 * because transactions are considered immutable.
	 * @return date of the transaction
	 */
	private  boolean isWithdrawalLast10Days() {
		for (int i = transactions.size() - 1; i >= 0; i--) {
			Transaction transaction = transactions.get(i);
			long daysBefore = getDaysBefore(transaction);
			if(daysBefore < 10) {
				if(transaction.getAmount() < 0) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Calculate number of days before a transaction was made. 
	 * @param transaction
	 * @return days (long)
	 */
	private long getDaysBefore(Transaction transaction) {
		/* Converting java.util.Date to java.time.LocalDateTime to make compatible with Java 8
		 * compiler used for coding this assignment */
		LocalDateTime date = transaction.getTransactionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime now = LocalDateTime.now();
	    Duration duration = Duration.between(now, date);
	    return duration.toDays();
	}

	public int getAccountType() {
		return accountType;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
