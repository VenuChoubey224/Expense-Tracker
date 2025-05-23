package ExpenseTracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TransactionManager {
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static Scanner in = new Scanner(System.in);
	private static List<Transaction> transactions = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		loadFromFile();
		boolean flag = true;
		while(flag) {
		System.out.println("Pick any one:\n1. Add Transaction\n2. View Transactions by Category\n3. View Transactions by Sub Category\n4. View Transactions by Category and Sub Category\n5. Show Monthly Summary\n6. View all transactions \n7. Exit");
        int option = in.nextInt();
        
        switch (option) {
        case 1:
        	System.out.println(addTransaction());
        	saveToFile();
        	break;
        case 2:
        	System.out.println("Category : Income/Expense");
        	String category = in.next();
        	System.out.println(getTransactionByCategory(category));
        	break;
        case 3:
        	System.out.println("Sub Category : Food/Rent/Travel/Utilities");
        	String subCategory = in.next();
        	System.out.println(getTransactionBySubCategory(subCategory));
        	break;
        case 4:
        	System.out.println("Category : Income/Expense");
        	String category1 = in.next();
        	System.out.println("Sub Category : Food/Rent/Travel/Utilities");
        	String subCategory1 = in.next();
        	System.out.println(getTransactionByCategoryAndSubCategory(category1, subCategory1));
        	break;
        case 5:
        	System.out.println("Year: ");
        	int year = in.nextInt();
        	System.out.println("Month: ");
        	int month = in.nextInt();
        	System.out.println(getMonthlySummary(year, month));
        	break;
        case 6:
        	System.out.println(allTransactions());        	
        	break;
        case 7:
        	flag = false;
        	break;
        default:
        	System.out.println("Invalid choice.");
        }
    }
	}
	
	public static Transaction addTransaction() throws Exception{
			
		System.out.println("Category: Income or Expense");
		String category = in.next().toLowerCase();
		while(!category.equals("income") && !category.equals("expense")) {
			System.out.println("Please enter either Income or Expense (Case Insensitive)");
			category = in.next().toLowerCase();
		}
		String subCategory = "";
		if(category.equals("income")) {
			System.out.println("Sub Category of Transaction: Salary/Business");
			subCategory = in.next().toLowerCase();
		}
		else if(category.equals("expense")) {
			System.out.println("Sub Category of Transaction: Food/Rent/Travel/Utilities");
			subCategory = in.next().toLowerCase();
		}
		
		
		System.out.println("Amount: ");
		double amount = in.nextDouble();
		
		System.out.println("Date of Transaction: ");		
		String dateStr = in.next();
		LocalDate date = LocalDate.parse(dateStr, formatter);
		
		Transaction transaction = new Transaction(category, subCategory, amount, date);
		transactions.add(transaction);
		
		return transaction;
		
	}
	
	public static void sortTransactionsByDate() {
		transactions.sort(Comparator.comparing(t -> t.date));
	}
	
	public static List<Transaction> getTransactionByCategory(String category) {
		List<Transaction> result = new ArrayList<>();
		
		for(Transaction t : transactions) {
			if(t.getCategory().equalsIgnoreCase(category)) {
				result.add(t);
			}
		}
		
		return result;
	}
	
	public static List<Transaction> getTransactionBySubCategory(String subCategory) {
		
		List<Transaction> result = new ArrayList<>();
		
		for(Transaction t : transactions) {
			if(t.getSubCategory().equalsIgnoreCase(subCategory)) {
				result.add(t);
			}
		}
		
		return result;
	}
	
	public static List<Transaction> getTransactionByCategoryAndSubCategory(String category, String subCategory) {
		
		List<Transaction> result = new ArrayList<>();
		
		for(Transaction t : transactions) {
			if(t.getCategory().equalsIgnoreCase(category) && t.getSubCategory().equalsIgnoreCase(subCategory)) {
				result.add(t);
			}
		}
		
		return result;
	}
	
	public static List<Transaction> getMonthlySummary(int year, int month){
		
		List<Transaction> result = new ArrayList<>();
		for(Transaction t : transactions) {
			if(t.getDate().getYear() == year && t.getDate().getMonthValue() == month) {
				result.add(t);
			}
		}
		return result;
	}
	
	public static List<Transaction> allTransactions(){
		
		return transactions;
	}
	
	private static void saveToFile() {
        
        String filePath = "Transactions.txt";

        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (Transaction t : transactions) {
                writer.println(t);
            }
            System.out.println("Transactions saved to file.");
            System.out.println("Saving " + transactions.size() + " transactions...");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }
	
	private static void loadFromFile() {
        
        String filePath = "Transactions.txt";
        transactions.clear();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length != 4) continue;
                String category = parts[0];
                String subCategory = parts[1];
                double amount = Double.parseDouble(parts[2]);
                LocalDate date = LocalDate.parse(parts[3], formatter);

                transactions.add(new Transaction(category, subCategory, amount, date));
            }
            System.out.println("Loaded " + transactions.size() + " transactions from file.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
