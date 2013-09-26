/*
  dOvs 1998
  WIG compiler by group 04 section 1:
  Jesper Riemer (jriemer@daimi.au.dk) 
  Peter Riis Østergaard (proest@daimi.au.dk)
  Jakob Skyberg (skyberg@daimi.au.dk)

  runwig4.h: Header file for functions used by compiled code.
*/

typedef struct FIELDS {
  char *name;
  char *value;
  struct FIELDS *next;
} FIELDS;

void parseFields();
char *getField(char *name);

char *randomString(char *name,int size);

int getGlobalInt(char *name);
int putGlobalInt(char *name,int value);

char *getGlobalString(char *name);
char *putGlobalString(char *name,char *value);

char *itoa(int i);

char *copyString(char **s, char *ct);

char *catString(char *s, char *ct);
