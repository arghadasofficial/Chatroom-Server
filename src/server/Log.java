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

/**
 *
 * @author Argha Das
 */
public class Log {

    public static void E(String text) {
        System.out.println("Error Log : " + text);
    }

    public static void P(String text) {
        System.out.println(text);
    }

    public static void T(String text) {
        System.out.println("Test Log : " + text);
    }
    
}
