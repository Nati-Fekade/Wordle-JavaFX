import java.util.Random;
import java.util.Scanner;
/**
 * Console implementation
 * 
 * Takes in the Words ArrayList
 * Creates a scanner to take in user guess input
 * Create a guess counter
 * if the is not the word, then checks each character:
 * if guess does not have the same length, notify !!!
 * Y if it is placed correctly, X if the char does not exist, C if it is close
 * if the guess is the word, then congratulation
 * 
 * @author Nathnael Fekade
 */
public class WordlePractice {
	public static void main(String[] args) {
		//String word = Words.list.get((int) Math.random());
		String word = Words.list.get(new Random().nextInt(Words.list.size())); // this gets a random word from our word list
		//String test = "hello";
		Scanner scan = new Scanner(System.in);
		
		int guessCounter = 6;
		String guess = "";
		
		System.out.print("-----Let the game BEGIN-----"); // prompt text
		
		while (!guess.equals(word) && guessCounter > 0) { // while our guess is not the 'word' and we have guesses left
			System.out.println("\nYou have " + guessCounter + " guesses" );
			guess = scan.nextLine();
			if(guess.length() != 5) { // check for the size of the guess
				System.out.println("Must be 5 characters");
				continue;
			}
			// I did a brute force technique to match the characters of my guess and the target word
			for (int i = 0; i < word.length(); i++) {
				boolean correct = false;
				for (int j = 0; j < word.length(); j++) {
					if (guess.charAt(i) == word.charAt(i)) { // if the characters of the word and our guess match at the same index = Y
						System.out.print("Y");
						correct = true;
						break;
					}
					if (guess.charAt(i) == word.charAt(j)) { // if the character in our guess exist in the 'word' at some other index = C
						System.out.print("C");
						correct = true;
						break;
					}
				}
				if (!correct) { // if the character does not exist at all = X
					System.out.print("X");
				}
			}
			guessCounter--; // Decrement the guess counter
		}
		// Last display text.
		if (guess.equals(word)) {
			System.out.println("\nCongratulations. You found the word");
		} else {
			System.out.println("\nYou LOST. Your word is " + word);
		}
	}
}
