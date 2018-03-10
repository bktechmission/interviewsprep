package designpattern;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionalInterfaceExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// first anonymous inner class
		carryOutWork(new Foo() {
			@Override
			public void doWork() {
				System.out.println("through anonymous inner class test.");
			}
		});
		
		// java 8 lamda
		carryOutWork(()->{System.out.println("through lamda single liner test.");});
		
		// java.util.function
		System.out.println(compute(MathOperations::square,10));
		System.out.println(compute(MathOperations::add10,30));
		
		printer.accept("Word for Word");
		printSquare.accept(20);
		
		System.out.println(generateRandomString.get());
	}
	
	static Consumer<String> printer = (arg)->{System.out.println(arg);};
	static Consumer<Integer> printSquare = (arg) ->{System.out.println("consumer square "+(arg*arg));};
	
	static final Random rand = new Random();
	
	static Supplier<String> generateRandomString = ()->{
		StringBuilder sb = new StringBuilder();
		int iterations = 10;
		
		// generate dic
		char[] dic = new char[26];
		for(int i= 0;i<26;i++) {
			dic[i] = (char) (i + 'a');
		}
		
		while(iterations>0) {
			sb.append(dic[rand.nextInt(26)]+"");
			iterations--;
		}
		
		return sb.toString();
	};

	
	
	public static void carryOutWork(Foo foo){
	    foo.doWork();
	}
	
	public static Integer compute(Function<Integer,Integer> func, Integer val){
	    return func.apply(val);
	}
	
}

class MathOperations{
	public static int square(int val) {
		return val*val;
	}
	
	public static int add10(int val) {
		return val+10;
	}
	
}

@FunctionalInterface
interface Foo {
	public void doWork();
	
	default public void doSomeWork() {
		System.out.println("Doing some work in interface impl...");
	}

	default public void doSomeOtherWork() {
		System.out.println("Doing some other work in interface impl...");
	}
}