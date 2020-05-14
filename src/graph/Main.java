package graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
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
//Initialize the graph with the index file not the friends file
    //
    private static Graph loadFiles () throws IOException {
        try {
            FileInputStream friends = new FileInputStream("friends.txt");
            Scanner friendScanner = new Scanner(friends);
            int linesInFriendsFile = 0;
            int numberOfEdges = Integer.parseInt(friendScanner.nextLine());
            linesInFriendsFile++;
//            if (friendScanner.hasNextLine()) {
//                linesInFriendsFile++;
//                friendScanner.nextLine();
//            }
            Graph socialNetwork = new SocialNetwork(numberOfEdges);
            System.out.println(numberOfEdges);
            while (friendScanner.hasNextLine()) {
                linesInFriendsFile++;
                String edgeData = friendScanner.nextLine();
                String[] edges = edgeData.split(" ");
                int[] ary = new int[edges.length];
                int i = 0;
                for (String edge : edges)
                    ary[i++] = Integer.parseInt(edge);

//                System.out.println(Arrays.toString(ary));
//                socialNetwork.addEdge(ary[0], ary[1]);
                socialNetwork.addEdge(0, 1);
                socialNetwork.addEdge(0, 2);
                socialNetwork.addEdge(0, 3);
                socialNetwork.addEdge(0, 4);
                socialNetwork.addEdge(1, 4);

            }

            if(linesInFriendsFile -1 != numberOfEdges) {
                throw new IOException("number edges in file is wrong");
            }
            friendScanner.close();
            FileInputStream index = new FileInputStream("index.txt");
            Scanner indexScanner = new Scanner(index);
            int linesInIndexFile = 1;
            int numberOfPeople = Integer.parseInt(indexScanner.nextLine());
            if (indexScanner.hasNextLine()) {
                linesInIndexFile++;
                indexScanner.nextLine();
            }
            while (indexScanner.hasNextLine()) {
                linesInIndexFile++;
                String peopleData = indexScanner.nextLine();
                String[] people = peopleData.split(" ");
                String[] ary = new String[people.length];
                int i = 0;
                for (String person : people)
                    ary[i++] = person;
//                socialNetwork.setLabel(Integer.parseInt(ary[0]), ary[1]);
                socialNetwork.setLabel(0, "frank");
            }
            if(linesInIndexFile != numberOfPeople) {
                throw new IOException("number people in file is wrong");
            }
            indexScanner.close();
            return socialNetwork;
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
