package catansim.Task1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//Runs all the test classes
@RunWith(Suite.class)
@Suite.SuiteClasses({
    NodeTest.class,
    PlayerTest.class,
    DiceTest.class,
    MapCatalogTest.class,
    MapPlayerHandTest.class,
    PieceHandlerTest.class
})
public class AllTests {

}