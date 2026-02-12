package no.hvl.dat110.rpc;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.*;

import java.io.IOException;

public class RPCClient {

	// underlying messaging client used for RPC communication
	private MessagingClient msgclient;

	// underlying messaging connection used for RPC communication
	private MessageConnection connection;
	
	public RPCClient(String server, int port) {
	
		msgclient = new MessagingClient(server,port);
	}
	
	public void connect() {
		
		// TODO - START
		// connect using the RPC client

        try {
            connection = msgclient.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // TODO - END
	}
	
	public void disconnect() {
		
		// TODO - START
		// disconnect by closing the underlying messaging connection
		
		if (connection != null){
            connection.close();
            connection = null;
        }
		
		// TODO - END
	}

	/*
	 Make a remote call om the method on the RPC server by sending an RPC request message and receive an RPC reply message

	 rpcid is the identifier on the server side of the method to be called
	 param is the marshalled parameter of the method to be called
	 */

	public byte[] call(byte rpcid, byte[] param) {
		
		byte[] returnval = null;
		
		// TODO - START

		/*

		The rpcid and param must be encapsulated according to the RPC message format

		The return value from the RPC call must be decapsulated according to the RPC message format

		*/

        byte[] requestPayload  = RPCUtils.encapsulate(rpcid, param);

        Message request = new Message(requestPayload);
        try {
            connection.send(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Message reply = null;
        try {
            reply = connection.receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] replyPayload = reply.getData();

        returnval = RPCUtils.decapsulate(replyPayload);


				

		
		// TODO - END
		return returnval;
		
	}

}
