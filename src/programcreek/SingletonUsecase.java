package programcreek;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SingletonUsecase {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException, ClassNotFoundException {
		// multi thread use case
		/*Runnable r = ()->{
			System.out.println("In Runnable");
			TransactionManager.startTransaction();
			IntStream.range(0, 12).forEach(x->{
				Singleton s = Singleton.getInstance();
				Context c1 = TransactionManager.getTransactionContext();
				String newId = c1.getUserId() +"_run"+ x;
				c1.setUserId(newId);
				System.out.println(x+". thread:"+Thread.currentThread().getName()+" object is "+s.hashCode());
			});
			System.out.println("final thread local is "+TransactionManager.getTransactionContext().userId);
			TransactionManager.endTransaction();
		};
		
		ExecutorService sc = Executors.newFixedThreadPool(10);
		IntStream.range(0, 20).forEach(x->{
			sc.submit(r);
		});
		
		System.out.println("Is terminated "+sc.isTerminated());
		sc.shutdown();
		sc.awaitTermination(10, TimeUnit.MILLISECONDS);
		System.out.println("Is terminated "+sc.isTerminated());
		*/
		
		
		// Serilaization example
		Singleton s = Singleton.getInstance();
		System.out.println("before searialzation hashcode is "+s.hashCode()+" object is "+s);
		File f = new File("singletonwrite.ser");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(s);
		oos.flush();
		oos.close();
		
		//File f = new File("singletonwrite.ser");
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
		s = (Singleton)ois.readObject();
		System.out.println("new searialzied hashcode is "+s.hashCode()+" object is "+s);
		ois.close();
	
	}
}

class TransactionManager {
	private static ThreadLocal<Context> context = new ThreadLocal<Context>() {
	     @Override protected Context initialValue() {
	    	 	System.out.println("ThreadLocal creating new Context Thread Local Specific");
	         return new Context();
	 }};
	public static void startTransaction() {
		Context c = context.get();
		String newId = c.getUserId() +"::"+ Thread.currentThread().getName()+"::";
		Context temp = new Context(newId);
		context.set(temp); 
	}
	public static Context getTransactionContext() {
		return context.get();
	}
	public static void endTransaction() {
		//logic to end a transaction
		//…
		context.remove();
	}
}

class Context{
	public Context() {
		this.userId = new String(new Integer(new Random().nextInt(100)).toString());
	}
	public Context(String userId) {
		this.userId = userId;
	}
	
	String userId;
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId=userId;
	}
}

class Singleton{
	// 1st no global access to contructor. so make it private
	// Everyone access its instance through getInstance static utility
	public static final long serialVersionUID = 1L;
	private String name;
	private transient Integer age;
	
	private static volatile Singleton ref;	// eager intialization
	private Singleton(String name,Integer age) {
		this.name = name;
		this.age = age;
		System.out.println("Singleton Contructor is called...");
	}

	private Singleton() {
		System.out.println("Private Singleton Contructor is called...");
	}
	public static Singleton getInstance() {
		if(ref==null) {
			synchronized(Singleton.class) {
				if(ref==null) {
					ref = new Singleton("bhupender",25);
				}
			}
		}
		return ref;
	}
	
	public String toString() {
		return "name:"+this.name+", age:"+age;
	}
	public static Singleton getInstanceHolderIdiom() {
		return SingletonHolder.instance;
	}
	
	private static class SingletonHolder{
		private static Singleton instance = new Singleton("bhupenderHolder",30);
	}
	
	// serialization fix
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		Integer age = (Integer)ois.readObject();
		this.age = age;
	}
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(age);
	}
	
	private Object readResolve() {
		return ref;
	}
}


enum SingletonEnum{
	INSTANCE;
}
