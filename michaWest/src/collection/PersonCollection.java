package collection;

import data.*;
import exceptions.*;
import inputManager.DateConverter;

import java.time.LocalTime;
import java.util.*;

public class PersonCollection {
    private Vector<Person> collection;
    private final java.time.LocalTime createTime;
    private HashSet<Integer> existId;
    private boolean order;

    public PersonCollection(){
        createTime = LocalTime.now();
        order = true;
        if(collection==null){
            collection = new Vector<>();
        }
    }

    public boolean deserializeCollection(String xml){
        ArrayList<String[]> col;
        try{
            if(xml==null||xml.isEmpty()){
                collection = new Vector<>();
            }else{
                Xml xmlParser = new Xml();
                col = xmlParser.readFile(xml);
                for(String[] parameter : col){
                    Person man = new Person();
                    ReadParameter getParameter = new ReadParameter();
                    man.addId(this.generateNextId());
                    if(!man.addName(parameter[0])) throw new ParameterException("Указаеа пустая строка");
                    if(!man.addId(getParameter.convertToId(parameter[1]))) throw new ParameterException("Указаеа пустая строка");
                    if(!man.addCreationDate(getParameter.convertToCreationTime(parameter[3]))) throw new ParameterException("Время указано неправльно");
                    if(!man.addHeight(getParameter.convertToHeight(parameter[4]))) throw new ParameterException("Высота указана неправильно");
                    if(!man.addWeight(getParameter.convertToWeight(parameter[5]))) throw new ParameterException("Вес указан неправильно");
                    if(!man.addColor(parameter[6])) throw new ParameterException("Цвет указан неправильно. Существуют только слудующие цвета: Red, Yellow, Brown");
                    if(!man.addNationality(parameter[7])) throw new ParameterException("Национальность указана неправильно. Существуют только следующие национальности: USA, China, Vatican, North_Korea, Japan");
                    if(!man.addCoordinates(getParameter.convertToCX(parameter[9]), getParameter.convertToCY(parameter[10]))) throw new ParameterException("Координаты указаны неправильно");
                    if(!man.addLocation(getParameter.convertToLX(parameter[11]), getParameter.convertToLY(parameter[12]), getParameter.convertToLZ(parameter[13]))) throw new ParameterException("Локация указана неправильно");
                    add(man, man.getId());
                }
            }
        }catch(ParameterException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private int generateNextId() {
        if (collection.isEmpty()){
            existId = new HashSet<>();
            existId.add(1);
            return 1;
        }
        else {
            int id = collection.lastElement().getId() + 1;
            if(existId.contains(id)){
                while (existId.contains(id)) id+=1;
            }
            existId.add(id);
            return id;
        }
    }

    public void clear(){
        this.collection.clear();
    }

    public String getInfo(){
        return "Vector объектов Person, количество элементов: "+collection.size()+", время создания: "+ DateConverter.dateToString(this.createTime);
    }

    public Vector<Person> getCollection(){
        return collection;
    }

    public String serializeCollection(){
        String text="";
        for (Person man : collection) {
            text = text + personToString(man);
        }
        return text;
    }

    public void add(Person p){
        add(p, generateNextId());
    }

    public void add(Person p, int id){
        p.addId(id);
        if(collection.isEmpty()) {
            collection.add(p);
        }else{
            for(int i=0;i<collection.size();i++){
                if(order){
                    if(collection.elementAt(i).getWeight()>p.getWeight()){
                        collection.add(i, p);
                        break;
                    }
                }else{
                    if(collection.elementAt(i).getWeight()<p.getWeight()){
                        collection.add(i, p);
                        break;
                    }
                }
                if(i==collection.size()-1){
                    collection.add(p);
                    break;
                }
            }
        }
    }

    public boolean checkId(int i){
        return collection.contains(getById(i));
    }

    public Person getById(int i){
        for(Person p: collection){
            if(p.getId() == i){
                System.out.println(p.getName());
                return p;
            }
        }
        return null;
    }

    public void removeById(int i){
        collection.removeElement(getById(i));
    }

    public void updateById(int id, Person newp){
        for (Person p : collection){
            if (p.getId() == id){
                newp.addId(id);
                collection.set(collection.indexOf(getById(id)), newp);
                System.out.println("Эдемент #"+ id +" обновлен");
                return;
            }
        }
    }

    public void remove(int number){
        collection.remove(number);
    }

    public Person minByWeight(){
        Long minW;
        Person rp = null;
        minW=collection.lastElement().getWeight();
        for(Person p: collection){
            if(p.getWeight()<=minW){
                minW = p.getWeight();
                rp =p;
            }
        }
        return rp;
    }

    public PersonCollection groupByNationality(Country n){
        PersonCollection r = new PersonCollection();
        for(Person p :collection){
            if(p.getNationality()==n){
                r.add(p);
            }
        }
        return r;
    }

    public String printCollection(){
        String text ="";
        for(Person p: collection){
            text=text+p.getId()+" "+p.getName()+"\n";
        }
        return text;
    }

    public int countByHairColor(Color c){
        int count = 0;
        for(Person p: collection){
            if(p.getHairColor()==c){
                count++;
            }
        }
        return count;
    }

    public void reorder(){
        order = !order;
        Vector<Person> rcol = new Vector<>();
        for(int i= collection.size()-1;i>=0;i--){
            rcol.add(collection.elementAt(i));
        }
        collection = rcol;
    }

    private String personToString(Person man){
        return man.getName()+"\n" +
                "   id: "+man.getId()+"\n"+
                "   координаты: ("+man.getCoordinates().getX()+";"+man.getCoordinates().getY()+")\n"+
                "   время создания: "+DateConverter.dateToString(man.getCreationDate())+"\n"+
                "   рост: "+man.getHeight()+"\n"+
                "   вес: "+man.getWeight()+"\n"+
                "   цвет волос: "+man.getHairColor()+"\n"+
                "   национальность: "+man.getNationality()+"\n"+
                "   локация: ("+man.getLocation().getX()+";"+man.getLocation().getY()+";"+man.getLocation().getZ()+")\n";
    }


}

