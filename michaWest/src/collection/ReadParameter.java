package collection;

import exceptions.InvalidDateFormatException;
import inputManager.DateConverter;

public class ReadParameter {

    public float convertToCX(String s){
        float x = -200;
        try{
            x =Float.parseFloat(s);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return x;
    }

    public float convertToCY(String s){
        float y = -200;
        try{
            y =Float.parseFloat(s);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return y;
    }

    public double convertToHeight(String s){
        double height = -1;
        try{
            height =Double.parseDouble(s);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return height;
    }

    public long convertToWeight(String s){
        long weight = -1L;
        try{
            weight =Long.parseLong(s);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return weight;
    }

    public double convertToLX(String s){
        double x = 0;
        try{
            x =Double.parseDouble(s);
        } catch (NumberFormatException|NullPointerException  e) {
            System.out.println(e.getMessage());
        }
        return x;
    }

    public long convertToLY(String s){
        long y = 0;
        try{
            y =Long.parseLong(s);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return y;
    }

    public double convertToLZ(String s){
        double z = 0;
        try{
            z =Double.parseDouble(s);
        } catch (NumberFormatException|NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return z;
    }

    public java.time.LocalTime convertToCreationTime(String s) throws InvalidDateFormatException {
        return DateConverter.parseLocalDate(s);
    }

    public int convertToId (String s){
        int i = 0;
        try{
            i =Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

}
