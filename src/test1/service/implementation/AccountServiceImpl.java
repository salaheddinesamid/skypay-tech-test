package test1.service.implementation;

import test1.service.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AccountServiceImpl implements AccountService {
    private int balance;
    private List<Transaction> transactions;
    private DateTimeFormatter dateFormatter;

    public AccountServiceImpl() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    @Override
    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        transactions.add(new Transaction(LocalDate.now(), amount, balance));
    }

    @Override
    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal");
        }
        balance -= amount;
        transactions.add(new Transaction(LocalDate.now(), -amount, balance));
    }

    @Override
    public void printStatement() {
        System.out.println("Date       || Amount  || Balance");
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            System.out.printf("%s  || %6d  || %7d%n",
                    t.getDate().format(dateFormatter),
                    t.getAmount(),
                    t.getBalance());
        }
    }

    private static class Transaction {
        private LocalDate date;
        private int amount;
        private int balance;

        public Transaction(LocalDate date, int amount, int balance) {
            this.date = date;
            this.amount = amount;
            this.balance = balance;
        }

        public LocalDate getDate() {
            return date;
        }

        public int getAmount() {
            return amount;
        }

        public int getBalance() {
            return balance;
        }
    }
}