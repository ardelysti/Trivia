import java.util.*;
import java.io.*;
/**
 * This is the QuestionTree class that keeps track of nodes
 * for the QuestionMain to use.
 *
 * @author Pande N Ardelysti Kardi
 * @version 12/3/2018
 */
public class QuestionTree
{

   private UserInterface my;
   private QuestionNode overallRoot;
   private int totalPlays;
   private int wonPlays;

   /**
    * This is the constructor that initializes the private field
    * values
    *
    * @param ui the type of user interface used
    */
   public QuestionTree (UserInterface ui)
   {
      my = ui;
      
      // Initially starts with computer
      overallRoot = new QuestionNode("computer");
      
      totalPlays = 0;
      wonPlays = 0; 
   }
   
   private boolean isLeaf(QuestionNode cRoot)
   {
      if (cRoot.left == null && cRoot.right == null)
         return true;
      return false;
   }
  
   /**
    * The method that interacts and plays the game with the user
    * to make the program an AI
    */
   public void play()
   {
      overallRoot = findAnswer(overallRoot);
      
      totalPlays++;
   }
   
   // helper method for play() that searches through the tree
   private QuestionNode findAnswer(QuestionNode cRoot)
   {
      if ( isLeaf(cRoot) )
      {
         my.print("Would your object happen to be " + cRoot.root + "?");
         boolean user = my.nextBoolean();
         
         if (user) // if the leaf is the answer
         {
            my.println("I win!");
            wonPlays++;
         }
         else // if the leaf is not the asnwer
         {
            my.print("I lose. What is your object?");
            String item = my.nextLine().trim(); // cuts whitespace
            my.print("Type a yes/no question to distinguish your item from "
                     + cRoot.root + ":");
            String newQ = my.nextLine().trim();
            my.print("And what is the answer for your object?");
            boolean route = my.nextBoolean();
            
            cRoot = makeSmarter(cRoot, newQ, item, route);
         }
      }
      else
      {
         my.print(cRoot.root); // Asks the current question node  
         boolean user = my.nextBoolean();
         
         
         //********** THIS WAS THE HARDEST TO FIGURE OUT **********\\
         if(user) // if yes, go left
         {
            cRoot.left = findAnswer(cRoot.left); 
         }
         else // if no, go right
         {
            cRoot.right = findAnswer(cRoot.right);
         }
      }
      
      // returns the root recursively to form the new overallTree
      return cRoot;
   }
   
   // helper method for play() that adds nodes when computer loses
   private QuestionNode makeSmarter(QuestionNode qn, String q, String leaf, boolean route)
   {
      QuestionNode temp = new QuestionNode(q); 
      if (route) // if leaf goes to the left of question node
      {
         temp.left = new QuestionNode(leaf);
         temp.right = qn;
      }
      else // if leaf goes to the right of the question node
      {
         temp.right = new QuestionNode(leaf);
         temp.left = qn;
      }
      
      return temp;
   }
   
   /**
    * This method saves the game's tree into an external .txt file
    *
    * @param output the PrintStream with the .txt file it will write to
    */
   public void save(PrintStream output)
   {
      save(output, overallRoot);
   }
   
   // helper method for save() that recursively prints into external .txt file
   private void save(PrintStream output, QuestionNode thisRoot)
   {// If thisRoot == null do nothing
      String a = "A:";
      String q = "Q:";
      
      // || thisRoot.right != null
      if (!isLeaf(thisRoot)) // if thisRoot is a quesiton
      {
         output.println( q + thisRoot.root);
         save(output, thisRoot.left);
         save(output, thisRoot.right);
      } // if this root is an answer/leaf
      else output.println( a + thisRoot.root);
   }
   
   /**
    * This method extracts the contents of an external .txt file to
    * be used for the game
    *
    * @param input the Scanner that will read from the .txt file
    */
   public void load(Scanner input)
   {
      overallRoot = load(input, overallRoot);
   }
   
   // helper method for load() that recursively reads from external .txt file
   private QuestionNode load(Scanner input, QuestionNode cRoot)
   {
      if ( input.hasNextLine() )
      {
         String[] line = input.nextLine().split(":");
         cRoot = new QuestionNode(line[1]);
         
         if ( line[0].equals("Q") )
         {
             cRoot.left = load(input, cRoot);
             cRoot.right = load(input, cRoot);
         }
         // if it's an answer, do nothing further
      }
      
      return cRoot; //
   }
   
   /**
    * This method returns the total number of games played
    *
    * @return total games played with user
    */
   public int totalGames()
   {
      return totalPlays;
   }
   
   /**
    * This method returns the total number of games won by
    * the computer
    *
    * @return total games won by computer
    */
   public int gamesWon()
   {
      return wonPlays;
   }
  
   //**** THE QuestionNode CLASS ****\\
   private class QuestionNode
   {
      private String root;
      private QuestionNode left;
      private QuestionNode right;
      
      private QuestionNode(String data)
      {
         this(data, null, null);
      }  
      
      private QuestionNode(String data, QuestionNode left,
                                   QuestionNode right)
      {
         this.root = data;
         this.left = left;
         this.right = right;
      }
   }
}