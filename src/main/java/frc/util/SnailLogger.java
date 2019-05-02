package frc.util;

import java.io.*;
import edu.wpi.first.wpilibj.Timer;

public class SnailLogger{
    
    String stream;
    PrintWriter out;
    Timer Timer;
    int logNumber;
    
    public SnailLogger(String outputStream){
        stream = outputStream;
        Timer = new Timer();

        getLogNumber();
    }

    public void getLogNumber(){
        logNumber = 1;
        String tempString = "";
        try{
            BufferedReader in = new BufferedReader(new FileReader("logdata.txt")); //f.readLine()
            tempString = in.readLine();
            while(tempString != null){
                logNumber++;
                tempString = in.readLine();
            }
            in.close();
        }
        catch(IOException e){
            logNumber = -1;
        }
    }

    public void open(){
        try{
            out = new PrintWriter(new BufferedWriter(new FileWriter(stream + logNumber + ".csv")));
        }
        catch(IOException e){}

        Timer.reset();
        Timer.start();
    }

    public void close(){
        out.close();
    }

    public void stopTimer(){
        Timer.stop();
    }

    public void printTimeStamp(){
        out.print(Timer.get() + ",");
    }

    public void println(String s){
        out.println(s);
    }

    public void print(String s){
        out.print(s + ",");
    }

    public void write(String s){
        out.println(s);
    }

    public void writeDouble(double d){
        write(Double.toString(d));
    }
    
}