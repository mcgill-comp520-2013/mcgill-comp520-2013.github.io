/***********************************************
 
           perfectwig v0.93

   Xavier SUDRE    <xavier@sudre.fr>
   Jerome CAILLAUD <deejay@born2frag.org>
   Tarak BEN SASSI <tarak5@caramail.com>

  GROUP 03, McGILL, COMPILER DESIGN, FALL 2001

 If you want to contribute, notice that you can
 get an access to the CVS repository by sending
 an e-mail to  <perfectwig@sudre.fr>
 
 Thank you,
   The perfectwig team...

************************************************/

/*This is the header file that is include in all the .c files
  produced by perfectwig
*/


/* Usefull global variables*/
 
char * URL;
char * SESSIONID;
int str_len_local; /*to retreive the length of the string from the file*/
int local_char_len; /*to retreive the length of the string from the file*/ 

FILE *localFILE;

/* Boolean type section */

#define true 1
#define false 0

typedef int bool;


/*************************************************************************

   This code part has been taken from runwig10.c

   Code's original authors:
   Jesper Riemer (jriemer@daimi.au.dk)
   Peter Riis stergaard (proest@daimi.au.dk)
   Jakob Skyberg (skyberg@daimi.au.dk)

   We have just renamed the functions to match with the project name
   Next vewrsion will have new functions there

**************************************************************************/

typedef struct FIELDS {
    char *name ;
    char *value ;
    struct FIELDS *next ;
} FIELDS ;

FIELDS *fields = NULL ;

unsigned char dd2c(char d1, char d2) { 
  register unsigned char digit;
 
  digit = (d1 >= 'A' ? ((d1 & 0xdf) - 'A')+10 : (d1 - '0'));
  digit *= 16;
  digit += (d2 >= 'A' ? ((d2 & 0xdf) - 'A')+10 : (d2 - '0'));
  return digit;
}

void perfectwig_parseFields(){ 
    FIELDS *f;
    int c,i,size;

    c=getchar();
    
    if (c==EOF) 
	return;
    f = (FIELDS *)malloc(sizeof(FIELDS));
    size=8;
    f->name = (char *)malloc(size);
    for (i=0; c!=EOF && c!='&' && c!='='; c=getchar(),i++) {
      switch(c) {
        case '+': 
             f->name[i] = ' ';
             break;
        case '%':
	     { char c1, c2;
       	     c1 = getchar();
	     c2 = getchar();
	     f->name[i] = dd2c(c1,c2);
	     }
             break;
        default:
             f->name[i] = c;
      }
      if (i+1 >= size) {
         size = 2*size;
         f->name = realloc(f->name,size);
      }
  }
  f->name[i] = '\0';
  c = getchar();
  size=8;
  f->value = (char *)malloc(size);
  for (i=0; c!=EOF && c!='&' && c!='='; c=getchar(),i++) {
      switch(c) {
        case '+': 
             f->value[i] = ' ';
             break;
        case '%':
	     { char c1, c2;
               c1 = getchar();
	       c2 = getchar();
	       f->value[i] = dd2c(c1,c2);
	     }
	     break;
        default:
             f->value[i] = c;
      }
      if (i+1 >= size) {
         size = 2*size;
         f->value = realloc(f->value,size);
      }
  }
  f->value[i] = '\0';
  f->next = fields;
  fields = f;
  if (c=='&') perfectwig_parseFields();
}

char *perfectwig_getField(char *name){
  FIELDS *f;

  f = fields;
  while (f!=NULL) {
    if (strcmp(name,f->name)==0) return f->value;
    f = f->next;
  }
  return "";
}

char *perfectwig_strcpy(char **copyhere, char *copyme) {

  *copyhere=(char *)malloc(strlen(copyme)+1);
  return strcpy(*copyhere, copyme);
}

char *perfectwig_strcat(char *concathere, char *concatme) {
  char *tempo;

  tempo=(char *)malloc(strlen(concathere)+strlen(concatme)+1);
  strcpy(tempo, concathere);

  return strcat(tempo, concatme);
}

/**********************************************************************************

     end of code part taken from runwig10.c

 **********************************************************************************/


/*get a GLOBAL char * from the file*/
#define MACRO_GET_GLOBAL_str(param)\
do{\
local_char_len=fgetc(localf);\
param=(char*)malloc((local_char_len+1)*sizeof(char));\
fread(param,sizeof(char),local_char_len,localf);\
param[local_char_len]='\0';}while(0)

/*put a GLOBAL char * into the file*/
#define MACRO_PUT_GLOBAL_str(param)\
do{\
fputc(strlen(param),localf);\
fwrite(param,sizeof(char),strlen(param),localf);\
}while(0)

/*get a GLOBAL int from the file*/
#define MACRO_GET_GLOBAL_int(param)\
do{\
fread(&param,sizeof(int),1,localf);\
}while(0)

/*put a GLOBAL int into the file*/
#define MACRO_PUT_GLOBAL_int(param)\
do{\
fwrite(&param,sizeof(int),1,localf);\
}while(0)

/*we retreive from the session file the step where
  we have to go into the session, the new PC.
*/        
#define getStep()\
do{\
localFILE=fopen(SESSIONID, "r");\
which_step=fgetc(localFILE);\
fclose(localFILE);\
}while(0)

/*Create a new session string to append to the URL*/
char *newSessionid(char *name) {
  char *tempo;
  int i,len_name;

  /* We use the str... functions */
  len_name=strlen(name);
  tempo=(char *)malloc(len_name+20+1); /* there was a bug here with the runwig10.c, they put +2 in order to put +1*/
  strcpy(tempo,name);
  strcat(tempo,"$");
  for (i=len_name+1; i<len_name+1+20; i++) tempo[i]='a'+lrand48()%('z'-'a'+1);
  tempo[len_name+20] = '\0'; 
  return tempo;

}

/*converts an interger into a string*/
char *itoa(int i) {
  char *str;
  
  str=(char *)malloc(5); /*5 characters are needed to print 65535 not 11 as there where in runwig10.c*/
  sprintf(str,"%i",i);
  return str;
}

