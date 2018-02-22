package programcreek;

public class PriorityQueue {
	public static void main(String[] args) {
		PQ pq = new PQ(10);
		pq.push(10);
		pq.push(4);
		pq.push(21);
		
		pq.push(51);
		pq.push(45);
		pq.push(32);
		
		pq.push(65);
		pq.push(34);
		pq.push(22);

		while(!pq.isEmpty()) {
			System.out.print(pq.pop()+" ");
		}
		
		
	}
	
}

// max heap: max at parent as compare to child
class PQ{
	int[] a;
	int size;
	
	PQ(int maxSize){
		a = new int[maxSize];
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	// implement 2 functions 
	//1st push
	public void  push(int e) {
		if(size==a.length) {
			throw new IllegalStateException();
		}
		// we always insert at the end, and then bubble up
		int pos = size;	// last index to be filled
		a[pos] = e;		// lets put the element
		
		while(pos>0) {
			// parent = (i+1)/2 +1   l,r = 2p+1, 2p+2
			int parent = (pos+1)/2-1;		//0 -> 1,2;  1->3,4   2->5,6   3->7,8	2n+1,2n+2
			if(a[parent]>a[pos]) break;
			swap(parent,pos);
			pos = parent;
		}
		
		size++;
	}
	
	public int pop() {
		if(size==0) {
			throw new IllegalStateException();
		}
		// always pop from top and then size-- and replace with last element then sink down
		int toReturn = a[0];
		int last = size-1;
		a[0] = a[last];
		size--;
		
		// sink down
		int pos = 0;
		adjust(this.a,0,size);
		return toReturn;
	}
	
	public void heapify(int[]a) {
		int size = a.length;
		for(int pos=size/2-1;pos>=0;pos--) {
			adjust(a,pos,size);
		}
	}
	
	public void adjust(int[]a, int pos, int size) {
		while(pos<size/2) {
			int leftChild = 2*pos+1;
			int rightChild = 2*pos+2;
			int maxPos=0;
			if(rightChild<size-1&&a[rightChild]>a[leftChild]) {
				maxPos = rightChild;
			}
			else {
				maxPos = leftChild;
			}
			if(a[maxPos]<a[pos]) break;
			swap(maxPos,pos);
			pos=maxPos;
		}
	}
	
	public int peek() {
		if(size==0) {
			throw new IllegalStateException();
		}
		return a[0];
	}
	
	private void swap(int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	// 2nd pop
	
}