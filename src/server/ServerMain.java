/*
 * Copyright (C) 2018 Argha Das
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;
import javax.swing.DefaultListModel;

/**
 *
 * @author Argha Das
 */
public class ServerMain {

    private ServerSocket serverSocket;
    private int port = 3333;
    private Socket socket;
    private ArrayList<Socket> connectionList = new ArrayList<>();
    private ArrayList<String> usernameList = new ArrayList<>();
    private ClientHandler clientHandler;

    public ServerMain() {
        try {
            startServer();
            connectionOperation();
        } catch (IOException ex) {
            Log.E(ex.getMessage());
        }
    }

    private void startServer() throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void connectionOperation() throws IOException {
        while (true) {
            acceptConnection();
        }
    }

    private void acceptConnection() throws IOException {
        socket = serverSocket.accept();
        addConnectionToList(socket);
        addUsernameToList(socket);
        handleClients(socket);
    }

    private void addConnectionToList(Socket s) {
        connectionList.add(s);
    }

    private void handleClients(Socket s) {
        clientHandler = new ClientHandler(this, s, connectionList);
        clientHandler.start();
    }

    public static void main(String[] args) {
        ServerMain serverMain = new ServerMain();
    }

    private void addUsernameToList(Socket s) throws IOException {
        Scanner input = new Scanner(s.getInputStream());
        String username = input.nextLine();
        usernameList.add(username);
        Log.P(username + " joined the room");
        for (int i = 0; i < connectionList.size(); i++) {
            Socket temp_socket = (Socket) connectionList.get(i);
            PrintWriter output = new PrintWriter(temp_socket.getOutputStream(), true);
            output.println(username + " joined the room");
        }
    }

    public ArrayList<String> getUsernameList() {
        return usernameList;
    }
}
