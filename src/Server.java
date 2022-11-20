import java.util.Arrays;
import java.util.stream.Collectors;

public class Server extends Helper{
    private String keySK = "qw31z1cv";

    private String keyCS;
    DES des = new DES();


    private String secretInf = "Kerberos /kɛərbərəs/ — сетевой протокол аутентификации, который предлагает механизм взаимной аутентификации клиента и сервера перед установлением связи между ними. Kerberos выполняет аутентификацию в качестве службы аутентификации доверенной третьей стороны, используя криптографический разделяемый секрет, при условии, что пакеты, проходящие по незащищенной сети, могут быть перехвачены, модифицированы и использованы злоумышленником. Kerberos построен на криптографии симметричных ключей и требует наличия центра распределения ключей. Расширения Kerberos могут обеспечить использование криптографии с открытым ключом на определенных этапах аутентификации.";


    public void clientServerConnection(String time,String ticket_TGSs_dec) throws Exception {

        String[] ticket_TGSs = Arrays.stream(ticket_TGSs_dec.split("(?<=\\G.{16})")).map(e ->des.decrypt(e,keySK)).toArray(String[]::new);

        keyCS = ticket_TGSs[3];

        if(timeDiffChecker(getTime()) && Integer.parseInt(getTime()) < Integer.parseInt(ticket_TGSs[2])){

        }else{
            throw new Exception("Неверные данные, обрыв соеденения");
        }
    }
}
