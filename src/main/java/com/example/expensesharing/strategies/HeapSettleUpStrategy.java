package com.example.expensesharing.strategies;

import com.example.expensesharing.models.*;

import java.util.*;

public class HeapSettleUpStrategy implements SettleUpStrategy {

    @Override
    public List<Expense> settleUp(List<Expense> expenses) {

        Map<User, Double> balanceMap = new HashMap<>();

        for (Expense expense : expenses) {
            for(UserExpense userExpense : expense.getUserExpenseList()) {

                User user = userExpense.getUser();
                double amount = userExpense.getAmount();

                //System.out.println(userExpense.getUser().getName() + " - " + expense.getName()+ "-> Amount = " + amount);

                balanceMap.putIfAbsent(user, 0.0);
                if (userExpense.getUserExpenseType().equals(UserExpenseType.PAID_BY)) {
                    balanceMap.put(user, amount + balanceMap.get(user));
                }
                if (userExpense.getUserExpenseType().equals(UserExpenseType.HAD_TO_PAY)) {
                    balanceMap.put(user, balanceMap.get(user) - amount);
                }
            }
        }

        // create max and min heap
        // creditors
        PriorityQueue<Map.Entry<User, Double>> maxHeap = new PriorityQueue<>(
                (a, b) -> Double.compare(b.getValue(), a.getValue()));

        // debtors
        PriorityQueue<Map.Entry<User, Double>> minHeap = new PriorityQueue<>(
                (a, b) -> Double.compare(a.getValue(), b.getValue()));

        // add entries in min and max heap
        for(Map.Entry<User, Double> entry : balanceMap.entrySet()) {
            double balance  = entry.getValue();
            if(Math.abs(balance) < 0.000001)
                continue;
            if( balance < 0)
                minHeap.offer(entry);
            else
                maxHeap.offer(entry);
        }

        List<Expense> settlementExpenses = new ArrayList<>();

        while(!minHeap.isEmpty() && !maxHeap.isEmpty()) {

            Map.Entry<User, Double> creditorEntry = maxHeap.poll();
            Map.Entry<User, Double> debtorEntry = minHeap.poll();
            System.out.println("Creditor: " + creditorEntry.getKey().getName() + " Amount: " + creditorEntry.getValue());
            System.out.println("Debtor: " + debtorEntry.getKey().getName() + " Amount: " + debtorEntry.getValue());

            User creditor = creditorEntry.getKey();
            User debtor = debtorEntry.getKey();

            double creditorAmount = creditorEntry.getValue();
            double debtorAmount = -debtorEntry.getValue();

            double settledAmount = Math.min(creditorAmount, debtorAmount);
            System.out.println("Settled Amount: " + settledAmount);

            Expense settlement = new Expense();
            settlement.setName("Settlement");
            settlement.setDescription(String.format("Settlement: %s pays Rs %.2f to %s",debtor.getName() ,settledAmount, creditor.getName()));
            settlement.setTotalAmount(settledAmount);
            settlement.setExpenseType(ExpenseType.DUMMY);

            UserExpense payer = new UserExpense();
            payer.setUser(debtor);
            payer.setAmount(debtorAmount);
            payer.setUserExpenseType(UserExpenseType.HAD_TO_PAY);
            payer.setExpense(settlement);

            UserExpense payee = new UserExpense();
            payee.setUser(creditor);
            payee.setAmount(creditorAmount);
            payee.setUserExpenseType(UserExpenseType.PAID_BY);
            payee.setExpense(settlement);

            settlement.setUserExpenseList(Arrays.asList(payee, payer));
            settlementExpenses.add(settlement);

            // updating minHeap and maxHeap after settling up highest debtor or creditor
            double debtorNetAmount = debtorEntry.getValue() + settledAmount;
            double creditorNetAmount = creditorEntry.getValue() - settledAmount;

            // if debtor is not yet settled insert it back in minHeap
            if(debtorNetAmount < -0.000001)
                minHeap.offer(new AbstractMap.SimpleEntry<>(debtor, debtorNetAmount));
            // if creditor is not yet settled insert it back in maxHeap
            if(creditorNetAmount > 0.000001)
                maxHeap.offer(new AbstractMap.SimpleEntry<>(creditor, creditorNetAmount));
        }


        return settlementExpenses;
    }
}
