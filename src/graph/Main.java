package graph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner keyboard = new Scanner(System.in);
           int loadingMenuOption = 0;
            // set default file names for the graph
            // these will be replaced if the user selects the corresponding menu item
            String[] loadingFiles = {"index.txt", "friends.txt"};
            while (loadingMenuOption != 3) {
                loadingMenuOption = showLoadingMenu();
                switch (loadingMenuOption) {
                    case 1:
                        // set loading menu to 4 so that the switch case breaks and goes to main menu
                        loadingMenuOption = 3;
                        break;
                    case 2:
                        System.out.println("Enter the vertex file name");
                        loadingFiles[0] = keyboard.nextLine();
                        System.out.println("Enter the edge file name");
                        loadingFiles[1] = keyboard.nextLine();
                        loadingMenuOption = 3;
                        break;
                    case 3:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("Program ended - Goodbye");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option selected");
                        System.out.println("Please select a valid option");
                        System.out.println("Press enter to continue");
                        keyboard.nextLine();
                }
            }
            // initialize the graph
            Graph socialNetwork = loadFiles(loadingFiles[0], loadingFiles[1]);
            int option = 0;
            while (option != 6) {
                option = showMainMenu();
                switch (option) {
                    case 1:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("Please enter the user name you would like to search for");
                        String nameFof = keyboard.nextLine();
                        ArrayList<String> graphUsers = getGraphUsers(socialNetwork);
                        // check the user exists otherwise notify not in graph
                        if(graphUsers.contains(nameFof)){
                            // get the neighbours of the requested user
                            // then iterate through the neighbours and get their neighbours
                            // add all these vertices to a set so that we remove duplicates
                            // finally remove the vertex for the originally requested vertex
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
                        } else {
                            System.out.println("user not in the graph press enter to continue");
                        }
                        keyboard.nextLine();
                        break;
                    case 2:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("Please enter the user name you would like to search for");
                        String nameF = keyboard.nextLine();
                        ArrayList<String> graphUsers2 = getGraphUsers(socialNetwork);
                        if(graphUsers2.contains(nameF)){
                            // although this code is similar to the search for friends of friends
                            // i opted to not abstract this logic out as there are slight differences
                            // in the way we are handling the 2 cases
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
                        } else {
                            System.out.println("user not in the graph press enter to continue");
                        }
                        keyboard.nextLine();
                        break;
                    case 3:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("Please enter the first user name you would like to find common friends");
                        String nameC1 = keyboard.nextLine();
                        System.out.println("Please enter the second user name you would like to find common friends");
                        String nameC2 = keyboard.nextLine();
                        ArrayList<String> graphUsers3 = getGraphUsers(socialNetwork);
                        // First get the friends of both selected users
                        // then iterate through the first array of neighbours
                        // within the loop iterate through every element in the second array
                        // compare if there are any matching labels
                        // if so add them to the new array list
                        // finally remove the labels of the two original users that were searched for
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
                        } else {
                            System.out.println("One or both users not in the graph press enter to continue");
                        }
                        keyboard.nextLine();
                        break;
                    case 4:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("Please enter the user name you would like to remove from the graph");
                        String deleteName = keyboard.nextLine();
                        ArrayList<String> graphUsers4 = getGraphUsers(socialNetwork);
                        // additional checks in place to ask if the user to confirm the removal
                        if(graphUsers4.contains(deleteName)) {
                            System.out.println("\nAre you sure you want to proceed? This cannot be undone\n");
                            System.out.println("1.Yes");
                            System.out.println("2.Cancel");
                            System.out.println("--------------");
                            System.out.println("Enter your choice:");
                            int isSure = keyboard.nextInt();
                            if (isSure != 1) {
                                System.out.println("Cancelled - press enter to continue");
                            } else {
                                // first delete the edges of the vertex
                                // then set the label on the vertex to null
                                int delIndex = graphUsers4.indexOf(deleteName);
                                int[] delNeighbours = socialNetwork.neighbors(delIndex);
                                for(int v=0; v < delNeighbours.length; v++){
                                    socialNetwork.removeEdge(delIndex, v);
                                }
                                socialNetwork.setLabel(delIndex, null);
                                System.out.println(deleteName + " has been deleted from the social network - press enter to continue");
                            }
                            keyboard.nextLine();
                        } else {
                            System.out.println("User not in the graph press enter to continue");
                        }
                        keyboard.nextLine();
                        break;
                    case 5:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("REPORT\n\nUsers sorted by most popular\n");
                        HashMap<String, Integer> popularUsers = new HashMap<String, Integer>();
                        // first create a hash map of the users and their respective number of friends
                        for(int m = 0; m < socialNetwork.size(); m++){
                            String namePop = String.valueOf(socialNetwork.getLabel(m));
                            int connections = socialNetwork.neighbors(m).length;
                            popularUsers.put(namePop, connections);
                        }
                        // Now sort the HashMap by values
                        // there is no direct way to sort HashMap by values but you
                        // can do this by writing a comparator, which takes
                        // Map.Entry object and arrange them in order decreasing by values.

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
                        LinkedHashMap<String, Integer> sortedUsersByValue = new LinkedHashMap<String, Integer>(listOfEntries.size());
                        // copying entries from List to Map
                        for(Map.Entry<String, Integer> entry : listOfEntries){
                            sortedUsersByValue.put(entry.getKey(), entry.getValue());
                        }
                        System.out.printf("%-13s %-4s %-4s \n", "Username", " | ", "Number of Friends");
                        System.out.println("____________________________________");
                        Set<Map.Entry<String, Integer>> entrySetSortedByValue = sortedUsersByValue.entrySet();
                        for(Map.Entry<String, Integer> mapping : entrySetSortedByValue){
                            // print with a table layout
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
                        // catch any entries that are not valid and ask the user to resubmit
                        System.out.println("Invalid option selected");
                        System.out.println("Please select a valid option");
                        System.out.println("Press enter to continue");
                        keyboard.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Can't find files, please check file path and try again");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /** Reads the data files as default or user specified.
     * validates that the files have the correct data.
     * If correct then returns a graph with the relevent vertices and edges
     * @return A graph initialised with the vertices and edges as supllied by the verified data files.
     */
    private static Graph loadFiles (String vertexFileName, String edgeFileName) throws IOException {
        try {
            FileInputStream vertexStream = new FileInputStream(vertexFileName);
            Scanner vertexScanner = new Scanner(vertexStream);
            int linesInVertexFile = 0;
            int numberOfVertices = Integer.parseInt(vertexScanner.nextLine());
            linesInVertexFile++;
            // Read in the file with vertices and data and initialize the graph
            Graph socialNetwork = new SocialNetwork(numberOfVertices);
            while (vertexScanner.hasNextLine()) {
                // iterate through each line in the vertices file and add them to the graph
                linesInVertexFile++;
                String peopleData = vertexScanner.nextLine();
                String[] people = peopleData.split(" ");
                String[] ary = new String[people.length];
                int i = 0;
                for (String person : people)
                    ary[i++] = person;
                socialNetwork.setLabel(Integer.parseInt(ary[0]), ary[1]);
            }
            // if the number of vertices supplied does not match
            // the number of vertices specified in the first line of the
            // file then throw an error
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
                // read through each line in the edge file
                // add these edges to the graph
                linesInEdgeFile++;
                String edgeData = edgeScanner.nextLine();
                String[] edges = edgeData.split(" ");
                int[] ary = new int[edges.length];
                int i = 0;
                for (String edge : edges)
                    ary[i++] = Integer.parseInt(edge);
                socialNetwork.addEdge(ary[0], ary[1]);
            }
            // if the number of edges supplied does not match
            // the number of edges specified in the first line of the
            // file then throw an error
            if(linesInEdgeFile -1 != numberOfEdges) {
                throw new IOException("number edges in file is wrong please correct this error and run again");
            }
            edgeScanner.close();
            // finally return the initialized graph
            return socialNetwork;
        } catch (FileNotFoundException e) {
            // if the file names are not found throw an error
            throw new IOException("1 or more files not found,\nPlease check file path and try again");
        } catch (IOException e) {
            throw e;
        }
    }
    /** Displays the main menu.
     * @return An int specifying the menu option the users selected.
     */
    private static int showMainMenu() {
        try{
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
        } catch (InputMismatchException e){
            System.out.println("Please select a NUMBER");
            System.out.println("Valid options are 1, 2, 3, 4, 5, and 6");
            return 0;
        }

    }
    /** Displays the loading menu.
     * @return An int specifying the menu option the users selected.
     */
    private static int showLoadingMenu() {
        try{
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
        } catch (InputMismatchException e){
            System.out.println("Please select a NUMBER");
            System.out.println("Valid options are 1, 2 and 3");
            return 0;
        }
    }

    /** Get the labels for the vertices in the graph.
     * @return A string array list with all the labels of the vertices in the graph.
     */
    private static ArrayList<String> getGraphUsers(Graph graph) {
        ArrayList<String> graphUsers = new ArrayList<String>();
        for(int i = 0; i < graph.size(); i++){
            graphUsers.add(String.valueOf(graph.getLabel(i)));
        }
        return graphUsers;
    }
}
