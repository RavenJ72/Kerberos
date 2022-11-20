import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client extends Helper{


    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);
        String password = "riko1234";

        KDC kdc = new KDC();
        DES des = new DES();
        Server server = new Server();


        String TGT_Ks[] = kdc.authentication("sunrise1","comp/122",des.encrypt(getTime(),password));

        String keyKS = des.decrypt(TGT_Ks[1],password);


        String[] ticket_TGSc = kdc.ticketRequest(TGT_Ks[0],des.encrypt(getTime(),keyKS));

        String[] ticket_TGS =  Arrays.stream(ticket_TGSc[0].split("(?<=\\G.{16})")).map(e ->des.decrypt(e,keyKS)).toArray(String[]::new);

        String ticket_TGSs = Arrays.stream(ticket_TGSc[1].split("(?<=\\G.{16})")).map(e ->des.decrypt(e,keyKS)).collect(Collectors.joining());



        server.clientServerConnection(des.encrypt(getTime(),ticket_TGS[3]),ticket_TGSs);


        System.out.println("\nСоеденение установелно успешно!\n" +
                "Теперь у сервера и пользователя есть сеансовый ключ, благодаря которому они могут обмениваться данными!");


    }
}
