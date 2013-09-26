/* This is the run-time library required by wig */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <string.h>
#include "runwig18.h"

int stacksize, sp, fp;
int *usedglobals, globalcount;
FILE **globals;

char *url, *sessionID, *gprefix;

STACK *retval;
STACK **stak;
FIELDS *fields = NULL;

STACK *trueSTACK, *falseSTACK;

void *Malloc(unsigned n)
{ void *p;

  if (!(p = malloc(n))) {
     fprintf(stderr,"Malloc(%d) failed.\n",n);
     fflush(stderr);
     abort();
   }
   return p;
}

void *Realloc(void *ptr, unsigned n)
{ void *p;

  if (!(p = realloc(ptr, n))) {
     fprintf(stderr,"Realloc(%d) failed.\n",n);
     fflush(stderr);
     abort();
   }
   return p;
}

char *strcat2(char *s1, char *s2)
{ char *s;
  s = (char *)Malloc(strlen(s1)+strlen(s2)+1);
  sprintf(s,"%s%s",s1,s2);
  return s;
}

char *strcat3(char *s1, char *s2, char *s3)
{ char *s;
  s = (char *)Malloc(strlen(s1)+strlen(s2)+strlen(s3)+1);
  sprintf(s,"%s%s%s",s1,s2,s3);
  return s;
}

char *itoa(int i)
{
  char *s;

  s=(char *)Malloc(12);
  sprintf(s, "%i", i);
  return s;
}

char *randomString()
{ 
  char *r;
  int i;
  r = (char *)Malloc(21);
  for (i=0; i<20; i++) r[i]='a'+lrand48()%('z'-'a'+1);
  r[20] = '\0';
  return r;
}

void incsp()
     /* Increase stack pointer */
{
  if(sp>=stacksize-1)
    stacksize *= 2;
  stak = (STACK **)Realloc(stak, stacksize * sizeof(STACK*));
  sp++;
}

SCHEMA *makeSCHEMA(char *name, STACK *value, SCHEMA *next)
{ SCHEMA *s;
  s = (SCHEMA *)Malloc(sizeof(SCHEMA));
  s->name = name;
  s->value = value;
  s->next = next;
  return s;
}

STACK *makeSTACKint(int i)
{ STACK *s;
  s = (STACK *)Malloc(sizeof(STACK));
  s->kind = intK;
  s->val.intS = i;
  return s;
}

STACK *makeSTACKchar(char *c)
{ STACK *s;
  s = (STACK *)Malloc(sizeof(STACK));
  s->kind=charK;
  s->val.charS = c;
  return s;
}

STACK *makeSTACKschema(SCHEMA *s)
{ STACK *st;
  st = (STACK *)Malloc(sizeof(STACK));
  st->kind=schemaK;
  st->val.schemaS = s;
  return st;
}

void use_globals(char *glob)
     /* Called from the generated c-code when a new session is started specifying
	which global values are used from that session */
{
  int i;
  globalcount = strlen(glob);
  usedglobals = (int *)Malloc(globalcount * sizeof(int));
  for(i=0; i<globalcount; ++i)
    usedglobals[i] = glob[i] - '0';
  loadglobals();
}

void init(char *prefix)
     /* Initializes the run-time system */
{
  char *qs;
  gprefix = prefix;
  srand48(time((time_t *)0));
  url = strcat3("http://", getenv("SERVER_NAME"), getenv("SCRIPT_NAME"));
  trueSTACK = (STACK *)Malloc(sizeof(STACK));
  trueSTACK->kind = intK;
  trueSTACK->val.intS = 1;
  falseSTACK = (STACK *)Malloc(sizeof(STACK));
  falseSTACK->kind = intK;
  falseSTACK->val.intS = 0;
  parseFields();
  qs = getenv("QUERY_STRING");
  stacksize = 32;
  sp = -1;
  fp = 0;
  stak = (STACK **)Malloc(stacksize * sizeof(STACK*));
  if(qs[0] == '$') {
    sessionID = qs+1;
    loadstate(sessionID);
    push_intvalue(0);
  }
  else {
    sessionID = randomString();
    push_globals();
    push_charvalue(qs);
    push_intvalue(1);
  }
}

void push_int()
     /* Push an integer with a standard value (called when entering a new scope) */
{
  STACK *s;
  incsp();
  s = (STACK *)Malloc(sizeof(STACK));
  s->kind = intK;
  s->val.intS = 0;
  stak[sp] = s;
}

void push_char()
     /* Push a string with a standard value (called when entering a new scope) */
{
  STACK *s;
  incsp();
  s = (STACK *)Malloc(sizeof(STACK));
  s->kind = charK;
  s->val.charS = "";
  stak[sp] = s;
}

void push_schema()
     /* Push an empty schema (called when entering a new scope) */
{
  STACK *s;
  incsp();
  s = (STACK *)Malloc(sizeof(STACK));
  s->kind = schemaK;
  s->val.schemaS = NULL;
  stak[sp] = s;
}

void setretval()
     /* Pop the return value from the top of the stack */
{
  retval = pop();
}

void getretval()
     /* Push the earlier specified return value onto the stack */
{
  incsp();
  stak[sp] = retval;
}

STACK *pop()
{ return stak[sp--];
}

void popmany(int count)
     /* Pop count elements off the stack (called when leaving a scope) */
{ sp -= count;
}

char *tostring(STACK *s)
     /* Generates a string representation of a STACK element */
{ char *str;
  SCHEMA *ts;
  switch(s->kind) {
  case intK:
    str=(char *)Malloc(12);
    sprintf(str, "%i", s->val.intS);
    return str;
    break;
  case charK:
    return s->val.charS;
    break;
  case schemaK:
    str=(char *)Malloc(1024);
    strcpy(str, "tuple {");
    for (ts = s->val.schemaS; ts; ts=ts->next) {
      strcat(str, ts->name);
      strcat(str, "=");
      strcat(str, tostring(ts->value));
      if(ts->next!=NULL)
	strcat(str, ",");
    }
    strcat(str, "}");
    return str;
    break;
  }
}

void push_adrvalue(int adr)
     /* Push an address onto the stack (called before jumping to a function) */
{ STACK *s;
  incsp();
  s = (STACK *)Malloc(sizeof(STACK));
  s->kind = intK;
  s->val.intS = adr;
  stak[sp] = s;
}

void store_int(int offset)
     /* Pop an integer from the stack and store it at the specified offset.
	If neccesary convert the element to an integer before storing */
{ 
  switch(stak[sp]->kind) {
  case intK:
    stak[offset] = stak[sp--];
    break;
  case charK:
    stak[offset] = makeSTACKint(atoi(stak[sp--]->val.charS));
    break;
  case schemaK:
    stak[offset] = makeSTACKint(0);
    sp--;
    break;
  }
}

void store_char(int offset)
     /* Pop a string from the stack and store it at the specified offset */
{ stak[offset] = stak[sp--];
}

void store_tuple(int offset)
     /* Pop a tuple from the stack and store it at the specified offset */
{ stak[offset] = stak[sp--];
}

void putfield(int offset, char *field)
     /* Sets the specified field in the tuple at the specified offset to
	the value of the element popped from the stack */
{
  SCHEMA *s;
  s = stak[offset]->val.schemaS;
  while(strcmp(s->name,field))
    s = s->next;
  s->value = stak[sp--];
}

int equal(STACK *s1, STACK *s2)
     /* Test if the values of the two STACK elements are the same */
{
  SCHEMA *t1, *t2;
  int res;
  switch(s1->kind) {
  case intK:
    if (s1->val.intS == s2->val.intS)
      res = 1;
    else
      res = 0;
    break;
  case charK:
    if (!strcmp(s1->val.charS, s2->val.charS))
      res = 1;
    else
      res = 0;
    break;
  case schemaK:
    res = 1;
    for (t1 = s1->val.schemaS; t1; t1=t1->next) {
      for (t2 = s2->val.schemaS; strcmp(t2->name, t1->name); t2=t2->next);
      switch(t1->value->kind) {
      case intK:
	if(t1->value->val.intS!=t2->value->val.intS)
	  res = 0;
	break;
      case charK:
	if(strcmp(t1->value->val.charS, t2->value->val.charS))
	  res = 0;
	break;
      case schemaK:
	break;
      }
    }
    break;
  }
  return res;
}

void eq()
     /* Push the result of the comparison of the two topmost STACK elements
	onto the stack */
{
  int res = equal(stak[sp--], stak[sp--]);
  switch(res) {
  case 1: 
    stak[++sp] = trueSTACK;
    break;
  case 0:
    stak[++sp] = falseSTACK;
    break;
  }
}

void neq()
     /* Negate and push the result of the comparison of the two topmost STACK
	elements onto the stack */
{
  int res = equal(stak[sp--], stak[sp--]);
  switch(res) {
  case 0: 
    stak[++sp] = trueSTACK;
    break;
  case 1:
    stak[++sp] = falseSTACK;
    break;
  }
}

void lt()
     /* Push the result of the comparison of the two topmost STACK elements
	onto the stack */
{
  STACK *s1, *s2;
  s1 = stak[sp--];
  s2 = stak[sp--];
  switch(s1->kind) {
  case intK:
    if (s1->val.intS > s2->val.intS)
      stak[++sp] = trueSTACK;
    else
      stak[++sp] = falseSTACK;
    break;
  case charK:
    if (strcmp(s1->val.charS, s2->val.charS)>0)
      stak[++sp] = trueSTACK;
    else
      stak[++sp] = falseSTACK;
    break;
  case schemaK:
    break;
  }
}

void gt()
     /* Push the result of the comparison of the two topmost STACK elements
	onto the stack */
{
  STACK *s1, *s2;
  s1 = stak[sp--];
  s2 = stak[sp--];
  switch(s1->kind) {
  case intK:
    if (s1->val.intS < s2->val.intS)
      stak[++sp] = trueSTACK;
    else
      stak[++sp] = falseSTACK;
    break;
  case charK:
    if (strcmp(s1->val.charS, s2->val.charS)<0)
       stak[++sp] = trueSTACK;
    else
      stak[++sp] = falseSTACK;
    break;
  case schemaK:
    break;
  }
}

void leq()
     /* Push the result of the comparison of the two topmost STACK elements
	onto the stack */
{
  STACK *s1, *s2;
  s1 = stak[sp--];
  s2 = stak[sp--];
  switch(s1->kind) {
  case intK:
    if (s1->val.intS >= s2->val.intS)
      stak[++sp] = trueSTACK;
    else
      stak[++sp] = falseSTACK;
    break;
  case charK:
    if (strcmp(s1->val.charS, s2->val.charS)>=0)
      stak[++sp] = trueSTACK;
    else
      stak[++sp] = falseSTACK;
    break;
  case schemaK:
    break;
  }
}

void geq()
     /* Push the result of the comparison of the two topmost STACK elements
	onto the stack */
{
  STACK *s1, *s2;
  s1 = stak[sp--];
  s2 = stak[sp--];
  switch(s1->kind) {
  case intK:
    if (s1->val.intS <= s2->val.intS)
      stak[++sp] = trueSTACK;
    else
      stak[++sp] = falseSTACK;
    break;
  case charK:
    if (strcmp(s1->val.charS, s2->val.charS)<=0)
      stak[++sp] = trueSTACK;
    else
      stak[++sp] = falseSTACK;
    break;
  case schemaK:
    break;
  }
}

void not()
  /* Negate the topmost element of the stack */
  /* LJH - must make sure we do not update trueStack and falseStack, 
          since they are used elsewhere */
{ if (stak[sp]->val.intS == 0)
    stak[sp] = trueSTACK;
  else 
    stak[sp] = falseSTACK;
   /* LJH - old code - stak[sp]->val.intS = 1-stak[sp]->val.intS; */
}
  

void plus()
     /* Add the two topmost elements and push the result */
{ STACK *s1, *s2;
  s1 = stak[sp--];
  s2 = stak[sp--];
  switch(s1->kind) {
  case intK:
    stak[++sp] = makeSTACKint(s1->val.intS + s2->val.intS);
    break;
  case charK:
    stak[++sp] = makeSTACKchar(strcat2(s2->val.charS,s1->val.charS));
    break;
  }
}

void minus()
     /* Subtract the two topmost elements and push the result */
{ STACK *s1, *s2;
  s1 = stak[sp--];
  s2 = stak[sp--];
  stak[++sp] = makeSTACKint(s2->val.intS-s1->val.intS);
}

void times()
     /* Multiply the two topmost elements and push the result */
{ STACK *s1, *s2;
  s1 = stak[sp--];
  s2 = stak[sp--];
  stak[++sp] = makeSTACKint(s2->val.intS*s1->val.intS);
}

void div_()
     /* Divide the two topmost elements and push the result */
{ STACK *s1, *s2;
  s1 = stak[sp--];
  s2 = stak[sp--];
  stak[++sp] = makeSTACKint(s2->val.intS/s1->val.intS);
}

void mod()
     /* Calculate modulo of the two topmost elements and push the result */
{ STACK *s1, *s2;
  s1 = stak[sp--];
  s2 = stak[sp--];
  stak[++sp] = makeSTACKint(s2->val.intS%s1->val.intS);
}

void and()
     /* Calculate the logical and of the two topmost elements and push the result */
{ STACK *s1, *s2;
  s1 = stak[sp--];
  s2 = stak[sp--];
  stak[++sp] = makeSTACKint(s2->val.intS&&s1->val.intS);
}

void or()
     /* Calculate the logical or of the two topmost elements and push the result */
{ STACK *s1, *s2;
  s1 = stak[sp--];
  s2 = stak[sp--];
  stak[++sp] = makeSTACKint(s2->val.intS||s1->val.intS);
}

void shift()
     /* Join the two topmost tuples and push the result */
{ SCHEMA *s1, *s2, *s1temp, *s2temp, *results;
  int found;
  s1=stak[sp--]->val.schemaS;
  s2=stak[sp--]->val.schemaS;

  results=NULL;
  s1temp=s1;
  while (s1temp!=NULL) {   /* Add all elements from s1 */
    results=makeSCHEMA(s1temp->name,s1temp->value,results);
    s1temp=s1temp->next;
  }

  s2temp=s2;
  while (s2temp!=NULL) {  /* Add the elements from s2 that are not in s1 */
    found=0;
    s1temp=s1;
    while (s1temp!=NULL) {
      if (!strcmp(s1temp->name,s2temp->name))
	found=1;
      s1temp=s1temp->next;
    }
    if (!found) {
      results=makeSCHEMA(s2temp->name,s2temp->value,results);
    }
    s2temp=s2temp->next;
  }

  stak[++sp] = makeSTACKschema(results);
}

void pproject(int count)
     /* Calculate a tuple with only the fields specified in the count topmost
	elements from the stack and push it  */
{ SCHEMA *origs, *temps, *results;
  STACK *tempval;
  char *c;
  origs=stak[sp-count]->val.schemaS;

  results=NULL;
  while (count>0) {
    c=stak[sp--]->val.charS;

    temps=origs;
    while (temps!=NULL) {   /* Fetch value of field with name c */
      if (!strcmp(temps->name,c))
	tempval=temps->value;
      temps=temps->next;
    }
    results=makeSCHEMA(c,tempval,results);
    count--;
  }
  sp--;
  stak[++sp] = makeSTACKschema(results);
}

void mproject(int count)
     /* Calculate a tuple with all fields excpet those specified in the count
	topmost elements from the stack and push it  */
{ SCHEMA *origs, *temps, *results;
  STACK *tempval;
  char *c;
  int found;
  origs=stak[sp-count]->val.schemaS;

  results=NULL;
  temps=origs;
  while (temps!=NULL) {
    
    found=0;
    while (count>0) {
      if (!strcmp(stak[sp+1-count]->val.charS,temps->name))
	found=1;
      count--;
    }
    if (!found) {
      results=makeSCHEMA(temps->name,temps->value,results);
    }
    temps=temps->next;
  }
  sp=sp-count-1;
  stak[++sp] = makeSTACKschema(results);
}

void push_intvalue(int value)
     /* Push an integer with the specified value */
{ incsp();
  stak[sp] = makeSTACKint(value);
}

void push_charvalue(char *value)
     /* Push a string with the specified value */
{ incsp();
  stak[sp] = makeSTACKchar(value);
}

void getcgifield(char *name)
     /* Get the specified cgifield and push it */
{ FIELDS *f;
  f = fields;
  while (f!=NULL) {
    if (!strcmp(name,f->name)) {
      incsp();
      stak[sp]=makeSTACKchar(f->value);
      return;
    }
    f = f->next;
  }
  incsp();
  stak[sp]=makeSTACKchar("");
}

void load_int(int offset)
     /* Load an integer from the specified offset and push it */
{ incsp();
  stak[sp] = stak[offset];
}

void load_char(int offset)
     /* Load a string from the specified offset and push it */
{ incsp();
  stak[sp] = stak[offset];
}

void load_tuple(int offset)
     /* Load a tuple from the specified offset and push it */
{ incsp();
  stak[sp] = stak[offset];
}

void getfield(int offset, char *field)
     /* Push the value of the specified field from the tuple at
	the specified offset */
{ SCHEMA *s, *temps;
  s = stak[offset]->val.schemaS;
  temps=s;
  while (temps!=NULL) {
    if (!strcmp(field,temps->name)) {
      incsp();
      stak[sp] = temps->value;
      return;
    }
    temps=temps->next;
  }
}

void push_fp(int argc)
     /* Push current framepointer and assign a new value to the
	framepointer that points to the first argument passed to
	the current function */
{ incsp();
  stak[sp] = makeSTACKint(fp);
  fp = sp-1-argc;
}

void pop_fp()
     /* Pop a previous pushed framepointer */
{ fp = stak[sp--]->val.intS;
}

void addfield(char *field, stackKind kind)
     /* Add a field with the specified name and kind to the tuple
	at the top of the stack */
{ SCHEMA *s;
  s = stak[sp--]->val.schemaS;
  switch (kind) {
  case intK:
    s = makeSCHEMA(field,makeSTACKint(0),s);
    break;
  case charK:
    s = makeSCHEMA(field,makeSTACKchar(""),s);
    break;
  case schemaK:
    break;
  }
  stak[++sp] = makeSTACKschema(s);
}


void addfieldvalue(char *field)
     /* Add a field with the specified name and value to the tuple
	at the top of the stack */
{ SCHEMA *s;
  STACK *st;
  st = stak[sp--];
  s = stak[sp--]->val.schemaS;
  switch (st->kind) {
  case intK:
    s = makeSCHEMA(field,makeSTACKint(st->val.intS),s);
    break;
  case charK:
    s = makeSCHEMA(field,makeSTACKchar(st->val.charS),s);
    break;
  case schemaK:
    break;
  }
  stak[++sp] = makeSTACKschema(s);
}

void dup_()
     /* Duplicates the top element */
{ incsp();
  stak[sp] = stak[sp-1];
}

unsigned char dd2c(char d1, char d2) 
{ register unsigned char digit;
 
  digit = (d1 >= 'A' ? ((d1 & 0xdf) - 'A')+10 : (d1 - '0'));
  digit *= 16;
  digit += (d2 >= 'A' ? ((d2 & 0xdf) - 'A')+10 : (d2 - '0'));
  return digit;
}

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

void loadstate()
{
  FILE *fil;
  int i, l, newsp;
  SCHEMA *ts, *oldts;
  STACK *st;
  if ((fil = fopen(sessionID, "rb")) == NULL) {
    printf("Content-type: text/html\n\n");
    printf("<html><head><title>Illegal request</title></head><body><h1>Illegal request</h1></body></html>");
    exit(1);
  }
  fread(&newsp, sizeof(int), 1, fil);
  for(i = 0; i <= newsp; i++) {
    st = (STACK *)Malloc(sizeof(STACK));
    fread(st, sizeof(STACK), 1, fil);
    incsp();
    stak[sp] = st;
  }
  for(i = 0; i <= sp; i++) {
    switch(stak[i]->kind) {
    case intK:
      break;
    case charK:
      fread(&l, sizeof(int), 1, fil);
      stak[i]->val.charS = (char *)Malloc(l+1);
      fread(stak[i]->val.charS, sizeof(char), l, fil);
      stak[i]->val.charS[l] = 0;
      break;
    case schemaK:
      oldts = NULL;
      do {       
	ts = (SCHEMA *)Malloc(sizeof(SCHEMA));
	if(oldts!=NULL)
	  oldts->next = ts;
	else
	  stak[i]->val.schemaS = ts;
	fread(ts, sizeof(SCHEMA), 1, fil);
	fread(&l, sizeof(int), 1, fil);
	ts->name = (char *)Malloc(l+1);
	fread(ts->name, sizeof(char), l, fil);
	ts->name[l] = 0;
	ts->value = (STACK *)Malloc(sizeof(STACK));
	fread(ts->value, sizeof(STACK), 1, fil);
	switch(ts->value->kind) {
	case intK:
	  break;
	case charK:
	  fread(&l, sizeof(int), 1, fil);
	  ts->value->val.charS = (char *)Malloc(l+1);
	  fread(ts->value->val.charS, sizeof(char), l, fil);
	  ts->value->val.charS[l] = 0;
	  break;
	case schemaK:
	  break;
	}
	oldts = ts;
      } while (ts->next!=NULL);
      break;
    }
  }
  fread(&globalcount, sizeof(int), 1, fil);
  usedglobals = (int *)Malloc(globalcount * sizeof(int));
  fread(usedglobals, sizeof(int), globalcount, fil);
  fclose(fil);
  loadglobals();
}

void savestate()
{
  FILE *fil;
  int i, l;
  SCHEMA *ts;
  push_intvalue(fp);
  if ((fil = fopen(sessionID, "wb")) == NULL) {
    printf("Couldn't save state, aborting\n");
    exit(1);
  }
  fwrite(&sp, sizeof(int), 1, fil);
  for(i = 0; i <= sp; i++)
    fwrite(stak[i], sizeof(STACK), 1, fil);
  for(i = 0; i <= sp; i++) {
    switch(stak[i]->kind) {
    case intK:
      break;
    case charK:
      l = strlen(stak[i]->val.charS);
      fwrite(&l, sizeof(int), 1, fil);
      fwrite(stak[i]->val.charS, sizeof(char), l, fil);
      break;
    case schemaK:
      for(ts=stak[i]->val.schemaS; ts; ts=ts->next) {
	fwrite(ts, sizeof(SCHEMA), 1, fil);
	l = strlen(ts->name);
	fwrite(&l, sizeof(int), 1, fil);
	fwrite(ts->name, sizeof(char), l, fil);
 	fwrite(ts->value, sizeof(STACK), 1, fil);
	switch(ts->value->kind) {
	case intK:
	  break;
	case charK:
	  l = strlen(ts->value->val.charS);
	  fwrite(&l, sizeof(int), 1, fil);
	  fwrite(ts->value->val.charS, sizeof(char), l, fil);
	  break;
	case schemaK:
	  break;
	}
      }
      break;
    }
  }
  fwrite(&globalcount, sizeof(int), 1, fil);
  fwrite(usedglobals, sizeof(int), globalcount, fil);
  fclose(fil);
  saveglobals();
}

void loadglobals()
{
  int i, l;
  SCHEMA *ts, *oldts;
  STACK *st;
  FILE *fil;
  globals = (FILE **)Malloc(globalcount * sizeof(FILE *));
  for(i=0; i<globalcount; ++i) 
    if(usedglobals[i]) {
      globals[i] = fopen(strcat3(gprefix, ".global.", itoa(i)), "rb+");
      fil = globals[i];
      if(fil!=NULL) {
	lockf(fileno(fil), F_LOCK, sizeof(STACK));
	st = (STACK *)Malloc(sizeof(STACK));
	fread(st, sizeof(STACK), 1, fil);
	stak[i] = st;
	switch(st->kind) {
	case intK:
	  break;
	case charK:
	  fread(&l, sizeof(int), 1, fil);
	  st->val.charS = (char *)Malloc(l+1);
	  fread(st->val.charS, sizeof(char), l, fil);
	  st->val.charS[l] = 0;
	  break;
	case schemaK:
	  oldts = NULL;
	  do {       
	    ts = (SCHEMA *)Malloc(sizeof(SCHEMA));
	    if(oldts!=NULL)
	      oldts->next = ts;
	    else
	      st->val.schemaS = ts;
	    fread(ts, sizeof(SCHEMA), 1, fil);
	    fread(&l, sizeof(int), 1, fil);
	    ts->name = (char *)Malloc(l+1);
	    fread(ts->name, sizeof(char), l, fil);
	    ts->name[l] = 0;
	    ts->value = (STACK *)Malloc(sizeof(STACK));
	    fread(ts->value, sizeof(STACK), 1, fil);
	    switch(ts->value->kind) {
	    case intK:
	      break;
	    case charK:
	      fread(&l, sizeof(int), 1, fil);
	      ts->value->val.charS = (char *)Malloc(l+1);
	      fread(ts->value->val.charS, sizeof(char), l, fil);
	      ts->value->val.charS[l] = 0;
	      break;
	    case schemaK:
	      break;
	    }
	    oldts = ts;
	  } while (ts->next!=NULL);
	  break;
	}
      }
      else {
	globals[i] = fopen(strcat3(gprefix, ".global.", itoa(i)), "wb+");
	fil = globals[i];
	fwrite(stak[i], sizeof(STACK), 1, fil);
	rewind(fil);
	lockf(fileno(fil), F_LOCK, sizeof(STACK));
      }
    }
}

void saveglobals()
{
  int i, l;
  SCHEMA *ts;
  FILE *fil;
  for(i=0; i<globalcount; i++)
    if(usedglobals[i]) {
      fil = globals[i];
      rewind(fil);
      fwrite(stak[i], sizeof(STACK), 1, fil);
      switch(stak[i]->kind) {
      case intK:
	break;
      case charK:
	l = strlen(stak[i]->val.charS);
	fwrite(&l, sizeof(int), 1, fil);
	fwrite(stak[i]->val.charS, sizeof(char), l, fil);
	break;
      case schemaK:
	for(ts=stak[i]->val.schemaS; ts; ts=ts->next) {
	  fwrite(ts, sizeof(SCHEMA), 1, fil);
	  l = strlen(ts->name);
	  fwrite(&l, sizeof(int), 1, fil);
	  fwrite(ts->name, sizeof(char), l, fil);
	  fwrite(ts->value, sizeof(STACK), 1, fil);
	  switch(ts->value->kind) {
	  case intK:
	    break;
	  case charK:
	    l = strlen(ts->value->val.charS);
	    fwrite(&l, sizeof(int), 1, fil);
	    fwrite(ts->value->val.charS, sizeof(char), l, fil);
	    break;
	  case schemaK:
	    break;
	  }
	}
	break;
      }
      rewind(fil);
      lockf(fileno(fil), F_ULOCK, sizeof(STACK));
      fclose(fil);
    }
}

void debugstack(int stop)
     /* Prints a snapshot of the current stack - used for debugging */
{
  int i;
  printf("Content-type: text/html\n\n");
  printf("sp: %2i  fp: %2i <br>\n", sp, fp);
  for (i = 0; i <= sp; i++) {
    printf("%2i: %s<br>\n", i, tostring(stak[i]));
  }
  if(stop)
    exit(0);
}
