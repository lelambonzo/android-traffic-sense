import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple Chat Server over TCP
 * @author Amr Tj. Wallas
 * 
 */
public class Server
{
    static ServerSocket serverSock;

    /**
     * 
     */
    public Server()
    {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
	serverSock = new ServerSocket(27960);
	while (true)
	{
	    new ClientThread(serverSock.accept()).start();
	}

    }

}

class ClientThread extends Thread
{
    public Socket clientSock;

    public ClientThread()
    {
	// TODO Auto-generated constructor stub
    }

    public ClientThread(Socket s)
    {
	this.clientSock = s;
    }

    @Override
    public void start()
    {
	try
	{
	    BufferedReader in = new BufferedReader(new InputStreamReader(
		    clientSock.getInputStream()));
	    String msg;
	    //System.out.println("here");
	    while(!clientSock.isClosed())
	    {
		msg = in.readLine();
		if (msg.contains("CMD_QUIT"))
		    clientSock.close();
		else
		    System.out.println(msg);
	    }
	    System.out.println("Connection to client terminated.");

	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
