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
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("Program ended - Goodbye");
                        System.exit(0);
                    default:
                        System.out.println("Sorry, please enter valid Option");
                }
            }
            Graph socialNetwork = loadFiles(loadingFiles[0], loadingFiles[1]);
            int option = 0;
            while (option != 6) {
                option = showMainMenu();
                switch (option) {
                    case 1:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("Please enter the user name you would like to search for");
                        String nameFof = keyboard.nextLine();
                        ArrayList<String> graphUsers = new ArrayList<String>();
                        for(int i = 0; i < socialNetwork.size(); i++){
                            graphUsers.add(String.valueOf(socialNetwork.getLabel(i)));
                        }
                        if(graphUsers.contains(nameFof)){
                            int[] neighbours = socialNetwork.neighbors(graphUsers.indexOf(nameFof));
                            Set<String> friends = new HashSet<String>();
                            for(int i = 0; i < neighbours.length; i++){
                                friends.add(String.valueOf(socialNetwork.getLabel(neighbours[i])));
                                int[] neighboursNeighbours = socialNetwork.neighbors(neighbours[i]);
                                for(int j = 0; j < neighboursNeighbours.length; j++){
                                    friends.add(String.valueOf(socialNetwork.getLabel(neighboursNeighbours[j])));
                                }
                            }
                            friends.remove(nameFof);
                            System.out.println("\nThe friends and friends of friends for " + nameFof + " are\n");
                            Iterator<String> itr = friends.iterator();
                           while(itr.hasNext()){
                                System.out.println(itr.next());
                            }
                            System.out.println("\nPress enter to continue");
                            keyboard.nextLine();
                        } else {
                            System.out.println("user not in the graph press enter to continue");
                            keyboard.nextLine();
                        }
                        break;
                    case 2:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("Please enter the user name you would like to search for");
                        String nameF = keyboard.nextLine();
                        ArrayList<String> graphUsers2 = new ArrayList<String>();
                        for(int i = 0; i < socialNetwork.size(); i++){
                            graphUsers2.add(String.valueOf(socialNetwork.getLabel(i)));
                        }
                        if(graphUsers2.contains(nameF)){
                            int[] neighbours = socialNetwork.neighbors(graphUsers2.indexOf(nameF));
                            Set<String> friends = new HashSet<String>();
                            for(int i = 0; i < neighbours.length; i++){
                                friends.add(String.valueOf(socialNetwork.getLabel(neighbours[i])));
                            }
                            System.out.println("\nThe friends for " + nameF + " are\n");
                            Iterator<String> itr = friends.iterator();
                            while(itr.hasNext()){
                                System.out.println(itr.next());
                            }
                            System.out.println("\nPress enter to continue");
                            keyboard.nextLine();
                        } else {
                            System.out.println("user not in the graph press enter to continue");
                            keyboard.nextLine();
                        }
                        break;
                    case 3:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("Please enter the first user name you would like to find common friends");
                        String nameC1 = keyboard.nextLine();
                        System.out.println("Please enter the second user name you would like to find common friends");
                        String nameC2 = keyboard.nextLine();
                        ArrayList<String> graphUsers3 = getGraphUsers(socialNetwork);
                        if(graphUsers3.contains(nameC1) && graphUsers3.contains(nameC2)) {
                            int[] c1Neighbours = socialNetwork.neighbors(graphUsers3.indexOf(nameC1));
                            int[] c2Neighbours = socialNetwork.neighbors(graphUsers3.indexOf(nameC2));
                            Set<String> commonFriends = new HashSet<String>();
                            for(int i=0; i < c1Neighbours.length; i++){
                                for(int j=0; j < c2Neighbours.length; j++){
                                    if(c1Neighbours[i] == c2Neighbours[j]){
                                        commonFriends.add(String.valueOf(socialNetwork.getLabel(c1Neighbours[i])));
                                    }
                                }
                            }
                            commonFriends.remove(nameC1);
                            commonFriends.remove(nameC2);
                            System.out.println("\nThe common friends for " + nameC1 + " and " + nameC2 + " are\n");
                            Iterator<String> itr = commonFriends.iterator();
                            while(itr.hasNext()){
                                System.out.println(itr.next());
                            }
                            System.out.println("\nPress enter to continue");
                            keyboard.nextLine();
                        } else {
                            System.out.println("One or both users not in the graph press enter to continue");
                            keyboard.nextLine();
                        }
                        break;
                    case 4:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("Please enter the user name you would like to remove from the graph");
                        String nameDel = keyboard.nextLine();
                        ArrayList<String> graphUsers4 = getGraphUsers(socialNetwork);
                        if(graphUsers4.contains(nameDel)) {
                            System.out.println("\nAre you sure you want to proceed? This cannot be undone\n");
                            System.out.println("1.Yes");
                            System.out.println("2.Cancel");
                            System.out.println("--------------");
                            System.out.println("Enter your choice:");
                            int isSure = keyboard.nextInt();
                            if (isSure == 1) {
                                int delIndex = graphUsers4.indexOf(nameDel);
                                int[] delNeighbours = socialNetwork.neighbors(delIndex);
                                for(int v=0; v < delNeighbours.length; v++){
                                    socialNetwork.removeEdge(delIndex, v);
                                }
                                socialNetwork.setLabel(delIndex, null);
                                System.out.println(nameDel + " has been deleted from the social network - press enter to continue");
                                keyboard.nextLine();
                                keyboard.nextLine();
                                break;
                            } else {
                                System.out.println("Cancelled - press enter to continue");
                                keyboard.nextLine();
                                keyboard.nextLine();
                                break;
                            }
                        } else {
                            System.out.println("User not in the graph press enter to continue");
                            keyboard.nextLine();
                            break;
                        }
                    case 5:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("REPORT\n\nUsers sorted by most popular\n");
                        HashMap<String, Integer> popularUsers = new HashMap<String, Integer>();
                        for(int m = 0; m < socialNetwork.size(); m++){
                            String namePop = String.valueOf(socialNetwork.getLabel(m));
                            int connections = socialNetwork.neighbors(m).length;
                            popularUsers.put(namePop, connections);
                        }

                        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {
                            @Override
                            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                                Integer v1 = e1.getValue();
                                Integer v2 = e2.getValue();
                                return v2.compareTo(v1);
                            }
                        };
                        Set<Map.Entry<String, Integer>> entries = popularUsers.entrySet();
                        // Sort method needs a List, so let's first convert Set to List in Java
                        List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<Map.Entry<String, Integer>>(entries);
                        // sorting HashMap by values using comparator
                        Collections.sort(listOfEntries, valueComparator);
                        LinkedHashMap<String, Integer> sortedByValue = new LinkedHashMap<String, Integer>(listOfEntries.size());
                        // copying entries from List to Map
                        for(Map.Entry<String, Integer> entry : listOfEntries){
                            sortedByValue.put(entry.getKey(), entry.getValue());
                        }
                        System.out.printf("%-13s %-4s %-4s \n", "Username", " | ", "Number of Friends");
                        System.out.println("____________________________________");
                        Set<Map.Entry<String, Integer>> entrySetSortedByValue = sortedByValue.entrySet();
                        for(Map.Entry<String, Integer> mapping : entrySetSortedByValue){
                            System.out.printf("%-13s %-8s %4s \n", mapping.getKey(), " | ", mapping.getValue());
                        }

                        System.out.println("\nPress enter to continue");
                        keyboard.nextLine();
                        break;
                    case 6:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
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
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Main Menu:");
        System.out.println("--------------");
        System.out.println("1.Get users friends and friends of friends");
        System.out.println("2.Get users friends");
        System.out.println("3.Get common friends between 2 users");
        System.out.println("4.Delete user and relationships");
        System.out.println("5.Print user list sorted by popularity");
        System.out.println("6.Quit");
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

    private static ArrayList<String> getGraphUsers(Graph graph) {
        ArrayList<String> graphUsers = new ArrayList<String>();
        for(int i = 0; i < graph.size(); i++){
            graphUsers.add(String.valueOf(graph.getLabel(i)));
        }
        return graphUsers;
    }
}
