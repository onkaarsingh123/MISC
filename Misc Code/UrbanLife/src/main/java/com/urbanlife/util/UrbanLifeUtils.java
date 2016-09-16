package com.urbanlife.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.omg.IOP.ServiceContext;
import org.springframework.web.context.ContextLoaderListener;

import com.urbanlife.constants.UrbanLifeConstants;
import com.urbanlife.rest.UrbanLifeMerchants;

public class UrbanLifeUtils {
	static final Logger logger = Logger.getLogger(UrbanLifeUtils.class);
      public static String SERVER_PATH = null;
	public static String getPath(String key) {

		Properties props = new Properties();
		InputStream input = null;

		ClassLoader classLoader = UrbanLifeUtils.class.getClassLoader();
		InputStream file = classLoader.getResourceAsStream("config.properties");

		try {
			// input = new FileInputStream(file);
			props.load(file);
		} catch (IOException ex) {

			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

		return props.getProperty(key);
	}

	public static String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition")
				.split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	// save to somewhere
	public static void writeFile(byte[] content, String filename)
			throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}

	public static byte[] readFile(String filename) throws IOException {

		File file = new File(filename);

		FileInputStream fin = null;
        byte[] fileContent= null;
		try {


			fin = new FileInputStream(file);

			 fileContent = new byte[(int) file.length()];

			// Reads up to certain bytes of data from this input stream into an
			// array of bytes.

			fin.read(fileContent);

			// create string from byte array

			String s = new String(fileContent);

			System.out.println("File content: " + s);

		}

		catch (FileNotFoundException e) {

			System.out.println("File not found" + e);

		}

		catch (IOException ioe) {

			System.out.println("Exception while reading file " + ioe);

		}
		return fileContent;
	}
	public static String savePhoto(List<InputPart> photo,String key) {
		String fileName = "";
		 
			String path = null;
					 if(key.equals("TEMP_PHOTO_PATH")){
						 path = SERVER_PATH +UrbanLifeUtils.getPath(key);
					 }else{
					 path = UrbanLifeUtils.getPath(key);
					
					 }

		
		
		for (InputPart inputPart : photo) {

			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName =  UrbanLifeUtils.getFileName(header);

				// convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class,
						null);

				byte[] bytes = IOUtils.toByteArray(inputStream);

				// constructs upload file pathzz
				boolean isDirCreated = false;
				
			
				String dirPath = path + File.separator
						+ System.currentTimeMillis() + File.separator
						+ "images";
				File dir = new File(dirPath);
				if (!dir.exists()) {
					isDirCreated = dir.mkdirs();
				}
				System.out.println("dirPath************************="+dirPath);
				if (isDirCreated) {
 
					fileName = dirPath + File.separator + fileName;
					 UrbanLifeUtils.writeFile(bytes, fileName);
				}

				System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return fileName;

	}
	public static Date getCurrentdate() {
	 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
	    Date date= new Date();  
	    String dateString = formatter.format(date);
	    Date date2 =null;
	    try {
	        date2 = formatter.parse(dateString);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
		return date2;
	}
	 public static String getUserName(HttpServletRequest request ){
		 HttpSession session = request.getSession();
		 String userName = (String) session.getAttribute(UrbanLifeConstants.USERNAME);
		return userName;
		 
	 }
	 
	 public static void clearTemp() {
		String  SRC_FOLDER  = getPath("TEMP_PHOTO_PATH");
	    	File directory = new File(SRC_FOLDER);
	 
	    	//make sure directory exists
	    	if(!directory.exists()){
	 
	           System.out.println("Directory does not exist.");
	           System.exit(0);
	 
	        }else{
	 
	           try{
	 
	               delete(directory);
	 
	           }catch(IOException e){
	               e.printStackTrace();
	               System.exit(0);
	           }
	        }
	 }
	 
	 private static void delete(File file)
		    	throws IOException{
		 
		    	if(file.isDirectory()){
		 
		    		//directory is empty, then delete it
		    		if(file.list().length==0){
		 
		    		   file.delete();
		    		   System.out.println("Directory is deleted : " 
		                                                 + file.getAbsolutePath());
		 
		    		}else{
		 
		    		   //list all the directory contents
		        	   String files[] = file.list();
		 
		        	   for (String temp : files) {
		        	      //construct the file structure
		        	      File fileDelete = new File(file, temp);
		 
		        	      //recursive delete
		        	     delete(fileDelete);
		        	   }
		 
		        	   //check the directory again, if empty then delete it
		        	   if(file.list().length==0){
		           	     file.delete();
		        	     System.out.println("Directory is deleted : " 
		                                                  + file.getAbsolutePath());
		        	   }
		    		}
		 
		    	}else{
		    		//if file, then delete it
		    		file.delete();
		    		System.out.println("File is deleted : " + file.getAbsolutePath());
		    	}
		    }
	 public static void setServerPath(String path){
		 SERVER_PATH = path;
		
		 
	 }
}
