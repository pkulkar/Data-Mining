import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.xml.crypto.Data;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class prediction {

	public static void main(String[] args) throws IOException {	
	
	BufferedReader file;
	file=new BufferedReader(new FileReader("product_distribution_training_set.txt"));
	File output= new File("output.txt");
	output.delete();
	int lines=0;
	int tokens;
	int i=0;
	int ID;
	int j,k,l;
	int first=88;
	int last=117;

	while(file.readLine() !=null)lines++;
	file.close();
	file=new BufferedReader(new FileReader("product_distribution_training_set.txt"));
	String product=file.readLine();
	int [] inputfile=new int[118];
	double sales [] []= new double [117] [2];
	
	forecasttotal();
	
	try {

		for(i=0;i<lines;i++) {
			tokens=0;
			StringTokenizer token = new StringTokenizer(product);
			
			ID=Integer.parseInt(token.nextToken());
		
			
			while(token.hasMoreTokens()) {
				inputfile[tokens++] = Integer.parseInt(token.nextToken());
			}
			l=0;
			j=l;
			k=l;
			
			while(l<(tokens-1)) {
			sales[j++][0]=inputfile[l];
			sales[k++][1]=inputfile[l+1];
			l++;
			}
			
			
			Ar1model(sales,ID,first,last);
			product=file.readLine();
			
			
		}
		
	}
	
	
	catch(Exception Except) {
		System.out.println(Except);
		}
	
	
	}









	public static void forecasttotal() throws IOException {
		// TODO Auto-generated method stub
		BufferedReader file = new BufferedReader(new FileReader("product_distribution_training_set.txt"));
		int [] inputfile=new int [118];
		int [] total=new int[118];
		int i=0;
		int j,tokens;
		while(i<29) {
			total[i]=0;
		i++;
		}
		i=0;
		double [] [] sales= new double[118][2];
		String product=file.readLine();
		
		try {
			while(product != null)
			{
					tokens=0;
					StringTokenizer token = new StringTokenizer(product);
					Integer.parseInt(token.nextToken());

					while(token.hasMoreTokens()) {
							inputfile[tokens++] = Integer.parseInt(token.nextToken());
					}
					j=0;
					i=0;
					while(i<(tokens))
					{
						total[j++] += inputfile[i];
						i++;
					}
				product = file.readLine();

		
			}
		}
		catch(Exception except) {
			System.out.println(except);
		}
		file.close();
		int k=0;
		while(k<(total.length-1)) {
			sales[k][0]=total[k];
			sales[k][1]=total[k+1];
			k++;
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
		writer.append(Integer.toString(0));
		Ar1model(sales,0,88,117);
		
	}









	public static void Ar1model(double[][] product,int ID,int startpred,int lastpred) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
		writer.append(Integer.toString(ID));
		
		SimpleRegression training=new SimpleRegression();
		training.addData(product);
		int i=0;
		for(i=startpred;i<lastpred;i++) {
			double value=training.predict(product[i][1]);
			writer.append("\t");
			writer.append(Integer.toString((int)Math.floor(value)));

		}
		
		writer.append(System.lineSeparator());
		writer.close();
	}

}
