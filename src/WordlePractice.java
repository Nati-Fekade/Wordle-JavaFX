import java.util.Random;
import java.util.Scanner;
/**
 * Consul implementation
 * Takes in the Words ArrayList
 * Creates a scanner to take in user guess input
 * Create a guess counter
 * if the is not the word, then checks each character:
 * if guess does not have the same length, notify !!!
 * Y if it is placed correctly, X if the char does not exist, C if it is close
 * if the guess is the word, then congratulation
 * @author NATI
 */
public class WordlePractice {
	public static void main(String[] args) {
		//String word = Words.list.get((int) Math.random());
		String word = Words.list.get(new Random().nextInt(Words.list.size()));
		//String test = "hello";
		Scanner scan = new Scanner(System.in);
		int guessCounter = 6;
		
		String guess = "";
		System.out.print("-----Let the game BEGIN-----");
		while (!guess.equals(word) && guessCounter > 0) {
			System.out.println("\nYou have " + guessCounter +" guesses" );
			guess = scan.nextLine();
			if(guess.length() != 5) {
				System.out.println("Must be 5 characters");
				continue;
			}
			for (int i = 0; i < word.length(); i++) {
				boolean correct = false;
				for (int j = 0; j < word.length(); j++) {
					if (guess.charAt(i) == word.charAt(i)) {
						System.out.print("Y");
						correct = true;
						break;
					}
					if (guess.charAt(i) == word.charAt(j)) {
						System.out.print("C");
						correct = true;
						break;
					}
				}
				if (!correct) {
					System.out.print("X");
				}
			}
			guessCounter--;
		}

		if (guess.equals(word)) {
			System.out.println("\nCongratulations. You found the word");
		} else {
			System.out.println("\nYou LOST. Your word is " + word);
		}
	}
}
