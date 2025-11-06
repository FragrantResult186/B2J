package fragrant.core.version;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public enum MCVersion {
    // 2025
    //v1_21_130("1.21.130", 1, 21130), // Upcoming "Holiday 2025" - Mounts of Mayhem
    v1_21_120("1.21.120", 1, 21120), // October 2025
    v1_21_114("1.21.114", 1, 21114), // October 2025
    v1_21_113("1.21.113", 1, 21113), // October 2025
    v1_21_112("1.21.112", 1, 21112), // October 2025
    v1_21_111("1.21.111", 1, 21111), // September 2025 - The Copper Age
    v1_21_101("1.21.101", 1, 21101), // August 2025
    v1_21_100("1.21.100", 1, 21100), // August 2025
    v1_21_94("1.21.94", 1, 21094), // July 2025
    v1_21_93("1.21.93", 1, 21093), // July 2025
    v1_21_92("1.21.92", 1, 21092), // June 2025
    v1_21_91("1.21.91", 1, 21091), // June 2025
    v1_21_90("1.21.90", 1, 21090), // June 2025 - Chase the Skies
    v1_21_84("1.21.84", 1, 21084), // June 2025
    v1_21_82("1.21.82", 1, 21082), // May 2025
    v1_21_81("1.21.81", 1, 21081), // May 2025
    v1_21_80("1.21.80", 1, 21080), // May 2025
    v1_21_73("1.21.73", 1, 21073), // April 2025
    v1_21_72("1.21.72", 1, 21072), // April 2025
    v1_21_71("1.21.71", 1, 21071), // March 2025
    v1_21_70("1.21.70", 1, 21070), // March 2025 - Spring to Life
    v1_21_62("1.21.62", 1, 21062), // February 2025
    v1_21_61("1.21.61", 1, 21061), // February 2025
    v1_21_60("1.21.60", 1, 21060), // February 2025
    v1_21_51("1.21.51", 1, 21051), // December 2024
    v1_21_50("1.21.50", 1, 21050), // December 2024 - The Garden Awakens
    v1_21_44("1.21.44", 1, 21044), // October-November 2024
    v1_21_43("1.21.43", 1, 21043), // October 2024
    v1_21_42("1.21.42", 1, 21042), // October 2024
    v1_21_41("1.21.41", 1, 21041), // October 2024
    v1_21_40("1.21.40", 1, 21040), // September 2024 - Bundles of Bravery
    v1_21_31("1.21.31", 1, 21031), // September 2024
    v1_21_30("1.21.30", 1, 21030), // September 2024
    v1_21_23("1.21.23", 1, 21023), // September 2024
    v1_21_22("1.21.22", 1, 21022), // August 2024
    v1_21_21("1.21.21", 1, 21021), // August 2024
    v1_21_20("1.21.20", 1, 21020), // August 2024
    v1_21_3("1.21.3", 1, 21003), // July 2024
    v1_21_2("1.21.2", 1, 21002), // July 2024
    v1_21_1("1.21.1", 1, 21001), // June 2024
    // 2024
    v1_21_0("1.21.0", 1, 21000), // June 13, 2024 - Tricky Trials
    v1_20_81("1.20.81", 1, 20081), // April-May 2024
    v1_20_80("1.20.80", 1, 20080), // April 2024 - Armored Paws
    v1_20_73("1.20.73", 1, 20073), // April 2024
    v1_20_72("1.20.72", 1, 20072), // March 2024
    v1_20_71("1.20.71", 1, 20071), // March 2024
    v1_20_70("1.20.70", 1, 20070), // January 2024
    v1_20_62("1.20.62", 1, 20062), // February 2024
    v1_20_61("1.20.61", 1, 20061), // February 2024
    v1_20_60("1.20.60", 1, 20060), // February 2024
    v1_20_51("1.20.51", 1, 20051), // December 2023
    v1_20_50("1.20.50", 1, 20050), // December 2023 - Bats and Pots
    v1_20_41("1.20.41", 1, 20041), // November 2023
    v1_20_40("1.20.40", 1, 20040), // October 2024
    v1_20_32("1.20.32", 1, 20032), // October 2023
    v1_20_31("1.20.31", 1, 20031), // September 2023
    v1_20_30("1.20.30", 1, 20030), // September 2023
    v1_20_15("1.20.15", 1, 20015), // August 2023
    v1_20_14("1.20.14", 1, 20014), // August 2023
    v1_20_13("1.20.13", 1, 20013), // July 2023
    v1_20_12("1.20.12", 1, 20012), // July 2023
    v1_20_10("1.20.10", 1, 20010), // July 2023
    v1_20_1("1.20.1", 1, 20001), // June 2023
    // 2023
    v1_20_0("1.20.0", 1, 20000), // June 7, 2023 - Trails & Tales
    v1_19_83("1.19.83", 1, 19083), // May 2023
    v1_19_81("1.19.81", 1, 19081), // April 2023
    v1_19_80("1.19.80", 1, 19080), // April 2023
    v1_19_73("1.19.73", 1, 19073), // March 2023
    v1_19_72("1.19.72", 1, 19072), // March 2023
    v1_19_71("1.19.71", 1, 19071), // March 2023
    v1_19_70("1.19.70", 1, 19070), // March 2023
    v1_19_63("1.19.63", 1, 19063), // February 2023
    v1_19_62("1.19.62", 1, 19062), // February 2023
    v1_19_60("1.19.60", 1, 19060), // February 2023
    v1_19_51("1.19.51", 1, 19051), // December 2022
    v1_19_50("1.19.50", 1, 19050), // November 2022
    v1_19_41("1.19.41", 1, 19041), // November 2022
    v1_19_40("1.19.40", 1, 19040), // October 2022
    v1_19_31("1.19.31", 1, 19031), // October 2022
    v1_19_30("1.19.30", 1, 19030), // September 2022
    v1_19_22("1.19.22", 1, 19022), // September 2022
    v1_19_21("1.19.21", 1, 19021), // August 2022
    v1_19_20("1.19.20", 1, 19020), // August 2022
    v1_19_11("1.19.11", 1, 19011), // July 2022
    v1_19_10("1.19.10", 1, 19010), // July 2022
    v1_19_2("1.19.2", 1, 19002), // June 2022
    // 2022
    v1_19_0("1.19.0", 1, 19000), // June 7, 2022 - The Wild Update
    v1_18_33("1.18.33", 1, 18033), // May 2022
    v1_18_32("1.18.32", 1, 18032), // May 2022
    v1_18_31("1.18.31", 1, 18031), // April 2022
    v1_18_30("1.18.30", 1, 18030), // April 2022
    v1_18_12("1.18.12", 1, 18012), // February 2022
    v1_18_11("1.18.11", 1, 18011), // February 2022
    v1_18_10("1.18.10", 1, 18010), // February 2022
    v1_18_2("1.18.2", 1, 18002), // December 2021
    v1_18_1("1.18.1", 1, 18001), // December 2021
    // 2021
    v1_18_0("1.18.0", 1, 18000), // November 30, 2021 - Caves & Cliffs Part II
    v1_17_41("1.17.41", 1, 17041), // October 2021
    v1_17_40("1.17.40", 1, 17040), // October 2021
    v1_17_34("1.17.34", 1, 17034), // October 2021
    v1_17_33("1.17.33", 1, 17033), // October 2021
    v1_17_32("1.17.32", 1, 17032), // September 2021
    v1_17_30("1.17.30", 1, 17030), // September 2021
    v1_17_11("1.17.11", 1, 17011), // August 2021
    v1_17_10("1.17.10", 1, 17010), // July 2021
    v1_17_2("1.17.2", 1, 17002), // June 2021
    v1_17_1("1.17.1", 1, 17001), // June 2021
    v1_17_0("1.17.0", 1, 17000), // June 8, 2021 - Caves & Cliffs Part I
    v1_16_221("1.16.221", 1, 16221), // April 2021
    v1_16_220("1.16.220", 1, 16220), // April 2021
    v1_16_210("1.16.210", 1, 16210), // March 2021
    v1_16_201("1.16.201", 1, 16201), // December 2020
    v1_16_200("1.16.200", 1, 16200), // December 2020
    v1_16_101("1.16.101", 1, 16101), // November 2020
    v1_16_100("1.16.100", 1, 16100), // November 2020
    v1_16_61("1.16.61", 1, 16061), // September 2020
    v1_16_60("1.16.60", 1, 16060), // September 2020
    v1_16_50("1.16.50", 1, 16050), // September 2020
    v1_16_42("1.16.42", 1, 16042), // September 2020
    v1_16_40("1.16.40", 1, 16040), // September 2020
    v1_16_21("1.16.21", 1, 16021), // August 2020
    v1_16_20("1.16.20", 1, 16020), // August 2020
    v1_16_10("1.16.10", 1, 16010), // July 2020
    v1_16_1_04("1.16.1.04", 1, 16104), // July 2020
    v1_16_1_03("1.16.1.03", 1, 16103), // July 2020
    v1_16_1("1.16.1", 1, 16001), // June-July 2020
    // 2020
    v1_16_0("1.16.0", 1, 16000), // June 23, 2020 - Nether Update
    v1_14_60("1.14.60", 1, 14060), // April 2020
    v1_14_41("1.14.41", 1, 14041), // March 2020
    v1_14_30("1.14.30", 1, 14030), // February 2020
    v1_14_21("1.14.21", 1, 14021), // January 2020
    v1_14_20("1.14.20", 1, 14020), // January 2020
    v1_14_1("1.14.1", 1, 14001), // December 2019
    v1_14_0_12("1.14.0.12", 1, 14012), // December 2019
    v1_14_0("1.14.0", 1, 14000), // December 10, 2019 - Buzzy Bees
    v1_13_3("1.13.3", 1, 13003), // November 2019
    v1_13_2("1.13.2", 1, 13002), // November 2019
    v1_13_1("1.13.1", 1, 13001), // November 2019
    v1_13_0("1.13.0", 1, 13000), // October 29, 2019
    v1_12_1("1.12.1", 1, 12001), // September 2019
    v1_12_0("1.12.0", 1, 12000), // July 9, 2019
    v1_11_4("1.11.4", 1, 11004), // May 2019
    v1_11_3("1.11.3", 1, 11003), // May 2019
    v1_11_2("1.11.2", 1, 11002), // May 2019
    v1_11_1("1.11.1", 1, 11001), // April-May 2019
    v1_11_0("1.11.0", 1, 11000), // April 23, 2019 - Village & Pillage
    v1_10_1("1.10.1", 1, 10001), // March 2019
    // 2019
    v1_10_0("1.10.0", 1, 10000), // March 19, 2019 - Texture Update
    v1_9_0("1.9.0", 1, 9000), // February 5, 2019
    v1_8_1("1.8.1", 1, 8001), // December 2018-January 2019
    v1_8_0("1.8.0", 1, 8000), // December 11, 2018
    v1_7_1("1.7.1", 1, 7001), // November 2018
    v1_7_0("1.7.0", 1, 7000), // October 16, 2018
    v1_6_2("1.6.2", 1, 6002), // October 2018
    v1_6_1("1.6.1", 1, 6001), // September 2018
    v1_6_0("1.6.0", 1, 6000), // August 28, 2018
    v1_5_3("1.5.3", 1, 5003), // August 2018
    v1_5_2("1.5.2", 1, 5002), // July 2018
    v1_5_1("1.5.1", 1, 5001), // July 2018
    v1_5_0("1.5.0", 1, 5000), // July 10, 2018 - Update Aquatic (Phase Two)
    v1_4_4("1.4.4", 1, 4004), // June 2018
    v1_4_3("1.4.3", 1, 4003), // June 2018
    v1_4_2("1.4.2", 1, 4002), // May 2018
    v1_4_1("1.4.1", 1, 4001), // May-June 2018
    v1_4_0("1.4.0", 1, 4000), // May 16, 2018 - Update Aquatic (Phase One)
    v1_2_13_60("1.2.13.60", 1, 21360), // April 2018
    v1_2_20("1.2.20", 1, 2020), // April 2018 (not in Wiki table)
    v1_2_15("1.2.15", 1, 2015), // April 2018
    v1_2_14("1.2.14", 1, 2014), // April 2018
    v1_2_13("1.2.13", 1, 2013), // April 2018
    v1_2_11("1.2.11", 1, 2011), // March 2018
    v1_2_10("1.2.10", 1, 2010), // February 2018
    v1_2_9("1.2.9", 1, 2009), // January 2018
    v1_2_8("1.2.8", 1, 2008), // December 2017
    v1_2_7("1.2.7", 1, 2007), // December 2017
    v1_2_6_1("1.2.6.1", 1, 2061), // December 2017
    v1_2_6("1.2.6", 1, 2006), // December 2017
    v1_2_5("1.2.5", 1, 2005), // November 2017
    v1_2_3("1.2.3", 1, 2003), // October 2017
    v1_2_2("1.2.2", 1, 2002), // October 2017
    v1_2_1("1.2.1", 1, 2001), // September 2017
    v1_2_0("1.2.0", 1, 2000), // September 20, 2017 - Better Together Update
    // 2017
    v1_1_7("1.1.7", 1, 1007), // September 2017
    v1_1_5("1.1.5", 1, 1005), // August 2017
    v1_1_4("1.1.4", 1, 1004), // July 2017
    v1_1_3("1.1.3", 1, 1003), // July 2017
    v1_1_2("1.1.2", 1, 1002), // June 2017
    v1_1_1("1.1.1", 1, 1001), // June 2017
    v1_1_0("1.1.0", 1, 1000), // June 1, 2017 - Discovery Update
    v1_0_9("1.0.9", 1, 9), // May 2017
    v1_0_8("1.0.8", 1, 8), // May 2017
    v1_0_7("1.0.7", 1, 7), // April 2017
    v1_0_6("1.0.6", 1, 6), // April 2017
    v1_0_5("1.0.5", 1, 5), // March 2017
    v1_0_4("1.0.4", 1, 4), // March 2017
    v1_0_3("1.0.3", 1, 3), // February 2017
    v1_0_2("1.0.2", 1, 2), // January 2017
    v1_0_1("1.0.1", 1, 1), // January 2017
    v1_0_0("1.0.0", 1, 0), // December 19, 2016 - Ender Update
    // Alpha
    v0_17_0("0.17.0", 0, 17), // November 2016
    v0_16_2("0.16.2", 0, 162), // November 2016
    v0_16_1("0.16.1", 0, 161), // November 2016
    v0_16_0("0.16.0", 0, 16), // October 21, 2016 - Boss Update
    v0_15_10("0.15.10", 0, 1510), // October 2016
    v0_15_9("0.15.9", 0, 159), // September 2016
    v0_15_8("0.15.8", 0, 158), // September 2016
    v0_15_7("0.15.7", 0, 157), // August-September 2016
    v0_15_6("0.15.6", 0, 156), // August 2016
    v0_15_5("0.15.5", 0, 155), // July-August 2016
    v0_15_4("0.15.4", 0, 154), // July 2016
    v0_15_3("0.15.3", 0, 153), // July 2016
    v0_15_2("0.15.2", 0, 152), // July 2016
    v0_15_1("0.15.1", 0, 151), // June 2016
    v0_15_0("0.15.0", 0, 15), // June 13, 2016 - Friendly Update
    v0_14_3("0.14.3", 0, 143), // May 2016
    v0_14_2("0.14.2", 0, 142), // April 2016
    v0_14_1("0.14.1", 0, 141), // April 2016
    v0_14_0("0.14.0", 0, 14), // February 18, 2016 - Overworld Update
    v0_13_2("0.13.2", 0, 132), // February 2016
    v0_13_1("0.13.1", 0, 131), // December 2015
    v0_13_0("0.13.0", 0, 13), // November 19, 2015
    v0_12_3("0.12.3", 0, 123), // October 2015
    v0_12_2("0.12.2", 0, 122), // October 2015
    v0_12_1("0.12.1", 0, 121), // September 2015
    v0_12_0_1("0.12.0.1", 0, 1201), // August 2015
    v0_12_0("0.12.0", 0, 12), // July-September 2015
    v0_11_2("0.11.2", 0, 112), // June 2015
    v0_11_1("0.11.1", 0, 111), // June 2015
    v0_11_0("0.11.0", 0, 11), // June 4, 2015
    v0_10_5("0.10.5", 0, 105), // January 2015
    v0_10_4("0.10.4", 0, 104), // December 2014
    v0_10_3("0.10.3", 0, 103), // November 2014
    v0_10_2("0.10.2", 0, 102), // November 2014
    v0_10_1("0.10.1", 0, 101), // November 2014
    v0_10_0("0.10.0", 0, 10), // November 18, 2014
    v0_9_5("0.9.5", 0, 95), // July 2014
    v0_9_4("0.9.4", 0, 94), // July 2014
    v0_9_3("0.9.3", 0, 93), // July 2014
    v0_9_2("0.9.2", 0, 92), // July 2014
    v0_9_1("0.9.1", 0, 91), // July 2014
    v0_9_0("0.9.0", 0, 9), // July 10, 2014
    v0_8_2("0.8.2", 0, 82), // April 2014
    v0_8_1("0.8.1", 0, 81), // December 2013
    v0_8_0("0.8.0", 0, 8), // December 12, 2013
    v0_7_6("0.7.6", 0, 76), // October 2013
    v0_7_5("0.7.5", 0, 75), // September 2013
    v0_7_4("0.7.4", 0, 74), // September 2013
    v0_7_3("0.7.3", 0, 73), // August 2013
    v0_7_2("0.7.2", 0, 72), // July 2013
    v0_7_1("0.7.1", 0, 71), // June 2013
    v0_7_0("0.7.0", 0, 7), // June 5, 2013
    v0_6_1("0.6.1", 0, 61), // January-February 2013
    v0_6_0("0.6.0", 0, 6), // January 30, 2013
    v0_5_0("0.5.0", 0, 5), // November 15, 2012
    v0_4_0_rev3("0.4.0 rev 3", 0, 403), // September 2012
    v0_4_0_rev2("0.4.0 rev 2", 0, 402), // September 2012
    v0_4_0("0.4.0", 0, 4), // September 13, 2012
    v0_3_3("0.3.3", 0, 33), // August 2012
    v0_3_2("0.3.2", 0, 32), // July 2012
    v0_3_0("0.3.0", 0, 3), // April 23, 2012
    v0_2_2("0.2.2", 0, 22), // March 2012
    v0_2_1_alpha2("0.2.1 alpha2", 0, 212), // March 2012
    v0_2_1("0.2.1", 0, 21), // March 2012
    v0_2_0("0.2.0", 0, 2), // February 11, 2012
    v0_1_3("0.1.3", 0, 13), // December 2011
    v0_1_2("0.1.2", 0, 12), // October-November 2011
    v0_1_1("0.1.1", 0, 11), // October 2011
    v0_1_0("0.1.0", 0, 1), // August 16, 2011
    ;

    public final String name;
    public final int major;
    public final int version;
    private static final Map<String, MCVersion> STRING_TO_VERSION = Arrays.stream(values())
            .collect(Collectors.toMap(MCVersion::toString, o -> o));

    MCVersion(String name, int major, int version) {
        this.name = name;
        this.major = major;
        this.version = version;
    }

    public static MCVersion fromString(String name) {
        return STRING_TO_VERSION.get(name);
    }

    public int getMajor() {
        return this.major;
    }

    public int getVersion() {
        return this.version;
    }

    public static MCVersion latest() {
        return values()[0];
    }

    public static MCVersion oldest() {
        return values()[values().length - 1];
    }

    public boolean isNewerThan(MCVersion v) {
        return this.compareTo(v) < 0;
    }

    public boolean isNewerOrEqualTo(MCVersion v) {
        return this.compareTo(v) <= 0;
    }

    public boolean isOlderThan(MCVersion v) {
        return this.compareTo(v) > 0;
    }

    public boolean isOlderOrEqualTo(MCVersion v) {
        return this.compareTo(v) >= 0;
    }

    public boolean isEqualTo(MCVersion v) {
        return this.compareTo(v) == 0;
    }

    public boolean isBetween(MCVersion a, MCVersion b) {
        return this.compareTo(a) <= 0 && this.compareTo(b) >= 0;
    }

    public boolean isBetweenExclusive(MCVersion a, MCVersion b) {
        return this.compareTo(a) < 0 && this.compareTo(b) > 0;
    }

    public MCVersion newer() {
        int i = this.ordinal() - 1;
        return i < 0 ? null : values()[i];
    }

    public MCVersion older() {
        int i = this.ordinal() + 1;
        return i >= values().length ? null : values()[i];
    }

    public boolean isRelease() {
        return isRelease(this);
    }

    public static boolean isRelease(MCVersion version) {
        return version.major >= 1;
    }

    public boolean hasNetherUpdate() {
        return isNewerOrEqualTo(v1_16_0);
    }

    public boolean hasCavesAndCliffs() {
        return isNewerOrEqualTo(v1_17_0);
    }

    public boolean hasWildUpdate() {
        return isNewerOrEqualTo(v1_19_0);
    }

    public boolean hasTrialsAndTales() {
        return isNewerOrEqualTo(v1_20_0);
    }

    public boolean hasTrickyTrials() {
        return isNewerOrEqualTo(v1_21_0);
    }

    @Override
    public String toString() {
        return this.name;
    }

}