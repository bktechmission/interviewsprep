package iterables;

import java.util.Iterator;

public class IterablesTest {
	
	public static void main(String[] args)
	{
		String[] k = {"hello", "seema", "malik", "bhupi"};
		MyBagOfWords<String> mywords = new MyBagOfWords<String>(k);
		for(String s : mywords)
		{
			System.out.println(s);
		}
		
	}

}


class MyBagOfWords<T> implements Iterable<T>{
	
	T[] words;
	
	public MyBagOfWords(T[] mywords) {
		words = mywords;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int index = 0;
			@Override
			public boolean hasNext() {
				return index < words.length;
			}

			@Override
			public T next() {
				return words[index++];
			}
			
		};
	}
	
}
