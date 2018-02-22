package Coding;

public class A {
	protected int a;
	protected  boolean flag = true;
	
	public A()
	{
		a = 10;
		flag = true;
	}
	
	public String toString(){
		return "[a="+a+", flag="+flag+"]";
	}
	
	public void setA(int a){
		this.a = a;
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
}
