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
import java.net.ServerSocket;
import java.net.Socket;

// Controller to control the flow of Hangman Game
public class HangmanController extends Thread{

	static String word;
	static String guess="";
	static String char_guessed;
	static int chance;
	final int MAX_Players=2;
	String clientId;
	Socket socket=null;
	static Object Lock=new Object();
	static int max_id;
	static int current_id=1;
	int myId;
	static String current_player;
	// Constructor to initialize the objects
	public HangmanController(Socket s,int id) {
		this.socket=s;
		this.myId=id;
	}

	// Main method, Program execution starts over here
	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket=new ServerSocket(3435);
		word=HangmanModel.RandomWord("input_test.txt");
		for(int index=0;index<word.length();index++)
			guess=guess+"_";
		System.out.println("\n-----------------------------------------");
		System.out.println("WORD: "+word);
		System.out.println("\n-----------------------------------------");
		HangmanModel.guess=guess;
		while(true)
		{
			max_id++;
			Socket socket=serverSocket.accept();
			new HangmanController(socket,max_id).start();
			System.out.println("Connection Established! ");
		}

	}

	// Thread run, to provide client connection and play the game
	@Override
	public void run() {
		try
		{

			synchronized (Lock) {
				if(this.myId!=current_id)
					Lock.wait();

				DataInputStream in=new DataInputStream(socket.getInputStream());
				DataOutputStream out=new DataOutputStream(socket.getOutputStream());
				this.clientId=in.readUTF();
				System.out.println("Server Connected to: "+this.clientId);


				do
				{
					System.out.println("\n-------------------------------------"
							+ "----");
					out.writeUTF(guess);

					char_guessed=in.readUTF();
					System.out.println("Player: "+this.clientId);
					System.out.println("Char Tried: "+char_guessed);
					chance=HangmanModel.StartGame(word, char_guessed.charAt(0));
					guess=HangmanModel.guess;
					out.writeUTF(guess);
					out.writeInt(chance);
					System.out.println("\n------------------------------------"
							+ "-----");
					System.out.println(Thread.activeCount());
					current_id=(current_id%max_id)+1;
					System.out.println(current_id);
					current_player=this.clientId;
					Lock.notifyAll();
					Lock.wait();
				}while(chance<8 && hasBlank(guess));

				if(chance!=8)
				{
					System.out.println("\nPlayer "+current_player+" Won!!");
					System.out.println("Word: "+guess);
				}
				else
				{
					System.out.println("Players LOST!!");
				}
				socket.close();
				System.exit(0);
			}


		}
		catch(Exception e)
		{

		}

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

}
