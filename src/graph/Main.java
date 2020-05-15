package graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner keyboard = new Scanner(System.in);
           int loadingMenuOption = 0;
            String[] loadingFiles = {"index.txt", "friends.txt"};
            while (loadingMenuOption != 4) {
                loadingMenuOption = showLoadingMenu();
                switch (loadingMenuOption) {
                    case 1:
                        loadingMenuOption = 4;
                        break;
                    case 2:
                        System.out.println("Enter the vertex file name");
                        loadingFiles[0] = keyboard.nextLine();
                        System.out.println("Enter the edge file name");
                        loadingFiles[1] = keyboard.nextLine();
                        loadingMenuOption = 4;
                        break;
                    case 3:
                        System.out.println("Program ended - Goodbye");
                        System.exit(0);
                    default:
                        System.out.println("Sorry, please enter valid Option");
                }
            }
            Graph socialNetwork = loadFiles(loadingFiles[0], loadingFiles[1]);
            int option = 0;
            while (option != 7) {
                option = showMainMenu();
                switch (option) {
                    case 1:
                        System.out.println("Get ");

                        break;
                    case 2:
                        System.out.println("Please enter the user name you would like to search for");
//                        Scanner keyboard = new Scanner(System.in);
                        String name = keyboard.nextLine();
                        ArrayList<String> graphUsers = new ArrayList<String>();
                        for(int i = 0; i < socialNetwork.size(); i++){
                            graphUsers.add(String.valueOf(socialNetwork.getLabel(i)));
                        }
                        if(graphUsers.contains(name)){
                            int[] neighbours = socialNetwork.neighbors(graphUsers.indexOf(name));
                            Set<String> friends = new HashSet<String>();
                            for(int i = 0; i < neighbours.length; i++){
                                friends.add(String.valueOf(socialNetwork.getLabel(neighbours[i])));
                                int[] neighboursNeighbours = socialNetwork.neighbors(neighbours[i]);
                                for(int j = 0; j < neighboursNeighbours.length; j++){
                                    friends.add(String.valueOf(socialNetwork.getLabel(neighboursNeighbours[i])));
                                }
                            }
                            System.out.println(friends);
                        } else {
                            System.out.println("user not in the graph press enter to continue");
                            String enter = keyboard.nextLine();
                        }
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
                        System.out.println("Program ended - Goodbye");
                        System.exit(0);
                    default:
                        System.out.println("Sorry, please enter valid Option");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Can't find files, please check file path and try again");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static Graph loadFiles (String vertexFileName, String edgeFileName) throws IOException {
        try {
            FileInputStream vertexStream = new FileInputStream(vertexFileName);
            Scanner vertexScanner = new Scanner(vertexStream);
            int linesInVertexFile = 0;
            int numberOfVertices = Integer.parseInt(vertexScanner.nextLine());
            linesInVertexFile++;
            Graph socialNetwork = new SocialNetwork(numberOfVertices);
            while (vertexScanner.hasNextLine()) {
                linesInVertexFile++;
                String peopleData = vertexScanner.nextLine();
                String[] people = peopleData.split(" ");
                String[] ary = new String[people.length];
                int i = 0;
                for (String person : people)
                    ary[i++] = person;
                socialNetwork.setLabel(Integer.parseInt(ary[0]), ary[1]);
            }
            if(linesInVertexFile -1 != numberOfVertices) {
                throw new IOException("number people in file is wrong please correct this error and run again");
            }
            vertexScanner.close();
            FileInputStream edgeStream = new FileInputStream(edgeFileName);
            Scanner edgeScanner = new Scanner(edgeStream);
            int linesInEdgeFile = 0;
            int numberOfEdges = Integer.parseInt(edgeScanner.nextLine());
            linesInEdgeFile++;
            while (edgeScanner.hasNextLine()) {
                linesInEdgeFile++;
                String edgeData = edgeScanner.nextLine();
                String[] edges = edgeData.split(" ");
                int[] ary = new int[edges.length];
                int i = 0;
                for (String edge : edges)
                    ary[i++] = Integer.parseInt(edge);
                socialNetwork.addEdge(ary[0], ary[1]);
            }
            if(linesInEdgeFile -1 != numberOfEdges) {
                throw new IOException("number edges in file is wrong please correct this error and run again");
            }
            edgeScanner.close();

            return socialNetwork;
        } catch (FileNotFoundException e) {
            throw new IOException("1 or more files not found,\nPlease check file path and try again");
        } catch (IOException e) {
            throw e;
        }
    }

    private static int showMainMenu() {
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

    private static int showLoadingMenu() {
        int option = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("SOCIAL NETWORK PROGRAM:");
        System.out.println("--------------");
        System.out.println("Welcome to the social network program");
        System.out.println("--------------");
        System.out.println("1.Start with default network");
        System.out.println("2.Load a network from different files");
        System.out.println("3.Quit");
        System.out.println("--------------");
        System.out.println("Enter your choice:");
        option = keyboard.nextInt();

        return option;
    }
}
