//import java.util.Random;

import joos.lib.*;

/*
 * This is an ABSTRACT Thread Player class. I will be used as a base that all different
 * types of player classes will inherit from.
 * It extends the thread class and implements run since all players run the same way.
 * It also has some abstract classes that other player inheriting have to implement. 
 * This class might have been better as an interface, but it allowed us to show
 * polymorphism, inheritance and threads in order to make our benchmark more robust.
 */
public class BefungeCoder {
	
  //player will sleep for a random seed, everytime it wakes up, the manager thread
  //will do some actions on player.	
  protected int sleepTime;
  protected JoosIO f;
  protected int r;
  protected BefungeStack coderStack;
  protected BefungeCanvas coderCanavas;
  
  protected String befungeOutput; 
  
  public BefungeCoder(BefungeCanvas inCanvas) 
  {
	  
	  super();
      //super();
	  f = new JoosIO();
      r = 4;//new Random().nextInt();
      coderStack = new BefungeStack();
      befungeOutput = "";
      coderCanavas = inCanvas;
      
      
      // sleep between 0 and 5 seconds
      if (r > 0)
         sleepTime = r % 5000;
      else
         sleepTime = (-r) % 5000;

      f.println( "System Thread Name: " + //this.getName() +
                          ";  sleep: " + sleepTime + " created.");
  }
  
  //execute the thread
  /*public void run()
  {
     // put thread to sleep for a random interval
     if (new JoosThread(null).sleep(sleepTime))
       f.println("Interruption of thread " + this.getName());

     // print thread name
     f.println( "Thread " + this.getName() + " ran.");                             
  }*/
  
  public String befungeParse()
  {
	  boolean endFlag;
	  boolean stringMode;
	  int direction;
	  char nextChar;
	  
	  endFlag = false;
	  stringMode = false;
	  direction = 2;
	  nextChar = coderCanavas.getNextChar();
	  
	  while(!endFlag)
	  {
	      if(nextChar == '"' || stringMode == true)
	      {
	    	  if(stringMode == false)
	    		  stringMode = true;
	    	  else if(stringMode == true && nextChar == '"')
	    		  stringMode = false;
	    	  else
	    	      coderStack.pushChar(nextChar);
	    	  nextChar = ' ';
	      }
		  else if(nextChar >= '0' && nextChar <= '9')
		  {
			  JoosString tmpString;
			  String tmpHolder;
			  
			  tmpHolder = "";
			  tmpHolder = tmpHolder + nextChar;
			  tmpString = new JoosString(tmpHolder);
			 	
			  coderStack.pushInt(tmpString.string2Int());
		  }
		  else if(nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '%' || nextChar == '/' || nextChar == '`')
		  {
			  Node tmpNode1;
			  Node tmpNode2;
			  tmpNode1 = coderStack.pop();
			  tmpNode2 = coderStack.pop();
			  if(tmpNode1 != null && tmpNode2 != null)
			  {
				  if(tmpNode1 instanceof IntNode && tmpNode2 instanceof IntNode)
				  {
					  int res;
					  res=0;
					  if(nextChar == '+')
						  res = ((IntNode)tmpNode1).getInt() + ((IntNode)tmpNode2).getInt();
					  else if(nextChar == '-')
						  res = ((IntNode)tmpNode2).getInt() - ((IntNode)tmpNode1).getInt();
					  else if(nextChar == '*')
						  res = ((IntNode)tmpNode1).getInt() * ((IntNode)tmpNode2).getInt();
					  else if(nextChar == '/' || nextChar == '%')
					  {
						  if(((IntNode)tmpNode1).getInt() != 0)
						  {
							  if(nextChar == '/')
								  res = ((IntNode)tmpNode2).getInt() / ((IntNode)tmpNode1).getInt();
							  else
								  res = ((IntNode)tmpNode2).getInt() % ((IntNode)tmpNode1).getInt();
						  }
						  else
							  res = 0;
					  }
					  else if (nextChar == '`') 
					  {
						if(((IntNode)tmpNode2).getInt() > ((IntNode)tmpNode1).getInt())
							res = 1;
						else
							res = 0;
					  }
					  
					  coderStack.pushInt(res);
				  }
			  }
		  }
		  else if (nextChar == '!') 
		  {
			  Node tmpNode1;
			  tmpNode1 = coderStack.pop();
			  if(tmpNode1 != null)
			  {
				  if(tmpNode1 instanceof IntNode)
				  {
					if(((IntNode) tmpNode1).getInt() == 0)
						coderStack.pushInt(1);
					else
						coderStack.pushInt(0);
				  }
			  }
		  }
		  else if(nextChar == ':')
		  {
			  Node tmpNode1;
			  tmpNode1 = coderStack.pop();
			  if(tmpNode1 != null)
			  {
				  if(tmpNode1 instanceof IntNode)
				  {
					  coderStack.pushInt(((IntNode)tmpNode1).getInt());
					  coderStack.pushInt(((IntNode)tmpNode1).getInt());
				  }
				  else
				  {
					  coderStack.pushChar(((CharNode)tmpNode1).getChar());
					  coderStack.pushChar(((CharNode)tmpNode1).getChar());
				  }
			  }
		  }
		  else if(nextChar == '\\')
		  {
			  Node tmpNode1;
			  Node tmpNode2;
			  tmpNode1 = coderStack.pop();
			  tmpNode2 = coderStack.pop();
			  coderStack.push(tmpNode1);
			  coderStack.push(tmpNode2);
		  }
		  else if( nextChar == '$')
		  {
			  coderStack.pop();
		  }
		  else if(nextChar == '.')
		  {
			  Node tmpNode1;
			  tmpNode1 = coderStack.pop();
			  if(tmpNode1 != null)
			  {
				  if(tmpNode1 instanceof IntNode)
					  befungeOutput = befungeOutput + ((IntNode)tmpNode1).getInt();
			  }
		  }
		  else if(nextChar == ',')
		  {
			  Node tmpNode1;
			  tmpNode1 = coderStack.pop();
			  if(tmpNode1 != null)
			  {
				  if(tmpNode1 instanceof CharNode)
					  befungeOutput = befungeOutput + ((CharNode)tmpNode1).getChar();
			  }
		  }
		  else if(nextChar == '>' || nextChar == '<' || nextChar == '^' || nextChar == 'v' || nextChar == '?')
		  {
			  if(nextChar == '>')
				  direction = 2;
			  if(nextChar == '<')
				  direction = 1;
			  if(nextChar == '^')
				  direction = 3;
			  if(nextChar == 'v')
				  direction = 4;
			  if(nextChar == '?')
				  direction = 2;
		  }
		  else if(nextChar == '_' || nextChar == '|')
		  {
			  Node tmpNode;
			  tmpNode = coderStack.pop();
              if(tmpNode == null)
              {
                  if(nextChar == '_')
                      direction = 2;
                  else if(nextChar == '|')
                      direction = 4;
              }
			  else
			  {
				  if(tmpNode instanceof IntNode)
				  {
					 if(nextChar == '_')
					 {
						 if(((IntNode)tmpNode).getInt() == 0)
							 direction = 2;
						 else
							 direction = 1;
					 }
					 if(nextChar == '|')
					 {
						 if(((IntNode)tmpNode).getInt() == 0)
							 direction = 4;
						 else
							 direction = 3;
					 }
				  }
				  else
				  {
					 if(nextChar == '_')
					 {
						 if(((CharNode)tmpNode).getChar() == '0')
							 direction = 2;
						 else
							 direction = 1;
					 }
					 if(nextChar == '|')
					 {
						 if(((CharNode)tmpNode).getChar() == '0')
							 direction = 4;
						 else
							 direction = 3;
					 }
				  }
			  }
				  
		  }
	      else if (nextChar == '@') 
	      {
			endFlag = true;
		  }
	      else if (nextChar == '#')
	      {
	    	  nextChar = coderCanavas.getNextChar();
	      }
		  
		  if(direction == 1)
			  coderCanavas.moveLeftCanavas();
		  else if(direction == 2)
			  coderCanavas.moveRightCanavas();
		  else if(direction == 3)
			  coderCanavas.moveTopCanavas();
		  else if(direction == 4)
			  coderCanavas.moveBottomCanavas();
		  
		  nextChar = coderCanavas.getNextChar();
	  }
	  
	  return befungeOutput;
  }
  
  //public abstract void fuseMsg(JoosConstants img, byte[] data, int offset);
 //public abstract byte[] defuseMsg(byte[] data);
}
