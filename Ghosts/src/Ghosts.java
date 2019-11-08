import shell.Command;
import shell.Shell;
import shell.exceptions.CriticalGhostsException;

public class Ghosts extends Shell{
    boolean nappal, ablakNyitva, orgMa, jatekVege, huhogMÉ, huhogEÉ, kacagMÉ, kacagEÉ, énekelMÉ, énekelEÉ;
    String sHuhogMÉ, sHuhogEÉ, sKacagMÉ, sKacagEÉ, sÉnekelMÉ, sÉnekelEÉ;
    int zavartalanDb;
    
    protected void Init() {
        super.init(); // meghívjuk a szülőjét (a szülőosztály init metódusát)
        jatekVege = false;
        ablakNyitva = false;
        orgMa = false;
        nappal = false;
        zavartalanDb = 0;
    }
    
    public Ghosts() {
        
        addCommand(new Command("new") {
            @Override
            public boolean execute(String... strings) {
                if(strings.length == 1 && strings[0].compareTo("initial") == 0)
                {
                    Init();
                    huhogMÉ = true;
                    kacagMÉ = true;
                    énekelMÉ = true;
                    sHuhogMÉ = "Huhogó huhog.";
                    sKacagMÉ = "Kacagó kacag.";
                    sÉnekelMÉ = "Éneklő énekel.";
                }
                else if(strings.length == 0)
                {
                    Init();
                    huhogMÉ = isHooting();
                    kacagMÉ = isLaughing();
                    énekelMÉ = isSinging();
                    
                    if(huhogMÉ == true)
                        sHuhogMÉ = "Huhogó huhog.";
                    else sHuhogMÉ = "Huhogó hallgat.";
                    if(kacagMÉ == true)
                        sKacagMÉ = "Kacagó kacag.";
                    else sKacagMÉ = "Kacagó hallgat.";
                    if(énekelMÉ == true)
                        sÉnekelMÉ = "Éneklő énekel.";
                    else sÉnekelMÉ = "Éneklő hallgat.";
                }
                else return false;
                
                return true;
            }
        });
        addCommand(new Command("print") {
            @Override
            public boolean execute(String... strings) {
                if(strings.length != 0)
                    return false; 
                
                if(nappal == false){
                    format("Éjszaka van:%n");
                    format("%s %s %s%n", sHuhogMÉ, sKacagMÉ, sÉnekelMÉ);
                } else {
                    format("Nappal van:%n");
                    format("Előző éjjel %s, %s és %s%n", sHuhogEÉ, sKacagEÉ, sÉnekelEÉ);
                    if(ablakNyitva == true)
                        format("Az ablak nyitva van. ");
                    else format("Az ablak zárva van. ");
                    if(orgMa == true)
                        format("Ma már orgonáltál.%n");
                    else format("Ma még nem orgonáltál.%n");
                }
                if(zavartalanDb == 3)
                    format("Megnyerted a játékot!%n");
                
                return true;
            }
        });
        addCommand(new Command("open") {
            @Override
            public boolean execute(String... strings) {
                if(strings.length != 1 || strings[0].compareTo("window") != 0 || ablakNyitva == true || nappal == false || jatekVege == true)
                    return false; 
                
                ablakNyitva=true;
                
                return true;
            }
        });
        addCommand(new Command("close") {
            @Override
            public boolean execute(String... strings) {
                if(strings.length != 1 || strings[0].compareTo("window") != 0 || ablakNyitva == false || nappal == false || jatekVege == true)
                    return false; 
                
                ablakNyitva=false;
                
                return true;
            }
        });
        addCommand(new Command("play") {
            @Override
            public boolean execute(String... strings) {
                if(strings.length != 1 || strings[0].compareTo("organ") != 0 || orgMa == true || nappal == false || jatekVege == true)
                    return false; 
                
                orgMa=true;
                
                return true;
            }
        });
        addCommand(new Command("go") {
            @Override
            public boolean execute(String... strings) {
                if(strings.length != 2 || strings[0].compareTo("to") != 0 || strings[1].compareTo("bed") != 0 
                        || nappal == false || jatekVege == true)
                    return false; 
                
                nappal=false;
                if(kacagEÉ == true)
                    huhogMÉ = true;
                else huhogMÉ = false;
                if(ablakNyitva == false)
                    if(huhogEÉ == true)
                        kacagMÉ = true;
                    else kacagMÉ = false;
                else 
                    if(huhogEÉ == true)
                        kacagMÉ = false;
                    else kacagMÉ = true;
                if(orgMa == true && kacagEÉ == false)
                    if(énekelEÉ == true)
                        énekelMÉ = false;
                    else énekelMÉ = true;
                else 
                    if(énekelEÉ == true)
                        énekelMÉ = true;
                    else énekelMÉ = false;
                
                if(huhogMÉ == true)
                    sHuhogMÉ = "Huhogó huhog.";
                else sHuhogMÉ = "Huhogó hallgat.";
                if(kacagMÉ == true)
                    sKacagMÉ = "Kacagó kacag.";
                else sKacagMÉ = "Kacagó hallgat.";
                if(énekelMÉ == true)
                    sÉnekelMÉ = "Éneklő énekel.";
                else sÉnekelMÉ = "Éneklő hallgat.";
                format("%s %s %s%n", sHuhogMÉ, sKacagMÉ, sÉnekelMÉ);
                
                if(huhogMÉ == false && kacagMÉ == false && énekelMÉ == false)
                    zavartalanDb++;
                else zavartalanDb = 0;
                
                return true;
            }
        });
        // még azt kell megcsinálnom, hogy mikor felkel a játékos, kiírja neki, hány éjszaka alszik zavartalanul zsinórban, valamint a kiiratásokra is lehetne egy metódust írni, hogy rövidebb elgyen a kód
        addCommand(new Command("get") {
            @Override
            public boolean execute(String... strings) {
                if(strings.length != 1 || strings[0].compareTo("up") != 0 || nappal == true || jatekVege == true)
                    return false; 
                
                nappal = true; orgMa= false;
                huhogEÉ = huhogMÉ; 
                kacagEÉ = kacagMÉ;
                énekelEÉ = énekelMÉ;
                if(huhogEÉ == true)
                    sHuhogEÉ = "Huhogó huhogott";
                else sHuhogEÉ = "Huhogó hallgatott";
                if(kacagEÉ == true)
                    sKacagEÉ = "Kacagó kacagott";
                else sKacagEÉ = "Kacagó hallgatott";
                if(énekelEÉ == true)
                    sÉnekelEÉ = "Éneklő énekelt.";
                else sÉnekelEÉ = "Éneklő hallgatott.";
                
                //format("Nappal van:%n");
                format("Előző éjjel %s, %s és %s%n", sHuhogEÉ, sKacagEÉ, sÉnekelEÉ);
                if(zavartalanDb == 3)
                    jatekVege = true;
                if(jatekVege == true)
                    format("Megnyerted a játékot!%n");
                else{
                    if(ablakNyitva == true)
                        format("Az ablak nyitva van. ");
                    else format("Az ablak zárva van. ");
                    if(orgMa == true)
                        format("Ma már orgonáltál.%n");
                    else format("Ma még nem orgonáltál.%n");
                }
                
                return true;
            }
        });
        addCommand(new Command("save") { // ezt a parancsot is meg kell még írnom !
            @Override
            public boolean execute(String... strings) {
                if(strings.length != 2 || strings[0].compareTo("to") != 0 || strings[1].compareTo("bed") != 0 
                        || nappal == false || jatekVege == true)
                    return false; 
                
                return true;
            }
        });
    }
    
    public static void main(String[] args) throws CriticalGhostsException {
        Shell sh = Loader.load();
        sh.readEvalPrint();
    }
}