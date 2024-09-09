package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public interface OperationHandler {
    void apply(Map<String, Integer> storage, FruitTransaction transaction);
}
