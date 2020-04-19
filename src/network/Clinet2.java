package network;


import java.net.*; 
import java.io.*;
import java.util.Scanner;

public class Clinet2 
{ 
    // initialize socket and input output streams 
    private Socket socket            = null; 
    private Scanner input   = null;
    private DataInputStream  inputserver   = null;
    private DataOutputStream out     = null;
    // check if the Number is valid 
    boolean validateInput(String i){
        try {
            int tmp = Integer.parseInt(i);
            if (tmp > 3 || tmp < 1)
            {
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    // constructor to put IP address and port 
    public Clinet2(String address, int port) 
    { 
        // establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            // takes input from terminal 
            input  = new Scanner(System.in);
            inputserver = new DataInputStream( 
                    new BufferedInputStream(socket.getInputStream()));
  
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
  
        // string to read message from input 
        String line = "";
        String balance;
        boolean isvalid= true; // to repeat the input from user if invalid
        boolean t1= true; // if the input not valid don't take message from server
        
  
        
        do
        {
            try
            {
            	
            	if(t1)
            	{
            		balance = inputserver.readUTF();
            		if(line.equals("player disconnected")) {
                		System.out.print("el wad el tany disconnected");
                		return;
                	}
            		// reading balance to print
            		//hello from the other side
            		//
            		System.out.println(balance);
            		t1 = false;
            	}
            	
            	if(isvalid)
                {
            		line = inputserver.readUTF();
            		if(line.equals("player disconnected")) {
                		System.out.print("el wad el tany disconnected");
                		return;
                	}
                	if(line.equals("won")){
                    	System.out.println("you Won");
                    	break;
                	}
                	else if(line.equals("lost")){
                    	System.out.println("You Lost");
                    	break;
                	}
                }
            	
            	isvalid = true;
                if(isvalid)
            	{
                	System.out.print("sub 1 , 2 or 3 points :");
                	line = input.nextLine();// read number from user to play
                	if(!validateInput(line)){
                		System.out.print("invalid input " + "\n");
                		//   continue;
                		isvalid = false;
                	}
            	}
                
                if(isvalid)
                {
                	out.writeUTF(line);
                	t1 = true;
                }
            }
            catch(IOException i)
            {
            	
            }
        }while (true);
  
        // close the connection 
        try
        { 
            input.close(); 
            out.close(); 
            socket.close();
            inputserver.close();
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
  
    public static void main(String args[]) 
    { 
    	Clinet2 client = new Clinet2("127.0.0.1", 12345); 
    } 
} 