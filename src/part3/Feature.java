package part3;

import java.util.Random;

public class Feature {

	int id;
	public int[] row = new int[10];
	public int[] col = new int [10];
	public int[] sgn = new int[4];
	public boolean isDummy;
	public double featureValue = 0;
	public double weightValue;

	public Feature(int id, boolean isDummy) {
		this.id = id;
		Random random = new Random(id);
		for(int i = 0; i < row.length; i++) {
			row[i] = random.nextInt(10);
		}
		for(int i = 0; i < col.length; i++) {
			col[i] = random.nextInt(10);
		}
		for(int i = 0; i < sgn.length; i++) {
			sgn[i] = random.nextInt(2);
		}
		this.isDummy = isDummy;
		this.weightValue = random.nextDouble();
	}

	public double getFeatureValue(int image[][]) {
		if(isDummy == true) {
			return 1;
		}
		else {
			int sum=0;
			for(int i=0; i < 4; i++)
			if (image[this.row[i]][this.col[i]] == this.sgn[i]) {
				sum++;
			}
			return (sum>=3)?1:0;
		}
	}
}
