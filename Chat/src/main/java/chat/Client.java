package chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Client {
	 private Socket socket;
	 private BufferedReader buffReader;
	 private BufferedWriter buffWriter;
	 private String name;
         private String messages;
	 public Client(Socket socket, String name){
		 try{
			 this.socket = socket;
			 this.buffWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			 this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 this.name = name;
                         this.messages=name;
                         buffWriter.write(name);
			 buffWriter.newLine();
			 buffWriter.flush();
		 }
		 catch (IOException e){
			 closeAll(socket, buffReader, buffWriter);
		 }
	 }
	 public void sendMessage(String message){
		 try{
			 
		 
			 if(socket.isConnected()){
				 buffWriter.write(name + ": " + message);
				 buffWriter.newLine();
				 buffWriter.flush();
			 	}
		 	
		 } 
		 catch(IOException e){
			 closeAll(socket, buffReader, buffWriter);
		 }
	 }
//	 public void readMessage(){
//		 new Thread( new Runnable() {
//			 @Override
//			 public void run() {
//				 String msfFromGroupChat;
//				 while(socket.isConnected()){
//					 try{
//						 msfFromGroupChat = this.readBuff();
//						 System.out.println(msfFromGroupChat);
//					 } 
//					 catch (IOException e){
//						 closeAll(socket, buffReader, buffWriter);
//					 }
//				 }
//			 }
//		 }).start();
//	 }
         public String readBuff(){
             String statement = null;
             try {
                 statement= buffReader.readLine();
             } catch (IOException ex) {
                 Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
             }
             return statement;
         }
	 public void closeAll(Socket socket, BufferedReader buffReader, BufferedWriter buffWriter){
		 try{
			 if(buffReader!= null){
			 	
			 }
			 if(buffWriter != null){
				 buffWriter.close();
			 }
			 if(socket != null){
				 socket.close();
			 }
		 } catch (IOException e){
			 e.getStackTrace();
		 }
	 }
         public String getName(){
             return name;
         }
//	 public static void main(String[] args) throws UnknownHostException, IOException{
//                         Scanner sc = new Scanner(System.in); 
//			 System.out.println("Enter your name: ");
//			 String name = sc.nextLine();
//			 Socket socket = new Socket("localhost", 1234);
//			 Client client = new Client(socket, name);
//			 client.readMessage();
//                         new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    while(true)
//                                     client.sendMessage(sc.nextLine());
//                                }
//                           }).start();
//			 
//		 
//	 }

}
