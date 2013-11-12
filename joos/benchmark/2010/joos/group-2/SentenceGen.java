import java.util.*;
import joos.lib.*;


public class SentenceGen {
	protected String Keyword0;
	protected String Keyword1;
	protected String Keyword2;
	protected String Keyword3;
	
	public SentenceGen(String _Keyword0,String _Keyword1,String _Keyword2,String _Keyword3){
		super();		
		Keyword0 = _Keyword0;
		Keyword1 = _Keyword1;
		Keyword2 = _Keyword2;
		Keyword3 = _Keyword3;		
	}	
	public String Generate(int NumberOfSentences, int seed0, int seed1, int seed2){		
		  
		String noun1; 	 
		String noun2; 	 
		String noun3; 	
		String noun4; 	 
		String noun5; 	 
		String noun6; 	 
		String verb1; 	 
		String verb2; 	 
		String verb3;   
		String verb4;    
		String verb5;   
		String verb6;    
		String verb7;		
		String finalSentence;
		int i;
		int NounCounter;
		int VerbCounter;
		int KeyWordCounter;		
		
		NounCounter = seed0;
		VerbCounter = seed1;
		KeyWordCounter = seed2;
		finalSentence = "";
		
		noun1= "human life";
		noun2= "computer science";
		noun3= "egg";
		noun4= "universitiy";
		noun5= "professor";
		noun6= "bicycle";
		verb1= "revolutionizes";
		verb2= "brings to a new era";
		verb3= "masters" ;
		verb4= "explodes";
		verb5= "rattles";
		verb6= "follows";
		verb7= "improves";	
		
		 for (i = 1; i <= NumberOfSentences; i++)
		 {
			 if (KeyWordCounter == 0){
				 finalSentence = finalSentence + Keyword0 + " ";
				 KeyWordCounter++;}
			 else if (KeyWordCounter == 1){
				 finalSentence = finalSentence + Keyword1 + " ";
				 KeyWordCounter++;}
			 else if (KeyWordCounter == 2){
				 finalSentence = finalSentence + Keyword2 + " ";
				 KeyWordCounter++;}
			 else if (KeyWordCounter == 3){
				 finalSentence = finalSentence + Keyword3 + " ";
				 KeyWordCounter = 0;}
			 
			 if (VerbCounter == 0){
				 finalSentence = finalSentence + verb1 + " ";
				 VerbCounter++;}
			 else if (VerbCounter == 1){
				 finalSentence = finalSentence + verb2 + " ";
				 VerbCounter++;}
			 else if (VerbCounter == 2){
				 finalSentence = finalSentence + verb3 + " ";
				 VerbCounter++;}
			 else if (VerbCounter == 3){
				 finalSentence = finalSentence + verb4 + " ";
				 VerbCounter++;}			 
			 else if (VerbCounter == 4){
				 finalSentence = finalSentence + verb5 + " ";
				 VerbCounter++;}					 
			 else if (VerbCounter == 5){
				 finalSentence = finalSentence + verb6 + " ";
				 VerbCounter++;}				 
			 else if (VerbCounter == 6){
				 finalSentence = finalSentence + verb7 + " ";
				 VerbCounter = 0;}	
			 
			 if (NounCounter == 0){
				 finalSentence = finalSentence + noun1 + ". ";
				 NounCounter++;}
			 else if (NounCounter == 1){
				 finalSentence = finalSentence + noun2 + ". ";
				 NounCounter++;}
			 else if (NounCounter == 2){
				 finalSentence = finalSentence + noun3 + ". ";
				 NounCounter++;}
			 else if (NounCounter == 3){
				 finalSentence = finalSentence + noun4 + ". ";
				 NounCounter++;}			 
			 else if (NounCounter == 4){
				 finalSentence = finalSentence + noun5 + ". ";
				 NounCounter++;}					 
			 else if (NounCounter == 5){
				 finalSentence = finalSentence + noun6 + ". ";
				 NounCounter = 0;} 			 
		 }
		 return finalSentence;
	}
}
