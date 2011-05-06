import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * Simple Chat Server over TCP
 * 
 * @author Amr Tj. Wallas
 * 
 */
public class Server
{
    static ServerSocket serverSock;

    /**
     * Useless Constructor. DO NOT USE!
     */
    public Server()
    {
	// TODO Auto-generated constructor stub
    }

    /**
     * Initiates the data server which listends and handles incoming data from
     * the android clients
     * 
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

/**
 * Client Thread is a dedicated thread for each client to handle data
 * communication after the connection gets 1st accepted by serverSock
 * 
 * @author Amr Tj. Wallas
 * 
 */
class ClientThread extends Thread
{
    public Socket clientSock;
    long device_id, date;
    double longitude, latitude, altitude;
    float bearing, accuracy, speed;

    /**
     * Useless constructor. DO NOT USE.
     */
    public ClientThread()
    {
	// TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new Client Thread given the initial connection to
     * serverSocket got accepted.
     * 
     * @param s
     *            The new Socket which is returned by the serverSocket on
     *            accepting the connection.
     */
    public ClientThread(Socket s)
    {
	this.clientSock = s;
    }

    /**
     * Starts a new thread in an asynchronous out of order fashion.
     */
    @Override
    public void start()
    {
	int noOfLines = 0;
	try
	{
	    BufferedReader in = new BufferedReader(new InputStreamReader(
		    clientSock.getInputStream()));
	    String msg;
	    // System.out.println("here");
	    while (!clientSock.isClosed())
	    {
		msg = in.readLine();
		noOfLines++;
		processMessage(msg, noOfLines);
	    }
	    System.out.println("Connection to client terminated.");

	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    /**
     * Parses the Received messages from the client and does a post request to
     * web-server. <br>
     * <b> If the no of lines received from the client is n, this method is
     * called n times. </b>
     * 
     * @param msg
     *            The message received from the client.
     * @param lineNo
     *            The current lineNo being read.
     * @throws IOException
     */
    public void processMessage(String msg, int lineNo) throws IOException
    {
	if (msg.contains("CMD_QUIT"))
	{
	    clientSock.close();
	    sendHTTP(device_id, longitude, latitude, altitude, bearing,
		    accuracy, speed, date);
	} else
	{
	    System.out.println(msg); /* For Testing purposes */
	    // TODO Filter the data and send an http request to 'web-server'
	    switch (lineNo)
	    {
		case 2:
		    device_id = 6666; /* For Testing Purposes */
		    break;

		case 3:
		    longitude = Double.parseDouble(msg);
		    break;

		case 4:
		    latitude = Double.parseDouble(msg);
		    break;

		case 5:
		    altitude = Double.parseDouble(msg);
		    break;

		case 6:
		    bearing = Float.parseFloat(msg);
		    break;

		case 7:
		    accuracy = Float.parseFloat(msg);
		    break;

		case 8:
		    speed = Float.parseFloat(msg);
		    break;

		case 9:
		    date = Long.parseLong(msg);
		    break;

	    }
	}
    }

    /**
     * Sends an HTTP Post request to 'web-server'
     * 
     * @param device_id
     * @param longitude
     * @param latitude
     * @param altitude
     * @param bearing
     * @param accuracy
     * @param speed
     * @param date
     */
    public void sendHTTP(long device_id, double longitude, double latitude,
	    double altitude, float bearing, float accuracy, float speed,
	    long date)
    {
	String data = "device_id=" + device_id + "&longitude=" + longitude
		+ "&latitude=" + latitude + "&altitude=" + altitude
		+ "&bearing=" + bearing + "&accuracy=" + accuracy + "&speed="
		+ speed + "&date=" + date;

	try
	{
	    URL url = new URL("http://localhost:9000/Statistics/newInfo");
	    URLConnection conn = url.openConnection();
	    conn.setDoOutput(true);
	    OutputStreamWriter writer = new OutputStreamWriter(
		    conn.getOutputStream());

	    writer.write(data);
	    writer.flush();

	    StringBuffer answer = new StringBuffer();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    conn.getInputStream()));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
		answer.append(line);
	    }
	    writer.close();
	    reader.close();

	    System.out.println(answer.toString());
	} catch (MalformedURLException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
