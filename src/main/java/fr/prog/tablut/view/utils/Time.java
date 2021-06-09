package fr.prog.tablut.view.utils;

/**
 * A Class that transform a timestamp in string time
 */
public abstract class Time {
    /**
     * Returns a timestamp in formatted string of type :
     * <p>00:00:00</p>
     * <p>If hours are equals to 0, then it only returns for minutes and seconds.</p>
     * @param ms The timestamp
     * @return The formatted time
     */
    public static String formatToString(int ms) {
        int[] arr = getTimesArray(ms);

        StringBuilder text = new StringBuilder();
        String[] units = { "ms", "s", "m", "h" };

        for(int i=units.length - 1; i > 0; i--) {
            if(arr[i] > 0) {
                text.append(arr[i]).append(units[i]);
            }
        }

        return text.toString();
    }

    /**
     * Returns a timestamp in formatted string of type :
     * <p>0h 0m 0s</p>
     * <p>If hours are equals to 0, then it only returns for minutes and seconds.</p>
     * @param ms The timestamp
     * @return The formatted time
     */
    public static String format(int ms) {
        int[] arr = getTimesArray(ms);

        String h = (arr[3] > 9? "":"0") + arr[3],
            m = (arr[2] > 9? "":"0") + arr[2],
            s = (arr[1] > 9? "":"0") + arr[1];

        return ((arr[3] == 0)? "" : h + ":") + m + ":" + s;
    }

    /**
     * Returns an array of timing depending to given timestamp.
     * <p>The array is of type { milliseconds, seconds, minuts, hours }</p>
     * @param ms The timestamp
     * @return The times array
     */
    private static int[] getTimesArray(int ms) {
        int time = ms;
        ms = time % 1000;
        time = (time - ms) / 1000;
        int secs = time % 60;
        time = (time - secs) / 60;
        int mins = time % 60;
        int hrs = (time - mins) / 60;

        return new int[]{ ms, secs, mins, hrs };
    }
}
