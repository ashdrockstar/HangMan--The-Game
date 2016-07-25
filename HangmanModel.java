package HW11_3;

/**
 * A Program to Implement a 4 player game using multiple threads using the MVC design pattern. 
 * Each player sits on a different computer.
 * @author Aishwary Pramanik
 * @author Shashank Gangadhara
 * @version 3.0 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

// Model to perform internal functions for the Hangman Game
public class HangmanModel {

	static int chance;
	static String guess;

	// Method to check whether a character already exists
	static int CharExists(String guess,char guess_character)
	{
		int index;
		for(index=0;index<guess.length();index++)
		{
			// if repeated character is given as input then 0 points
			if(guess.charAt(index)==guess_character)
				return 0;
		}

		return 10;

	}
	// This method generates a random word from a given file
	static String RandomWord(String filename)
	{
		File input_file=new File(filename);
		int no_of_lines=0;
		int position=0;
		int line_no;
		String word="";
		Scanner scanner;
		try {
			scanner = new Scanner(input_file);

			// to generate random line number
			Random random_no=new Random();

			while(scanner.hasNextLine())
			{
				no_of_lines++;
				scanner.nextLine();
			}
			scanner.close();
			scanner=new Scanner(input_file);
			while(position==0)
				position=random_no.nextInt(no_of_lines+1) ;
			line_no=0;
			while(scanner.hasNextLine() && line_no!=position)
			{
				word=scanner.nextLine();
				line_no++;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!!");

		}
		return word;

	}

	// This method starts the game

	static int StartGame(String word,char char_guessed)
	{
		//		int counter;
		//		int blank_count=guess.length();
		int index=0;
		int score=0;
		int flag=0;


		// loop to show blanks according to word length

		//		blank_count=0;
		//		for(counter=0;counter<guess.length();counter++)
		//		{
		//			if(guess.charAt(counter)=='_')
		//				blank_count++;
		//		}
		//		//				if(blank_count==0)
		//		//					break;

		if(char_guessed=='/')
			return chance;
		System.out.println();
		index=0;
		flag=0;
		while(index<word.length())
		{
			if(word.charAt(index)==char_guessed)
			{
				flag=1;
				score=score+CharExists(guess, char_guessed);
				guess=guess.substring(0,index)+char_guessed+
						guess.substring(index+1);
			}
			index++;
		}
		if(flag==0)
		{
			System.out.println("Wrong alphabet!!\n");
			score=score-5;
			chance++;
			//ShowHangman(chance);
		}
		System.out.println("Chance(s) Left: "+(8-chance));
		index=0;
		System.out.print("PLAYER GUESS: ");
		while(index<guess.length())
		{
			System.out.print(guess.charAt(index)+" ");
			index++;
		}


		return chance;

	}

}
