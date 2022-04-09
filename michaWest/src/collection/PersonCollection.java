//: collection/PersonCollection.java
package collection;

import data.*;
import exceptions.*;
import inputManager.DateConverter;

import java.time.LocalTime;
import java.util.*;
/**Класс коллекция типd Vector для объектов Person*/
public class PersonCollection {
    private Vector<Person> collection;
    private final java.time.LocalTime createTime;
    private HashSet<Integer> existId;
    private boolean order;

    /**Конструктое PersonCollection. Определяет время создания. Порядок сортировки. И создают коллекцию по необходимости*/
    public PersonCollection(){
        createTime = LocalTime.now();
        order = true;
        if(collection==null){
            collection = new Vector<>();
        }
    }

    /**Преобразует переменную String в объекты класса, и добавляет их в коллекцию. Аргумент должен быть в формате xml*/
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

    /**Очищает коллекцию*/
    public void clear(){
        this.collection.clear();
    }

    /**Выводит информацию о коллекции. Размер и время создания*/
    public String getInfo(){
        return "Vector объектов Person, количество элементов: "+collection.size()+", время создания: "+ DateConverter.dateToString(this.createTime);
    }

    /**Возврашает коллекцию Vector<Person>*/
    public Vector<Person> getCollection(){
        return collection;
    }

    /**Преобразует коллекцию в строковое представление*/
    public String serializeCollection(){
        String text="";
        for (Person man : collection) {
            text = text + personToString(man);
        }
        return text;
    }

    /**Добавляем объект Person в коллекцию, и генерируем для него id*/
    public void add(Person p){
        add(p, generateNextId());
    }

    /**Добавляем объект Person в коллекцию*/
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

    /**Проверяем если ли объект с id i в коллекции. Если есть возвращаем true, иначе false*/
    public boolean checkId(int i){
        return collection.contains(getById(i));
    }

    /**Возвращает объект Person из коллекции по id. Если такого объекта нет возвращает null*/
    public Person getById(int i){
        for(Person p: collection){
            if(p.getId() == i){
                System.out.println(p.getName());
                return p;
            }
        }
        return null;
    }

    /**Удаляет объект с id равным i. Если этот объект был в коллекции, то он будет удален и будет возвращено true, иначе false*/
    public boolean removeById(int i){
        return collection.removeElement(getById(i));
    }

    /**Обновляем поля объекта Person с id равным i*/
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

    /**Удаляем элемент который находится на позиции number*/
    public void remove(int number){
        collection.remove(number);
    }

    /**Возвращает объект Person с минимальным весом в коллекции*/
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

    /**Возвращает коллекцию объектов Person с национальностью Country n*/
    public PersonCollection groupByNationality(Country n){
        PersonCollection r = new PersonCollection();
        for(Person p :collection){
            if(p.getNationality()==n){
                r.add(p);
            }
        }
        return r;
    }

    /**Выводить имена и id и объектов коллекции*/
    public String printCollection(){
        String text ="";
        for(Person p: collection){
            text=text+p.getId()+" "+p.getName()+"\n";
        }
        return text;
    }

    /**Возвращает количество объектов Person с цветом волос Color c*/
    public int countByHairColor(Color c){
        int count = 0;
        for(Person p: collection){
            if(p.getHairColor()==c){
                count++;
            }
        }
        return count;
    }

    /**Меняем порядок сортировки элементов в коллекции*/
    public void reorder(){
        order = !order;
        Vector<Person> rcol = new Vector<>();
        for(int i= collection.size()-1;i>=0;i--){
            rcol.add(collection.elementAt(i));
        }
        collection = rcol;
    }

    /**Возвращаем объект Person в строковом представлении. Со всеми полями*/
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

