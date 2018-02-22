package fbinterviews;

public class FindCelebrityInGraph {
	public static void main(String[] args)
	{
		int[][] mat = {{1,0,0,1,0},{0,1,1,1,0}, {0,0,1,1,0}, {0,0,0,1,0},{0,0,0,1,1}};
		System.out.println("Is Celebity exists : " + isCelebityExists(mat));
		
	}
	
	public static boolean isCelebityExists(int[][] mat)
	{
		int celebrity = 0;
		int N = mat.length;
		System.out.println("Length is : " + N);
		for(int i=1; i<N;i++)
		{
			if(know(mat, celebrity, i))
			{
				celebrity = i;
			}
			else if(know(mat, i, celebrity))
			{
				// do nothing here
			}
			else if((i+1)<N)
			{
				celebrity = i+1;
				i++;
			}
		}
		System.out.println("Before Celebity is : " + celebrity);
		for(int i=0; i<N;i++)
		{
			if(!know(mat, i, celebrity) || (i!=celebrity && know(mat, celebrity, i)))
				return false;
		}
		
		System.out.println("Celebity is : " + celebrity);
		
		return true;
	}
	
	// return true if x knows y
	public static boolean know(int[][] matrix, int x, int y)
	{
		if(matrix[x][y] == 1) return true;
		return false;
	}
}
