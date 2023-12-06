package org.atm;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapTest {

    @Test
    public void should_return_true_when_add_new_element() {
        Map<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(1, 2);
        Assertions.assertEquals(2, hashMap.get(1));
    }
}
