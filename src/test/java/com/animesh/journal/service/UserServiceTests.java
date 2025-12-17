package com.animesh.journal.service;

import com.animesh.journal.repositry.UserRepositry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepositry userRepositry;

    @Disabled // this ensures that this test will not run
    @Test
    public void testfindByUserName(){
        assertNotNull(userRepositry.findByUserName("Sourav"));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "2,3,6"
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b,"Failed for "+expected );
    }
}
