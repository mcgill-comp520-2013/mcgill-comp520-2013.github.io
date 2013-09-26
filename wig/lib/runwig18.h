#ifndef __runwig18_h
#define __runwig18_h

typedef enum {intK,charK,schemaK} stackKind;

typedef struct SCHEMA {
  char *name;
  struct STACK *value;
  struct SCHEMA *next;
} SCHEMA;

typedef struct STACK {
  stackKind kind;
  union {
    int intS;
    char *charS;
    struct SCHEMA *schemaS;
  } val;
} STACK;

typedef struct FIELDS {
  char *name;
  char *value;
  struct FIELDS *next;
} FIELDS;

void init(char *prefix);
void use_globals(char *glob);
void push_int();
void push_char();
void push_schema();
void setretval();
void getretval();
STACK *pop();
void popmany();
char *tostring(STACK *s);
void push_adrvalue(int adr);
void store_int(int offset);
void store_char(int offset);
void store_tuple(int offset);
void putfield(int offset, char *field);
void eq();
void neq();
void lt();
void gt();
void leq();
void geq();
void not();
void plus();
void minus();
void times();
void div_();
void mod();
void and();
void or();
void shift();
void pproject(int count);
void mproject(int count);
void push_intvalue(int value);
void push_charvalue(char *value);
void getcgifield(char *name);
void load_int(int offset);
void load_char(int offset);
void load_tuple(int offset);
void getfield(int offset, char *field);
void push_fp(int argc);
void pop_fp();
void addfield(char *field, stackKind kind);
void addfieldvalue(char *field);
void dup_();

void parseFields();
void loadstate();
void savestate();
void loadglobals();
void saveglobals();

void debugstack();

#endif /* !__runwig18_h */
