package TCPClientobject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Scanner;



import Message.RequestMsg;
import Message.ResponseMsg;
import Message.Message;
import Message.Message.Header;
import Message.Message.Sender;

public class HouseCommittee extends Person  implements Runnable{
	/**
	 * 
	 */
	private enum options{LOG_OUT}//TODO Fill all options
	private static final long serialVersionUID = 1L;
	public int seniority;
	private String userName;
	private String password;
	private boolean exit;
	int sumOfpayments;
	//Data bases for chache data:
	

	public HouseCommittee(String userName, String password){
		this.setPassword(password);
		this.setUserName(userName);
		this.exit=false;
	}

	public int getSeniority() {
		return this.seniority;
	}

	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void run(){

		try {

			Socket clientSocket = new Socket("localhost", 10000); 		
			ObjectOutputStream  outToServer = new ObjectOutputStream (clientSocket.getOutputStream() );
			ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream() );

			
			//Scanner sc=new Scanner(System.in);
			while (!userExist(outToServer,inFromServer)){				
				BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
				System.out.println("write your username:");
				this.userName=br.readLine();
				System.out.println("write your password:");
				this.password=br.readLine();
				br.close();
			}

			
			//start exchanging data with the server
			while (!exit){

				displayMenu();
				//options.options option=options.values()[sc.nextInt()] ;
				options option=options.LOG_OUT;
				switch (option){
				case LOG_OUT: //payments
					logOut(outToServer, inFromServer);
					clientSocket.close();
					break;
					//send

				}					
			}
			


		}
		catch (IOException | ClassNotFoundException e) {
			System.out.println("Cannot connect to port 1000");
		}

		//outToServer.writeObject();	

	}

	private void displayMenu() {
		System.out.println("Choose one of the foloowing options:");
		System.out.println("1. LogOut");
		System.out.println("2. Get Monthly Payments of a practicular tenant");
		System.out.println("3. Get Monthly Revenue of a practicular tenant");
		//TODO: Complete all menu options!

	}
	private void logOut(ObjectOutputStream outToServer, ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
		Message msg=new RequestMsg(Header.LOGOUT, Sender.COMMITTEE, null); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		exit=true;
		
		
	}

	private boolean userExist(ObjectOutputStream outToServer, ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
		ArrayList<String> args=new ArrayList<String>(Arrays.asList(this.userName,this.password));
		Message msg=new RequestMsg(Header.LOGIN, Sender.COMMITTEE, args); //Message (instead of RequestMsg) for Possible future abstraction usage on serverSide
		outToServer.writeObject(msg);
		ResponseMsg response=(ResponseMsg)inFromServer.readObject();
		System.out.println(response.getMsgInfo());
		return response.isSucceed();


	}

}
