package hotel.management;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
class ServerClientThread extends Thread {
  Socket serverClient;
  int clientNo;
  int squre;
  ServerClientThread(Socket inSocket,int counter){
    serverClient = inSocket;
    clientNo=counter;
  }
  public void run(){
    try{
      DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
      DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
      String clientMessage="", serverMessage="";
      int number = 10;
      while(!clientMessage.equals("bye")){
        clientMessage=inStream.readUTF();
        System.out.println("From Client-" +clientNo+ ": Number is :"+clientMessage);
        number = number - 1;
        serverMessage="From Server to Client-" + clientNo + " now the number is:" +number;
        outStream.writeUTF(serverMessage);
        outStream.flush();
      }
      inStream.close();
      outStream.close();
      serverClient.close();
    } catch (IOException ex) {
          Logger.getLogger(ServerClientThread.class.getName()).log(Level.SEVERE, null, ex);
      }finally{
      System.out.println("Client -" + clientNo + " exit!! ");
    }
  }
}
