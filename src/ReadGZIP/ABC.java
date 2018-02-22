package ReadGZIP;


import java.util.*;

 import java.util.jar.*;

 import java.util.zip.*;

 import java.io.*;

 /**
 * Reads GZIP, Zip, and Jar files and outputs their content to the console.
 */

 public class ABC {
   public static void main(String[] args) 
   {
	   	String fileName = "C:\\sftp\\Lodging Data\\Scores.tar.gz";

      	System.out.println("contents of " + fileName + "...");
      
		 if (fileName.endsWith(".gz")) {
		  readGZIPFile(fileName);
		
		 } else if (fileName.endsWith(".zip")) {
		  readZipFile(fileName);
		
		 } else if (fileName.endsWith(".jar")) {
		  readJarFile(fileName);
		
		 }
    }
    /**
    * Reads a GZIP file and dumps the contents to the console.
    */
   private static void readGZIPFile(String fileName) {
      // use BufferedReader to get one line at a time
      BufferedReader gzipReader = null;
      try {
         // simple loop to dump the contents to the console
         gzipReader = new BufferedReader(
               new InputStreamReader(
                new GZIPInputStream(
                new FileInputStream(fileName))));
         while (gzipReader.ready()) {
            System.out.println(gzipReader.readLine());
         }
         gzipReader.close();
     } catch (FileNotFoundException fnfe) {
         System.out.println("The file was not found: " + fnfe.getMessage());
      } catch (IOException ioe) {
         System.out.println("An IOException occurred: " + ioe.getMessage());
      } finally {
         if (gzipReader != null) {
            try { gzipReader.close(); } catch (IOException ioe) {}
         }
      }
   }

   /**
    * Reads a Zip file, iterating through each entry and dumping the contents
    * to the console.
    */
   private static void readZipFile(String fileName) {
      ZipFile zipFile = null;
      try {
         // ZipFile offers an Enumeration of all the files in the Zip file
         zipFile = new ZipFile(fileName);
         for (Enumeration e = zipFile.entries(); e.hasMoreElements();) {
            ZipEntry zipEntry = (ZipEntry) e.nextElement();
            System.out.println(zipEntry.getName() + " contains:");
            // use BufferedReader to get one line at a time
            BufferedReader zipReader = new BufferedReader(
                 new InputStreamReader(zipFile.getInputStream(zipEntry)));
            while (zipReader.ready()) {
               System.out.println(zipReader.readLine());
            }
            zipReader.close();
         }
      } catch (IOException ioe) {
         System.out.println("An IOException occurred: " + ioe.getMessage());
      } finally {
         if (zipFile != null) {
            try { zipFile.close(); } catch (IOException ioe) {}
         }
      }
   }

   /**
    * Reads a Jar file, displaying the attributes in its manifest and dumping
    * the contents of each file contained to the console.
    */
   private static void readJarFile(String fileName) {
      JarFile jarFile = null;
      try {
         // JarFile extends ZipFile and adds manifest information
         jarFile = new JarFile(fileName);
         if (jarFile.getManifest() != null) {
            System.out.println("Manifest Main Attributes:");
            Iterator iter =
              jarFile.getManifest().getMainAttributes().keySet().iterator();
            while (iter.hasNext()) {
               Attributes.Name attribute = (Attributes.Name) iter.next();
               System.out.println(attribute + " : " +
                   jarFile.getManifest().getMainAttributes().getValue(attribute));
            }
            System.out.println();
         }
         // use the Enumeration to dump the contents of each file to the console
         System.out.println("Jar file entries:");
         for (Enumeration e = jarFile.entries(); e.hasMoreElements();) {
            JarEntry jarEntry = (JarEntry) e.nextElement();
            if (!jarEntry.isDirectory()) {
               System.out.println(jarEntry.getName() + " contains:");
               BufferedReader jarReader = new BufferedReader(
                  new InputStreamReader(jarFile.getInputStream(jarEntry)));
               while (jarReader.ready()) {
                  System.out.println(jarReader.readLine());
               }
               jarReader.close();
            }
         }
      } catch (IOException ioe) {
         System.out.println("An IOException occurred: " + ioe.getMessage());
      } finally {
         if (jarFile != null) {
            try { jarFile.close(); } catch (IOException ioe) {}
         }
      }
   }

 }