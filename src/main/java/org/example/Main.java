package org.example;


import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {


        final String oldEvent = "Auth_First_Launch,Auth_App_Open,Auth_Signup_completed,Nav_Product_shelf,Purchase_Flat_Start,Purchase_Flat_To_Step_Address,Purchase_Flat_To_Step_Risk,Purchase_Flat_To_Step_Coverage,Purchase_Flat_To_Step_Period,Purchase_Flat_Payment_Success,Purchase_Flat_Payment_Decline,Purchase_House_Start,Purchase_House_To_Step_Address,Purchase_House_To_Step_Risk,Purchase_House_To_Step_Coverage,Purchase_House_To_Step_Period,Purchase_House_To_Step_Yourdata,Purchase_House_Payment_Success,Purchase_House_Payment_Decline,Purchase_NS_Start,Purchase_NS_To_Step_Count,Purchase_NS_To_Step_Risk,Purchase_NS_To_Step_Coverage,Purchase_NS_To_Step_Period,Purchase_NS_To_Step_YourData,Purchase_NS_Payment_Decline,Purchase_NS_Payment_Success,Purchase_Pet_Start,Purchase_Pet_To_Step_PetType,Purchase_Pet_To_Step_Risk,Purchase_Pet_To_Step_Coverage,Purchase_Pet_To_Step_Period,Purchase_Pet_Payment_Success,Purchase_Pet_Payment_Decline,Purchase_Sport_Start,Purchase_Sport_To_Step_Count,Purchase_Sport_To_Step_Type,Purchase_Sport_To_Step_Coverage,Purchase_Sport_To_Step_Pay,Purchase_Sport_Payment_Decline,Purchase_Sport_Payment_Success,Purchase_Mite_Start,Purchase_Mite_To_Step_Price,Purchase_Mite_To_Step_Pay,Purchase_Mite_Payment_Success,Purchase_Mite_Payment_Decline";
        final String newEvent = "Purchase_Flat_Start_Open,Purchase_Flat_Yourdata_Open,Purchase_Flat_Map_Open,Purchase_Flat_Address_Open,Purchase_Flat_Risk_Open,Purchase_Flat_Addrisk_Open,Purchase_Flat_Cover_Open,Purchase_Flat_Period_Open,Purchase_Flat_Confirm_Open,Purchase_Flat_Docs_Open,Purchase_Flat_Pay_Open,Purchase_House_Start_Open,Purchase_House_Yourdata_Open,Purchase_House_Map_Open,Purchase_House_Address_Open,Purchase_House_Buildtype_Open,Purchase_House_Risk_Open,Purchase_House_Addrisk_Open,Purchase_House_Cover_Open,Purchase_House_Period_Open,Purchase_House_Confirm_Open,Purchase_House_Docs_Open,Purchase_House_Pay_Open,Purchase_Sport_Start_Open,Purchase_Sport_Yourdata_Open,Purchase_Sport_Count_Open,Purchase_Sport_Days_Open,Purchase_Sport_FastDays_Tap,Purchase_Sport_Types_Open,Purchase_Sport_Search_Tap,Purchase_Sport_Cover_Open,Purchase_Sport_Data_Open,Purchase_Sport_Me_Tap,Purchase_Sport_Confirm_Open,Purchase_Sport_Docs_Open,Purchase_Sport_Pay_Open,Purchase_Mite_Start_Open,Purchase_Mite_Type_List,Purchase_Mite_Yourdata_Open,Purchase_Mite_Price_Open,Purchase_Mite_PriceList_Tap,Purchase_Mite_Data_Open,Purchase_Mite_Me_Tap,Purchase_Mite_Confirm_Open,Purchase_Mite_Docs_Open,Purchase_Mite_Pay_Open,Purchase_Pet_Start_Open,Purchase_Pet_Yourdata_Open,Purchase_Pet_PetType_Open,Purchase_Pet_Risk_Open,Purchase_Pet_Addrisk_Open,Purchase_Pet_Cover_Open,Purchase_Pet_Period_Open,Purchase_Pet_Confirm_Open,Purchase_Pet_Docs_Open,Purchase_Pet_Pay_Open,Purchase_NS_Start_Open,Purchase_NS_Yourdata_Open,Purchase_NS_Count_Open,Purchase_NS_Risk_Open,Purchase_NS_Addrisk_Open,Purchase_NS_Cover_Open,Purchase_NS_Period_Open,Purchase_NS_Data_Open,Purchase_NS_Me_Tap,Purchase_NS_Confirm_Open,Purchase_NS_Docs_Open,Purchase_NS_Pay_Open,Purchase_VZR_Start_Open,Purchase_VZR_Yourdata_Open";

        List<String> oldList = List.of(oldEvent.split(","));
        List<String> newList = List.of(newEvent.split(","));

        List<String> result = new ArrayList<>();


        try {
            result = Files.readAllLines(Path.of(getLastFileCSV().getPath()))
                    .stream()
                    .map(line -> Arrays.asList(line.split(",")))
                    .flatMap(List::stream)
                    .map(str -> str.replaceAll(("^\"|\"$"), ""))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Список старых событий : " + oldList);
        System.out.println(notHeppendEvent(oldList, result));
        System.out.println("=======================================");

        System.out.println("Cписок новых событий : " + newList);
        System.out.println(notHeppendEvent(newList, result));

        System.out.println("=======================================");
        System.out.println("События отсутствуют");
        System.out.println("Старые:");
        System.out.println(notHeppendEvent(oldList, result));
        System.out.println("=======================================");
        System.out.println("События отсутствуют");
        System.out.println("Новые:");
        System.out.println(notHeppendEvent(newList, result));
    }

    public static List<String> notHeppendEvent(List<String> ourEvent, List<String> heppendEvent) {

        List<String> result = new ArrayList<>();
        for (String ourString : ourEvent) {
            if (!heppendEvent.contains(ourString)) {
                result.add(ourString);
            }
        }
        return result;
    }

    public static File getLastFileCSV() {
        String directoryPath = "."; // Текущая директория
        File dir = new File(directoryPath);
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                // Возвращаем true только для файлов с расширением .csv
                return file.isFile() && file.getName().toLowerCase().endsWith(".csv");
            }
        });

        if (files == null || files.length == 0) {
            System.out.println("Файлы .cvt не найдены в директории.");
        }

        File latestFile = files[0];
        for (File file : files) {
            if (file.lastModified() >= latestFile.lastModified()) {
                latestFile = file;
            }
        }
        System.out.println("---===ИМЯ ФАЙЛА который проверяем " + latestFile.toString() + "===---");

        return latestFile;
    }

}