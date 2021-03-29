package ru.senina.itmo.lab6;

import java.util.*;

public interface ICollectionKeeper<T extends CollectionElement> {

    void setTime(Date time);
    void setMyListType(String myListType);
    Collection<T> getList();
    void setList(Collection<T> list);
    String getType();

    /**@return Amount of elements in collection*/
    int getAmountOfElements();

    /**@return Time of collection creation */
    String getTime();

    /**@return the result of element update*/
    String updateID(long id, T element);

    /**@return the result of adding element to collection*/
    String add(T element);

    /** @return the result of removing element by it id*/
    String removeById(long id);

    /** @return String result of clearing collection. If it finished successful*/
    String clear();

    /**Remove element with given index*/
    String removeAt(int index);

    /**Remove element with grater value of SelfStudyHours of given element*/
    String removeGreater(T element);

    /**Sort collection */
    String sort();

    //TODO: должен ли этот метод тут быть?
    /**Sort by difficulty of subject*/
    T minByDifficulty();

    //TODO: All elements of collections have to has description? (optional)
    /**Filter by given description*/
    List<T> filterByDescription(String description);

    /**Method to sort the list of elements
     * @return sorted collection of elements*/
    Collection<T> getSortedList();
}
