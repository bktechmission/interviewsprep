package Coding;

import java.io.*;

/**
* Simple utilities to return the stack trace of an
* exception as a String.
*/
public final class ExceptionToStringTest {

  public static String getStackTrace(Throwable aThrowable) {
    final Writer result = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(result);
    aThrowable.printStackTrace(printWriter);
    return result.toString();
  }

  /**
  * Defines a custom format for the stack trace as String.
  */
  public static String getCustomStackTrace(Throwable aThrowable) {
    //add the class name and any message passed to constructor
    final StringBuilder result = new StringBuilder( "BOO-BOO: " );
    result.append(aThrowable.toString());
    final String NEW_LINE = System.getProperty("line.separator");
    result.append(NEW_LINE);

    //add each element of the stack trace
    for (StackTraceElement element : aThrowable.getStackTrace() ){
      result.append( element );
      result.append( NEW_LINE );
    }
    return result.toString();
  }

  /** Demonstrate output.  */
  public static void main (String... aArguments){
    final Throwable throwable = new IllegalArgumentException("Blah");
    //System.out.println( getStackTrace(throwable) );
    //System.out.println( getCustomStackTrace(throwable) );
    System.out.println( throwable.getStackTrace());
    ChannelType ch = ChannelType.MobileApp;
    System.out.println(ch);
    Integer i = 10;
    int x =10;
    if(x == i)
    {
    	System.out.println("yes");
    }
  }
  
} 

enum ChannelType
{
    Desktop (1, "Traditional Browser"),
    Mobile (2, "Mobile Site"),
    MobileApp (3, "Mobile App");

    private int id;
    private String name;

    private ChannelType(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}