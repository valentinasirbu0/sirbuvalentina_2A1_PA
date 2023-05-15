package org.example.Algorithms;

import gurobi.*;
import org.graph4j.Digraph;
import org.graph4j.GraphBuilder;

import java.util.Random;

public class TournamentOrganizer {
    public static int numPlayers;
    public int maxGamesPerDay;
    public int maxTournamentDays;
    static GRBVar[][] games;
    GRBModel model;
    static Digraph graph;

    public TournamentOrganizer(int numPlayers, int maxGamesPerDay, int maxTournamentDays) {
        TournamentOrganizer.numPlayers = numPlayers;
        this.maxGamesPerDay = maxGamesPerDay;
        this.maxTournamentDays = maxTournamentDays;
        graph = GraphBuilder.numVertices(numPlayers).buildDigraph();
    }

    public void organizeTournament() {
        try {
            System.setProperty("GRB_LICENSE_FILE", "C:\\gurobi1000\\gurobi.lic");

            // Create the Gurobi environment
            GRBEnv env = new GRBEnv();

            // Create the Gurobi model
            model = new GRBModel(env);

            // Define the decision variables
            games = new GRBVar[numPlayers][numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                for (int j = 0; j < numPlayers; j++) {
                    games[i][j] = model.addVar(0, 1, 0, GRB.BINARY, "game_" + i + "_" + j);
                }
            }

            // Define the constraints
            addPlayerConstraints();
            addGameConstraints();
            addDayConstraints();

            // Set up the Gurobi model

            // Optimize the model
            model.optimize();

            // Extract and interpret the solution
            if (model.get(GRB.IntAttr.Status) == GRB.OPTIMAL) {
                extractSolution();
            } else {
                System.out.println("No feasible solution found.");
            }

            // Free up resources
            model.dispose();
            env.dispose();

        } catch (GRBException e) {
            e.printStackTrace();
        }
    }

    private void addPlayerConstraints() {
        // Each player plays exactly once against each other player
        for (int i = 0; i < numPlayers; i++) {
            GRBLinExpr expr = new GRBLinExpr();
            for (int j = 0; j < numPlayers; j++) {
                if (i != j) {
                    expr.addTerm(1, games[i][j]);
                }
            }
            try {
                model.addConstr(expr, GRB.EQUAL, 1, "player_" + i + "_constraint");
            } catch (GRBException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void addGameConstraints() {
        // Each player has at most p games in a day
        for (int i = 0; i < numPlayers; i++) {
            for (int day = 0; day < maxTournamentDays; day++) {
                GRBLinExpr expr = new GRBLinExpr();
                for (int j = 0; j < numPlayers; j++) {
                    if (i != j) {
                        expr.addTerm(1, games[i][j]);
                    }
                }
                try {
                    model.addConstr(expr, GRB.LESS_EQUAL, maxGamesPerDay, "player_" + i + "_day_" + day + "_constraint");
                } catch (GRBException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void extractSolution() {
        System.out.println("Tournament Schedule:");

        for (int i = 0; i < numPlayers; i++) {
            for (int j = 0; j < numPlayers; j++) {
                if (i != j && !graph.containsEdge(i, j) && !graph.containsEdge(j, i)) {
                    // Check if the game variable is set to 1 in the solution
                    try {
                        if (games[i][j].get(GRB.DoubleAttr.X) > 0.5) {
                            System.out.println("Player " + i + " vs Player " + j);
                            boolean outcome = new Random().nextBoolean();
                            if (outcome) {
                                graph.addEdge(i, j);
                            } else {
                                graph.addEdge(j, i);
                            }
                        }
                    } catch (GRBException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void addDayConstraints() {
        // The tournament finishes in at most d days
        for (int day = 0; day < maxTournamentDays; day++) {
            GRBLinExpr expr = new GRBLinExpr();
            for (int i = 0; i < numPlayers; i++) {
                for (int j = 0; j < numPlayers; j++) {
                    if (i != j) {
                        expr.addTerm(1, games[i][j]);
                    }
                }
            }
            try {
                model.addConstr(expr, GRB.LESS_EQUAL, maxGamesPerDay * (numPlayers - 1), "day_" + day + "_constraint");
            } catch (GRBException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main() {
        TournamentOrganizer organizer = new TournamentOrganizer(10, 2, 5); // Example parameters
        organizer.organizeTournament();
    }
}
