package randomnumbers;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;


public class RandomNumberGenerator {
	public static void main(String[] args) {
		PseudoRandomNumberGenerator prng = new PseudoRandomNumberGenerator(10001);
		IntStream.range(0, 10).forEach(x->System.out.println(prng.nextInt(1000000)));

		PseudoRandomNumberGenerator prng2 = new PseudoRandomNumberGenerator(10001);
		IntStream.range(0, 10).forEach(x->System.out.println(prng2.nextInt(3)));
		
	}
}

//this is Pseudo Random Generator
class PseudoRandomNumberGenerator{
	private final AtomicLong seed = new AtomicLong();
	
	// LCG: Linear Congruential Generator Algo
	// Xn+1 = (a*Xn + c) mod m
	private long a = 3L;
	private long c = 2L;
	
	long mod = (1L<<48)-1;	// this is 2^48 and for % operator we do -1
	
	PseudoRandomNumberGenerator(long seed){
		System.out.println("a c m are"+ a+ "  "+c + " "+mod );
		this.seed.set(seed+System.nanoTime());
	}
	
	public int nextInt(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be positive");
		
		if((n&-n) == n) // i.e., n is a power of 2
		{
			return (int)((n*(long)next(31))>>31);
		}
		
		int bits,val;
		do {
			bits = next(31);
			val = bits % n;
		}while(bits-val + (n-1) <0); //check whether bits - val + (n-1) >=8; if so, that means the number came from the "last", incomplete copy of the range we want: 123123[12]
		return val;
	}
	
	private int next(int bits) {
		long oldseed, nextseed;
		do {
			oldseed = seed.get();
			nextseed = (a*oldseed + c)&(mod);
		}while(!seed.compareAndSet(oldseed, nextseed));
		return (int) (nextseed >>>(48-bits));
	}
}