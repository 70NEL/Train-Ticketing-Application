package bestroute;

public interface TimeCalculator {
    default int timeAsInt(String time) {
        if(time == null || !time.contains(":")) {
            return 0;
        }
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return hours * 60 + minutes;
    }
}
