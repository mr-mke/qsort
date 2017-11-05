package qsort;

import java.util.concurrent.RecursiveAction;

public class quickSort extends RecursiveAction {
    private int[] data;
    private int left;
    private int right;

    public quickSort(int[] data) {
        this.data = data;
        left = 0;
        right = data.length - 1;
    }

    public quickSort(int[] data, int left, int right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute(){
        if(left < right){
            int pivot = partition(data, left, right);
            invokeAll(new quickSort(data,left, pivot), new quickSort(data, pivot + 1, right));
        }
    }

    private int partition(int[] array, int low, int high) {
        
        int pivot = array[low];
        int i = low - 1;
        int j  = high + 1;
        
        while (true){
            do {
                i++;
            }while (array[i] < pivot);

            do {
                j--;
            }while (array[j] > pivot);
            
            if (i >= j)
                return j;

            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
