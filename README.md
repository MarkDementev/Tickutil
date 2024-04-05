# Overview

Usage: tickutil [-hV] [-c=cities] relative file path
Utility calculates 2 type of values in JSON file with array of air tickets.     
You can add city codes to specify the calculation.
By default, utility works with Владивосток (VVO) and Тель-Авив (TLV) cities.
Calculates:
1) Minimum flight time in minutes between cities for each air carrier.     
2) The difference between the average and the median prices between cities.

      relative file path   file path to JSON file to work with
  -c, --cities=cities      two cities codes separated by whitespace to work
                             with [default:VVO TLV]
  -h, --help               Show this help message and exit.
  -V, --version            Print version information and exit.
