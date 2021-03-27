package ru.senina.itmo.lab6;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.senina.itmo.lab6.labwork.Discipline;
import ru.senina.itmo.lab6.labwork.ElementOfCollection;
import ru.senina.itmo.lab6.labwork.LabWork;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class to keep collection's elements
 */
public class CollectionKeeper implements CollectionKeepers{

    @JsonIgnore
    private final Date time = new Date();
    @JsonIgnore
    private final Comparator comparator = new Comparator();
    private LinkedList<LabWork> list;

    @Override
    public void setTime(Date time) {
    }

    @Override
    @JsonIgnore
    public void setMyListType(String myListType) {
    }

    public CollectionKeeper() {
        this.list = new LinkedList<>();
    }

    /**
     * @param list of collection's elements
     */
    public CollectionKeeper(LinkedList<LabWork> list) {
        this.list = list;
    }

    @Override
    public LinkedList<LabWork> getList() {
        return list;
    }


    //TODO: think what to do with that
    @Override
    public void setList(Collection<? extends ElementOfCollection> list) throws IllegalArgumentException{
        if(list instanceof LabWork) {
            this.list = new LinkedList(list);
        }else{
            throw new IllegalArgumentException("This elements aren't labWork!");
        }
    }

    @JsonIgnore
    public String getType(){
        return "LinkedList";
    }

    @JsonIgnore
    public int getAmountOfElements() {
        //TODO: не должен возвращать null - проверить
        return Optional.of(list.size()).orElse(0);
    }

    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        return dateFormat.format(time);
    }

    /**
     * Update element with given ID
     * @param id given ID
     * @param element element update value
     * @return String result of method work. If it finished successful
     */
    //TODO: think about this cast to LabWork element. Originally I thought that this class works only with LabWork elements list
    public String updateID(long id, ElementOfCollection element){
        if(element instanceof LabWork){
            for(int i=0; i < list.size(); i++){
                if(list.get(i).getId() == id){
                    list.set(i, (LabWork)element);
                    return "Element with id: " + id + " was successfully updated.";
                }
            }
            return "There is no element with id: " + id + " in collection.";
        }else {
            return "This elements aren't labWork and don't cast to collection.";
        }
    }

    /**
     * Add element to collection
     * @param element element to add
     * @return String result of method work. If it finished successful
     */
    @Override
    public String add(ElementOfCollection element) {
        if(element instanceof LabWork) {
            list.add((LabWork) element);
            return "Element with id: " + element.getId() + " was successfully added.";
        }else{
            return "Given element doesn't cast to collection.";
        }
    }

    /**
     * Remove element with given id
     * @param id given id
     * @return String result of method work. If it finished successful
     */
    @Override
    public String removeById(long id) {
        for(int i=0; i < list.size(); i++){
            if(list.get(i).getId() == id){
                list.remove(i);
                return "Element with id: " + id + " was successfully removed.";
            }
        }
        return "There is no element with id: " + id + " in collection.";
    }

    /**
     * Clear collection
     * @return String result of method work. If it finished successful
     */
    public String clear() {
        list.clear();
        return "The collection was successfully cleared.";
    }

    /**
     * Remove element with given index
     * @param index given index
     * @return String result of method work. If it finished successful
     */
    @Override
    public String removeAt(int index) {
        try {
            long id = list.remove(index).getId();
            return "Element with index " + index + " and id " + id + " was successfully removed.";
        }catch (IndexOutOfBoundsException e){
            return "Removing an element with index " + index + " was failed. No such index in the collection.";
        }
    }

    /**
     * Sort collection
     * @return String result of method work. If it finished successful
     */
    public String sort() {
        list.sort(comparator);
        return "Collection was successfully sort.";
    }

    /**
     * Remove element with grater value of SelfStudyHours of given element
     * @param element given element
     * @return String result of method work. If it finished successful
     */
    @Override
    public String removeGreater(ElementOfCollection element) {
        if(element instanceof LabWork) {
            List<Integer> indexToDelete = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (comparator.compare(list.get(i), (LabWork) element) > 0) {
                    indexToDelete.add(i);
                }
            }
            for (int i = indexToDelete.size() - 1; i >= 0; i--) {
                list.remove((int) indexToDelete.get(i));
            }
            return "All elements greater then entered were successfully removed.";
        }else {
            return "The entered Element isn't labWork and couldn't be contrasted with it's elements.";
        }
    }

    /**
     * Sort by difficulty of subject
     * @return String result of method work. If it finished successful
     * @throws IndexOutOfBoundsException if no elements in collection
     */
    public LabWork minByDifficulty() throws IndexOutOfBoundsException{
        try {
            LabWork element = list.get(0);
            for (LabWork labWork : list) {
                if (comparator.compareByDifficulty(element, labWork) > 0) {
                    element = labWork;
                }
            }
            return element;
        }catch (IndexOutOfBoundsException e){
            // TODO: Не кидать эту ошибку наверх?
            throw new InvalidArgumentsException("No elements in collection. Can't choose the less by Difficulty.");
        }
    }

    /**
     * Filter by given description
     * @param description given description
     * @return String result of method work. If it finished successful
     */
    @Override
    public List<? extends ElementOfCollection> filterByDescription(String description) {
        List<LabWork> filteredElements = new ArrayList<>();
        for(LabWork element : list){
            if(element.getDescription().equals(description)){
                filteredElements.add(element);
            }
        }
        return filteredElements;
    }

    /**
     * Method to sort the list of elements
     * @return sorted list of LabWork objects
     */
    @Override
    public List<LabWork> getSortedList() {
        List<LabWork> newList = this.list;
        newList.sort(comparator);
        return newList;
    }

    /**
     * Class to override compare method for LabWork objects
     */
    static class Comparator implements java.util.Comparator<LabWork> {

        @Override
        public int compare(LabWork o1, LabWork o2) {
            Discipline discipline1 = o1.getDiscipline();
            Discipline discipline2 = o2.getDiscipline();
            return discipline1.getSelfStudyHours() - discipline2.getSelfStudyHours();
        }

        public int compareByDifficulty(LabWork o1, LabWork o2) {
            return o1.getDifficulty().getValue() - o2.getDifficulty().getValue();
        }
    }
}
