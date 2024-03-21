package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
public class ClientHandler implements Runnable{
	public static ArrayList<ClientHandler>clientHandlers = new ArrayList<>();
	public Socket socket;
	private BufferedReader buffReader;
	private BufferedWriter buffWriter;
	private String name;
        String nameList;
        public String listingClient(){
            nameList="";
            for (int i=0;i<clientHandlers.size();i++){
                nameList+=clientHandlers.get(i).getName()+"\n";
            }
            return nameList;
        }
        public int numberOfClients(){
            return clientHandlers.size();
        }
	public ClientHandler(Socket socket){
		try{
			this.socket = socket;
			this.buffWriter = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
			this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.name = buffReader.readLine();
			clientHandlers.add(this);
			boradcastMessage("SERVER: " + name + " has entered in the room");
		} catch(IOException e){
			closeAll(socket, buffReader, buffWriter);
		}
}
 @Override
 public void run() {
	 String messageFromClient;
	 while(socket.isConnected()){
	 try{
		 messageFromClient = buffReader.readLine();
		 boradcastMessage(messageFromClient);
	 } catch(IOException e){
		 closeAll(socket, buffReader, buffWriter);
		 break;
	 }
 }
 }
 public void boradcastMessage(String messageToSend){
	 for(ClientHandler clientHandler: clientHandlers){
	 try{
		 if(!clientHandler.name.equals(name)){
			 clientHandler.buffWriter.write(messageToSend);
			 clientHandler.buffWriter.newLine();
			 clientHandler.buffWriter.flush();
		 }
	 } catch(IOException e){
	 closeAll(socket,buffReader, buffWriter);
	 	}
	 }
 }
 public void removeClientHandler(){
	 clientHandlers.remove(this);
	 boradcastMessage("SERVER " + name + " has gone");
 }
 public void closeAll(Socket socket, BufferedReader buffReader, BufferedWriter buffWriter){
	 removeClientHandler();
	 try{
	 if(buffReader!= null){
		 buffReader.close();
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
}
