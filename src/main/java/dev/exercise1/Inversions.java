package dev.exercise1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Inversions {

//	37.8681830,-122.2582610

    private static final DateFormat df  = new SimpleDateFormat();

    public static void main(String[] args) {
        int[] arr = { 2, 5, 3, 8, 9, 10, 11, 1 };
        System.out.println(String.format("Inversion: %d", new Inversions().findInversions(arr)));
        System.out.println(printarr(arr));
    }

    public int findInversions(int[] arr) {
    	int[] narr = new int[arr.length];
        return sort(arr, narr, 0, arr.length - 1);
    }

    private int sort(int[] arr, int[] narr, int start, int end) {
        if (start >= end)
            return 0;
        int mid = (end + start) / 2;
        int totalswaps = sort(arr, narr, start, mid) + sort(arr, narr, mid + 1, end);

        totalswaps += merge(arr, narr, start, end);
        return totalswaps;
    }

    private int merge(int[] arr, int[] narr, int start, int end) {
//        System.out.println(String.format("length {}", narr.length);

        int mid = (end + start) / 2;
        System.out.println(String.format("%s, %d, %d, %d", printarr(arr), start, end, mid));
        int i = start, j = mid+1;
        int totalswaps = 0;

        for (int k = start; k <= end; k++) {
            System.out.println(String.format("%s, i:%d, j:%d, mid:%d, k:%d", printarr(narr), i, j, mid, k));
        	if (i>mid) narr[k] = arr[j++];
        	else if (j>end) narr[k] = arr[i++];
        	else if (arr[i] < arr[j]) {
                narr[k] = arr[i++];
            } else {
                narr[k] = arr[j++];
                System.out.println(String.format(String.format("Total swaps: %d", totalswaps)));
                totalswaps += (mid - i + 1);
                System.out.println(String.format(String.format("Total swaps: %d", totalswaps)));
            }
            System.out.println(String.format("%s, i:%d, j:%d, mid:%d, k:%d", printarr(narr), i, j, mid, k));
        }

        for (int k = start; k <= end; k++)
            arr[k] = narr[k];
        System.out.println(printarr(arr));
        System.out.println(printarr(narr));
        System.out.println(String.format(String.format("Total swaps: %d", totalswaps)));
        return totalswaps;
    }
    
    private static String printarr(int[] arr) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("[");
    	for (int elem : arr) {
    		sb.append(elem);
    		sb.append(", ");
    	}
    	sb.deleteCharAt(sb.length()-1);
    	sb.deleteCharAt(sb.length()-1);
    	sb.append("]");
    	return sb.toString();
    }
}