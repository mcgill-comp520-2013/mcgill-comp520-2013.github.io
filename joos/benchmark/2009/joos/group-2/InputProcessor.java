import joos.lib.*;

public class InputProcessor {
    protected JoosIO io;
    //protected AsciiConverter convert;
  
    public InputProcessor() {
        super();
        io = new JoosIO();
    }

    public void proccessInput(){
        ImageConverter conv;
        ImageTransform tf;
        String line;
        int width;
        int height;
        String path;
        line = io.readLine();
        while(line != null){
            io.println(line);
            width = this.getWitdthFromLine(line);
            height = this.getHeightFromLine(line);
            path = this.getPathFromLine(line);
            io.println("width: "+width+" height: "+height+ " path: "+path);
            tf = new BasicBrightnessTransform(width, height);
            conv = new ImageConverter(path,tf);
            this.printImage(conv.convert());
            line = io.readLine();
        }
    }
    public void printImage(ASCIIImage img) {
       int i, j;

       for (i=0; i<img.getWidth(); i++) {
           for(j=0; j<img.getHeight(); j++) {
                io.print(img.getCharacter(j,i));
           }
           io.println("");
       }
    }
    public int getWitdthFromLine(String line){
        int index;
        Integer intValue;
        String valueString;
        line.trim();
        index = line.indexOf(";",0);
        valueString = line.substring(0,index);
        valueString.trim();
        intValue = new Integer(valueString);
        return intValue.intValue();
    }
    public int getHeightFromLine(String line){
        int index;
        int index2;
        Integer intValue;
        String valueString;
        line.trim();
        index = line.indexOf(";",0);
        index2 = line.indexOf(";", index);
        valueString = line.substring(index+1,index+index2+1);
        valueString.trim();
        intValue = new Integer(valueString);
        return intValue.intValue();
    }
    public String getPathFromLine(String line){
        int index;
        int index2;
        String valueString;
        line.trim();
        index = line.indexOf(";",0);
        index2 = line.indexOf(";", index);
        valueString = line.substring(index+index2+2,line.length());
        valueString.trim();
        return valueString;
    }

}
