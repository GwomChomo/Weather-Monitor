package monitor;

/**
 * Created by Gwom HP on 5/25/2017.
 */
public class View {
        double data, rainfall, temperature;
        String time;

    public View(double data, String time){
        this.data = data;
        this.time = time;
    }
    public View(double temperature, double rainfall, String time){
        this.temperature = temperature;
        this.rainfall = rainfall;
        this.time = time;
    }

    public double getData(){
        return data;
    }
    public String getTime(){
        return time;
    }
    public double getRainfall(){
        return rainfall;
    }

    public double getTemperature() {
        return temperature;
    }
}
