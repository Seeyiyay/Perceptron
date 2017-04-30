package part3;

import java.util.ArrayList;

public class Image {

	public int id;
	public int cols;
	public int rows;
	public int coords[][];
	public String classifier;
	public ArrayList<Feature> features = new ArrayList<Feature>();

	public Image(int id, int cols, int rows, int coords[][], String classifier) {
		this.id = id;
		this.cols = cols;
		this.rows = rows;
		this.coords = coords;
		this.classifier = classifier;
	}

}