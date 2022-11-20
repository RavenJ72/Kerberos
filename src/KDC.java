import java.util.*;
import java.util.stream.Collectors;
public class KDC extends Helper{

    private DES des = new DES();
    private Map<String,String> users = new HashMap<>();
    {
        users.put("sunrise1","riko1234");
        users.put("teacher2","ilovebmw");
    }

    private Map<String,String> servers = new HashMap<>();
    {
        servers.put("comp/122","op98vgfs");
        servers.put("serv/221","p0954fgn");
    }
    private Map<String,String> sessionKeys = new HashMap<>();

    private Map<String,String> domainsHis = new HashMap<>();
    {
        domainsHis.put("sunrise1","");
        domainsHis.put("teacher2","");
    }

    private String keyMK = "qw31z1cv";

    private String keySK = "qw31z1cv";




    public String[] authentication(String login,String domain,String encTime) throws Exception {
        if(servers.containsKey(domain) && users.containsKey(login)
                && timeDiffChecker(des.decrypt(encTime,users.get(login)))){

            sessionKeys.put(login,GeneratingRandomKey());
            domainsHis.replace(login,domain);

            String TGT_Ks[] = new String[4];

            TGT_Ks[0] = des.encrypt(sessionKeys.get(login),keyMK) + des.encrypt(login,keyMK) +
                    des.encrypt(String.valueOf(Integer.parseInt(getTime()) + 9999),keyMK);


            TGT_Ks[1] = des.encrypt(sessionKeys.get(login),users.get(login));


            return TGT_Ks;

        }else{
            throw new Exception("Неверные данные, обрыв соеденения");
        }
    }


    public String[] ticketRequest(String tgt,String time) throws Exception {


        String[] TGT = tgt.split("(?<=\\G.{16})");

        for (int i = 0; i < TGT.length; i++) {
            TGT[i] = des.decrypt(TGT[i],keyMK);
        }


        if(Integer.parseInt(TGT[2]) > Integer.parseInt(getTime())
                &&  TGT[0].equals(sessionKeys.get(TGT[1]))){

            String[] ticket_TGS = new String[4];
            ticket_TGS[0] = TGT[1];
            ticket_TGS[1] = domainsHis.get(TGT[1]);
            ticket_TGS[2] = String.valueOf(Integer.parseInt(getTime()) + 9999);
            ticket_TGS[3] = servers.get(domainsHis.get(TGT[1]));

            String ticket_TGSs = Arrays.stream(ticket_TGS).map(e->des.encrypt(e,keySK)).collect(Collectors.joining());

            String[] ticket_TGSc = new String[2];

            ticket_TGSc[0] = Arrays.stream(ticket_TGS).map(e->des.encrypt(e,TGT[0])).collect(Collectors.joining());

            ticket_TGSc[1] = Arrays.stream(ticket_TGSs.split("(?<=\\G.{8})")).map(e ->des.encrypt(e,TGT[0])).collect(Collectors.joining());

            return ticket_TGSc;

        }else{
            throw new Exception("Неверные данные, обрыв соеденения");
        }
    }

}
