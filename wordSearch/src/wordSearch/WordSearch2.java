package wordSearch;

import java.util.Arrays;
import java.util.Scanner;

/* Cassidy Williams newsletter practice example 2/7/2022
Implement a word search. Given a 2D array of letters and a word to find,
return the 2D array with the found word’s letters replaced with an asterisk (*).

Example:

let grid = [['a','a','q','t'],
            ['x','c','w','e'],
            ['r','l','e','p']]

$ findWord(grid, 'ace')
$ [['*','a','q','t'],
   ['x','*','w','e'],
   ['r','l','*','p']]
*/
public class WordSearch2 {
	//int[][] coordinates = new int[3][4]; //this is what our enum will traverse?
	
	//these are constants which represent the side of our grid
	static final int r = 3;
	static final int c = 4;
	
//	public static char[][] findWord(char[][] input, String cancelWord) {
//		for(int i = 0; i < input.length; i++) {
//			for(int e = 0; e < input[i].length; e++) {
//				if(cancelWord.charAt(i) == input[i][e]) {
//					input[i][e] = '*'; //changes element at index of loop to asterisk
//				}
//			}
//		}
//		return input;
//	}
	
	public static boolean findMatch(char[][]input, String word, int x, int y,
			int nrow, int ncol, int level) {
		
		//int l = word.length(); //index
		
		// pattern matcher
		if(level == 1) {
			return true;
		}
		
		//OOB
		if (x < 0 || y < 0 || x >= nrow || y >= ncol) {
			return false;
		}
		
		//if grid matches with a letter
		if (input[x][y] == word.charAt(level)) {
			//marks this cell as visited
			char temp = input[x][y];
			input[x][y] = '#';
			
			//finds subpattern in 4 directions TODO: make if statements instead? then saves coords
												//and another method to change coords?
			boolean res = findMatch(input, word, x - 1, y, nrow, ncol, level + 1) |
					findMatch(input, word, x + 1, y, nrow, ncol, level + 1) |
					findMatch(input, word, x, y - 1, nrow, ncol, level + 1) |
					findMatch(input, word, x, y + 1, nrow, ncol, level + 1);
			
			//marking this cell as unvisited again
			input[x][y] = temp;
			return res;
			}
		else {
			return false;
		}
	}//end method
	
	public static char[][] checkMatch (char[][] input, String word, int nrow, int ncol) {
		
			//index
			int l = word.length();
			
			//if total characters in matrix is < String length:
			if( l > nrow * ncol) {
				return input;
				//return false;
			}
			
			//traversing the grid and changing matching coordinates
			//TODO: needs a way to stop from changing multiple letters in a row
			for(int i = 0; i < nrow; i++) {
				for(int e = 0; e < ncol; e++) {
					if(input[i][e] == word.charAt(i)) {
			
						//0 here is our starting level
						//i and e represent x and y in findMatch()
						if(findMatch(input, word, i, e, nrow, ncol, 0)) {  //TODO: could have another method that saves word/positions from findmatch
							//return true;
							input[i][e] = '*';
							}
						else {
							continue;
						}
				}
			}
		}
			return input;
	}//end method
	
	public static void main(String[] args) { //Driver Code
		
		char[][] grid = {
				{'a','a','q','t'},
				{'x','c','w','e'},
				{'r','l','e','p'}
		}; //end of grid
		
		System.out.println("Below you will see the starting contents of our grid:");	
		System.out.println(Arrays.deepToString(grid)); 
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter in a 3-letter String: ");
		String word = scanner.nextLine();
		//char[] charWord = word.toCharArray();
		
		char[][] answer = checkMatch(grid, word, r, c);
		
		System.out.println("And here is the answer!: ");
		System.out.println(Arrays.deepToString(answer));
		} //end main method
}
