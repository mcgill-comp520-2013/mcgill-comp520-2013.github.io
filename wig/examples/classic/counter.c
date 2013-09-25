/* This file is generated from the wig file ../examples/classic/counter.wig */

#include <stdlib.h>
#include <stdio.h>

#include "runwig18.h"

extern int fp;
extern char *url, *sessionID;

void output_Nikolaj(char *gap_no) {
  printf("<%s ", "body");
  printf("> ");
  printf("%s", "\n     ");
  printf("<%s ", "img");
  printf("%s ", "src");
  printf("=");
  printf("\"%s\" ", "http://www.brics.dk/~mis/babybath.jpg");
  printf("> ");
  printf("%s", "\n     ");
  printf("<%s ", "p");
  printf("> ");
  printf("%s", "\n     ");
  printf("<%s ", "i");
  printf("> ");
  printf("%s", "You are visitor number ");
  printf("%s ", gap_no);
  printf("</%s> ", "i");
  printf("%s", "\n  ");
  printf("</%s> ", "body");
}

void push_globals() {
  push_int();
}

int main(int argc, char **argv) {
  init(argv[0]);
  if(!pop()->val.intS)
  goto return_;
  dup_();
  push_charvalue("Access");
  neq();
  if(!pop()->val.intS)
  goto session_0;
  goto error;

session_0:
  pop();
  use_globals("1");
  load_int(0);
  push_intvalue(1);
  plus();
  dup_();
  store_int(0);
  pop();
  load_int(0);
  printf("Content-type: text/html\n\n<html>");
  output_Nikolaj(tostring(pop()));
  printf("</html>\n");
  saveglobals();
  unlink(sessionID);
  exit(0);
  printf("Content-type: text/html\n\n");
  printf("<html><head><title>Session terminated</title></head><body><h1>This session is over</h1></body>\n");
  saveglobals();
  unlink(sessionID);
  exit(0);

return_:
  pop_fp();
  switch(pop()->val.intS) {
  case   0 : goto session_0; break;
}


exit:
  savestate();
  return 0;

error:
  printf("Content-type: text/html\n\n");
  printf("<html><head><title>Illegal request</title></head><body><h1>Illegal request</h1></body></html>\n");
  return 0;
}

