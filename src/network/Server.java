package network;

// A Java program for a Server 
import java.net.*; 
import java.io.*; 
  
public class Server 
{ 
    //initialize socket and input stream 
    private ServerSocket    server   = null;
    
    public  void New_game(Socket  socket,Socket socket2) throws Exception
    {
        
        
        Thread playingthread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DataInputStream in       =  null;
		        DataInputStream in2      =  null;
		        DataOutputStream out     =  null;
		        DataOutputStream out2    =  null;
		        int balance = 21; //the starting Number
		        int counter = 0;  // to choose the plater
		    	// takes input & output from the client socket 
		        try {
		        	in = new DataInputStream( 
		        			new BufferedInputStream(socket.getInputStream()));
		        	in2 = new DataInputStream( 
		        			new BufferedInputStream(socket2.getInputStream()));
		        	out    = new DataOutputStream(socket.getOutputStream());
		        	out2   = new DataOutputStream(socket2.getOutputStream());
		        }catch(Exception ignore) {}
		        // string to read message from input
		        String line = ""; 
				while (true)
		        { 
		            try
		            {
		                if (counter % 2 == 0){ // player 1(clinet1)
		                	out.writeUTF(Integer.toString(balance) );//sending balance
		                    line = in.readUTF();
		                    balance -= Integer.parseInt(line);
		                    if(balance <= 0) {
		                        out.writeUTF("lost");
		                        out2.writeUTF("won");
		                        break;
		                    }else
		                        out2.writeUTF(balance + "");
		                }else{ // player 2 ( clinet2)
		                	out2.writeUTF(Integer.toString(balance) );//sending balance
		                    line = in2.readUTF();
		                    balance -= Integer.parseInt(line);
		                    if(balance <= 0) {
		                        out.writeUTF("won");
		                        out2.writeUTF("lost");
		                        break;
		                    }else
		                        out.writeUTF(balance + ""); // sending balance
		                }
		                System.out.println(balance);
		                counter++; //change between players
		            }
		            catch(IOException i)
		            {
		                //i.printStackTrace();
		                try {
		                	if (counter % 2 == 0){
		                		out2.writeUTF("player disconnected");
		                		socket2.close();
		                	}else {
		                		out.writeUTF("player disconnected");
		                		socket.close();
		                	}
		                }catch(Exception ignore) {}
		                break;
		            }
		        } 
				
			}
		});
        playingthread.start();
        // playing loop 
        
    }
    // constructor with port 
    public Server(int port) throws IOException 
    { 
        // starts server and waits for a connection 

        server = new ServerSocket(port); 
        System.out.println("Server started"); 

        System.out.println("Waiting for a client ..."); 
    	while(true)
    	{
        try
        { 
  

            Socket socket   = null;
            Socket socket2  = null;
            
            socket = server.accept(); 
            System.out.println("Client1 accepted"); 
            socket2 = server.accept(); 
            System.out.println("Client2 accepted");
            
            New_game(socket, socket2);
            
            //System.out.println("Closing connection"); 
  
            // close connection 
            
        } 
        catch(Exception i) 
        { 
            i.printStackTrace();
            break;
        }
    	}
        server.close();
        
    } 
  
    public static void main(String args[]) throws IOException 
    {
        Server server = new Server(12345);
    } 
} 