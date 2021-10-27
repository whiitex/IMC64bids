package IMC64bids_1;

public class TimeValue {
    public double calculateTime(int days,int hours,int minutes) {
        double timeDays = (double)days / 365;
        double timeHours = (double)hours / 8760;
        double timeMinutes = (double)minutes / 525600;
        double time = timeDays + timeHours + timeMinutes ;
        System.out.println(time);
        return time;
    }
}
