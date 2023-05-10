package org.example;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import org.example.JPA.DAOTests.AlbumDAOTest;
import org.example.JPA.model.Album;

import java.util.*;

public class AlbumSelection {

    public static void AlbumSelection(Integer atLeast, Integer yearsBetween) {
        // Get the list of all albums
        List<Album> allAlbums = getAllAlbums();

        Map<Character, List<Album>> albumsByInitial = groupAlbumsByInitial(allAlbums);

        // Create a Choco solver instance
        Model model = new Model("AlbumSelection");

        // Create decision variables
        List<IntVar> vars = new ArrayList<>();
        for (List<Album> group : albumsByInitial.values()) {
            IntVar[] groupVars = new IntVar[group.size()];
            for (int i = 0; i < group.size(); i++) {
                int startYear = group.get(i).getYear();
                int endYear = startYear + yearsBetween;
                IntVar yearVar = model.intVar(startYear, endYear);
                groupVars[i] = yearVar;
            }
            vars.addAll(Arrays.asList(groupVars));
        }
        System.out.println("Number of decision variables: " + vars.size());

        // Create constraints
        List<IntVar> selectedVars = new ArrayList<>();
        for (List<Album> group : albumsByInitial.values()) {
            System.out.println("Group size: " + group.size() + ", atLeast: " + atLeast);
            if (group.size() >= atLeast) {
                List<IntVar> groupVars = new ArrayList<>();
                for (Album album : group) {
                    IntVar yearVar = model.intVar(album.getYear());
                    IntVar yearVarPlus = model.intVar("Album" + album.getId(), album.getYear(), album.getYear() + yearsBetween);
                    model.arithm(yearVarPlus, "=", yearVar, "+", yearsBetween).post();
                    groupVars.add(yearVarPlus); // Add yearVarPlus to groupVars
                }
                Constraint atLeastK = model.atLeastNValues(groupVars.toArray(new IntVar[0]), model.intVar(atLeast), false);
                model.post(atLeastK);
                selectedVars.addAll(groupVars);
            }
        }
        System.out.println("Number of constraints: " + model.getCstrs().length);

        // Solve the problem
        Solution solution = model.getSolver().findSolution();

        // Print the selected albums
        if (solution != null) {
            System.out.println("Selected albums:");
            for (IntVar var : selectedVars) {
                if (solution.getIntVal(var) != 0) {
                    String id = var.getName().substring(5);
                    Album album = getAlbumById(allAlbums, id);
                    if (album != null) {
                        System.out.println(album.getTitle() + " (" + album.getYear() + ")");
                    }
                }
            }
        } else {
            System.out.println("No solution found.");
        }
        System.out.println("Solutions explored: " + model.getSolver().getMeasures().getSolutionCount());
    }

    private static List<Album> getAllAlbums() {
        return AlbumDAOTest.getAllAlbums();
    }

    private static Map<Character, List<Album>> groupAlbumsByInitial(List<Album> albums) {
        Map<Character, List<Album>> groups = new HashMap<>();
        for (Album album : albums) {
            char initial = album.getTitle().charAt(0);
            groups.putIfAbsent(initial, new ArrayList<>());
            groups.get(initial).add(album);
        }
        return groups;
    }

    private static Album getAlbumById(List<Album> albums, String id) {
        for (Album album : albums) {
            if (album.getId().equals(Integer.parseInt(id))) {
                return album;
            }
        }
        return null;
    }
}