import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Simple client chat app
 * @author Amr Tj.Wallas
 */
public class Client extends Thread
{
    Socket s;
    BufferedReader in;
    PrintWriter out;
    String msg;

    public Client()
    {

    }

    @Override
    public void start()
    {
	try
	{
	    this.s = new Socket("localhost", 27960);
	} catch (UnknownHostException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	try
	{
	    this.out = new PrintWriter(this.s.getOutputStream(), true);
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	this.in = new BufferedReader(new InputStreamReader(System.in));

	while (!this.s.isClosed())
	{
	    try
	    {
		this.msg = in.readLine();
	    } catch (IOException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    if(msg != null)
	        this.out.println(this.msg);
	    
	    if (this.msg.contains("CMD_QUIT"))
		try
		{
		    this.s.close();
		} catch (IOException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}
	System.out.println("Client Disconnected.");
    }

    /**
     * @param args
     * @throws IOException
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException,
	    IOException
    {
	Client c = new Client();
	c.start();

    }

}
