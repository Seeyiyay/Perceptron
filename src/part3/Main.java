package part3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
//		File imageFile = new File(args[0]);
		File imageFile = new File("data/image.data");

		File outputFile = new File ("Output.txt");
		ArrayList<Image> imageList = loadImages(imageFile, outputFile, true);
		ArrayList<Image> originalImages = loadImages(imageFile, outputFile, true);
		new Algorithm(imageList, originalImages, outputFile);

	}

	public static ArrayList<Image> loadImages(File imageFile, File outputFile, boolean toWrite){
	    try{
    		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
	    	if(toWrite == true) {
				writer.write("Program run on +" + new Date() + "\n");
				writer.write("Original image values:" + "\n");
				writer.write("Image Id" + "\t" + "Image Class" + "\n");
	    	}
	    	ArrayList<Image> imageList = new ArrayList<Image>();
	    	java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
	    	Scanner f = new Scanner(imageFile);
	    	int id = 1;
	    	while (f.hasNextLine() == true) {
		    	int[][] newImage = null;
		    	//if (!f.nextLine().equals("P1")){} //System.out.println("Not a P1 PBM file" );
		    	String P1 = f.next();
		    	String classifier = f.next();

		    	int rows = 0;
		    	int cols = 0;
		    	if(f.hasNextInt()){
			    	rows = f.nextInt();
			    	cols = f.nextInt();
		    	}
		    	newImage = new int[rows][cols];
		    	for (int r=0; r<rows; r++){
		    		for (int c=0; c<cols; c++){
		    			newImage[r][c] = (Integer.parseInt(f.findWithinHorizon(bit,0)));
		    		}
		    	}

		    	imageList.add(new Image(id, rows, cols, newImage, classifier));
		    	id ++;
	    	}
	    	if(toWrite == true) {
				for(Image i : imageList) {
					writer.write(i.id + "\t" + i.classifier + "\n");
				}
	    	}
	    	writer.close();
	    	f.close();
	    	return imageList;
	    }
	    catch(IOException e) {
	    	System.out.println("Load from file failed");
	    }
		return null;
	}
}
