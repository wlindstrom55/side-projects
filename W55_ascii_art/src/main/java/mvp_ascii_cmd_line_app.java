
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.lang3.math.NumberUtils;
import org.imgscalr.Scalr;

public class mvp_ascii_cmd_line_app {
	
//Locally-stored picture choices hard-coded here:
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\samplepic.jpeg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\Ascii1_bird.png";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\chloeTeeth.JPG";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\eye.jpg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\640x640fast-furious.jpeg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\deathstar.jpg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\fishpic.jpg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\headshot.JPG";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\spaceship.png";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\samatmerrit.jpg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\flyfishing.jpg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\flyfishing2.jpg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\flag.jpg";
	static String filePath = "C:\\Users\\wlind\\Pictures\\cat.jpg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\capitol.jpg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\cap.jpg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\pineapple.jpg";
	//private static String filePath = "C:\\Users\\wlind\\Pictures\\eldenring.jpg";
	
	private static String algorithmChoice = "Luminosity";
	//"Average" || "Max/Min" || "Luminosity"
	
	//changing these allowedAscii string inverts the brightness/darkness of the image.
		//private static String allowedAscii = " `^\",:;Il!i~+_-?][}{1)(|\'/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
		private static String allowedAscii = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\'|()1{}[]?-_+~i!lI;:,\"^` ";
	
	public static void main(String[] args) throws Exception {
		BufferedImage loadedImage = loadImage(filePath);
		System.out.println("Image successfully loaded!");
		System.out.println("Image size: " + loadedImage.getWidth() +
				" x " + loadedImage.getHeight());
	//TEST - display the initial image!
		//displayBI(initialImage);
	//rotate image
		//BufferedImage rotatedImage = rotateImage(initialImage);
	//resize image
		BufferedImage resizedImage = resizeImage(loadedImage);
		System.out.println("Image resized!");
		System.out.println("Image size: " + resizedImage.getWidth() +
		" x " + resizedImage.getHeight());
	//TEST - display the resized image!
		//displayBI(resizedImage);
	//turn resized image into brightness matrix 2d int array
		int[][] bMatrix = createBrightnessMatrix(resizedImage);
		System.out.println("Brightness matrix compiled!");
		System.out.println("BMatrix is: " + bMatrix.length + "x" + bMatrix[0].length);
	//create the ascii matrix for printing...
		System.out.println("Creating the final result...");
		String finalResult = createAsciiMatrix(bMatrix, allowedAscii);
		System.out.println(finalResult);
	} //end main method
	
public static BufferedImage loadImage(String filePath) throws IOException {
		File input = new File(filePath); 
		return ImageIO.read(input);
}
public static BufferedImage rotateImage(BufferedImage bImage) {
	BufferedImage rotated = Scalr.rotate(bImage, Scalr.Rotation.CW_90, Scalr.OP_ANTIALIAS);
	return rotated;
}
private static BufferedImage resizeImage(BufferedImage bImage) { 
	BufferedImage thumbnail = Scalr.resize(bImage, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
		320, 320, Scalr.OP_ANTIALIAS); 
	//80x80 seems to work well for smaller size images. Then 160x160, 320x320
	//Corresponding console width is 240, 480, 960
	return thumbnail;
}

public static int[][] createBrightnessMatrix(BufferedImage resizedInput) throws Exception {
	int width = resizedInput.getWidth(); 
    int height = resizedInput.getHeight();
	int[][] pixels = new int[width][height];
	
	for(int i=0; i < width; i++) {
        for(int j=0; j < height; j++) {
        	Color c = new Color(resizedInput.getRGB(i, j));
        	if (algorithmChoice == "Average") {
        	pixels[i][j] = ((c.getRed() + c.getGreen() + c.getBlue()) / 3);
        	}
        	else if (algorithmChoice == "Max/Min") {
        	pixels[i][j] = (NumberUtils.max(c.getRed(), c.getGreen(), c.getBlue()) +
        			NumberUtils.min(c.getRed(), c.getGreen(), c.getBlue())) / 2; 
        	}
        	else if (algorithmChoice == "Luminosity") { 
        	pixels[i][j] = (int) ((0.21 * c.getRed()) + (0.72 * c.getGreen()) + (0.07 * c.getBlue()));	
        	}
        	else {
        		throw new Exception("Please make sure the brightness algorithm selection is appropriate.");
        	}
        }
       }
	return pixels;
}

public static String createAsciiMatrix(int[][] input, String asciiChars) {
	String asciiTxt = "";
	int widthArr = input.length;
    int heightArr = input[0].length;
    //int[][] output = new int[widthArr][heightArr]; //for write to file method
    
    //these for loops traverse the image backwards so that it saves in the string correctly
    for (int i = 0; i < heightArr; i ++) {  
		for( int e = 0; e < widthArr; e++) {
			for(int p = 0; p < 3; p++ ) { //adds the char 3 times. ADJUST CONSOLE SIZE
			asciiTxt = asciiTxt + (asciiChars.charAt(((int)((float)input[e][i]/255 * (asciiChars.length() - 1)))));
			//output[i][e] = (asciiChars.charAt(((int)((float)input[i][e]/255 * (asciiChars.length() - 1)))));
		}}}
	return asciiTxt;
}

public static void writeToFile (int[][] input) { //diagnostic method for writing output to text file - didn't work great
	try {
		FileWriter myWriter = new FileWriter("C:\\Users\\wlind\\Pictures\\file.txt");
		//BufferedWriter bw = new BufferedWriter(myWriter);
		for (int i = 0; i < input.length; i++) {
			for (int p = 0; p < input[0].length; p++) {
				myWriter.write(input[i][p]);
			}//is this working? it is still writing it out at as a string.
		} //needs some kind of new line character?
		myWriter.close();
		System.out.println("Successfully wrote to file.");
	} catch (IOException e) {
		System.out.println("An error occurred.");
		e.printStackTrace();
	}
}

public static void calculateWH(BufferedImage input) {
	
	
}
public static void displayBI(BufferedImage input) { //diagnostic method...creates Jframe window to show BI orientation
	JFrame frame = new JFrame();
	frame.getContentPane().setLayout(new FlowLayout());
	frame.getContentPane().add(new JLabel(new ImageIcon(input)));
	//frame.getContentPane().add(new JLabel(new ImageIcon(img2)));
	//frame.getContentPane().add(new JLabel(new ImageIcon(img3)));
	frame.pack(); //resizes the frame so its contents are at or above preferred sizes
	frame.setVisible(true); //shows or hides this window depending on boolean parameter
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //allows the top-right x
}
	
}
