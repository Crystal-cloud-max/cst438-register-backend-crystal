package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@SpringBootTest
public class EndToEndNewStudentTest{

   public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";

   public static final String URL = "http://localhost:3000";

   public static final String TEST_USER_EMAIL = "test@csumb.edu";

   public static final int TEST_STUDENT_ID = 1; 

   public static final String TEST_STUDENT_NAME = "test";

   public static final int SLEEP_DURATION = 1000; // 1 second.
   
   /*
    * When running in @SpringBootTest environment, database repositories can be used
    * with the actual database.
    */
   
   @Autowired
   StudentRepository studentRepository;
   
   /*
    * Student add course TEST_STUDENT_ID 
    */
   
   @Test
   public void addStudentTest() throws Exception {

      /*
       * if student is already enrolled, then delete the student.
       */
      
      Student x = null;
      do {
         x = studentRepository.findByEmail(TEST_USER_EMAIL);
         if (x != null)
            studentRepository.delete(x);
      } while (x != null);

      // set the driver location and start driver
      //@formatter:off
      // browser  property name           Java Driver Class
      // edge  webdriver.edge.driver      EdgeDriver
      // FireFox  webdriver.firefox.driver   FirefoxDriver
      // IE       webdriver.ie.driver     InternetExplorerDriver
      //@formatter:on

      //TODO update the property name for your browser 
      System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
      
      //TODO update the class ChromeDriver()  for your browser
      // For chromedriver 111 need to specify the following options 
      ChromeOptions ops = new ChromeOptions();
      ops.addArguments("--remote-allow-origins=*"); 
      WebDriver driver = new ChromeDriver(ops);
      // Puts an Implicit wait for 10 seconds before throwing exception
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

      try {

         driver.get(URL);
         Thread.sleep(SLEEP_DURATION);

      // Locate and click "Add Student" button which is the first and only button on the page.
         //find the 1st <button> tag with id=addStudentButton
         //this button is the dialog to add student
         WebElement we = driver.findElement(By.id("addStudentButton"));
         we.click();
         Thread.sleep(SLEEP_DURATION);//for 1 second
         
         // enter student no and click Add button
         //find all <textfield> tags with attribute names
         driver.findElement(By.name("name")).sendKeys(TEST_STUDENT_NAME);
         driver.findElement(By.name("email")).sendKeys(TEST_USER_EMAIL);
         driver.findElement(By.id("add")).click();//button clicked to add student
         Thread.sleep(SLEEP_DURATION);

         /*
         * verify that new course shows in schedule.
         * get the title of all courses listed in schedule
         */ 
      
         x = studentRepository.findByEmail(TEST_USER_EMAIL);
         assertNotNull(x, "Student added but not listed.");
         if(x != null) {
            studentRepository.delete(x);
         }

      } 
      catch (Exception ex) {
         throw ex;
      } 
      finally {

         // clean up database.
         x = studentRepository.findByEmail(TEST_USER_EMAIL);
         if (x != null)
            studentRepository.delete(x);

         driver.quit();
      }

   }
}
