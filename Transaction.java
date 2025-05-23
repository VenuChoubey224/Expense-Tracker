package ExpenseTracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Transaction {
	String category;
	String subCategory;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	double amount;
	LocalDate date;
	public Transaction(String category, String subCategory, double amount, LocalDate date) {
		super();
		this.category = category;
		this.subCategory = subCategory;
		this.amount = amount;
		this.date = date;
	}
	
	@Override
	 public String toString() {
//	        return category + "," + subCategory + "," + amount + "," + date;
		 return category + "," + subCategory + "," + amount + "," + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    }
	
}
