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
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Argha Das
 */
public class ClientHandler extends Thread {

    private Socket socket;
    private PrintWriter output;
    private Scanner input;
    private ArrayList<Socket> connectionList;
    private ServerMain serverMain;

    public ClientHandler(ServerMain serverMain, Socket s, ArrayList<Socket> connectionList) {
        this.socket = s;
        this.connectionList = connectionList;
        this.serverMain = serverMain;
    }

    @Override
    public void run() {
        try {
            getMessage();
        } catch (IOException ex) {
            Log.E(ex.getMessage());
        }
    }

    private void getMessage() throws IOException {
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new Scanner(socket.getInputStream());
        String message = "";
        while (input.hasNext()) {
            message = input.nextLine();
            if (message.contains("+-+")) {
                showOnlineUsers();
            } else {
                Log.P(socket + message);
                sendMessage(message);
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        for (int i = 0; i < connectionList.size(); i++) {
            Socket temp_socket = (Socket) connectionList.get(i);
            PrintWriter temp_output = new PrintWriter(temp_socket.getOutputStream(), true);
            temp_output.println(message);
        }
    }

    private void showOnlineUsers() throws IOException {
        for (int i = 0; i < connectionList.size(); i++) {
            Socket temp_socket = (Socket) connectionList.get(i);
            PrintWriter temp_output = new PrintWriter(temp_socket.getOutputStream(), true);
            temp_output.println("+-+" + serverMain.getUsernameList());
        }
    }
}