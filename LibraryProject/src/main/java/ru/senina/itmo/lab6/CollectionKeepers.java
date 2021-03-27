package ru.senina.itmo.lab6;

import ru.senina.itmo.lab6.labwork.ElementOfCollection;
import ru.senina.itmo.lab6.labwork.LabWork;

import java.util.*;

public interface CollectionKeepers {

    void setTime(Date time);
    void setMyListType(String myListType);
    Collection<? extends ElementOfCollection> getList();
    void setList(Collection<? extends ElementOfCollection> list);
    String getType();

    /**@return Amount of elements in collection*/
    int getAmountOfElements();

    /**@return Time of collection creation */
    String getTime();

    /**@return the result of element update*/
    String updateID(long id, ElementOfCollection element);

    /**@return the result of adding element to collection*/
    String add(ElementOfCollection element);

    /** @return the result of removing element by it id*/
    String removeById(long id);

    /** @return String result of clearing collection. If it finished successful*/
    String clear();

    /**Remove element with given index*/
    String removeAt(int index);

    /**Remove element with grater value of SelfStudyHours of given element*/
    String removeGreater(ElementOfCollection element);

    /**Sort collection */
    String sort();

    //TODO: должен ли этот метод тут быть?
    /**Sort by difficulty of subject*/
    ElementOfCollection minByDifficulty();

    //TODO: All elements of collections have to has description? (optional)
    /**Filter by given description*/
    List<? extends ElementOfCollection> filterByDescription(String description);

    /**Method to sort the list of elements
     * @return*/
    Collection<? extends ElementOfCollection> getSortedList();
}
