/*
 *   Code's original authors:
 *   Jesper Riemer (jriemer@daimi.au.dk)
 *   Peter Riis Østergaard (proest@daimi.au.dk)
 *   Jakob Skyberg (skyberg@daimi.au.dk)
 *
 *   Adopted by GROUP 10, Feng, Mark, Felix
 */

typedef struct FIELDS {
    char *name ;
    char *value ;
    struct FIELDS *next ;
} FIELDS ;

FIELDS *fields = NULL ;

unsigned char dd2c(char d1, char d2) 
{ register unsigned char digit;
 
  digit = (d1 >= 'A' ? ((d1 & 0xdf) - 'A')+10 : (d1 - '0'));
  digit *= 16;
  digit += (d2 >= 'A' ? ((d2 & 0xdf) - 'A')+10 : (d2 - '0'));
  return digit;
}

void parseFields()
{ 
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
