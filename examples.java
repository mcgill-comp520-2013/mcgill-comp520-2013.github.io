import java.util.List ;
import java.util.ArrayList;

class Examples { 
    public static void foo (int i,char c) {}

    public static void m(char c, int i) {}
    public static void m(int c, char i) {}
    public static void m(String c, String i) {}
    public static void m (char c, char d) {}

    public static void d(double d, int c) {}

    public static void main() {
        // int is assignable from char
        char ch = 'f' ;
        int a = ch ;
        // so the method call foo(CHAR,CHAR) match with foo(INT,CHAR)
        foo('a','b') ;
        // char is not assignable from int
        char b  = a;
        // so the method call foo(INT,INT) doesn't match with FOO(INT,CHAR)
        foo(1,2) ;
        // ambiguous 
        // both works because int is assignable from char
        // no one has all the parameters assignable from the other parameters
        // so ambiguous
        m('a','a') ;
        // double is assignable from int
        double d = a ;
        // but not int from double
        int i = d ;
        // so call to FOO(DOUBLE,INT) doesnt work
        d(d,d) ;
        // but call to FOO (INT,INT) works
        d(i,i) ;

        // cast and instanceof : c < t OR t < c 
        Object o ;
        List<Object> l ; 
        String s ;
        // direct cast doesn't work : inconvertible types
        s = (String) l ;

        // cheating via (Object) works
        o = (Object)l ;
        s = (String)o ;
        /*
        */
    }
}

