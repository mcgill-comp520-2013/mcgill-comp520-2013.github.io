/*
  dOvs 1998
  WIG compiler by group 04 section 1:
  Jesper Riemer (jriemer@daimi.au.dk) 
  Peter Riis Østergaard (proest@daimi.au.dk)
  Jakob Skyberg (skyberg@daimi.au.dk)

  runwig4.c: Implements functions used by compiled code.
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "runwig4.h"

unsigned char dd2c(char d1, char d2) 
{ register unsigned char digit;
 
  digit = (d1 >= 'A' ? ((d1 & 0xdf) - 'A')+10 : (d1 - '0'));
  digit *= 16;
  digit += (d2 >= 'A' ? ((d2 & 0xdf) - 'A')+10 : (d2 - '0'));
  return digit;
}

FIELDS *fields = NULL;

void parseFields()
{ FIELDS *f;
  int c,i,size;
  c=getchar();
  if (c==EOF) return;
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
  if (c=='&') parseFields();
}

char *getField(char *name)
{ FIELDS *f;
  f = fields;
  while (f!=NULL) {
    if (strcmp(name,f->name)==0) return f->value;
    f = f->next;
  }
  return "";
}

char *randomString(char *name,int size)
{ char *r;
  int i,l;
  l = strlen(name);
  r = (char *)malloc(l+size+2);
  for (i=0; i<l; i++) r[i]=name[i];
  r[l] = '$';
  for (i=l+1; i<l+size+1; i++) r[i]='a'+lrand48()%('z'-'a'+1);
  r[l+size] = '\0';
  return r;
}

int getGlobalInt(char *name)
{ FILE *f;
  int i;
  if (f = fopen(name,"r")) /* if file exists get value */
    { fscanf(f,"%i\n",&i);
      fclose(f);
      return i;
    }
  else return(0); /* else use default initial value */
}

int putGlobalInt(char *name,int value)
{ FILE *f;
  f = fopen(name,"w");
  fprintf(f,"%i\n",value);
  fclose(f);
  return value;
}

char *getGlobalString(char *name)
{ FILE *f;
  char *s;
  char *s2 = malloc(12);
  int strsize;

  if (f = fopen(name,"r")) /* if the file exists, get the string */
    { fgets(s2, 12, f);
      strsize = atoi(s2);
      s = (char *)malloc(strsize +1);
      if (strsize > 0)
        fgets(s, strsize +1, f);
      else
        *s = 0;
      fclose(f);
      return s;
    }
  else /* file does not exist, return default initial value */
    return("");
}

char *putGlobalString(char *name, char *value)
{ FILE *f;
  f = fopen(name,"w");
  fprintf(f, "%i\n", strlen(value));
  fprintf(f,"%s\n",value);
  fclose(f);
  return value;
}

char *itoa(int i)
{ char *a;
  a = (char *)malloc(11);
  sprintf(a,"%i",i);
  return a;
}

char *copyString(char **s, char *ct) {
  *s = (char *)malloc(strlen(ct) + 1);
  return strcpy(*s, ct);
}

char *catString(char *s, char *ct) {
  char *t;
  t = (char *)malloc(strlen(s) + strlen(ct) + 1);
  strcpy(t, s);
  return strcat(t, ct);
}
