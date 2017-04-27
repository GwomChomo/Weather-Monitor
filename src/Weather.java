/**
 * Created by Ernest Keita on 4/21/2017.
 */
public class Weather {
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTemp() {
        return temp;
    }

    public String getRainFall() {
        return rainFall;
    }

    private String name;
    private String date;
    private String temp;
    private String rainFall;

    public Weather(String name,String date,String temp,String rainFall){
        this.name=name;
        this.date=date;
        this.temp=temp;
        this.rainFall=rainFall;
    }
    public String toString(){
        return name;
    }
}
