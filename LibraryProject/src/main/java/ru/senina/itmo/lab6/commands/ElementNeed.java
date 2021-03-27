package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.labwork.LabWork;

/**
 * Interface to mark classes who need to receive an element
 */
abstract public interface ElementNeed {
    abstract void setLabWorkElement(LabWork labWorkElement);
}
