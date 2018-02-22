package Coding;

import java.io.Serializable;

class B extends A{
	int b = 100;
	boolean p = false;
	
	public String toString(){
		return "toSupper = "+ super.toString() + "B is [ b = " + b + ", p= "+ p+ "]"+ "[a="+a+", flag="+flag+"]";
	}
}
public class ABase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		A obj = new B();
		obj.setA(10000);
		obj.setFlag(true);
		System.out.println(obj);
		
		double a = 1;
		Double b = 1.0;
		
		StringBuilder sb = new StringBuilder();
		sb.append("123.2|23.1|");
		System.out.println(sb.toString());
		//sb.setLength(sb.length()-1);
		System.out.println(sb.substring(0, sb.length()-1));
		
		ResultType resultType1 = ResultType.REGION;
		ResultType resultType2 = ResultType.REGION;
		
		System.out.println(resultType1.name() + "  " + resultType1.value());
		System.out.println("Result is equals : " + (resultType1 == resultType2));
		
		Double d = new Double(0.45);
		String name = new String("tlaScorer");
		System.out.println(d.hashCode());
		System.out.println(name.hashCode());
		obj = null;
		if(obj instanceof B)
			System.out.println("Yes");
	}
	
	

}

enum ResultType implements Serializable
{
	REGION,
	HOTEL,
	UNKNOWN;
	
	/**
	 * Returns the name of this enum constant, exactly as declared in its
     * enum declaration.
	 */
	public String value()
	{
		return name();
	}
	
	public static ResultType getResultType(String type)
	{
		if (type != null)
		{
			for (ResultType resultType : ResultType.values())
			{
				if (type.equalsIgnoreCase(resultType.value()))
				{
					return resultType;
				}
			}
		}
		return UNKNOWN;
	}
}
