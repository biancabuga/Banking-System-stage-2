package org.poo.main;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonOutput {
    /**
     *interfata pentru afisare
     */
    ObjectNode toJson();
}
