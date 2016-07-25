package HW11_3;

/**
 * A Program to Implement a 4 player game using multiple threads using the MVC design pattern. 
 * Each player sits on a different computer.
 * @author Aishwary Pramanik
 * @author Shashank Gangadhara
 * @version 3.0 
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

// View to interact with players sitting on different machines
public class HangmanView {
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	static int chance;
	int myId;

	// Main method, Program execution starts here
	public static void main(String[] args) throws UnknownHostException, 
	IOException {
		socket=new Socket("192.168.0.18",3435);
		DataInputStream in=new DataInputStream(socket.getInputStream());
		DataOutputStream out=new DataOutputStream(socket.getOutputStream());
		Scanner scanner=new Scanner(System.in);
		String guess = null;
		String char_guessed;
		int index;

		out.writeUTF(InetAddress.getLocalHost().getHostAddress());
		try{
		do
		{
			guess=in.readUTF();
			System.out.println("\n----------------------------------");
			System.out.print("Word before Attempt: ");
			for(index=0;index<guess.length();index++)
				System.out.print(guess.charAt(index)+" ");
			System.out.print("\nEnter a character(Press '/' to get updates"
					+ " by other players): ");
			char_guessed=scanner.next();
			out.writeUTF(char_guessed);
			guess=in.readUTF();
			System.out.print("Word after Attempt: ");
			for(index=0;index<guess.length();index++)
				System.out.print(guess.charAt(index)+" ");
			System.out.println();
			chance=in.readInt();
			ShowHangman(chance);
			System.out.println("Chances Left: "+(8-chance));
			System.out.println("----------------------------------");
			System.out.println("Waiting for other Player");

		}while(hasBlank(guess) && chance<8);
		if(chance!=8)
		{
			System.out.println("You Won!!");
			System.out.println("Word: "+guess);
		}
		else
		{
			System.out.println("YOU LOST");
		}
		}
		catch(Exception e)
		{
			
		}
		socket.close();
		scanner.close();
		System.exit(0);
		
	}

	// Method to check the ending condition
	static boolean hasBlank(String guess)
	{
		int index;
		for(index=0;index<guess.length();index++)
		{
			if(guess.charAt(index)=='_')
				break;
		}
		if(index==guess.length())
			return false;
		else
			return true;
	}
	// This method displays the current Hangman status
	static void ShowHangman(int chance)
	{
		if(chance!=0)
			System.out.println("HANGMAN:");
		// graphical representation of Hangman
		switch(chance)
		{
		case 1:
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			break;
		case 2:
			System.out.println("# - -");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			break;
		case 3:

			System.out.println("# - - - -");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			break;
		case 4:

			System.out.println("# - - - -");
			System.out.println("#    !");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			break;
		case 5:

			System.out.println("# - - - -");
			System.out.println("#    !");
			System.out.println("#    O");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			break;
		case 6:

			System.out.println("# - - - -");
			System.out.println("#    !");
			System.out.println("#    O");
			System.out.println("#  -- --");
			System.out.println("#");
			System.out.println("#");
			System.out.println("#");
			break;
		case 7:

			System.out.println("# - - - -");
			System.out.println("#    !");
			System.out.println("#    O");
			System.out.println("#  -- --");
			System.out.println("#    |");
			System.out.println("#");
			System.out.println("#");
			break;
		case 8:
			System.out.println("# - - - -");
			System.out.println("#    !");
			System.out.println("#    O");
			System.out.println("#  -- --");
			System.out.println("#    |");
			System.out.println("#   / \\");
			System.out.println("#   DEAD");

		}


	}
}
