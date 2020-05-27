package com.game.module;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FieldTest {

    List<Integer> listOfPossibleFieldValues = new ArrayList<Integer>() {
        {
            add(0);
            add(2);
            add(4);
            add(8);
            add(16);
            add(32);
            add(64);
            add(128);
            add(256);
            add(512);
            add(1024);
            add(2048);
        }
    };

    // TODO: 18.05.2020 CHCE ODPALAC TESTY W GRADLU
    //  ale nie umiem ._.
    //  umiem za to przez android studio odpalic wszystkie na raz, ale chce w gradlu!
    @Test
    public void fieldCreationTest() {
        for (int i : this.listOfPossibleFieldValues) {
            Assertions.assertEquals(new Field(i).getValue(), i);
        }
        Assertions.assertNotEquals(new Field(-1), -1);
    }

    @Test
    public void fieldSetValueTest() {
        Field field = new Field(4);
        Assertions.assertEquals(4, field.getValue());
        field.setValue(3);
        // TODO: 27.05.2020 jakos nie podoba mi sie to ze jak oczekujesz ze test sie nie wykona to podajesz
        //  field a jak ma sie wykonac to field.getValue()
        // TODO: 27.05.2020 testy sie nie zgadzaja, dziwne bo liczba 3 przechodzi a nie powinn
        Assertions.assertNotEquals(3, field);
        Assertions.assertEquals(4, field.getValue());
        field.setValue(12);
        Assertions.assertNotEquals(12, field);
        Assertions.assertEquals(4, field.getValue());
    }

}