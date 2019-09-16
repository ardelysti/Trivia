import java.util.*;
import java.io.*;

public class QuestionTreeBAD
{

   private UserInterface my;
   private QuestionNode overallRoot;
   private int totalPlays;
   private int wonPlays;

   public QuestionTreeBAD (UserInterface ui)
   {
      my = ui;
      
      // Initially starts with computer
      overallRoot = new QuestionNode("computer");
      
      totalPlays = 0;
      wonPlays = 0; 
   }
  
   public void play()
   {
      QuestionNode current = findAnswer(overallRoot);
      my.print("Would your object happen to be " + current.root + "?");
      boolean ans = my.nextBoolean();
      if (ans) 
      {
         my.println("I win!"); // If computer won 
         wonPlays++; 
         return;
      }
      
      // else
      my.print("I lose. What is your object? ");
      String leaf = my.nextLine(); 
      my.print("Type a yes/no question to distinguish your item from " +
                                                        answer + ":");
      String question = my.nextLine();
      my.print("And what is the answer for your object?");
      boolean route = my.nextBoolean();
      current = addNode(current, question, leaf, route);
      
      totalPlays++;
   }
   
   // helper method for play() that searches through the tree
   private QuestionNode findAnswer(QuestionNode cRoot)
   {
      // Keeps track of the node until it hits the current leaf
      if (cRoot.left == null && cRoot.right == null)
      {
         return cRoot; 
      }
      // else    
      my.print(cRoot.root); // Asks user the question node
      boolean ans = my.nextBoolean();
//*** ASK MR WOOD: can I immediately if(my.nextBoolean())?
      if (ans) 
      {
         return findAnswer(cRoot.left); // if yes go left
      }
      else return findAnswer(cRoot.right); // if no go right
   }
   
//*** ASK MR WOOD: can I do this?
   // helper method for play() that adds nodes when computer loses
   private QuestionNode addNode(QuestionNode qn, String question, String leaf, boolean route)
   {
      QuestionNode newLeaf = new QuestionNode(leaf); // subtree
      QuestionNode otherLeaf = qn; // other subtree
      
      if (route)
      {// leaf becomes the left subtree
           qn = new QuestionNode(question, newLeaf, otherLeaf);
           otherLeaf.left = null;
           otherLeaf.right = null;
      }
      else 
      {// leaf becomes the right subtree
         current = new QuestionNode(question, otherLeaf, newLeaf);
      }
   }
   
   /**
    *
    */
   public void save(PrintStream output)
   {
      save(output, overallRoot);
   }
   
   // helper method for save() that recursively prints
   private void save(PrintStream output, QuestionNode thisRoot)
   {// If thisRoot == null do nothing
      if (thisRoot != null)
      {
         output.println(thisRoot.root);
         save(output, thisRoot.left);
         save(output, thisRoot.right);
      }
   }
  
   public void load(Scanner input)
   {
      load(input, overallRoot);
   }
   
   // helper
   private void load(Scanner input, QuestionNode thisRoot)
   {
      if (input.hasNextLine())
      {
         String[] line = (input.nextLine()).split(":");
         if (line[0].equals("Q"));
      }
   }
   
   /**
    *
    */
   public int totalGames()
   {
      return totalPlays;
   }
   
   /**
    *
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