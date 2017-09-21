package fr.jee;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloWorld {

	public static void main(String[] args) {
		System.out.println("Hello World \n ");
		
		// Current Date
		// System.out.println(LocalDateTime.now());
		 System.out.println(java.util.Calendar.getInstance().getTime() + "\n");
		
		 // Read line by line from a TEXT file (no memory consumption)
		 String absolutPath = "C:/Users/ali/Desktop/Formation/MODIS_POEI/_Projects/AVL/formation-java/TP1/src/fr/jee/ressources/hello.txt";
		 
		 String relativePath = "hell.txt";
		 
		 String content = null;
		 
		 /*	BufferedReader br = null;
		 try {
		 	File file = new File (filePath); 
		 		 
			br = new BufferedReader(new FileReader(file));
			
			while((content = br.readLine()) != null) {
	            System.out.println("File content : " + content);
	        }
		 } catch (Exception e) {	
			e.printStackTrace();
		 } */
		 		
		 try {
			content = new String(Files.readAllBytes(Paths.get(absolutPath)));
			System.out.println("File content using absolutPath: " + content);
				
			content = new String(Files.readAllBytes(Paths.get(relativePath)));
			System.out.println("File content using relativePath : " + content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
