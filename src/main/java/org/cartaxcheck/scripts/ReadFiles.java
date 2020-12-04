package org.cartaxcheck.scripts;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.cartaxcheck.scripts.Constants.REGISTRATION_PATTERN;

/**
 * Class to read data from the input file usng the regular expression and extract registration numbers
 */
public class ReadFiles
{
    private static final Pattern r = Pattern.compile(REGISTRATION_PATTERN);

    public static List<String> readInputFile() throws IOException {
        //read registration numbers
        List<String> registrationNumbers = new ArrayList<String>();

        URL resource = ReadFiles.class.getClassLoader().getResource("car_input.txt");
        File file = new File(resource.getFile());

        try (FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher  = r.matcher(line);
                if (matcher.find()) {
                    registrationNumbers.add(matcher.group());
                }
            }
            return registrationNumbers;
        }
     }

        public static List<VehicleInfo> readOutputFile () throws IOException
        {
            List<VehicleInfo> vehicleInfoList = new ArrayList<>();
            vehicleInfoList.add(new VehicleInfo("SG18HTN", "Volkswagen", "Golf Se Navigation Tsi Evo", "White", "2018"));
            vehicleInfoList.add(new VehicleInfo("DN09HRM", "BMW", "320D Se", "Black", "2009"));
            vehicleInfoList.add(new VehicleInfo("BW57BOF", "Toyota", "Yaris T2", "Black", "2010"));
            vehicleInfoList.add(new VehicleInfo("KT17DLX", "Skoda", "Superb Sportline Tdi S-A", "White", "2017"));

            return vehicleInfoList;
        }
    }