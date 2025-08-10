package practice.lld.problems;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Transaction {
    public int id;
    public int time;
    public int userId;
    public int amount;

    public Transaction(int id, int time, int userId, int amount) {
        this.id = id;
        this.time = time;
        this.userId = userId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction { id=" + id + ", time=" + time + ", userId=" + userId + ", amount=" + amount + '}';
    }
}

class Filter {
    public String field;     // e.g. "amount"
    public String operator;  // e.g. ">", "<", "="
    public int value;        // e.g. 10

    public Filter(String field, String operator, int value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }
}

class TransactionFilterService {

    public static List<Transaction> filterTransactions(List<Transaction> transactions, List<Filter> filters) {
        return transactions.stream()
            .filter(transaction -> matchesAllFilters(transaction, filters))
            .collect(Collectors.toList());
    }

    private static boolean matchesAllFilters(Transaction transaction, List<Filter> filters) {
        for (Filter filter : filters) {
            try {
                Field field = Transaction.class.getDeclaredField(filter.field);
                field.setAccessible(true);
                int fieldValue = (int) field.get(transaction);

                if (!applyOperator(fieldValue, filter.operator, filter.value)) {
                    return false;
                }
            } catch (Exception e) {
                System.err.println("Invalid field: " + filter.field);
                return false;
            }
        }
        return true;
    }

    private static boolean applyOperator(int fieldValue, String operator, int targetValue) {
        return switch (operator) {
            case "=" -> fieldValue == targetValue;
            case ">" -> fieldValue > targetValue;
            case "<" -> fieldValue < targetValue;
            case ">=" -> fieldValue >= targetValue;
            case "<=" -> fieldValue <= targetValue;
            case "!=" -> fieldValue != targetValue;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
}

public class TxnResolver {
    public static void main(String[] args) {
        List<Transaction> transactions = Arrays.asList(
            new Transaction(1, 1, 1, 10),
            new Transaction(2, 2, 3, 10),
            new Transaction(3, 3, 4, 11),
            new Transaction(4, 4, 2, 12)
        );

        List<Filter> filters = Arrays.asList(
            new Filter("amount", ">", 10),
            new Filter("userId", "<", 4)
        );

        var result = TransactionFilterService.filterTransactions(transactions, filters);

        result.forEach(System.out::println);
    }
}

