package designpattern;

import java.util.LinkedList;
import java.util.List;

public class ObserverPattern {
	public static void main(String[] args) {
		NewsPublishObserver news1 = new NewsPublishObserver();
		EmailPublishObserver email1 = new EmailPublishObserver();
		CassandraLogPublishObserver cslog1 = new CassandraLogPublishObserver();
		
		LoanProduct loan = new LoanProduct(3.56, 4.90, 98036);
		loan.subscribe(news1);
		loan.subscribe(email1);
		
		
		loan.setRate15yr(3.96);
		
		loan.subscribe(cslog1);
		loan.setRate30yr(4.96);
	}
}

interface Subject{
	public void subscribe(Observer observer);	// observe()
	public void unsubscribe(Observer observer); //unobserve()
	public void notifyAllSubscribers();
}

interface Observer{
	public void update(double rate15yr, double rate30yr, int zipcode);
}

class LoanProduct implements Subject{
	private double rate15yr;
	private double rate30yr;
	private int zipcode;
	
	LoanProduct(double rate15yr, double rate30yr, int zipcode){
		this.rate15yr = rate15yr;
		this.rate30yr = rate30yr;
		this.zipcode = zipcode;
		notifyAllSubscribers();
	}
	
	List<Observer> observersList = new LinkedList<>();
	
	public double getRate15yr() {
		return rate15yr;
	}

	public void setRate15yr(double rate15yr) {
		this.rate15yr = rate15yr;
		notifyAllSubscribers();
	}

	public double getRate30yr() {
		return rate30yr;
	}

	public void setRate30yr(double rate30yr) {
		this.rate30yr = rate30yr;
		notifyAllSubscribers();
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
		notifyAllSubscribers();
	}
	
	
	@Override
	public void subscribe(Observer observer) {
		observersList.add(observer);
	}

	@Override
	public void unsubscribe(Observer observer) {
		observersList.remove(observer);
	}

	@Override
	public void notifyAllSubscribers() {
		observersList.forEach(o->{
			o.update(this.rate15yr,this.rate30yr,this.zipcode);
		});
	}
}

class NewsPublishObserver  implements Observer{

	@Override
	public void update(double rate15yr, double rate30yr, int zipcode) {
		System.out.println("NewsPublished Loan rate for zipcode "+ zipcode +" are 15yr rate: "+ rate15yr+ " and 30yr rate: "+rate30yr);
	}
	
}

class EmailPublishObserver  implements Observer{

	@Override
	public void update(double rate15yr, double rate30yr, int zipcode) {
		System.out.println("EmailPublished Loan rate for zipcode "+ zipcode +" are 15yr rate: "+ rate15yr+ " and 30yr rate: "+rate30yr);
	}
	
}

class CassandraLogPublishObserver  implements Observer{

	@Override
	public void update(double rate15yr, double rate30yr, int zipcode) {
		System.out.println("CassandraLogPublished Loan rate for zipcode "+ zipcode +" are 15yr rate: "+ rate15yr+ " and 30yr rate: "+rate30yr);
	}
	
}


