import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;

public class Helper {

    static String getTime(){
        String data = String.valueOf(new Date().getTime());
        return data.substring(data.length() - 8);
    }
    static boolean timeDiffChecker(String time){
        return Integer.parseInt(getTime())  -  Integer.parseInt(time) < MAX_TIME_DIFFERENCE;
    }
    static final int MAX_TIME_DIFFERENCE = 50;


    public static String GeneratingRandomKey() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
