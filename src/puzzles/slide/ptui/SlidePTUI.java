package puzzles.slide.ptui;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.slide.model.SlideConfig;
import puzzles.slide.model.SlideModel;
import puzzles.slide.model.SlideClientData;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SlidePTUI implements Observer<SlideModel, SlideClientData> {
    private SlideModel model;

    public void init(String filename) throws IOException {
        this.model = new SlideModel(filename);
        this.model.addObserver(this);
        System.out.println("Loaded: " + filename);
        System.out.println(model.getCurrentConfig());
        displayHelp();
    }

    @Override
    public void update(SlideModel model, SlideClientData data) {
        System.out.println(model.getCurrentConfig());
    }

    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    public void run() {
        Scanner in = new Scanner( System.in );
        for ( ; ; ) {
            System.out.print( "> " );
            String line = in.nextLine();
            String[] words = line.split( "\\s+" );
            if (words.length > 0) {
                if (words[0].startsWith( "q" )) {
                    break;}

                else if (words[0].startsWith("l")){
                    try{
                        model = new SlideModel(words[1]);
                        System.out.println("Loaded: " + words[1]);
                        System.out.println(model.getCurrentConfig());
                    } catch(Exception E){displayHelp();}}

                else if (words[0].startsWith("s")){
                    try{
                        int row = Integer.parseInt(words[1]);
                        int col = Integer.parseInt(words[2]);
                        int result = model.makeMove(row, col);
                        if (result == -1) System.out.println("Invalid selection (" + words[1] + ", " + words[2] + ")");
                        else if (result == 0) System.out.println("Selected (" + words[1] + ", " + words[2] + ")");
                        else if (result == 1){
                            int[] previous = model.getSelected();
                            System.out.println("Moved from " + "(" + previous[0] + ", " + previous[1] +
                                    ") to (" + words[1] + ", " + words[2] + ")");
                        }
                        System.out.println(model.getCurrentConfig());
                    } catch(Exception E){displayHelp();}}

                else if (words[0].startsWith("h")){
                    Solver solver = new Solver();
                    List<Configuration> path = solver.solve(model.getCurrentConfig());
                    if (model.getCurrentConfig().isSolution()) {
                        System.out.println("Already Solved");
                        System.out.println(model.getCurrentConfig());
                    }
                    else if (path.isEmpty()) System.out.println("No solution");
                    else {
                        try{
                            System.out.println("Next Step");
                            SlideConfig s = (SlideConfig) path.get(1);
                            model.getCurrentConfig().setGrid(s.copyGrid());
                            System.out.println(model.getCurrentConfig());}
                        catch (Exception E) {}
                    }
                }

                else if (words[0].startsWith("r")){
                    model.resetPuzzle();
                    System.out.println("Puzzle Reset");
                    System.out.println(model.getCurrentConfig());
                }

                else {
                    displayHelp();
                }

            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java SlidePTUI filename");
        } else {
            try {
                SlidePTUI ptui = new SlidePTUI();
                ptui.init(args[0]);
                ptui.run();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}

