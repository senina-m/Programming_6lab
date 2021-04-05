package ru.senina.itmo.lab6;

import ru.senina.itmo.lab6.labwork.LabWork;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public abstract class ICollectionKeeper {
    private LinkedList<LabWork> list;

    abstract void setTime(Date time);
    abstract void setMyListType(String myListType);


    public abstract LinkedList<LabWork> getList();

    public abstract void setList(LinkedList<LabWork> list);

    public abstract String getType();

    /**@return Amount of elements in collection*/
    public abstract int getAmountOfElements();

    /**@return Time of collection creation */
    public abstract String getTime();

    /**@return the result of element update*/
    public abstract String updateID(long id, LabWork element);

    /**@return the result of adding element to collection*/
    public abstract String add(LabWork element);

    /** @return the result of removing element by it id*/
    public abstract String removeById(long id);

    /** @return String result of clearing collection. If it finished successful*/
    public abstract String clear();

    /**Remove element with given index*/
    public abstract String removeAt(int index);

    /**Remove element with grater value of SelfStudyHours of given element*/
    public abstract String removeGreater(LabWork element);

    /**Sort collection */
    public abstract String sort();

    /**Sort by difficulty of subject*/
    public abstract LabWork minByDifficulty();

    /**Filter by given description*/
    public abstract List<LabWork> filterByDescription(String description);

    /**Method to sort the list of elements
     * @return sorted collection of elements*/
    public abstract List<LabWork> getSortedList();
}
