package graph;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Graph graph = loadFiles();
            int option = 0;
            while (option != 3) {
                option = showMenu();
                switch (option) {
                    case 1:
                        System.out.println("Get ");
                        break;
                    case 2:
                        System.out.println("message");
                        break;
                    case 3:
                        System.out.println("message");
                        break;
                    case 4:
                        System.out.println("message");
                        break;
                    case 5:
                        System.out.println("message");
                        break;
                    case 6:
                        System.out.println("message");
                        break;
                    case 7:
                        System.out.println("Goodbye3");
                        System.exit(0);
                    default:
                        System.out.println("Sorry, please enter valid Option");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find files, please check file path and try again");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Graph loadFiles () throws IOException {
        try {
            File friendsFile = new File("friends.txt");
            FileInputStream friendsIs = new FileInputStream("friends.txt");
            FileInputStream index = new FileInputStream("index.txt");
            Scanner friendScanner = new Scanner(friendsIs);
            Scanner indexScanner = new Scanner(index);
            byte[] byteArray = new byte[(int)friendsFile.length()];
            friendsIs.read(byteArray);
            String data = new String(byteArray);
            String[] stringArray = data.split("\r\n");
            System.out.println("Number of lines in the file are ::"+stringArray.length);
            int numberOfEdges = Integer.parseInt(friendScanner.nextLine());
//            System.out.println(numberOfEdges);
            if (friendScanner.hasNextLine()) {
                friendScanner.nextLine();
            }
            while (friendScanner.hasNextLine()) {
                String edgeData = friendScanner.nextLine();
                String[] edges = edgeData.split(" ");
                int[] ary = new int[edges.length];
                int i = 0;
                for (String edge : edges)
                    ary[i++] = Integer.parseInt(edge);
//                System.out.println(Arrays.toString(ary));      //returns the line that was skipped
            }
            friendScanner.close();
            Graph mygraph = new Graph(4);
            if(false) {
                throw new IOException("number of file is wrong");
            }
            return mygraph;
        } catch (FileNotFoundException e) {
            throw new IOException("Files not found");
        } catch (IOException e) {
            throw e;
        }
    }

    private static int showMenu() {
        int option = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Main Menu:");
        System.out.println("--------------");
        System.out.println("1.Load new files");
        System.out.println("2.Get users friends and friends of friends");
        System.out.println("3.Get users friends");
        System.out.println("4.Get common friends between 2 users");
        System.out.println("5.Delete user and relationships");
        System.out.println("6.Print user list sorted by popularity");
        System.out.println("7.Quit");
        System.out.println("--------------");
        System.out.println("Enter your choice:");
        option = keyboard.nextInt();

        return option;
    }
}
