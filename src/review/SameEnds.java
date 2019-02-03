package review;

import java.util.ArrayList;
public class SameEnds {
    private ArrayList<Integer> arrayList;

    //array 1,2,4,5,3,1,2,4
    public SameEnds(ArrayList<Integer> arrayList) {
        this.arrayList = arrayList;
    }

    //ArrayList /2
    //check all - if not matched
        //ignore the end of first array, and first of second array
    // we get the first item and its index
    //we will loop to see if that item is repeated again in the array


    public String getMatch() {
        ArrayList matchingEnds = new ArrayList();
        int size = arrayList.size();
        int half = size/2;
        if(arrayList.subList(0, half) == arrayList.subList(half,size-1 )){
            return String.format("The longest sequence is %s and the length is %d",arrayList.subList(0, half), arrayList.subList(0,half).size());
        }else {
            int count = 0;
            for (int i = 0; i < half; i++) {
                for (int j = half; j < size; j++) {
                    if (arrayList.get(i) == arrayList.get(j)) {
                        count++;
                        matchingEnds.add(arrayList.get(i));
                        break;
                    }
                }
            }
            return ("The longest sequence is " + matchingEnds + " and the sequence length is " + count);

        }
    }
}
