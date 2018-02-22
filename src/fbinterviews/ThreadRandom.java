package fbinterviews;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ThreadRandom {

	public static void main(String[] args) {
	    // TODO Auto-generated method stub
	
	    new ThreadRandom().run();
	}
	
	private static final Random RANDOM = new Random();
	//DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		----> this is not thread safe
	public void run()
	{
	    Runnable task = new Runnable() {
	        @Override public void run() {
	        	for(int i=0;i<2;i++)
	        	{
	        		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        		Date date = new Date(RANDOM.nextLong());
	        		//GregorianCalendar calendar = new GregorianCalendar();
	        	    //Date date = calendar.getTime();
	        		System.out.println("CurrentTime:"+ System.currentTimeMillis() + "\tThreadName: " + Thread.currentThread().getName()  + "\tRandomDate:" + date);// + "\t\t" + sdf.format(date));//sdf.format(date));
	        		Calendar cal = Calendar.getInstance();
	        	    cal.setTime(date);
	        	    int randomHour = cal.get(Calendar.HOUR_OF_DAY);
	        	    int randomMin = cal.get(Calendar.MINUTE);
	        	    int randomSec = cal.get(Calendar.SECOND);

	        		//System.out.println("year:" + cal.get(Calendar.YEAR) + ",month:" + cal.get(Calendar.MONTH) +",day:"+ cal.get(Calendar.DAY_OF_MONTH) + ",hour:" + cal.get(Calendar.HOUR_OF_DAY) + ",min:" + cal.get(Calendar.MINUTE) +"sec:"+ cal.get(Calendar.SECOND));
	        		//System.out.println("new date is : "+ new Date(,04,13,Calendar.HOUR_OF_DAY, Calendar.MINUTE,Calendar.SECOND));
	        	    Date date1 = new Date(1429316812000L);
	        	    System.out.println("Date is " + date1);
	        	    cal.setTime(date1);
	        	    
	        	    int stayDateYear = cal.get(Calendar.YEAR);
	        	    int stayDateMonth = cal.get(Calendar.MONTH);
	        	    int stayDateDay = cal.get(Calendar.DAY_OF_MONTH);
	        	    
	        	    // create a new date
	        	    cal.set(Calendar.YEAR, stayDateYear);
	                cal.set(Calendar.MONTH, stayDateMonth);
	                cal.set(Calendar.DAY_OF_MONTH, stayDateDay);
	                cal.set(Calendar.HOUR_OF_DAY, randomHour);
	                cal.set(Calendar.MINUTE, randomMin);
	                cal.set(Calendar.SECOND, randomSec);
	                //cal2.set(Calendar.MILLISECOND, 0);
	        	    //System.out.println(sdf.format(cal3.getTime()));
	        	    Date tempDate = null;
					try {
						tempDate = sdf.parse(sdf.format(cal.getTime()));
						//System.out.println("formattedDate is:" + cal.getTime());
						//System.out.println("formattedDate is:" + tempDate);
						System.out.println("formattedDateString is:" + sdf.format(cal.getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	  
	        	    		
	        	    
	        	}
	        }
	    };
	
	    Thread t1, t2,t3,t4,t5;
	
	    t1 = new Thread(task, "ONE");
	    t2 = new Thread(task, "TWO");
	    //t3 = new Thread(task, "THREE");
	   // t4 = new Thread(task, "FOUR");
	   // t5 = new Thread(task, "FIVE");
	
	    t1.start();
	    t2.start();
	    //t3.start();
	   // t4.start();
	   //t5.start();
	}
}