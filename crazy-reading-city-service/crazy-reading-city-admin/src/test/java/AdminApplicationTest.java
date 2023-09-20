import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author rjj
 * @date 2023/8/25 - 9:12
 */
@Slf4j
@SpringBootTest
public class AdminApplicationTest {

    @Test
    public void demo(){
        String a = "中国1";
        System.out.println(a.substring(0,a.length()-1));
        System.out.println(a.substring(a.length()-1));
    }

}
