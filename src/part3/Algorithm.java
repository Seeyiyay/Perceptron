package part3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Algorithm {

	public ArrayList<Image> images;
	public ArrayList<Image> originalImages;

	public Algorithm(ArrayList<Image> images, ArrayList<Image> originalImages, File outputFile) {
		this.images = images;
		this.originalImages = originalImages;
		executeAlgorithm(outputFile);
	}

	public void executeAlgorithm(File outputFile) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true));
			writer.write("============\n");
			writer.write("Running of the algorithm:" + "\n");

			ArrayList<Feature> features = new ArrayList<Feature>();
			Feature dummyFeature = new Feature(1, true);
			features.add(dummyFeature);
			for(int i = 0; i < 50; i++) {
				features.add(new Feature(i + 2, false));
			}
			//Add feature values
			for(Feature f : features) {
				for(Image i : images) {
					i.features.add(f);
				}
			}
			for(Image i : images) {
				for(Feature f : i.features) {
					f.featureValue = f.getFeatureValue(i.coords);
				}
			}
			//Main iteration
			int itrCount = 0;
			while(itrCount<100) {
				writer.write("Iteration number: " + (itrCount + 1) + "\n");
				for(Image i : images) {
					double totalWeight = 0;
					for(Feature f : i.features) {
						totalWeight = totalWeight + (f.weightValue * f.getFeatureValue(i.coords));
					}
					if(totalWeight > 1) {
						System.out.println("Greater than zero: " + totalWeight +"    Therefore #Yes");
						i.classifier = "#Yes";
					}
					else {
						System.out.println("Less than zero: " + totalWeight +"    Therefore #other");
						i.classifier = "#other";
					}
				}
				int correctTally = 0;
				System.out.println("====================NewIteration===========================");
				for(Image i : images) {
					System.out.println("Original: " + originalImages.get(i.id-1).classifier + "Changed: " + i.classifier);
					if(isCorrect(i)) {
						System.out.println("Class correct");
						correctTally ++;
					}
					else if(i.classifier.equals("#Yes")) {
						System.out.println("Adjusting weights negatively");
						for(Feature f : i.features) {
							double currentFeatureValue = f.getFeatureValue(i.coords);
							//System.out.println("Current: " + f.weightValue);
							f.weightValue = f.weightValue - (currentFeatureValue * 0.1);
							//System.out.println("New: " + f.weightValue);
						}
					}
					else {
						System.out.println("Adjusting weights positively");
						for(Feature f : i.features) {
							double currentFeatureValue = f.getFeatureValue(i.coords);
							//System.out.println("Current: " + f.weightValue);
							f.weightValue = f.weightValue + (currentFeatureValue * 0.1);
							//System.out.println("New: " + f.weightValue);
						}
					}
				}
				System.out.println("Itr Count: " + itrCount);
				System.out.println("Correct Tally: " + correctTally);
				writer.write("Number correct: " + correctTally + " out of " + images.size() + "\n");
				itrCount ++;
			}
			writer.write("============\n");
			writer.write("Final image values:" + "\n");
			writer.write("Image Id" + "\t" + "Image Class" + "\n");
			for(Image i : images) {
				writer.write(i.id + "\t" + i.classifier + "\n");
			}
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isCorrect(Image image) {
		if(originalImages.get(image.id - 1).classifier.equals(image.classifier)) {
			return true;
		}
		else {
			return false;
		}
	}
}
