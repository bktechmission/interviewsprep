package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class TreeSum {

	  public static class TreeNode {
	    public int val;
	    public TreeNode left;
	    public TreeNode right;

	    public TreeNode(int val) {
	      this.val = val;
	    }

	  }
	  public static void main(String[] args)
	  {
		  TreeNode root = new TreeNode(2);
		  root.left=new TreeNode(8);
		  root.right = new TreeNode(1);
		  root.left.left = new TreeNode(9);
		  root.left.right=new TreeNode(2);
		  root.left.right.left=new TreeNode(8);
		  root.left.right.right=new TreeNode(7);
		  root.left.right.right.right=new TreeNode(1);
		  
		  root.right.left = new TreeNode(10);
		  root.right.right = new TreeNode(1);
		  root.right.right.left=new TreeNode(8); // getCountOfPathWithSum
		  System.out.println("total paths: "+getCountOfPathsWithSumOptimized(root,10));
		  printAllPaths(root,10);
		  System.out.println("\n\nPrinting spiral: ");
		  printSpiralPaths(root);
		  
		  reversebits(43261596);
		  swapbitsEvenOddPlaces(43261596);
		  printDoubleToBinary(512.2343D);
		  
	  }
	  
	  static void printAllPaths(TreeNode root, int x)
	  {
		  printAllPathsHelper(root, x, new ArrayList<Integer>(),0);
	  }
	  // nlogn space and nlogn time
	  static void printAllPathsHelper(TreeNode root, int sum, ArrayList<Integer> buffer, int level)
	  {
		  if(root==null)
			  return;
		  
		  int temp = sum;
		  buffer.add(root.val);
		  // log(goes depth of node level max above)
		  for(int i=level;i>-1;i--)
		  {
			  temp-=buffer.get(i);
			  if(temp==0)
				  printPaths(buffer,i,level);
		  }
		  // clone here for next recursion paths
		  ArrayList<Integer> c1 = new ArrayList<Integer>(buffer);
		  ArrayList<Integer> c2 = new ArrayList<Integer>(buffer);
		  printAllPathsHelper(root.left,sum,c1,level+1);
		  printAllPathsHelper(root.right,sum,c2,level+1);
	  }
	  
	  static void printPaths(List<Integer> buffer, int start, int level)
	  {
		  System.out.println();
		  for(int i=start;i<=level;i++)
		  {
			  System.out.print(buffer.get(i)+" ");
		  }
	  }
	  
	  static int getCountOfPathsHasSum(TreeNode root, int sum)
	  {
		  if(root==null)
			  return 0;
		  int totalPaths = getCountOfPathsStartFromNode(root,sum,0);
		  
		  totalPaths+=getCountOfPathsHasSum(root.left,sum);
		  totalPaths+=getCountOfPathsHasSum(root.right,sum);
		  return totalPaths;
	  }
	  
	  static int getCountOfPathsStartFromNode(TreeNode root, int sum, int runningSum)
	  {
		  if(root==null)
			  return 0;
		  
		  runningSum+=root.val;
		  int totalPaths =0;
		  if(runningSum==sum)
			  totalPaths++;
		  totalPaths+=getCountOfPathsStartFromNode(root.left,sum,runningSum);
		  totalPaths+=getCountOfPathsStartFromNode(root.right, sum, runningSum);
		  return totalPaths;
	  }
	  
	  static int getCountOfPathsWithSumOptimized(TreeNode root, int sum)
	  {
		  return getCountOfPathsWithOptimizedHelper(root, sum, 0, new HashMap<Integer, Integer>());
	  }
	  static int getCountOfPathsWithOptimizedHelper(TreeNode root, int sum, int runningSum, Map<Integer,Integer> map)
	  {
		  // base case
		  if(root==null)
			  return 0;
		  runningSum+=root.val;
		  int remainingSum = runningSum-sum;
		  int totalPaths = map.getOrDefault(remainingSum, 0);
		  if(runningSum==sum)
			  totalPaths++;
		  
		  mapIncrement(map, runningSum, 1);
		  totalPaths+=getCountOfPathsWithOptimizedHelper(root.left,sum,runningSum,map);
		  totalPaths+=getCountOfPathsWithOptimizedHelper(root.right,sum,runningSum,map);
		  mapIncrement(map,runningSum,-1);
		  return totalPaths;
	  }
	  
	  static void mapIncrement(Map<Integer, Integer> map, int key, int delta)
	  {
		  int value= map.getOrDefault(key, 0) + delta;
		  if(value==0)
			  map.remove(key);
		  else
			  map.put(key, value);
	  }
	  
	  static void printSpiralPaths(TreeNode root)
	  {
		  if(root==null || root.left==null)
		  {
			  System.out.println(root);
			  return;
		  }
		  Stack<TreeNode> s1 = new Stack<TreeNode>();
		  Stack<TreeNode> s2 = new Stack<TreeNode>();
		  
		  s1.push(root);
		  while(!s1.isEmpty() || !s2.isEmpty())
		  {
			  System.out.println();
			  while(!s1.isEmpty())
			  {
				  TreeNode node = s1.pop();
				   System.out.print(node.val+" ");
				   if(node.right!=null)
					   s2.push(node.right);
				   if(node.left!=null)
					   s2.push(node.left);
			  }
			  System.out.println();
			  while(!s2.isEmpty())
			  {
				  TreeNode node = s2.pop();
				   System.out.print(node.val+" ");
				   if(node.left!=null)
					   s1.push(node.left);
				   if(node.right!=null)
					   s1.push(node.right);
			  }
		  }
		  
	  }
	  
	  
	  static void reversebits(int num)
	  {
		  int newNum=0;
		  System.out.println("\nBefore Reverse bits : ");
		  for(int i=0;i<32;i++)
		  {
			  int lastBit = (num>>>(31-i))&1; 
			  System.out.print(lastBit);
			  newNum|=(lastBit<<(i));
		  }
		  
		  System.out.println("\nAfter Reverse bits : ");
		  for(int i=0;i<32;i++)
		  {
			  int lastBit = (newNum>>>(31-i))&1; 
			  System.out.print(lastBit);
		  }
		  
	  }
	  
	  static void swapbitsEvenOddPlaces(int num)
	  {
		  System.out.println("\nSWAP bits : ");
		  for(int i=0;i<32;i++)
		  {
			  int lastBit = (num>>>(31-i))&1; 
			  System.out.print(lastBit);
		  }
		  int newNum = ((num&(0xaaaaaaaa))>>1)|((num&(0x55555555))<<1);
		  
		  System.out.println();
		  for(int i=0;i<32;i++)
		  {
			  int lastBit = (newNum>>>(31-i))&1; 
			  System.out.print(lastBit);
		  }
	  }
	  
	  static void printDoubleToBinary(double num)
	  {
		  // wholePart + "." + fractionalPart
		  long wholePart = (long)num;
		  
		  double fractionalPart = (num-wholePart);
		  
		  System.out.println("\n\ndouble to binary String: "+toBinaryStringFromLong(wholePart) + "." + toBinaryStringFromFractional(fractionalPart,5));
		  
	  }
	  
	  static String toBinaryStringFromLong(long x)
	  {
		  StringBuilder sb = new StringBuilder();
		  for(int i=63;i>=0;i--)
		  {
			  sb.append(((x>>>i)&1));
		  }
		  return sb.toString();
	  }
	  
	  static String toBinaryStringFromFractional(double fraction, int precesion)
	  {
		  StringBuilder sb = new StringBuilder();
		  for(int i=0;i<precesion;i++)
		  {
			  fraction*=2;
			  int b = (int)fraction;
			  if(b==1)
			  {
				  sb.append(1);
				  fraction-=1;
			  }
			  else
				  sb.append(0);
			  
		  }
		  return sb.toString();
	  }
	  
}