package org.cartaxcheck.scripts;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.cartaxcheck.scripts.Constants.*;


public class Driver extends EventFiringWebDriver {
    public enum BrowserDriver
    {
        CHROME,  // Could use other browsers here
    }

    private static ClassLoader classLoader;
    private static File file;
    protected static WebDriver driver = null;
    public static WebDriverWait driverWait = null;
    private static DesiredCapabilities capabilities;
    protected static String deviceName = null;
    protected static String platformVersion = null;

    private static final Dimension BROWSER_WINDOW_SIZE = new Dimension(1280, 1024);
    private static String platform = null;
    private static final Integer IMPLICIT_WAIT_TIMEOUT = 20;
    public static Properties properties = new Properties();

    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            driver.quit();
        }
    };

    public Driver() {
        super(driver);
    }

    static
    {
        deviceName = System.getProperty("deviceName");
        platformVersion = System.getProperty("platformVersion");

        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public static WebDriver getDriver(BrowserDriver browserDriver)
    {
        capabilities = new DesiredCapabilities();

        driver = getChromeDriver();
        customiseDriver();

        int driverTimeWait = StringUtils.isEmpty(getDriverWait()) ? 10 : Integer.parseInt(getDriverWait());
        driverWait = new WebDriverWait(driver, driverTimeWait);

        return driver;
    }

    private static void getResource(String driverName, String webDriverKey)
    {
        classLoader = Driver.class.getClassLoader();
        file = new File(classLoader.getResource(driverName).getFile());
        System.setProperty(webDriverKey, file.getAbsolutePath());
    }

    private static WebDriver getChromeDriver()
    {
        ChromeOptions chromeOptions = new ChromeOptions();
        getResource(CHROME_DRIVER_FILE_PATH, CHROME_DRIVER_KEY);
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);

        capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        capabilities.setBrowserName("chrome");
        capabilities.setPlatform(Platform.MAC);
        capabilities.setCapability("applicationCacheEnabled", true);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        capabilities.setCapability("acceptSslCerts", true);
        driver = new ChromeDriver(capabilities);
        return driver;
    }

    private static void customiseDriver()
    {
        driver.manage().window().setSize(BROWSER_WINDOW_SIZE);
        driver.manage().window().maximize();
    }

    @Override
    public void close()
    {
        if (Thread.currentThread() != CLOSE_THREAD)
        {
            throw new UnsupportedOperationException(
                    "You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    public static String getDriverWait() {
        return properties.getProperty("driver_wait");
    }
}
