# Overview

Utility calculates 2 type of values in JSON file with array of air tickets.     
You can add city codes to specify the calculation.
By default, utility works with Владивосток (VVO) and Тель-Авив (TLV) cities.

## JSON file format structure

The keys below are necessary, but the values are invented:

```sh
{
  "tickets": [{
    "origin": "VVO",
    "origin_name": "Владивосток",
    "destination": "TLV",
    "destination_name": "Тель-Авив",
    "departure_date": "12.05.18",
    "departure_time": "16:20",
    "arrival_date": "12.05.18",
    "arrival_time": "22:10",
    "carrier": "TK",
    "stops": 3,
    "price": 12400
  }]
}
```

## Utility usage

Usage: tickutil [-hV] [-c=cities] relative file path

Calculates:
1) Minimum flight time in minutes between cities for each air carrier.     
2) The difference between the average and the median prices between cities.

relative file path   file path to JSON file to work with

-c, --cities=cities      two cities codes separated by whitespace to workwith [default:VVO TLV]

-h, --help               Show this help message and exit.

-V, --version            Print version information and exit.
