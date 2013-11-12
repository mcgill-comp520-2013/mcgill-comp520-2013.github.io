Martin Ashton
Simon Brisson

README FILE - cs520a2 - Benchmark.
----------------------------------

- Our benchmark program is a pokemon battling system.

- To run the program, at the command line do: "java Battle".
  Do "java Battle < in1 > out1" to use the in1 file as standard in,
  and to put the results in out1.

- The poke.db file contains a repertoire of pokemon available to the user.

- The input should be the following format (without the quotes):

"<pname>,<pname>,<pname>,<pname>,<pname>,<pname>\n
<pname>,<pname>,<pname>,<pname>,<pname>,<pname>\n"

  where:
  - <pname> is a valid pokemon name in poke.db (first word on every 
    line is a pokemon name) 
  - the <pname>'s are _only_ seperated by commas (NO WHITESPACE)!
  
- The program will create two teams with six pokemon on each team.

- The program will make the pokemon on both teams fight until one team
is victorious (at least one surviving pokemon). If both teams' last pokemon
faint on the same turn, the game ends in a draw.

- The game is probably not balanced for competitive play.
