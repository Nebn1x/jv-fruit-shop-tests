package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private DataConverterImpl dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20)
        );
        List<String> actualData = List.of(
                "b,banana,50",
                "s,apple,100",
                "r,banana,10",
                "p,apple,20"
        );
        List<FruitTransaction> actual = dataConverter.convertToTransaction(actualData);
        Assertions.assertEquals(actual,expected);
    }

    @Test
    void convertToTransaction_nullData_notOk() {
        List<String> invalidData = new ArrayList<>();
        invalidData.add(null);
        Assertions.assertThrows(RuntimeException.class,() -> {
            dataConverter.convertToTransaction(invalidData);
        });
    }

    @Test
    void convertToTransaction_emptyData_ok() {
        List<String> emptyData = new ArrayList<>();
        Assertions.assertDoesNotThrow(() -> {
            dataConverter.convertToTransaction(emptyData);
        });
    }

    @Test
    void convertToTransaction_invalidData_notOk() {
        List<String> invalidData = new ArrayList<>();
        invalidData.add("any , worlds , :) ");
        Assertions.assertThrows(RuntimeException.class,() -> {
            dataConverter.convertToTransaction(invalidData);
        });
    }

    @Test
    void convertToTransaction_unknownOperation_notOk() {
        List<String> invalidData = new ArrayList<>();
        invalidData.add("???,banana,50");
        Assertions.assertThrows(RuntimeException.class,() -> {
            dataConverter.convertToTransaction(invalidData);
        });
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> invalidData = List.of(
                "b,banana,",
                "s,apple,???",
                "r,banana,13"
        );
        Assertions.assertThrows(RuntimeException.class,() -> {
            dataConverter.convertToTransaction(invalidData);
        });
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        List<String> negativeData = new ArrayList<>();
        negativeData.add("s,banana,-10");
        negativeData.add("p,apple,-15");
        Assertions.assertThrows(RuntimeException.class,() -> {
            dataConverter.convertToTransaction(negativeData);
        });
    }
}
