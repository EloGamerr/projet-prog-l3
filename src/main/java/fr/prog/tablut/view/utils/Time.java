package fr.prog.tablut.view.utils;

public abstract class Time {
    public static String formatToString(int ms) {
        int arr[] = getTimesArray(ms);

        String text = "";
        String units[] = { "ms", "s", "m", "h" };

        for(int i=units.length - 1; i > 0; i--) {
            if(arr[i] > 0) {
                text += arr[i] + units[i];
            }
        }

        return text;
    }

    public static String format(int ms) {
        int arr[] = getTimesArray(ms);

        String h = (arr[3] > 9? "":"0") + arr[3],
            m = (arr[2] > 9? "":"0") + arr[2],
            s = (arr[1] > 9? "":"0") + arr[1];

        return ((arr[3] == 0)? "" : h + ":") + m + ":" + s;
    }

    private static int[] getTimesArray(int ms) {
        int time = ms;
        ms = time % 1000;
        time = (time - ms) / 1000;
        int secs = time % 60;
        time = (time - secs) / 60;
        int mins = time % 60;
        int hrs = (time - mins) / 60;

        int arr[] = { ms, secs, mins, hrs };

        return arr;
    }
}
