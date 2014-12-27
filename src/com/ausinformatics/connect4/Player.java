package com.ausinformatics.connect4;

import java.util.Random;

import com.ausinformatics.phais.core.interfaces.PersistentPlayer;
import com.ausinformatics.phais.core.server.ClientConnection;
import com.ausinformatics.phais.core.server.DisconnectedException;

public class Player implements PersistentPlayer {


	private int ID;
	private String name;
	private ClientConnection connection;
	
	public Player(int ID, ClientConnection connection) {
		this.ID = ID;
		this.connection = connection;
	}
	
	public int getID() {
		return ID;
	}

	public String getName() {
		if (name == null) {
			generateNewName();
		}
		return name;
	}

	public void generateNewName() {
		if (name == null) {
			connection.sendInfo("NAME");
			
			try {
				String inputString = connection.getStrInput();
				String[] tokens = inputString.split("\\s");
				if (tokens.length < 2) {
					connection.disconnect();
				} else if (!tokens[0].equals("NAME")) {
					connection.disconnect();
				} else {
					if (tokens[1].length() > 16) {
						connection.sendInfo("ERROR Your name is too long");
						connection.disconnect();
						return;
					}
					name = tokens[1];
				}
			} catch (DisconnectedException e) {
				name = "DisconnectedPlayer " + new Random().nextInt(1000);
				e.printStackTrace();
				// TODO print out something useful
			}
		} else {
			name += new Random().nextInt(1000);
		}
	}

	public ClientConnection getConnection() {
		return connection;
	}


}
