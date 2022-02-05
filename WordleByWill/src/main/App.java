package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
	//Copy-cat Wordle application that I've made for fun and practice.

	//TODO could you list a whole alphabet like the keyboard in real wordle? maybe just a method 
	//TODO as it stands you can input a string larger than 5 char, probably due to the StringBuilder
	//and for loop that will only run 5 times. But, you cannot enter less than 5 char w/out an
	//ArrayIndexOutOfBounds exception.
	//TODO test if NPE works on a null return value from readDocAndPickWord()
	//TODO create side-projects repo on github, initialize side-projects file locally

	public static void main(String[] args) {
		
		boolean isGameOver = false;
		int numLives = 9;
		
		char[] chosenWord = new char[5];
		try {
			chosenWord = readDocAndPickWord().toCharArray(); //supplies a random word from our .txt
		} catch (NullPointerException e) {
			System.out.println("Something seems to have gone wrong with choosing a random word.");
		}
		
		printRules(); //prints out the brief rules
		
		Scanner scanner = new Scanner(System.in);
		
		while(isGameOver != true) {
			System.out.print("Please print a five letter word as your guess:  ");
			char[] guess = scanner.nextLine().toCharArray();
			char[] checked = guessCheck(guess, chosenWord);//sends the guess and secret word to guessCheck
			System.out.print("Your previous answer: ");
			System.out.println(checked);
			System.out.println("You now have " + numLives + " attempt(s) remaining.");
			if(Arrays.equals(checked, chosenWord) == true) { //victory condition -> your guess = the random word
				isGameOver = true;
				System.out.println("~~~~Congratulations, you win!~~~~");
			}
			if(numLives == 0) { //loss condition - run out of attempts
				isGameOver = true;
				System.out.println("Sorry, you've run out of attempts!");
				System.out.print("The correct answer was: ");
				System.out.println(chosenWord);
			}
			numLives--; //decrements the attempts for each cycle of while loop
			} //end of while loop
		scanner.close();
	}// end of main method

	public static void printRules() {
		System.out.println("Rules: You must guess a 5-letter word. If the guessed letter is not"
				+ " in the random word, you'll see a '*'.");
		System.out.println("------ If your guessed letter is in the wrong position, you'll see an '@'.");
	}

	public static char[] guessCheck(char[] guessToBeChecked, char[] actualAnswer) {
		/*
		 * This method will programmatically check each char to the corresponding position
		 * in the other char array, if equal then it should return some signal (green),
		 * if the char is not the same, but is somewhere in the rest of the actualAnswer array,
		 * then it should return some signal (yellow). And it should be grayed or x'd out 
		 */
		char[] toBeReturned = new char[5]; //this is our char array to modify and return
		
		for(int i = 0; i < 5; i++) { //this for loop will do our comparison
			String meh = null;
			meh = String.valueOf(guessToBeChecked[i]);
			StringBuilder newActualAnswer = new StringBuilder();
			newActualAnswer.append(actualAnswer);
			String newNewActualAnswer = newActualAnswer.toString();
			if(guessToBeChecked[i] == actualAnswer[i]) { //if letter is equal
				toBeReturned[i] = guessToBeChecked[i]; //saves it in a new array at the same position
			}
			if(guessToBeChecked[i] != actualAnswer[i]) { //if letter isn't equal
				if(newNewActualAnswer.contains(meh) == true) { //if actual answer contains guessToBeChecked[i]
					toBeReturned[i] = '@';
				} else {
					toBeReturned[i] = '*';
				}
			}
		} // end for loop
		return toBeReturned;
	}

	/* Breakdown of method below:
	  * This method uses a BufferedReader which reads text from a character-input
	  * stream, buffering characters so as to provide for the efficient reading of
	  * characters, arrays, and lines. In general, each read request made of a Reader
	  * causes a corresponding read request to be made of the underlying character or
	  * byte stream. It is therefore advisable to wrap a BR around any Reader whose
	  * read() operations may be costly, such as FileReaders and InputStreamReaders.
	  * -We instantiate FileReader in our BR instantiation, with the file path to our 
	  * dictionary document as its argument.
	  * -
	 */
	public static String readDocAndPickWord() {

		String holderString = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"C:\\Users\\wlind\\eclipse-sideProjects\\side-projects\\WordleByWill\\src\\resources\\dictionary"));
			String line = reader.readLine();
			List<String> lines = new ArrayList<String>();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
			Random r = new Random(); 
			// here use nextInt() on our random object. The parameter is the constraint,
			// we are specifying it so that it cannot exceed the length of our List, lines
			String randomLine = lines.get(r.nextInt(lines.size()));
			// JOptionPane.showMessageDialog(null,randomLine); //this makes a yes/no box, for testing
			return randomLine.toLowerCase(); //sets output to all lower case
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return holderString; //since randomLine is returned inside the try/catch,
		//we have to have a return outside of the try/catch as well. I'm just
		//making it a null String.
	}
	
} //end class
