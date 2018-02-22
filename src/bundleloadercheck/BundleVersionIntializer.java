package bundleloadercheck;

public class BundleVersionIntializer{

    public static void main(String[] args) {
    	BundleVersionIntializer bi = new BundleVersionIntializer();
        UrgencyBundleVersionHolder.getInstance(true).loadVersionsAndSchedule();
        synchronized (bi) {
        	 try {
     			bi.wait(1000*60*10);
     			bi.notify();
     		} catch (InterruptedException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
		}
    }
}
