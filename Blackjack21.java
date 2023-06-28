import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;
public class Blackjack21
{
   static String message = null;
   static int munny = 1000;
   static int deckcount = 6;
   static int totalcards = 52*deckcount;
   static int penetration = totalcards / 4;
   static int cardcount = totalcards;
   static int runningcount = 0;
   static double truecount = 0.0;
   static double decksleft = deckcount;
   static int [] cards = new int[totalcards];
   
   //public void getClassName() 
   //{
      //String className = this.getClass().getSimpleName(); 
      //System.out.println("Name:" + className);
   //}
   
   public static void main(String [] args)
   {  
      //Blackjack21 t = new BlackJack21();
      //t.getClassName();
      shuffle();
      int bet = 0;
      while(munny > 50)
      {  
         boolean acceptableBet = false;
         while(acceptableBet == false)
         {   
            bet = payBox();
            if (bet <= munny)
            {
               acceptableBet = true;
            }
            else
            {
               JOptionPane.showMessageDialog(null, "Insufficient munny!", "Message", -1);
            }
         }
         munny = munny - bet;
         int result = 0;
         result = logic(bet);
         munny = munny + result;       
         if(munny < 100)
         {
            JOptionPane.showMessageDialog(null, "Out of munny! Try again next time! \n Terminating", "Message", -1);
         }
      }
   }
   public static void recommend(int strategy)
   {
           System.out.print("Recommendation: "); 
           if(strategy == 0)
           {
               System.out.println("Hit");
           }
           else if(strategy == 1)
           {
               System.out.println("Stand");
           }
           else if(strategy == 2)
           {
               System.out.println("Double");
           }
           else if(strategy == 3)
           {
               System.out.println("Split");
           }
           else
           {
               System.out.println("Error: " + strategy);
           }
   }
   public static void shuffle()
   {
      for(int i = 0; i < cards.length; i++)
      {
         cards[i] = i+1;
      }
      cardcount = totalcards;
   }
   public static int logic(int bet)
   {
     int pl = 0;
     boolean plsc = false;
     boolean asc = false;
     boolean bsc = false;
     boolean instapl = false;   
     int reward = 0;   
     int a = card();
     int b = card(); 
     //int a = 1;
     //int b = 1;
            
     pl = a + b;
     if(a == 1 || b == 1)
     {
         plsc = true;
         pl = pl + 10;
     }    
     if(a == 1 && b == 10)
     {
         instapl = true;
     }
     if(a == 10 && b == 1)
     {
         instapl = true;
     }
     boolean desc = false;
     boolean instade = false;
     int de = 0;
     int c = card();
     int g = card();
     de = c;
     int insure = -1;    
     if(c == 1)
     {
         desc = true;
         de = de + 10;
     }    
     message = (" Player: " + "    " + a + "  " + b + 
               "\n Dealer: " + soft(desc) + " (" + c + ") ");              
     JOptionPane.showMessageDialog(null, message, "Message", -1);   
     message = (" Player: " + soft(plsc) + " (" + pl + 
                ") \n Dealer: " + soft(desc) + " (" + de + ") "); 
     if(c == 1 && instapl == false)
     { 
         int i = insurance();
         if (i == 0)
         {
               if ((munny - (bet / 2)) >= 0)
               {
                  munny = munny - (bet / 2);
                  insure = i;
               }
               else
               {
                   JOptionPane.showMessageDialog(null, "Insufficient munny!", "Message", -1);
               }
         }               
         if (g == 10)
         {
            instade = true;
         }
     }
     if(a == 1)
     {
         asc = true;
         a = a + 10;
     }    
     if(b == 1)
     {
         bsc = true;
         b = b + 10;
     }   
     boolean doub = false;
     boolean splitready = false;
     if (a == b)
     {
         splitready = true;
     }
     boolean split = false;    
     int turn = 0;
     while(pl <= 21 && instapl == false && instade == false)
     { 
        turn++;
        int inp = -1;
        int strategy = strat(splitready, plsc, turn, pl, de);
        recommend(strategy);
        //System.out.println(strategy);
        if(turn == 1)
        {
          inp = firstoptionBox(splitready);
          
          if (inp == 2)
          {
            if ((munny - bet) >= 0)
            {
               munny = munny - bet;
               doub = true;
            }
            else
            {
               JOptionPane.showMessageDialog(null, "Insufficient munny!", "Message", -1);
               inp = 4;
            }
          }
          if (inp == 3)
          {
            if ((munny - bet) >= 0)
            {
               munny = munny - bet;
               split = true;
            }
            else
            {
               JOptionPane.showMessageDialog(null, "Insufficient munny!", "Message", -1);
               inp = 4;
            }
          }
        } 
        if (doub == false && split == false)
        {
           int in = -1;
           if(turn == 1 && inp != 4)
           {
              in = inp;
           }
           else
           {
              in = optionBox();
           }
           if(in == 0)
           {
              int d = card();
              if (d == 1 && plsc == false && pl < 11)
              {
                  plsc = true;
                  pl = pl + 10;
              }
              pl = pl + d;                      
              if(plsc == true && pl > 21)
              {
                  pl = pl - 10;
                  plsc = false;
              }            
              message = (" Player: " + soft(plsc) + " (" + pl + ") " + d + 
                       "\n Dealer: " + soft(desc) + " (" + de + ") ");             
           }
           else if (in == 1)
           {
               if(plsc == true && pl < 11)
               {
                  pl = pl + 10;
               }
               break;
           }
        }
        else if (doub == true)
        {
              int d = card();
              if (d == 1 && plsc == false && pl < 11)
              {
                  plsc = true;
                  pl = pl + 10;
              }
              pl = pl + d;
                       
              if(plsc == true && pl > 21)
              {
                  pl = pl - 10;
                  plsc = false;
              }             
              message = (" Player: " + soft(plsc) + " (" + pl + ") " + d + 
                       "\n Dealer: " + soft(desc) + " (" + de + ") ");
              JOptionPane.showMessageDialog(null, message, "Message", -1);
              break;
        }
        else
        {
          break;
        }
     }
     if(split == true)
     {
         message = (" P Hand 1: ->" + soft(asc) + " (" + a + ") " +
                  "\n P Hand 2:   " + soft(bsc) + " (" + b + ") " +
                    "\n Dealer:       " + soft(desc) + " (" + de + ") ");
         JOptionPane.showMessageDialog(null, message, "Message", -1);        
         while(a <= 21)
         {
           int strategy = strat(false, asc, 2, a, de);
           recommend(strategy);
           //System.out.println(strategy);
           int in = optionBox();
           if(in == 0)
           {
              int d = card();
              if (d == 1 && asc == false && a < 11)
              {
                  asc = true;
                  a = a + 10;
              }
              a = a + d;                      
              if(asc == true && a > 21)
              {
                  a = a - 10;
                  asc = false;
              }             
              message = (" P Hand 1: ->" + soft(asc) + " (" + a + ") " + d +
                       "\n P Hand 2:   " + soft(bsc) + " (" + b + ") " +
                     "\n Dealer:       " + soft(desc) + " (" + de + ") ");
              
           }
           else if (in == 1)
           {
               if(asc == true && a < 11)
               {
                  a = a + 10;
               }
               break;
           }
         }
                       message = (" P Hand 1:   " + soft(asc) + " (" + a + ") " +
                       "\n P Hand 2: ->" + soft(bsc) + " (" + b + ") " +
                     "\n Dealer:       " + soft(desc) + " (" + de + ") ");
                     JOptionPane.showMessageDialog(null, message, "Message", -1);  
         
               
         while(b <= 21)
         {
           int strategy = strat(false, bsc, 2, b, de);
           recommend(strategy);
           //System.out.println(strategy);
           int in = optionBox();
           if(in == 0)
           {
              int d = card();
              if (d == 1 && bsc == false && b < 11)
              {
                  bsc = true;
                  b = b + 10;
              }
              b = b + d;
                       
              if(bsc == true && b > 21)
              {
                  b = b - 10;
                  bsc = false;
              }            
              message = (" P Hand 1:   " + soft(asc) + " (" + a + ") " +
                       "\n P Hand 2: ->" + soft(bsc) + " (" + b + ") " + d +
                     "\n Dealer:       " + soft(desc) + " (" + de + ") ");         
           }
           else if (in == 1)
           {
               if(bsc == true && b < 11)
               {
                  b = b + 10;
               }
               break;
           }
         }                 
     }    
     int decount = 0;
     while(de <= 16 && instade == false)
     {
        decount++;
        int e;
        if(decount == 1)
        {
            e = g;
        }
        else
        {
            e = card();
        }
        if (e == 1 && desc == false && de < 11)
        {
               desc = true;
               de = de + 10;
        }
        de = de + e;      
        if(decount == 1 && de == 21)
        {
            instade = true;
            message = (" Player: " + soft(plsc) + " (" + pl + 
                   ") \n Dealer: " + soft(desc) + " (" + de + ") " + e);
                   
            JOptionPane.showMessageDialog(null, message, "Message", -1);         
            break;
        }       
        if(desc == true && de > 21)
        {
           de = de - 10;
           desc = false;
        }      
        if(split == false)
        {
            message = (" Player: " + soft(plsc) + " (" + pl + 
               ") \n Dealer: " + soft(desc) + " (" + de + ") " + e);              
        }
        else
        {
              message = (" P Hand 1: " + soft(asc) + " (" + a + ") " +
                       "\n P Hand 2: " + soft(bsc) + " (" + b + ") " +
                     "\n Dealer:     " + soft(desc) + " (" + de + ") " + e);
        }              
        JOptionPane.showMessageDialog(null, message, "Message", -1);
     }     
     if(split == false)
     {
        if(instapl == true && instade == false)
        {
           reward = ((bet * 5) / 2);
           message = ("Win big!     +" + reward);
        }       
        else if(instapl == true && instade == true)
        {
           reward = bet; 
           message = ("Blackjack tie     +" + reward);
        }      
        else if(instapl == false && instade == true)
        {
           reward = bet * 0;
           message = ("Blackjack lose     +" + reward);
           if (insure == 0)
           {
               reward = (bet * 3) / 2;
               message = ("Blackjack lose, insurance gained!     +" + reward);
           }
        }      
        else if(pl <= 21 && pl > de)
        {
           reward = bet * 2;
           message = ("Win!     +" + reward);
           if (doub == true)
           {
             reward = bet * 4;
             message = ("Double Win!     +" + reward);
           }
        }      
        else if(pl <= 21 && de > 21)
        {
           reward = bet * 2;
           message = ("Win!     +" + reward);
           if (doub == true)
           {
             reward = bet * 4;
             message = ("Double Win!     +" + reward);
           }
        }       
        else if(pl <= 21 && de == pl)
        {
          reward = bet;
          message = ("Tie.     +" + reward);
          if (doub == true)
          {
            reward = bet * 2;
            message = ("Double Tie.     +" + reward);
          }
        }      
        else
        {
          reward = bet * 0;
          message = ("Lose     +" + reward);
        }
     }
     else
     {
        int astatus = -1;
        int bstatus = -1;  
        if(a <= 21 && a > de)
        {
           astatus = 20;
        }       
        else if(a <= 21 && de > 21)
        {
           astatus = 20;
        }        
        else if(a <= 21 && de == a)
        {
          astatus = 10;
        }       
        else
        {
          astatus = 0;
        }      
        if(b <= 21 && b > de)
        {
           bstatus = 2;
        }        
        else if(b <= 21 && de > 21)
        {
           bstatus = 2;
        }       
        else if(b <= 21 && de == b)
        {
          bstatus = 1;
        }      
        else
        {
          bstatus = 0;
        }
       int pairstatus = astatus + bstatus;
       if(pairstatus == 22)
       {
         reward = bet * 4;
         message = ("Hand 1 and Hand 2 Wins!     +" + reward);
       }
       else if(pairstatus == 21)
       {
         reward = bet * 3;
         message = ("Hand 1 Wins! Hand 2 Ties.     +" + reward);
       }
       else if(pairstatus == 20)
       {
         reward = bet * 2;
         message = ("Hand 1 Wins! Hand 2 Loses     +" + reward);
       }
       else if(pairstatus == 12)
       {
         reward = bet * 3;
         message = ("Hand 1 Ties. Hand 2 Wins!     +" + reward);
       }
       else if(pairstatus == 11)
       {
         reward = bet * 2;
         message = ("Hand 1 Ties. Hand 2 Ties.     +" + reward);
       }
       else if(pairstatus == 10)
       {
         reward = bet;
         message = ("Hand 1 Ties. Hand 2 Loses     +" + reward);
       }
       else if(pairstatus == 2)
       {
         reward = bet * 2;
         message = ("Hand 1 Loses Hand 2 Wins!     +" + reward);
       }
       else if(pairstatus == 1)
       {
         reward = bet * 1;
         message = ("Hand 1 Loses Hand 2 Ties.     +" + reward);
       }
       else
       {
         reward = bet * 0;
         message = ("Hand 1 and Hand 2 Loses     +" + reward);
       }
     }    
     JOptionPane.showMessageDialog(null, message, "Message", -1);
     if(cardcount < penetration)
     {
         shuffle();
         runningcount = 0;
         truecount = 0.0;
     }
     return reward;
   }  
   /*public static int card()      //infinite decks
   {
      Random rand = new Random();
      int n = rand.nextInt(13) + 1;
      if(n >= 10)
      {
         n = 10;
      }
      return n;
   }*/
   public static int card()       //finite decks
   {
      while(true)
      {
         Random rand = new Random();
         int n = rand.nextInt(totalcards) + 1;
         for(int i = 0; i < cards.length; i++)
         {
            if(n == cards[i])
            {
               cards[i] = 0;
               cardcount--;
               n = n%13;
               if(n == 0 || n > 10) {n = 10;}
               if(n >= 2 && n <= 6)  {runningcount++;}
               if(n == 1 || n == 10) {runningcount--;}
               decksleft = cardcount/52.0;
               truecount = runningcount / decksleft;             
               System.out.println(n + " " + cardcount + " " + decksleft + " " + runningcount + " " + truecount);
               return n;
            }
         }
      }
   }   
   public static int firstoptionBox(boolean a)
   {
        if(a == true)
        {
           Object[] options = { "Hit", "Stand", "Double", "Split"};
           int option = (int)JOptionPane.showOptionDialog(null, message, "Blackjack", -1, -1, null, options, options[0]);
           if(option < 0)
           {
            JOptionPane.showMessageDialog(null, "Terminating", "Message", -1);
            System.exit(0);
           }
           return option;
        }
        else
        {
           Object[] options = { "Hit", "Stand", "Double"};
           int option = (int)JOptionPane.showOptionDialog(null, message, "Blackjack", -1, -1, null, options, options[0]);
           if(option < 0)
           {
            JOptionPane.showMessageDialog(null, "Terminating", "Message", -1);
            System.exit(0);
           }
           return option;
        }
   }
   public static int optionBox()
   {
        Object[] options = { "Hit", "Stand" };
        int option = (int)JOptionPane.showOptionDialog(null, message, "Blackjack", -1, -1, null, options, options[0]);
        if(option < 0)
        {
            JOptionPane.showMessageDialog(null, "Terminating", "Message", -1);
            System.exit(0);
        }
        return option;
   }
   public static int payBox()
   {

        
        Object[] options = {"100", "200", "500", "1000"};
        //Object[] options = {100, 200, 500, 1000};
        int option = (int)JOptionPane.showOptionDialog(null, "Current munny: " + munny + "\n Place your bets!", "Blackjack", -1, -1, null, options, options[0]);
        if(option < 0)
        {
            JOptionPane.showMessageDialog(null, "Terminating", "Message", -1);
            System.exit(0);
        }
        int choice = 0;
        if (option == 0)
        {
            choice = 100;
        }
        if (option == 1)
        {
            choice = 200;
        }
        if (option == 2)
        {
            choice = 500;
        }
        if (option == 3)
        {
            choice = 1000;
        }
        return choice;
        //return (int) options[option];
   }  
   public static int insurance()
   {
        Object[] options = { "Yes", "No" };
        int option = (int)JOptionPane.showOptionDialog(null, message + "\n Insurance? : ", "Blackjack", -1, -1, null, options, options[0]);
        if(option < 0)
        {
            JOptionPane.showMessageDialog(null, "Terminating", "Message", -1);
            System.exit(0);
        }
        return option;
   }
   public static String soft(boolean a)
   {
     String s = null;
     if(a == true)
     {
         s = "Soft ";
     }
     else
     {
         s = "     ";
     }
     return s;
   }
   
                         //splitready    soft    turn   player dealer
   public static int strat(boolean r, boolean s, int t, int p, int d)
   {
      //if(truecount >= 5 && p == 16 && d == 9) {}
   
   
      if(t > 1)
      {
         r = false;
      }
      if (r == true)
      {
         if(s == true && t == 1 && p == 12 && d == 2)  {return 3;}
         if(s == true && t == 1 && p == 12 && d == 3)  {return 3;}
         if(s == true && t == 1 && p == 12 && d == 4)  {return 3;}
         if(s == true && t == 1 && p == 12 && d == 5)  {return 3;}
         if(s == true && t == 1 && p == 12 && d == 6)  {return 3;}
         if(s == true && t == 1 && p == 12 && d == 7)  {return 3;}
         if(s == true && t == 1 && p == 12 && d == 8)  {return 3;}
         if(s == true && t == 1 && p == 12 && d == 9)  {return 3;}
         if(s == true && t == 1 && p == 12 && d == 10) {return 3;}
         if(s == true && t == 1 && p == 12 && d == 11) {return 3;}
         
         if(s == false && t == 1 && p == 4 && d == 2)  {return 0;}
         if(s == false && t == 1 && p == 4 && d == 3)  {return 0;}
         if(s == false && t == 1 && p == 4 && d == 4)  {return 3;}
         if(s == false && t == 1 && p == 4 && d == 5)  {return 3;}
         if(s == false && t == 1 && p == 4 && d == 6)  {return 3;}
         if(s == false && t == 1 && p == 4 && d == 7)  {return 3;}
         if(s == false && t == 1 && p == 4 && d == 8)  {return 0;}
         if(s == false && t == 1 && p == 4 && d == 9)  {return 0;}
         if(s == false && t == 1 && p == 4 && d == 10) {return 0;}
         if(s == false && t == 1 && p == 4 && d == 11) {return 0;}
         
         if(s == false && t == 1 && p == 6 && d == 2)  {return 0;}
         if(s == false && t == 1 && p == 6 && d == 3)  {return 0;}
         if(s == false && t == 1 && p == 6 && d == 4)  {return 3;}
         if(s == false && t == 1 && p == 6 && d == 5)  {return 3;}
         if(s == false && t == 1 && p == 6 && d == 6)  {return 3;}
         if(s == false && t == 1 && p == 6 && d == 7)  {return 3;}
         if(s == false && t == 1 && p == 6 && d == 8)  {return 0;}
         if(s == false && t == 1 && p == 6 && d == 9)  {return 0;}
         if(s == false && t == 1 && p == 6 && d == 10) {return 0;}
         if(s == false && t == 1 && p == 6 && d == 11) {return 0;}
         
         if(s == false && t == 1 && p == 8 && d == 2)  {return 0;}
         if(s == false && t == 1 && p == 8 && d == 3)  {return 0;}
         if(s == false && t == 1 && p == 8 && d == 4)  {return 0;}
         if(s == false && t == 1 && p == 8 && d == 5)  {return 0;}
         if(s == false && t == 1 && p == 8 && d == 6)  {return 0;}
         if(s == false && t == 1 && p == 8 && d == 7)  {return 0;}
         if(s == false && t == 1 && p == 8 && d == 8)  {return 0;}
         if(s == false && t == 1 && p == 8 && d == 9)  {return 0;}
         if(s == false && t == 1 && p == 8 && d == 10) {return 0;}
         if(s == false && t == 1 && p == 8 && d == 11) {return 0;}
         
         if(s == false && t == 1 && p == 10 && d == 2)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 3)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 4)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 5)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 6)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 7)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 8)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 9)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 10) {return 0;}
         if(s == false && t == 1 && p == 10 && d == 11) {return 0;}
         
         if(s == false && t == 1 && p == 12 && d == 2)  {return 0;}
         if(s == false && t == 1 && p == 12 && d == 3)  {return 3;}
         if(s == false && t == 1 && p == 12 && d == 4)  {return 3;}
         if(s == false && t == 1 && p == 12 && d == 5)  {return 3;}
         if(s == false && t == 1 && p == 12 && d == 6)  {return 3;}
         if(s == false && t == 1 && p == 12 && d == 7)  {return 0;}
         if(s == false && t == 1 && p == 12 && d == 8)  {return 0;}
         if(s == false && t == 1 && p == 12 && d == 9)  {return 0;}
         if(s == false && t == 1 && p == 12 && d == 10) {return 0;}
         if(s == false && t == 1 && p == 12 && d == 11) {return 0;}
         
         if(s == false && t == 1 && p == 14 && d == 2)  {return 3;}
         if(s == false && t == 1 && p == 14 && d == 3)  {return 3;}
         if(s == false && t == 1 && p == 14 && d == 4)  {return 3;}
         if(s == false && t == 1 && p == 14 && d == 5)  {return 3;}
         if(s == false && t == 1 && p == 14 && d == 6)  {return 3;}
         if(s == false && t == 1 && p == 14 && d == 7)  {return 3;}
         if(s == false && t == 1 && p == 14 && d == 8)  {return 0;}
         if(s == false && t == 1 && p == 14 && d == 9)  {return 0;}
         if(s == false && t == 1 && p == 14 && d == 10) {return 0;}
         if(s == false && t == 1 && p == 14 && d == 11) {return 0;}
         
         if(s == false && t == 1 && p == 16 && d == 2)  {return 3;}
         if(s == false && t == 1 && p == 16 && d == 3)  {return 3;}
         if(s == false && t == 1 && p == 16 && d == 4)  {return 3;}
         if(s == false && t == 1 && p == 16 && d == 5)  {return 3;}
         if(s == false && t == 1 && p == 16 && d == 6)  {return 3;}
         if(s == false && t == 1 && p == 16 && d == 7)  {return 3;}
         if(s == false && t == 1 && p == 16 && d == 8)  {return 3;}
         if(s == false && t == 1 && p == 16 && d == 9)  {return 3;}
         if(s == false && t == 1 && p == 16 && d == 10) {return 3;}
         if(s == false && t == 1 && p == 16 && d == 11) {return 3;}
         
         if(s == false && t == 1 && p == 18 && d == 2)  {return 3;}
         if(s == false && t == 1 && p == 18 && d == 3)  {return 3;}
         if(s == false && t == 1 && p == 18 && d == 4)  {return 3;}
         if(s == false && t == 1 && p == 18 && d == 5)  {return 3;}
         if(s == false && t == 1 && p == 18 && d == 6)  {return 3;}
         if(s == false && t == 1 && p == 18 && d == 7)  {return 0;}
         if(s == false && t == 1 && p == 18 && d == 8)  {return 3;}
         if(s == false && t == 1 && p == 18 && d == 9)  {return 3;}
         if(s == false && t == 1 && p == 18 && d == 10) {return 1;}
         if(s == false && t == 1 && p == 18 && d == 11) {return 1;}
         
         if(s == false && t == 1 && p == 20 && d == 2)  {return 1;}
         if(s == false && t == 1 && p == 20 && d == 3)  {return 1;}
         if(s == false && t == 1 && p == 20 && d == 4)  {return 1;}   
         if(s == false && t == 1 && p == 20 && d == 5 && truecount >= 5.0)  {System.out.println("D: Split on 5+"); return 3;}
         if(s == false && t == 1 && p == 20 && d == 5)  {return 1;} //Split on 5+
         if(s == false && t == 1 && p == 20 && d == 6 && truecount >= 5.0)  {System.out.println("D: Split on 5+"); return 3;}
         if(s == false && t == 1 && p == 20 && d == 6)  {return 1;} //Split on 5+
         
         if(s == false && t == 1 && p == 20 && d == 7)  {return 1;}
         if(s == false && t == 1 && p == 20 && d == 8)  {return 1;}
         if(s == false && t == 1 && p == 20 && d == 9)  {return 1;}
         if(s == false && t == 1 && p == 20 && d == 10) {return 1;}
         if(s == false && t == 1 && p == 20 && d == 11) {return 1;}
         
       }
       else
       {  
         if(s == true && t == 1 && p == 13 && d == 2)  {return 0;}
         if(s == true && t == 1 && p == 13 && d == 3)  {return 0;}
         if(s == true && t == 1 && p == 13 && d == 4)  {return 0;}
         if(s == true && t == 1 && p == 13 && d == 5)  {return 2;}
         if(s == true && t == 1 && p == 13 && d == 6)  {return 2;}
         if(s == true && t == 1 && p == 13 && d == 7)  {return 0;}
         if(s == true && t == 1 && p == 13 && d == 8)  {return 0;}
         if(s == true && t == 1 && p == 13 && d == 9)  {return 0;}
         if(s == true && t == 1 && p == 13 && d == 10) {return 0;}
         if(s == true && t == 1 && p == 13 && d == 11) {return 0;}
         
         if(s == true && t == 1 && p == 14 && d == 2)  {return 0;}
         if(s == true && t == 1 && p == 14 && d == 3)  {return 0;}
         if(s == true && t == 1 && p == 14 && d == 4)  {return 0;}
         if(s == true && t == 1 && p == 14 && d == 5)  {return 2;}
         if(s == true && t == 1 && p == 14 && d == 6)  {return 2;}
         if(s == true && t == 1 && p == 14 && d == 7)  {return 0;}
         if(s == true && t == 1 && p == 14 && d == 8)  {return 0;}
         if(s == true && t == 1 && p == 14 && d == 9)  {return 0;}
         if(s == true && t == 1 && p == 14 && d == 10) {return 0;}
         if(s == true && t == 1 && p == 14 && d == 11) {return 0;}
         
         if(s == true && t == 1 && p == 15 && d == 2)  {return 0;}
         if(s == true && t == 1 && p == 15 && d == 3)  {return 0;}
         if(s == true && t == 1 && p == 15 && d == 4)  {return 2;}
         if(s == true && t == 1 && p == 15 && d == 5)  {return 2;}
         if(s == true && t == 1 && p == 15 && d == 6)  {return 2;}
         if(s == true && t == 1 && p == 15 && d == 7)  {return 0;}
         if(s == true && t == 1 && p == 15 && d == 8)  {return 0;}
         if(s == true && t == 1 && p == 15 && d == 9)  {return 0;}
         if(s == true && t == 1 && p == 15 && d == 10) {return 0;}
         if(s == true && t == 1 && p == 15 && d == 11) {return 0;}
         
         if(s == true && t == 1 && p == 16 && d == 2)  {return 0;}
         if(s == true && t == 1 && p == 16 && d == 3)  {return 0;}
         if(s == true && t == 1 && p == 16 && d == 4)  {return 2;}
         if(s == true && t == 1 && p == 16 && d == 5)  {return 2;}
         if(s == true && t == 1 && p == 16 && d == 6)  {return 2;}
         if(s == true && t == 1 && p == 16 && d == 7)  {return 0;}
         if(s == true && t == 1 && p == 16 && d == 8)  {return 0;}
         if(s == true && t == 1 && p == 16 && d == 9)  {return 0;}
         if(s == true && t == 1 && p == 16 && d == 10) {return 0;}
         if(s == true && t == 1 && p == 16 && d == 11) {return 0;}
         
         if(s == true && t == 1 && p == 17 && d == 2)  {return 0;}
         if(s == true && t == 1 && p == 17 && d == 3)  {return 2;}
         if(s == true && t == 1 && p == 17 && d == 4)  {return 2;}
         if(s == true && t == 1 && p == 17 && d == 5)  {return 2;}
         if(s == true && t == 1 && p == 17 && d == 6)  {return 2;}
         if(s == true && t == 1 && p == 17 && d == 7)  {return 0;}
         if(s == true && t == 1 && p == 17 && d == 8)  {return 0;}
         if(s == true && t == 1 && p == 17 && d == 9)  {return 0;}
         if(s == true && t == 1 && p == 17 && d == 10) {return 0;}
         if(s == true && t == 1 && p == 17 && d == 11) {return 0;}
         
         if(s == true && t == 1 && p == 18 && d == 2)  {return 1;}
         if(s == true && t == 1 && p == 18 && d == 3)  {return 2;}
         if(s == true && t == 1 && p == 18 && d == 4)  {return 2;}
         if(s == true && t == 1 && p == 18 && d == 5)  {return 2;}
         if(s == true && t == 1 && p == 18 && d == 6)  {return 2;}
         if(s == true && t == 1 && p == 18 && d == 7)  {return 1;}
         if(s == true && t == 1 && p == 18 && d == 8)  {return 1;}
         if(s == true && t == 1 && p == 18 && d == 9)  {return 0;}
         if(s == true && t == 1 && p == 18 && d == 10) {return 0;}
         if(s == true && t == 1 && p == 18 && d == 11) {return 0;}
         
         if(s == true && t == 1 && p == 19 && d == 2)  {return 1;}
         if(s == true && t == 1 && p == 19 && d == 3)  {return 1;}
         if(s == true && t == 1 && p == 19 && d == 4)  {return 1;}
         if(s == true && t == 1 && p == 19 && d == 5)  {return 1;}
         if(s == true && t == 1 && p == 19 && d == 6)  {return 1;}
         if(s == true && t == 1 && p == 19 && d == 7)  {return 1;}
         if(s == true && t == 1 && p == 19 && d == 8)  {return 1;}
         if(s == true && t == 1 && p == 19 && d == 9)  {return 1;}
         if(s == true && t == 1 && p == 19 && d == 10) {return 1;}
         if(s == true && t == 1 && p == 19 && d == 11) {return 1;}
         
         if(s == true && t == 1 && p == 20 && d == 2)  {return 1;}
         if(s == true && t == 1 && p == 20 && d == 3)  {return 1;}
         if(s == true && t == 1 && p == 20 && d == 4)  {return 1;}
         if(s == true && t == 1 && p == 20 && d == 5)  {return 1;}
         if(s == true && t == 1 && p == 20 && d == 6)  {return 1;}
         if(s == true && t == 1 && p == 20 && d == 7)  {return 1;}
         if(s == true && t == 1 && p == 20 && d == 8)  {return 1;}
         if(s == true && t == 1 && p == 20 && d == 9)  {return 1;}
         if(s == true && t == 1 && p == 20 && d == 10) {return 1;}
         if(s == true && t == 1 && p == 20 && d == 11) {return 1;}
         
                  
         if(s == true && p == 11 && d == 2)  {return 0;}
         if(s == true && p == 11 && d == 3)  {return 0;}
         if(s == true && p == 11 && d == 4)  {return 0;}
         if(s == true && p == 11 && d == 5)  {return 0;}
         if(s == true && p == 11 && d == 6)  {return 0;}
         if(s == true && p == 11 && d == 7)  {return 0;}
         if(s == true && p == 11 && d == 8)  {return 0;}
         if(s == true && p == 11 && d == 9)  {return 0;}
         if(s == true && p == 11 && d == 10) {return 0;}
         if(s == true && p == 11 && d == 11) {return 0;}
                  
         if(s == true && p == 12 && d == 2)  {return 0;}
         if(s == true && p == 12 && d == 3)  {return 0;}
         if(s == true && p == 12 && d == 4)  {return 0;}
         if(s == true && p == 12 && d == 5)  {return 0;}
         if(s == true && p == 12 && d == 6)  {return 0;}
         if(s == true && p == 12 && d == 7)  {return 0;}
         if(s == true && p == 12 && d == 8)  {return 0;}
         if(s == true && p == 12 && d == 9)  {return 0;}
         if(s == true && p == 12 && d == 10) {return 0;}
         if(s == true && p == 12 && d == 11) {return 0;}
         
         if(s == true && p == 13 && d == 2)  {return 0;}
         if(s == true && p == 13 && d == 3)  {return 0;}
         if(s == true && p == 13 && d == 4)  {return 0;}
         if(s == true && p == 13 && d == 5)  {return 0;}
         if(s == true && p == 13 && d == 6)  {return 0;}
         if(s == true && p == 13 && d == 7)  {return 0;}
         if(s == true && p == 13 && d == 8)  {return 0;}
         if(s == true && p == 13 && d == 9)  {return 0;}
         if(s == true && p == 13 && d == 10) {return 0;}
         if(s == true && p == 13 && d == 11) {return 0;}
         
         if(s == true && p == 14 && d == 2)  {return 0;}
         if(s == true && p == 14 && d == 3)  {return 0;}
         if(s == true && p == 14 && d == 4)  {return 0;}
         if(s == true && p == 14 && d == 5)  {return 0;}
         if(s == true && p == 14 && d == 6)  {return 0;}
         if(s == true && p == 14 && d == 7)  {return 0;}
         if(s == true && p == 14 && d == 8)  {return 0;}
         if(s == true && p == 14 && d == 9)  {return 0;}
         if(s == true && p == 14 && d == 10) {return 0;}
         if(s == true && p == 14 && d == 11) {return 0;}
         
         if(s == true && p == 15 && d == 2)  {return 0;}
         if(s == true && p == 15 && d == 3)  {return 0;}
         if(s == true && p == 15 && d == 4)  {return 0;}
         if(s == true && p == 15 && d == 5)  {return 0;}
         if(s == true && p == 15 && d == 6)  {return 0;}
         if(s == true && p == 15 && d == 7)  {return 0;}
         if(s == true && p == 15 && d == 8)  {return 0;}
         if(s == true && p == 15 && d == 9)  {return 0;}
         if(s == true && p == 15 && d == 10) {return 0;}
         if(s == true && p == 15 && d == 11) {return 0;}
         
         if(s == true && p == 16 && d == 2)  {return 0;}
         if(s == true && p == 16 && d == 3)  {return 0;}
         if(s == true && p == 16 && d == 4)  {return 0;}
         if(s == true && p == 16 && d == 5)  {return 0;}
         if(s == true && p == 16 && d == 6)  {return 0;}
         if(s == true && p == 16 && d == 7)  {return 0;}
         if(s == true && p == 16 && d == 8)  {return 0;}
         if(s == true && p == 16 && d == 9)  {return 0;}
         if(s == true && p == 16 && d == 10) {return 0;}
         if(s == true && p == 16 && d == 11) {return 0;}
         
         if(s == true && p == 17 && d == 2)  {return 0;}
         if(s == true && p == 17 && d == 3)  {return 0;}
         if(s == true && p == 17 && d == 4)  {return 0;}
         if(s == true && p == 17 && d == 5)  {return 0;}
         if(s == true && p == 17 && d == 6)  {return 0;}
         if(s == true && p == 17 && d == 7)  {return 0;}
         if(s == true && p == 17 && d == 8)  {return 0;}
         if(s == true && p == 17 && d == 9)  {return 0;}
         if(s == true && p == 17 && d == 10) {return 0;}
         if(s == true && p == 17 && d == 11) {return 0;}
         
         if(s == true && p == 18 && d == 2)  {return 1;}
         if(s == true && p == 18 && d == 3)  {return 0;}
         if(s == true && p == 18 && d == 4)  {return 0;}
         if(s == true && p == 18 && d == 5)  {return 0;}
         if(s == true && p == 18 && d == 6)  {return 0;}
         if(s == true && p == 18 && d == 7)  {return 1;}
         if(s == true && p == 18 && d == 8)  {return 1;}
         if(s == true && p == 18 && d == 9)  {return 0;}
         if(s == true && p == 18 && d == 10) {return 0;}
         if(s == true && p == 18 && d == 11) {return 0;}
         
         if(s == true && p == 19 && d == 2)  {return 1;}
         if(s == true && p == 19 && d == 3)  {return 1;}
         if(s == true && p == 19 && d == 4)  {return 1;}
         if(s == true && p == 19 && d == 5)  {return 1;}
         if(s == true && p == 19 && d == 6)  {return 1;}
         if(s == true && p == 19 && d == 7)  {return 1;}
         if(s == true && p == 19 && d == 8)  {return 1;}
         if(s == true && p == 19 && d == 9)  {return 1;}
         if(s == true && p == 19 && d == 10) {return 1;}
         if(s == true && p == 19 && d == 11) {return 1;}
         
         if(s == true && p == 20 && d == 2)  {return 1;}
         if(s == true && p == 20 && d == 3)  {return 1;}
         if(s == true && p == 20 && d == 4)  {return 1;}
         if(s == true && p == 20 && d == 5)  {return 1;}
         if(s == true && p == 20 && d == 6)  {return 1;}
         if(s == true && p == 20 && d == 7)  {return 1;}
         if(s == true && p == 20 && d == 8)  {return 1;}
         if(s == true && p == 20 && d == 9)  {return 1;}
         if(s == true && p == 20 && d == 10) {return 1;}
         if(s == true && p == 20 && d == 11) {return 1;}
         
         if(p <= 8)
         {
            if(s == false) {return 0;}
         }
         
         if(s == false && t == 1 && p == 9 && d == 2 && truecount >= 1.0)  {System.out.println("D: Double on 1+"); return 2;}
         if(s == false && t == 1 && p == 9 && d == 2)  {return 0;} //Double on 1+
         if(s == false && t == 1 && p == 9 && d == 3)  {return 2;}
         if(s == false && t == 1 && p == 9 && d == 4)  {return 2;}
         if(s == false && t == 1 && p == 9 && d == 5)  {return 2;}
         if(s == false && t == 1 && p == 9 && d == 6)  {return 2;}
         if(s == false && t == 1 && p == 9 && d == 7 && truecount >= 4.0)  {System.out.println("D: Double on 4+"); return 2;}
         if(s == false && t == 1 && p == 9 && d == 7)  {return 0;} //Double on 4+
         if(s == false && t == 1 && p == 9 && d == 8)  {return 0;}
         if(s == false && t == 1 && p == 9 && d == 9)  {return 0;}
         if(s == false && t == 1 && p == 9 && d == 10) {return 0;}
         if(s == false && t == 1 && p == 9 && d == 11) {return 0;}
         
         if(s == false && t == 1 && p == 10 && d == 2)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 3)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 4)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 5)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 6)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 7)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 8)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 9)  {return 2;}
         if(s == false && t == 1 && p == 10 && d == 10 && truecount >= 4.0)  {System.out.println("D: Double on 4+"); return 2;}
         if(s == false && t == 1 && p == 10 && d == 10) {return 0;} //Double on 4+
         if(s == false && t == 1 && p == 10 && d == 11 && truecount >= 4.0)  {System.out.println("D: Double on 4+"); return 2;}
         if(s == false && t == 1 && p == 10 && d == 11) {return 0;} //Double on 4+
         
         if(s == false && t == 1 && p == 11 && d == 2)  {return 2;}
         if(s == false && t == 1 && p == 11 && d == 3)  {return 2;}
         if(s == false && t == 1 && p == 11 && d == 4)  {return 2;}
         if(s == false && t == 1 && p == 11 && d == 5)  {return 2;}
         if(s == false && t == 1 && p == 11 && d == 6)  {return 2;}
         if(s == false && t == 1 && p == 11 && d == 7)  {return 2;}
         if(s == false && t == 1 && p == 11 && d == 8)  {return 2;}
         if(s == false && t == 1 && p == 11 && d == 9)  {return 2;}
         if(s == false && t == 1 && p == 11 && d == 10) {return 2;}
         if(s == false && t == 1 && p == 11 && d == 11 && truecount >= 1.0)  {System.out.println("D: Double on 1+"); return 2;}
         if(s == false && t == 1 && p == 11 && d == 11) {return 0;} //Double on 1+
         
         if(s == false && p == 9 && d == 2)  {return 0;} 
         if(s == false && p == 9 && d == 3)  {return 0;}
         if(s == false && p == 9 && d == 4)  {return 0;}
         if(s == false && p == 9 && d == 5)  {return 0;}
         if(s == false && p == 9 && d == 6)  {return 0;}
         if(s == false && p == 9 && d == 7)  {return 0;}
         if(s == false && p == 9 && d == 8)  {return 0;}
         if(s == false && p == 9 && d == 9)  {return 0;}
         if(s == false && p == 9 && d == 10) {return 0;}
         if(s == false && p == 9 && d == 11) {return 0;}
         
         if(s == false && p == 10 && d == 2)  {return 0;}
         if(s == false && p == 10 && d == 3)  {return 0;}
         if(s == false && p == 10 && d == 4)  {return 0;}
         if(s == false && p == 10 && d == 5)  {return 0;}
         if(s == false && p == 10 && d == 6)  {return 0;}
         if(s == false && p == 10 && d == 7)  {return 0;}
         if(s == false && p == 10 && d == 8)  {return 0;}
         if(s == false && p == 10 && d == 9)  {return 0;}
         if(s == false && p == 10 && d == 10) {return 0;}
         if(s == false && p == 10 && d == 11) {return 0;}
         
         if(s == false && p == 11 && d == 2)  {return 0;}
         if(s == false && p == 11 && d == 3)  {return 0;}
         if(s == false && p == 11 && d == 4)  {return 0;}
         if(s == false && p == 11 && d == 5)  {return 0;}
         if(s == false && p == 11 && d == 6)  {return 0;}
         if(s == false && p == 11 && d == 7)  {return 0;}
         if(s == false && p == 11 && d == 8)  {return 0;}
         if(s == false && p == 11 && d == 9)  {return 0;}
         if(s == false && p == 11 && d == 10) {return 0;}
         if(s == false && p == 11 && d == 11) {return 0;}
         
         if(s == false && p == 12 && d == 2 && truecount >= 4.0)  {System.out.println("D: Stand on 4+"); return 1;}
         if(s == false && p == 12 && d == 2)  {return 0;} //Stand on 4+
         if(s == false && p == 12 && d == 3 && truecount >= 2.0)  {System.out.println("D: Stand on 2+"); return 1;}
         if(s == false && p == 12 && d == 3)  {return 0;} //Stand on 2+
         if(s == false && p == 12 && d == 4 && truecount < 0.0)  {System.out.println("D: Stand on 0+, hit if lower"); return 0;}
         if(s == false && p == 12 && d == 4)  {return 1;} //Stand on 0+
         if(s == false && p == 12 && d == 5 && truecount < -1.0)  {System.out.println("D: Stand on -1+. hit if lower"); return 0;}
         if(s == false && p == 12 && d == 5)  {return 1;} //Stand on -1+, hit if lower
         if(s == false && p == 12 && d == 6 && truecount < -1.0)  {System.out.println("D: Stand on -1+. hit if lower"); return 0;}
         if(s == false && p == 12 && d == 6)  {return 1;} //Stand on -1+, hit if lower
         if(s == false && p == 12 && d == 7)  {return 0;}
         if(s == false && p == 12 && d == 8)  {return 0;}
         if(s == false && p == 12 && d == 9)  {return 0;}
         if(s == false && p == 12 && d == 10) {return 0;}
         if(s == false && p == 12 && d == 11) {return 0;}
         
         if(s == false && p == 13 && d == 2 && truecount < -1.0)  {System.out.println("D: Stand on -1+. hit if lower"); return 0;}
         if(s == false && p == 13 && d == 2)  {return 1;} //Stand on -1+, hit if lower
         if(s == false && p == 13 && d == 3 && truecount < -2.0)  {System.out.println("D: Stand on -2+. hit if lower"); return 0;}
         if(s == false && p == 13 && d == 3)  {return 1;} //Stand on -2+, hit if lower
         if(s == false && p == 13 && d == 4)  {return 1;}
         if(s == false && p == 13 && d == 5)  {return 1;}
         if(s == false && p == 13 && d == 6)  {return 1;}
         if(s == false && p == 13 && d == 7)  {return 0;}
         if(s == false && p == 13 && d == 8)  {return 0;}
         if(s == false && p == 13 && d == 9)  {return 0;}
         if(s == false && p == 13 && d == 10) {return 0;}
         if(s == false && p == 13 && d == 11) {return 0;}
         
         if(s == false && p == 14 && d == 2)  {return 1;}
         if(s == false && p == 14 && d == 3)  {return 1;}
         if(s == false && p == 14 && d == 4)  {return 1;}
         if(s == false && p == 14 && d == 5)  {return 1;}
         if(s == false && p == 14 && d == 6)  {return 1;}
         if(s == false && p == 14 && d == 7)  {return 0;}
         if(s == false && p == 14 && d == 8)  {return 0;}
         if(s == false && p == 14 && d == 9)  {return 0;}
         if(s == false && p == 14 && d == 10) {return 0;}
         if(s == false && p == 14 && d == 11) {return 0;}
         
         if(s == false && p == 15 && d == 2)  {return 1;}
         if(s == false && p == 15 && d == 3)  {return 1;}
         if(s == false && p == 15 && d == 4)  {return 1;}
         if(s == false && p == 15 && d == 5)  {return 1;}
         if(s == false && p == 15 && d == 6)  {return 1;}
         if(s == false && p == 15 && d == 7)  {return 0;}
         if(s == false && p == 15 && d == 8)  {return 0;}
         if(s == false && p == 15 && d == 9)  {return 0;}
         if(s == false && p == 15 && d == 9 && truecount >= 4.0)  {System.out.println("D: Stand on 4+"); return 1;}
         if(s == false && p == 15 && d == 10) {return 0;} //Stand on 4+
         if(s == false && p == 15 && d == 11) {return 0;}
         
         if(s == false && p == 16 && d == 2)  {return 1;}
         if(s == false && p == 16 && d == 3)  {return 1;}
         if(s == false && p == 16 && d == 4)  {return 1;}
         if(s == false && p == 16 && d == 5)  {return 1;}
         if(s == false && p == 16 && d == 6)  {return 1;}
         if(s == false && p == 16 && d == 7)  {return 0;}
         if(s == false && p == 16 && d == 8)  {return 0;}
         if(s == false && p == 16 && d == 9 && truecount >= 5.0)  {System.out.println("D: Stand on 5+"); return 1;}
         if(s == false && p == 16 && d == 9)  {return 0;} //Stand on 5+
         if(s == false && p == 16 && d == 10 && truecount >= 0.0)  {System.out.println("D: Stand on 0+"); return 1;}
         if(s == false && p == 16 && d == 10) {return 0;} //Stand on 0+
         if(s == false && p == 16 && d == 11) {return 0;}
         
         if(p >= 17)
         {
            if(s == false) {return 1;}
         }
         
         if(p == 21)
         {
            return 1;
         }
         
         
      }
      System.out.println(r + " " + s + " " + t + " " + p + " " + d);
      return -1;
   }
}
