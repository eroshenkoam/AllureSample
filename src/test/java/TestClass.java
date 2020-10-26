import io.qameta.allure.Allure;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class TestClass implements ITest {
    private final TestEnum value;
    private final ThreadLocal<String> testName = new ThreadLocal<>();

    @DataProvider
    public static Object[][] dataProvider() {
        Object[][] result = new Object[5][1];
        int index = 0;
        for (TestEnum value : TestEnum.values()) {
            result[index++][0] = value;
        }
        return result;
    }

    @Factory(dataProvider = "dataProvider")
    public TestClass(TestEnum value) {
        this.value = value;
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName.set(method.getName() + "_" + value.toString());
    }

    @Test
    public void sampleTest() {
        Allure.getLifecycle().updateTestCase(t -> t.setName("Тестируем значение: " + value.toString()));
        Allure.parameter("value", value);
        System.out.println(value.toString());
    }

    @Override
    public String getTestName() {
        return testName.get();
    }
}
