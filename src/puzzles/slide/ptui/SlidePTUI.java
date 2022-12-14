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

/**
 * PTUI for slide puzzle
 *
 * @author Trent Wesley taw8452
 */
public class SlidePTUI implements Observer<SlideModel, SlideClientData> {
    private SlideModel model;

    /**
     * Initialize PTUI
     * @param filename name of file
     * @throws IOException
     */
    public void init(String filename) throws IOException {
        this.model = new SlideModel(filename);
        this.model.addObserver(this);
        System.out.println("Loaded: " + filename);
        System.out.println(model);
        displayHelp();
    }

    /**
     * update the PTUI view
     * @param model model that view is based on
     * @param data optional data the server.model can send to the observer
     */
    @Override
    public void update(SlideModel model, SlideClientData data) {
        System.out.println(data.data);
        System.out.println(model);
    }

    /**
     * Display help
     */
    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    /**
     * Run PTUI program
     */
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
                        this.model.addObserver(this);
                        System.out.println("Loaded: " + words[1]);
                        System.out.println(model);
                        displayHelp();
                    } catch(Exception E){displayHelp();}}

                else if (words[0].startsWith("s")){
                    try{
                        int row = Integer.parseInt(words[1]);
                        int col = Integer.parseInt(words[2]);
                        model.makeMove(row, col);
                    } catch(Exception E){displayHelp();}}

                else if (words[0].startsWith("h")){
                    model.hint();}

                else if (words[0].startsWith("r")){
                    model.resetPuzzle();}

                else {
                    displayHelp();}
            }
        }
    }

    /**
     * get filename and run PTUI
     * @param args filename
     */
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

