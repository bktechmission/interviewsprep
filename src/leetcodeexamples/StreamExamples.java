package leetcodeexamples;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StreamExamples {
	public static final int MAX_CAPACITY = 30;
	public static int index = 0;
	public static void main(String[] args) {
		BlockingQueue<String> queue = new PriorityBlockingQueue<>(MAX_CAPACITY,Comparator.reverseOrder());
		MyProducer<String> myProducer = new MyProducer<String>(queue);
		MyConsumer<String> myConsumer = new MyConsumer<String>(queue);
		
		Random rand = new Random();
		Supplier<String> supplier = ()->{
			System.out.println("putting now:"+index++);
			return "This is message"+ rand.nextInt(1000);};
		Consumer<String> consumer = s->System.out.println("tookout:"+s);
		
		new Thread(() -> {
	        for (int i = 0; i <MAX_CAPACITY+100 ; i++) {
	            myProducer.produce(supplier);
	        }
	    }).start();
		
		new Thread(() -> {
	        for (int i = 0; i < MAX_CAPACITY+100; i++)
	            myConsumer.consume(consumer);
	    }).start();
		
		System.out.println("Ending main program");
	}
	
	static class MyProducer<T>{
		BlockingQueue<T> queue = null;
		public MyProducer(BlockingQueue<T> queue) {
			this.queue = queue;
		}
		
		public void produce(Supplier<T> supplier) {
			T msg = supplier.get();
			try {
				queue.put(msg);
				System.out.println("Putting msg: "+msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	static class MyConsumer<T>{
		BlockingQueue<T> queue;
		MyConsumer(BlockingQueue<T> queue){
			this.queue = queue;
		}
		
		public void consume(Consumer<T> consumer) {
			try {
				consumer.accept(queue.take());
				try {
					System.out.println("consumer sleep for now....");
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
	}
	
}
