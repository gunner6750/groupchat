package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
	 private ServerSocket serverSocket;
         public Server(){
         }
	 public Server(ServerSocket serverSocket){
		 this.serverSocket = serverSocket;
	 }
         public void setServerSocket(ServerSocket serverSocket){
                this.serverSocket = serverSocket;
         }

    /**
     *
     */
    public void serverStart() {
		 try{
			 while(!serverSocket.isClosed()){
				 Socket socket = serverSocket.accept();
				 System.out.println("New Friend Connected");
				 ClientHandler clientHandler = new ClientHandler(socket);
                                 
				 Thread thread = new Thread(clientHandler);
				 thread.start();
			 }
		 } 
		 catch (IOException e){
		 }
	 };
	 public void closerServer(){
		 try{
		 if(serverSocket != null){
			 serverSocket.close();
                         serverSocket=null;
		 }
	 } 
		 catch(IOException e){
			 e.printStackTrace();
		 }
	 }
//	 public static void main(String[] args) throws Exception {
//		 ServerSocket serverSocket = new ServerSocket(1234);
//		 Server server = new Server(serverSocket);
//		 server.serverStart();
//	 }
}