package test.bioinfo.algorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the implementation of 4-approximation and 2-approximation algorithm written in Java for the assignment of Algorithms in BioInformatics
 * Created by XuLu on 2017/5/30.
 */
@SuppressWarnings("Duplicates")
public class Assignment {

    /**
     * implementation of 4-approximation algorithm
     *
     * @param flag
     * @param integerList
     * @return
     */
    protected static List<Integer> fourApproximation(int flag, List<Integer> integerList) {
        printList(integerList);
        if (integerList.get(integerList.size() - 1) == 1) {
            Collections.reverse(integerList);
            printList(integerList);
        }

        int breakPointNum = computeBreakPointNum(integerList);

        while (breakPointNum > 0) {
            List<List<Integer>> stripList = getStrip(integerList);
            if (isAllStripIncreasing(stripList)) {
                stripList.set(stripList.indexOf(stripList.get(0)), reverse(stripList.get(0)));
                integerList = concatSmallList(stripList);
            }
            List<List<Integer>> lists = getStrip(integerList);
            List<List<Integer>> descLists = new ArrayList<>();
            for (int i = 0; i < lists.size(); i++) {
                List<Integer> integers = lists.get(i);
                if (isIncreasing(integers) == false) {
                    descLists.add(integers);
                }
            }

            int piMin = findPiMin(descLists);
            int piMinSubOne = piMin - 1;
            integerList = reverse(integerList, piMin, piMinSubOne);
            printList(integerList);
            breakPointNum = computeBreakPointNum(integerList);
        }

        return integerList;
    }


    /**
     * implementation of 2-approximation algorithm
     *
     * @param flag
     * @param integerList
     * @return
     */
    protected static List<Integer> twoApproximation(int flag, List<Integer> integerList) {
        if (integerList.get(integerList.size() - 1) == 1) {
            Collections.reverse(integerList);
        }

        List<List<Integer>> stripList = getStrip(integerList);
        if (isAllStripIncreasing(stripList) == true) {
            for (int i = 0; i < stripList.size(); i++) {
                stripList.set(stripList.indexOf(stripList.get(i)), reverse(stripList.get(i)));
            }
            integerList = concatSmallList(stripList);
        }
        printList(integerList);
        int breakPointNum = computeBreakPointNum(integerList);

        while (breakPointNum > 0) {
            stripList = getStrip(integerList);

            if (isAllStripIncreasing(stripList) == true) {
                for (int i = 0; i < stripList.size(); i++) {
                    stripList.set(stripList.indexOf(stripList.get(i)), reverse(stripList.get(i)));
                    integerList = concatSmallList(stripList);
                    printList(integerList);
                    breakPointNum = computeBreakPointNum(integerList);
                    stripList = getStrip(integerList);
                }
            }
            List<List<Integer>> descLists = new ArrayList<>();
            for (int i = 0; i < stripList.size(); i++) {
                List<Integer> integers = stripList.get(i);
                if (isIncreasing(integers) == false) {
                    descLists.add(integers);
                }
            }

            int piMax = findPiMax(descLists);
            int piMin = findPiMin(descLists);
            List<Integer> rhoMin = getRhoMin(integerList, piMin);
            if (isIncreasing(rhoMin) == false) {
                List<Integer> subList = integerList.subList(integerList.indexOf(rhoMin.get(0)), integerList.indexOf(rhoMin.get(rhoMin.size() - 1)) + 1);
                integerList = reverse(integerList, subList.get(subList.size() - 1).intValue(), subList.get(subList.size() - 1).intValue() - 1);
                printList(integerList);
                breakPointNum = computeBreakPointNum(integerList);
            } else if (isIncreasing(getRhoMax(integerList, piMin))) {
                if (isIncreasing(getRhoMax(integerList, piMax)) == false) {
                    stripList = getStrip(integerList);
                    List<Integer> rhoMax = getRhoMax(integerList, piMax);
                    stripList.set(stripList.indexOf(rhoMax), reverse(rhoMax));
                    integerList = concatSmallList(stripList);
                    printList(integerList);
                    breakPointNum = computeBreakPointNum(integerList);
                }
            } else {
                List<List<Integer>> strips = getStrip(integerList);
                strips.forEach(integerList1 -> {
                    if (isIncreasing(integerList1)) {
                        strips.set(strips.indexOf(integerList1), reverse(integerList1));
                    }
                });
                integerList = concatSmallList(strips);
                breakPointNum = computeBreakPointNum(integerList);
                printList(integerList);
            }
        }

        return integerList;
    }

    /**
     * check whether all strip in the permutation is increasing or not
     *
     * @param integerList
     * @return
     */
    private static boolean isAllStripIncreasing(List<List<Integer>> integerList) {
        boolean flag = true;
        for (int i = 0; i < integerList.size(); i++) {
            if (isIncreasing(integerList.get(i)) == false) {
                flag = false;
                break;
            }
        }

        return flag;
    }


    /**
     * get rho min range
     *
     * @param integerList
     * @param piMin
     * @return
     */
    private static List<Integer> getRhoMin(List<Integer> integerList, int piMin) {
        int startIndex, endIndex;
        if (integerList.get(integerList.size() - 1) == 1)
            return integerList;

        if (integerList.indexOf(piMin) > integerList.indexOf(piMin - 1)) {
            startIndex = integerList.indexOf(piMin - 1);
            endIndex = integerList.indexOf(piMin);
            List<Integer> subList = integerList.subList(startIndex + 1, endIndex + 1);

            return subList;
        } else {
            startIndex = integerList.indexOf(piMin);
            endIndex = integerList.indexOf(piMin - 1);
            List<Integer> subList = integerList.subList(startIndex + 1, endIndex + 1);

            return subList;
        }
    }

    /**
     * get rho max range
     *
     * @param integerList
     * @param piMax
     * @return
     */
    private static List<Integer> getRhoMax(List<Integer> integerList, int piMax) {
        int startIndex, endIndex;
        if (integerList.get(integerList.size() - 1) == 1)
            return integerList;

        if (integerList.indexOf(piMax) > integerList.indexOf(piMax + 1)) {
            startIndex = integerList.indexOf(piMax + 1);
            endIndex = integerList.indexOf(piMax);
            List<Integer> subList = integerList.subList(startIndex + 1, endIndex + 1);

            return subList;
        } else {
            startIndex = integerList.indexOf(piMax);
            endIndex = integerList.indexOf(piMax + 1);
            List<Integer> subList = integerList.subList(startIndex + 1, endIndex + 1);

            return subList;
        }
    }


    /**
     * reverse a list
     *
     * @param integers
     * @return
     */
    private static List<Integer> reverse(List<Integer> integers) {
        Collections.reverse(integers);

        return integers;
    }


    /**
     * find PI min value
     *
     * @param descList
     * @return
     */
    private static int findPiMin(List<List<Integer>> descList) {
        List<Integer> minList = new ArrayList<>();
        descList.forEach(integerList -> {
            minList.add(Collections.min(integerList));
        });

        return Collections.min(minList);
    }

    /**
     * find PI max value
     *
     * @param descList
     * @return
     */
    private static int findPiMax(List<List<Integer>> descList) {
        List<Integer> maxList = new ArrayList<>();
        descList.forEach(integerList -> {
            maxList.add(Collections.max(integerList));
        });

        return Collections.max(maxList);
    }

    /**
     * concat many small List into a big one
     *
     * @param integerList
     * @return
     */
    private static List<Integer> concatSmallList(List<List<Integer>> integerList) {
        List<Integer> integers = new ArrayList<>();
        integerList.forEach(list -> {
            list.forEach(integer -> {
                integers.add(integer);
            });
        });

        return integers;
    }

    /**
     * reverse a list given its end point values
     *
     * @param arr
     * @param val1
     * @param val2
     * @return
     */
    private static List<Integer> reverse(List<Integer> arr, int val1, int val2) {
        int startIndex = 0, endIndex = 0;
        if (arr.indexOf(val1) > arr.indexOf(val2)) {
            startIndex = arr.indexOf(val2);
            endIndex = arr.indexOf(val1);
            List<Integer> subList = arr.subList(startIndex + 1, endIndex + 1);
            Collections.reverse(subList);
            for (int i = startIndex + 1; i < endIndex; i++) {
                arr.set(i, subList.get(i - startIndex - 1));
            }
        } else {
            startIndex = arr.indexOf(val1);
            endIndex = arr.indexOf(val2);
            List<Integer> subList = arr.subList(startIndex + 1, endIndex + 1);
            Collections.reverse(subList);
            for (int i = startIndex + 1; i <= endIndex; i++) {
                arr.set(i, subList.get(i - startIndex - 1));
            }
        }

        return arr;
    }

    /**
     * compute the number of a breakpoint in a permutation
     *
     * @param integerList
     * @return
     */
    protected static int computeBreakPointNum(List<Integer> integerList) {
        int breakPointNum = 2;
        if (isIncreasing(integerList))
            breakPointNum = 0;
        int currentIndex = 0;
        for (int i = 0; i < integerList.size() - 1; i++) {
            if (Math.abs(integerList.get(currentIndex).intValue() - integerList.get(currentIndex + 1).intValue()) <= 1) {
                currentIndex++;
            } else {
                breakPointNum++;
                currentIndex++;
            }
        }

        return breakPointNum;
    }

    /**
     * find all strip in a permutation
     *
     * @param integerList
     * @return
     */
    protected static List<List<Integer>> getStrip(List<Integer> integerList) {
        List<List<Integer>> breakPointList = new ArrayList<>();
        List<Integer> breakPointSubList = new ArrayList<>();
        for (int i = 0; i < integerList.size() - 1; i++) {
            if (Math.abs(integerList.get(i).intValue() - integerList.get(i + 1).intValue()) <= 1) {
                breakPointSubList.add(integerList.get(i).intValue());
            } else {
                breakPointSubList.add(integerList.get(i).intValue());
                breakPointList.add(breakPointSubList);
                breakPointSubList = new ArrayList<>();
            }
        }

        if (breakPointSubList.size() > 0)
            breakPointList.add(breakPointSubList);

        List<Integer> lastSub = breakPointList.get(breakPointList.size() - 1);
        if (Math.abs(integerList.get(integerList.size() - 1) - lastSub.get(lastSub.size() - 1)) <= 1) {
            lastSub.add(integerList.get(integerList.size() - 1));
            breakPointList.set(breakPointList.size() - 1, lastSub);
        } else {
            breakPointSubList = new ArrayList<>();
            breakPointSubList.add(integerList.get(integerList.size() - 1));
            breakPointList.add(breakPointSubList);
        }

        return breakPointList;
    }

    /**
     * judge whether the strip is increasing or decreasing;
     * true for increasing, false for decreasing
     *
     * @param strip
     * @return
     */
    protected static boolean isIncreasing(List<Integer> strip) {
        boolean flag;
        if (strip.size() == 1) {
            if (strip.get(0).intValue() == 1)
                flag = true;
            else
                flag = false;
        } else {
            if (strip.get(strip.size() - 1).intValue() == strip.size())
                flag = true;
            else {
                if (strip.get(0).intValue() < strip.get(strip.size() - 1).intValue()) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        }

        return flag;
    }

    private static List<Integer> readPermutationFromTxt(String txtPath) throws IOException {
        List<String> stringList = Files.readAllLines(Paths.get(txtPath));
        String[] strings = stringList.get(1).split(" ");
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            result.add(Integer.parseInt(strings[i].trim()));
        }

        return result;
    }

    public static void printList(List<Integer> list) {
        System.out.print("(\t");
        list.forEach(integer -> {
            System.out.print(integer + "\t");
        });
        System.out.println(")");
    }

    private static void output(List<Integer> integerList, String filepath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        integerList.forEach(integer -> {
            stringBuilder.append(integer).append(" ");
        });
        Files.write(Paths.get(filepath), stringBuilder.toString().getBytes());
    }

    public static void main(String[] args) throws IOException {
        String filePath = "E:/MyDocument/BioAlgorithm/ass/input2.txt";
        List<Integer> integerList = readPermutationFromTxt(filePath);
        long startTime = System.currentTimeMillis();
        twoApproximation(1, integerList);
//        fourApproximation(1, integerList);
        long endTime = System.currentTimeMillis();
        System.out.println("It takes " + (endTime - startTime) + " ms in all!");
    }

}