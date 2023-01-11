package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerChat extends Thread {
	// Attributs
	private int nbrClient;
	// déclaration d'une liste des cilents
	private List<Conversation> clients = new ArrayList<Conversation>();

	// Main

	public static void main(String[] args) {
		new ServerChat().start();
	}

	// m2thode run de notre serveur
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(2547);
			System.out.println("Démmarage du serveur ......");
			while (true) {
				Socket s = ss.accept();
				nbrClient++;
				Conversation client = new Conversation(s, nbrClient);
				clients.add(client);
				client.start();
			}
		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	// Classe conversation
	public class Conversation extends Thread {
		protected Socket s;
		protected int nbrClient;

		public Conversation(Socket s, int nbrClient) {
			this.s = s;
			this.nbrClient = nbrClient;
		}

		// methode broadcast

		public void broadcast(String msg, Socket s, int numClient) {
			for (Conversation client : clients) {
				try {
					if (client.s != s) {
						if (client.nbrClient == numClient || numClient == -1) {
							PrintWriter printWriter = new PrintWriter(client.s.getOutputStream(), true);
							printWriter.println(msg);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// run de classe Conversation

		public void run() {
			try {
				InputStream is = s.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String ip = s.getRemoteSocketAddress().toString();
				System.out.println("Le client numéro " + nbrClient + " connecte, son address ip est " + ip);
				OutputStream os = s.getOutputStream();
				PrintWriter pw = new PrintWriter(os, true);
				while (true) {
					String req = br.readLine();
					if (req.contains("=>")) {
						String[] reqParams = req.split("=>");
						if (reqParams.length == 2) {
							String message = reqParams[1];
							int numeroClient = Integer.parseInt(reqParams[0]);
							broadcast(message, s, numeroClient);
						}
					} else {
						broadcast(req, s, -1);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
